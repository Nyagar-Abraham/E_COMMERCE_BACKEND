package org.abraham.user_service.repository;

import org.abraham.user_service.dto.UserStatus;
import org.abraham.user_service.entity.UserEntity;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.BindParam;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface UserRepository extends ReactiveCrudRepository<UserEntity, UUID> {
    Mono<UserEntity> findByEmail(String email);

    @Modifying
    @Query("UPDATE users SET last_login_at = :lastLoginAt WHERE id = :id")
    Mono<Integer> updateLastLoginAt(@BindParam("lastLoginAt") LocalDateTime lastLoginAt,
                                    @BindParam("id") UUID id);

    @Modifying
    @Query("UPDATE users SET status = CAST(:status AS user_status_enum) WHERE id = :id ")
    Mono<Integer> updateStatus(@BindParam("status") UserStatus status, @BindParam("id") UUID id);

    @Modifying
    @Query("UPDATE users SET last_login_at = :lastLoginAt, status =  CAST(:status AS user_status_enum)  WHERE id = :id")
    Mono<Integer> updateLastLoginAtAndStatus(@BindParam("lastLoginAt") LocalDateTime lastLoginAt,@BindParam("status") UserStatus status,
                                    @BindParam("id") UUID id);

    @Modifying
    @Query("UPDATE users SET mfa_secret = :secret WHERE id = :id AND mfa_secret IS NULL")
    Mono<Integer> updateMfaSecret(@BindParam("secret") String secret, @BindParam("id") UUID id);

    @Modifying
    @Query("UPDATE users SET email_verified = :emailVerified WHERE id = :id ")
    Mono<Integer> updateEmailVerified(@BindParam("emailVerified") boolean emailVerified, @BindParam("id") UUID id);

    @Modifying
    @Query("UPDATE users SET password_hash = :passwordHash WHERE id = :id ")
    Mono<Integer> updatePasswordHash(@BindParam("passwordHash") String passwordHash, @BindParam("id") UUID id);


}
