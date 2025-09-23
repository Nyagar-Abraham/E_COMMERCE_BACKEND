package org.abraham.user_service.repository;

import org.abraham.user_service.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface UserRepository extends ReactiveCrudRepository<UserEntity, UUID> {
    Mono<UserEntity> findByEmail(String email);
}
