package org.abraham.user_service.handlers;

import org.abraham.user_service.entity.UserEntity;
import org.abraham.user_service.repository.UserRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class UserHandler {
    private final UserRepository userRepository;

    public UserHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Mono<UserEntity> findUserById(UUID id){
        return userRepository.findById(id);
    }
}
