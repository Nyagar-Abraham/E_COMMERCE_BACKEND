package org.abraham.user_service.handlers;

import com.google.protobuf.Timestamp;
import dev.samstevens.totp.exceptions.QrGenerationException;
import lombok.AllArgsConstructor;
import org.abraham.models.LoginRequest;
import org.abraham.models.LoginResponse;
import org.abraham.models.RegisterUserRequest;
import org.abraham.models.User;

import org.abraham.user_service.auth.jwt.JwtUtil;

import org.abraham.user_service.auth.mfa.TotpManagerImpl;
import org.abraham.user_service.entity.UserEntity;
import org.abraham.user_service.mapper.UserMapper;
import org.abraham.user_service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

//Handles User Login, Registration, PasswordReset, Email And phone Verification.
@Service
@AllArgsConstructor
public class AuthHandler {
    private static final Logger log = LoggerFactory.getLogger(AuthHandler.class);
    private final UserRepository userRepository;
    private  final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final TotpManagerImpl totpManagerImpl;


    public Mono<User> registerUser(Mono<RegisterUserRequest> request) {
        return request
                .map(user -> UserMapper.registerUserRequestToUserEntity(user, passwordEncoder.encode(user.getPassword())) )
                .flatMap(userRepository::save)
                .flatMap(user -> userRepository.findById(user.getId()))
                .map(UserMapper::userEntityToUser);
    }

    @Transactional
    public Mono<LoginResponse> login(LoginRequest request) {
      return userRepository.findByEmail(request.getEmail())
                .flatMap(u -> userRepository.updateLastLoginAt(LocalDateTime.now(ZoneId.systemDefault()), u.getId())
                        .map(i -> u))
                .flatMap(this::buildLoginResponse);
    }

    private Mono<LoginResponse> buildLoginResponse(UserEntity u) {
        var accessToken = jwtUtil.generateAccessToken(u);
        var refreshToken = jwtUtil.generateRefreshToken(u);

        var responseBuilder = LoginResponse.newBuilder()
                .setAccessToken(accessToken)
                .setRefreshToken(refreshToken);

        if (!u.getEnableMfa()) {
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

}
