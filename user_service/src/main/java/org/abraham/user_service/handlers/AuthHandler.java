package org.abraham.user_service.handlers;

import com.google.protobuf.Timestamp;
import lombok.AllArgsConstructor;
import org.abraham.models.RegisterUserRequest;
import org.abraham.models.User;
import org.abraham.models.UserStatus;
import org.abraham.user_service.entity.UserEntity;
import org.abraham.user_service.mapper.UserMapper;
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
    private  final PasswordEncoder passwordEncoder;



    public Mono<User> registerUser(Mono<RegisterUserRequest> request) {
        return request
                .map(user -> UserMapper.registerUserRequestToUserEntity(user, passwordEncoder.encode(user.getPassword())) )
                .flatMap(userRepository::save)
                .flatMap(user -> userRepository.findById(user.getId()))
                .map(UserMapper::userEntityToUser);
    }

}
