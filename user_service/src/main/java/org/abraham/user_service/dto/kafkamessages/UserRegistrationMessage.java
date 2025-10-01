package org.abraham.user_service.dto.kafkamessages;

import org.abraham.commondtos.UserStatus;

import java.util.UUID;

public record UserRegistrationMessage(UUID id, String email, String username, UserStatus status,String phoneNumber ) {
}
