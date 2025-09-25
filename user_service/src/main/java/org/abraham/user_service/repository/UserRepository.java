package org.abraham.user_service.repository;

import org.abraham.user_service.entity.UserEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface UserRepository extends ReactiveCrudRepository<UserEntity, UUID> {
    Mono<UserEntity> findByEmail(String email);

    @Modifying
    @Query("UPDATE users SET last_login_at = :lastLoginAt WHERE id = :id")
    Mono<Integer> updateLastLoginAt(@Param("lastLoginAt") LocalDateTime lastLoginAt,
                                    @Param("id") UUID id);

}
