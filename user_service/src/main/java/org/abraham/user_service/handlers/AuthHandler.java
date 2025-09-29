package org.abraham.user_service.handlers;

import dev.samstevens.totp.exceptions.QrGenerationException;
import lombok.AllArgsConstructor;
import org.abraham.constants.Constants;
import org.abraham.models.*;
import org.abraham.user_service.auth.jwt.JwtUtil;
import org.abraham.user_service.auth.mfa.TotpManagerImpl;
import org.abraham.user_service.dto.UserStatus;
import org.abraham.user_service.entity.EmailVerificationToken;
import org.abraham.user_service.entity.PasswordResetToken;
import org.abraham.user_service.entity.UserEntity;
import org.abraham.user_service.mapper.UserMapper;
import org.abraham.user_service.repository.EmailVerificationTokenRepository;
import org.abraham.user_service.repository.PasswordResetTokenRepository;
import org.abraham.user_service.repository.UserRepository;
import org.abraham.user_service.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;
import java.util.UUID;

//Handles User Login, Registration, PasswordReset, Email And phone Verification.
@Service
@AllArgsConstructor
public class AuthHandler {
    private static final String EMAIL_TEMPLATE = "Hi %s,\n" +
            "Thanks for signing up! We just need to verify your email address before you can get started.\n" +
            "Click the button below to complete the process:\n" +
            "%s\n" +
            "If you didn't create an account with us, you can safely ignore this email.\n" +
            "Thanks,\n" +
            "The %s Team";
   private static final LocalDateTime EMAIL_TOKEN_EXPIRATION =   LocalDateTime.now(ZoneId.of("Africa/Nairobi")).plusMinutes(30);
    private static final Logger log = LoggerFactory.getLogger(AuthHandler.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final TotpManagerImpl totpManagerImpl;
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final MailService mailService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;


    @Transactional
    public Mono<User> registerUser(Mono<RegisterUserRequest> request) {
        var secret = UUID.randomUUID().toString();
        return request
                .map(user -> UserMapper.registerUserRequestToUserEntity(user, passwordEncoder.encode(user.getPassword())))
                .flatMap(user ->
                                userRepository.save(user)
                                        .flatMap(u -> {

                                            var verifyEmailToken = new EmailVerificationToken();
                                            verifyEmailToken.setToken(secret);
                                            verifyEmailToken.setUserId(u.getId());
                                            verifyEmailToken.setExpiresAt(
                                                  EMAIL_TOKEN_EXPIRATION
                                            );

                                            return emailVerificationTokenRepository.save(verifyEmailToken).map(vet -> u);
                                        })
                                        .flatMap(savedUser ->
//                                        SEND EMAIL
                                                        Mono.fromCallable(() -> {
                                                            var url = "http://localhost:8081/verify-email?token=%s".formatted(secret);
                                                            mailService.sendRegistrationEmail(savedUser, url, Constants.COMPANY_NAME, Constants.COMPANY_URL);
                                                            return savedUser; // return the user after sending email
                                                        }).subscribeOn(Schedulers.boundedElastic())
                                        )
                )
                .flatMap(user -> userRepository.findById(user.getId()))
                .map(UserMapper::userEntityToUser);
    }

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

                    // Mark the user's email as verified
                    return userRepository.updateEmailVerified(true, emailVerificationToken.getUserId())
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

}
