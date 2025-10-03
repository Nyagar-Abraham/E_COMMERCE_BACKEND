package org.abraham.user_service.handlers;

import dev.samstevens.totp.exceptions.QrGenerationException;
import io.grpc.Status;
import lombok.AllArgsConstructor;

import org.abraham.constants.Constants;
import org.abraham.models.*;
import org.abraham.user_service.auth.jwt.JwtUtil;
import org.abraham.user_service.auth.mfa.TotpManagerImpl;
import org.abraham.commondtos.UserStatus;
import org.abraham.user_service.dto.kafkamessages.UserCreatedEvent;
import org.abraham.user_service.entity.EmailVerificationToken;
import org.abraham.user_service.entity.PasswordResetToken;
import org.abraham.user_service.entity.UserEntity;
import org.abraham.user_service.exceptions.TokenExpiredException;
import org.abraham.user_service.mapper.UserMapper;
import org.abraham.user_service.repository.EmailVerificationTokenRepository;
import org.abraham.user_service.repository.PasswordResetTokenRepository;
import org.abraham.user_service.repository.UserRepository;
import org.abraham.user_service.service.MailService;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.AbstractMap;
import java.util.Objects;
import java.util.UUID;

//Handles User Login, Registration, PasswordReset, Email And phone Verification.
@Service
@AllArgsConstructor
public class AuthHandler {
    private static final LocalDateTime EMAIL_TOKEN_EXPIRATION = LocalDateTime.now(ZoneId.of("Africa/Nairobi")).plusMinutes(30);
    private static final Logger log = LoggerFactory.getLogger(AuthHandler.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final TotpManagerImpl totpManagerImpl;
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final MailService mailService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final KafkaSender<String, UserCreatedEvent> kafkaSender;


    /*
     Method (registerUser)
     Registers new user and sends an email to confirm their email
     */
    @Transactional
    public Mono<User> registerUser(Mono<RegisterUserRequest> request) {

        return request.flatMap(req -> {
            var userEntity = UserMapper.registerUserRequestToUserEntity(
                    req, passwordEncoder.encode(req.getPassword())
            );

            return userRepository.save(userEntity)
                    .flatMap(savedUser -> {
                        var secret = UUID.randomUUID().toString();
                        var verifyEmailToken = new EmailVerificationToken();
                        verifyEmailToken.setToken(secret);
                        verifyEmailToken.setUserId(savedUser.getId());
                        verifyEmailToken.setExpiresAt(EMAIL_TOKEN_EXPIRATION);

                        return emailVerificationTokenRepository.save(verifyEmailToken)
                                .thenReturn(new AbstractMap.SimpleEntry<>(savedUser, secret));
                    })
                    .flatMap(tuple -> {
                        UserEntity savedUser = tuple.getKey();
                        var secret = tuple.getValue();

                        // Send email asynchronously
                        return Mono.fromCallable(() -> {
                            var url = "http://localhost:8081/verify-email?token=%s".formatted(secret);
                            mailService.sendRegistrationEmail(savedUser, url, Constants.COMPANY_NAME, Constants.COMPANY_URL);
                            return savedUser;
                        }).subscribeOn(Schedulers.boundedElastic());

                    })
                    .flatMap(user -> userRepository.findById(user.getId()))
                    .flatMap(savedUser -> {
                        var userCreateEvent = new UserCreatedEvent(savedUser.getId(), savedUser.getEmail(), savedUser.getUsername(), savedUser.getStatus(), savedUser.getPhoneNumber());

                        var senderRecord = SenderRecord.create(
                                new ProducerRecord<>(Constants.USER_CREATED_TOPIC, userCreateEvent.id().toString(),userCreateEvent),
                                userCreateEvent.id().toString()
                        );

                        return kafkaSender.send(Mono.just(senderRecord))
                                .doOnError(e-> log.error("Send failed", e))
                                .doOnNext(r -> System.out.printf("Message #%s send response: %s\n", r.correlationMetadata(), r.recordMetadata()))
                                .then(Mono.just(savedUser));
                    })
                    .map(UserMapper::userEntityToUser);
        });
    }


    /*
 Method (login)
 Login existing and sends  mfa qrCode if Mfa is enabled
 */
    @Transactional
    public Mono<LoginResponse> login(LoginRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .flatMap(u -> {
                    if (u.getEnableMfa())
                        return userRepository.updateLastLoginAt(LocalDateTime.now(ZoneId.systemDefault()), u.getId())
                                .map(i -> u);
                    return updateUserLastLoginAtAndStatus(Mono.just(u));
                })
                .flatMap(this::buildLoginResponse);
    }

    //    ==============================================
    //    Update last log in and status if mfa is not set to true
    //    ==============================================
    private Mono<UserEntity> updateUserLastLoginAtAndStatus(Mono<UserEntity> userEntity) {
        var lastLoginAt = LocalDateTime.now(ZoneId.systemDefault());
        return userEntity
                .flatMap(u -> userRepository.updateLastLoginAtAndStatus(lastLoginAt, UserStatus.ACTIVE, u.getId()).map(i -> u));
    }

    /*
private Method (buildLoginResponse)
creates login response depending on whether mfa is enabled
*/
    private Mono<LoginResponse> buildLoginResponse(UserEntity u) {
        var accessToken = jwtUtil.generateAccessToken(u);
        var refreshToken = jwtUtil.generateRefreshToken(u);

        var responseBuilder = LoginResponse.newBuilder()
                .setAccessToken(accessToken)
                .setRefreshToken(refreshToken);

        if (!u.getEnableMfa() || Objects.nonNull(u.getMfaSecret())) {
            return Mono.just(responseBuilder.build());
        }


        // MFA is enabled - generate secret and QR code
        var secret = totpManagerImpl.generateSecret();

        return userRepository.updateMfaSecret(secret, u.getId())
                .map(i -> {
                    try {
                        var qrCodeImage = totpManagerImpl.generateCode(secret, u.getEmail());
                        return responseBuilder.setQrCodeImage(qrCodeImage).build();
                    } catch (QrGenerationException e) {
                        throw new RuntimeException("Failed to generate QR code: " + e.getMessage(), e);
                    }
                });
    }


    /*
Method (verifyMfaCode)
verifies the mfa code submitted by user
 */
    public Mono<VerifyMfaCodeResponse> verifyMfaCode(String code, UUID userId) {
        return userRepository.findById(userId)
                .flatMap(userEntity -> {
                    var isValid = totpManagerImpl.verifyCode(userEntity.getMfaSecret(), code);

                    var response = VerifyMfaCodeResponse.newBuilder();

                    if (!isValid)
                        return Mono.just(response.setMessage("Invalid MFA code").build());

                    return userRepository.updateStatus(UserStatus.ACTIVE, userEntity.getId())
                            .map(u -> response.setMessage("Success")
                                    .setUser(UserMapper.userEntityToUser(userEntity))
                                    .build());
                });
    }

    /*
Method (verifyEmailToken)
verifies the email token submitted by user
 */
    public Mono<VerifyEmailTokenResponse> verifyEmailToken(String token) {
        return emailVerificationTokenRepository.findByToken(token)
                .flatMap(emailVerificationToken -> {
                    if (emailVerificationToken.expired()) {
                        return emailVerificationTokenRepository.delete(emailVerificationToken)
                                .thenReturn(
                                        VerifyEmailTokenResponse.newBuilder()
                                                .setSuccess(false)
                                                .setMessage("Verification email token expired")
                                                .build()
                                );
                    }

                    // Mark the user's email as verified and delete EmailVerification Token
                    return userRepository.updateEmailVerified(true, emailVerificationToken.getUserId())
                            .flatMap(i -> emailVerificationTokenRepository.delete(emailVerificationToken))
                            .thenReturn(
                                    VerifyEmailTokenResponse.newBuilder()
                                            .setSuccess(true)
                                            .setMessage("Email successfully verified")
                                            .build()
                            );
                })
                .switchIfEmpty(Mono.just(
                        VerifyEmailTokenResponse.newBuilder()
                                .setSuccess(false)
                                .setMessage("Invalid or missing token")
                                .build()
                ));
    }

    //=======================================
//Method (forgotPassword)
//Send user a reset password email
// =====================================
    public Mono<ForgotPasswordResponse> forgotPassword(ForgotPasswordRequest request) {
        return userRepository.findByEmail(request.getEmail())
                .flatMap(userEntity -> {
                    var token = UUID.randomUUID().toString();
                    var resetPasswordUrl = "http://localhost:8081/reset-password?token=%s".formatted(token);

                    var passwordResetToken = new PasswordResetToken();
                    passwordResetToken.setToken(token);
                    passwordResetToken.setUserId(userEntity.getId());
                    passwordResetToken.setExpiresAt(LocalDateTime.now(ZoneId.systemDefault()).plusMinutes(30)); // example: 30 min expiry

                    return passwordResetTokenRepository.save(passwordResetToken)
                            .flatMap(savedToken ->
                                    Mono.fromCallable(() -> {
                                        mailService.sendPasswordResetEmail(
                                                userEntity,
                                                resetPasswordUrl,
                                                Constants.COMPANY_NAME,
                                                Constants.COMPANY_URL
                                        );
                                        return savedToken;
                                    }).subscribeOn(Schedulers.boundedElastic())
                            );
                })
                .map(token -> ForgotPasswordResponse.newBuilder()
                        .setMessage("Password reset email sent")
                        .setSuccess(true)
                        .build())
                .switchIfEmpty(Mono.just(
                        ForgotPasswordResponse.newBuilder()
                                .setMessage("User with given email not found")
                                .setSuccess(false)
                                .build()
                ));
    }


    //=======================================
//Method (resetPassword)
//Resets user password
// =====================================
    public Mono<ResetPasswordResponse> resetPassword(ResetPasswordRequest request) {
        var response = ResetPasswordResponse.newBuilder();
        return passwordResetTokenRepository.findByToken(request.getToken())
                .flatMap(prt -> {
//                   If token expires, delete and the error
                    if (prt.expired())
                        return passwordResetTokenRepository.delete(prt)
                                .thenReturn(Mono.error(new TokenExpiredException("Password reset ")));

                    var passwordHash = passwordEncoder.encode(request.getPassword());
//                   update User password the delete the password reset token
                    return userRepository.updatePasswordHash(passwordHash, prt.getUserId())
                            .flatMap(user -> passwordResetTokenRepository.delete(prt));
                })
                .onErrorMap(TokenExpiredException.class, e -> Status.INTERNAL
                        .withDescription(e.getMessage())
                        .asRuntimeException())
                .thenReturn(
                        response
                                .setSuccess(true)
                                .setMessage("Password reset successful")
                                .build()
                )
                .switchIfEmpty(Mono.just(response
                        .setMessage("Invalid or token")
                        .build()))
                ;
    }

    public Mono<ChangePasswordResponse> changePassword(ChangePasswordRequest request, UUID userId) {
        var response = ChangePasswordResponse.newBuilder();
        return userRepository.findById(userId)
                .flatMap(user -> {
//                    return error if old password is incorrect
                    if (!passwordEncoder.matches(request.getOldPassword(), user.getPasswordHash()))
                        return Mono.just(response
                                .setMessage("Incorrect old password")
                                .build());

                    var passwordHash = passwordEncoder.encode(request.getNewPassword());
                    return userRepository.updatePasswordHash(passwordHash, user.getId())
                            .map(i -> response.setMessage("Password reset successful").setSuccess(true).build());
                })
                .switchIfEmpty(Mono.just(response
                        .setMessage("Something went wrong")
                        .build())
                );
    }
}
