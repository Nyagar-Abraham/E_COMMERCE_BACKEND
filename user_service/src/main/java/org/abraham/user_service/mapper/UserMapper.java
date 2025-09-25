package org.abraham.user_service.mapper;

import com.google.protobuf.Timestamp;

import org.abraham.models.RegisterUserRequest;
import org.abraham.models.User;
import org.abraham.models.UserStatus;
import org.abraham.user_service.entity.UserEntity;


import java.time.Instant;
import java.time.ZoneId;


public class UserMapper {


    public static User userEntityToUser(UserEntity userEntity) {
        return User.newBuilder()
                .setId(userEntity.getId().toString())
                .setEmail(userEntity.getEmail())
                .setUsername(userEntity.getUsername())
                .setFirstName(userEntity.getFirstName())
                .setLastName(userEntity.getLastName())
                .setPhoneNumber(userEntity.getPhoneNumber())
                .setEmailVerified(userEntity.getEmailVerified())
                .setPhoneVerified(userEntity.getPhoneVerified())
                .setStatus(UserStatus.valueOf(userEntity.getStatus().name()))
                .setCreatedAt(Timestamp.newBuilder().setSeconds(Instant.from(userEntity.getCreatedAt().atZone(ZoneId.systemDefault()).toInstant()).getEpochSecond()).build())
                .setUpdatedAt(Timestamp.newBuilder().setSeconds(Instant.from(userEntity.getUpdatedAt().atZone(ZoneId.systemDefault()).toInstant()).getEpochSecond()).build())
                .build();
    }

    public static UserEntity registerUserRequestToUserEntity(RegisterUserRequest user, String hashedPassword) {
        return UserEntity.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .passwordHash(hashedPassword)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
