package org.abraham.user_service.mapper;


import org.abraham.models.RegisterUserRequest;
import org.abraham.models.User;
import org.abraham.models.UserStatus;
import org.abraham.user_service.entity.UserEntity;
import org.abraham.user_service.utils.Utils;

import java.util.Objects;


public class UserMapper {


    public static User userEntityToUser(UserEntity userEntity) {
        var response = User.newBuilder()
                .setId(userEntity.getId().toString())
                .setEmail(userEntity.getEmail())
                .setUsername(userEntity.getUsername())
                .setFirstName(userEntity.getFirstName())
                .setLastName(userEntity.getLastName())
                .setPhoneNumber(userEntity.getPhoneNumber())
                .setEmailVerified(userEntity.getEmailVerified())
                .setPhoneVerified(userEntity.getPhoneVerified())
                .setStatus(UserStatus.valueOf(userEntity.getStatus().name()))
                .setCreatedAt(Utils.convertToTimestamp(userEntity.getCreatedAt()))
                .setUpdatedAt(Utils.convertToTimestamp(userEntity.getUpdatedAt()))
                .build();
        if (Objects.nonNull(userEntity.getLastLoginAt())) {
            return response.toBuilder()
                    .setLastLogin(Utils.convertToTimestamp(userEntity.getLastLoginAt())).build();
        }
        return response;
    }

    public static UserEntity registerUserRequestToUserEntity(RegisterUserRequest user, String hashedPassword) {
        return UserEntity.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .passwordHash(hashedPassword)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .enableMfa(user.getEnableMfa())
                .build();
    }
}
