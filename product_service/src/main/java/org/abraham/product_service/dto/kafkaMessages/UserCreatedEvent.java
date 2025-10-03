package org.abraham.product_service.dto.kafkaMessages;

import org.abraham.commondtos.UserStatus;

import java.util.UUID;

public record UserCreatedEvent(UUID id, String email, String username, UserStatus status, String phoneNumber) {
}
