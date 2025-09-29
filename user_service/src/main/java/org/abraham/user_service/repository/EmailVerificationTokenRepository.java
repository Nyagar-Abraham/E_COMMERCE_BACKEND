package org.abraham.user_service.repository;

import org.abraham.models.VerifyEmailTokenResponse;
import org.abraham.user_service.entity.EmailVerificationToken;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface EmailVerificationTokenRepository extends ReactiveCrudRepository<EmailVerificationToken, UUID> {
    Mono<EmailVerificationToken> findByToken(String token);
}
