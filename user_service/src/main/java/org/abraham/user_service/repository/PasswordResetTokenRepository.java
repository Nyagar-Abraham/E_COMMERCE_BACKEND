package org.abraham.user_service.repository;

import org.abraham.user_service.entity.PasswordResetToken;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.UUID;

public interface PasswordResetTokenRepository extends ReactiveCrudRepository<PasswordResetToken, UUID> {
}
