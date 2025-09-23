package org.abraham.user_service.handlers;

import com.google.protobuf.Timestamp;
import lombok.AllArgsConstructor;
import org.abraham.models.RegisterUserRequest;
import org.abraham.models.User;
import org.abraham.user_service.entity.UserEntity;
import org.abraham.user_service.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.time.ZoneId;

//Handles User Login, Registration, PasswordReset, Email And phone Verification.
@Service
@AllArgsConstructor
public class AuthHandler {
    private static final Logger log = LoggerFactory.getLogger(AuthHandler.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public Mono<User> registerUser(Mono<RegisterUserRequest> request) {
        return request
                .doOnNext(r -> log.info("Registering user 2, {}", r))
                .map(user -> UserEntity.builder()
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .passwordHash(passwordEncoder.encode(user.getPassword()))
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .phoneNumber(user.getPhoneNumber())
                        .build())
                .flatMap(userRepository::save)
                .flatMap(user -> userRepository.findById(user.getId()))
                .doOnNext(user -> log.info("User 2, {}", user))
                .map(user ->User.newBuilder()
                        .setId(user.getId().toString())
                        .setEmail(user.getEmail())
                        .setUsername(user.getUsername())
                        .setFirstName(user.getFirstName())
                        .setLastName(user.getLastName())
                        .setPhoneNumber(user.getPhoneNumber())
                        .setEmailVerified(user.getEmailVerified())
                        .setPhoneVerified(user.getPhoneVerified())

                        .setCreatedAt(Timestamp.newBuilder().setSeconds(Instant.from(user.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant()).getEpochSecond()).build())
                        .setUpdatedAt(Timestamp.newBuilder().setSeconds(Instant.from(user.getUpdatedAt().atZone(ZoneId.systemDefault()).toInstant()).getEpochSecond()).build())
                        .build());
    }

}
