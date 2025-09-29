package org.abraham.user_service.handlers;

import lombok.AllArgsConstructor;
import org.abraham.user_service.entity.UserEntity;
import org.abraham.user_service.repository.UserRepository;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@AllArgsConstructor
public class UserHandler {
    private final UserRepository userRepository;

    public Mono<UserEntity> findUserById(UUID id){
        return userRepository.findById(id);
    }
}
