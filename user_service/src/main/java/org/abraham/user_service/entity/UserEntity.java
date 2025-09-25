package org.abraham.user_service.entity;

import lombok.*;
import org.abraham.user_service.dto.UserRoles;
import org.abraham.user_service.dto.UserStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Table(name = "users")
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    private UUID id;
    private String username;
    private String email;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column("password_hash")
    private String passwordHash;

    @Column("phone_number")
    private String phoneNumber;

    @Column("status")
    private UserStatus status;

    @Column("role")
    private UserRoles role;

    @Column("email_verified")
    private Boolean emailVerified;

    @Column("phone_verified")
    private Boolean phoneVerified;

    @Column("email_verification_token")
    private String emailVerificationToken;

    @Column("email_verification_expires_at")
    private LocalDateTime emailVerificationExpiresAt;

    @Column("phone_verification_token")
    private String phoneVerificationToken;

    @Column("phone_verification_expires_at")
    private LocalDateTime phoneVerificationExpiresAt;

    @Column("password_reset_token")
    private String passwordResetToken;

    @Column("password_reset_expires_at")
    private LocalDateTime passwordResetExpiresAt;

    @Column("last_login_at")
    private LocalDateTime lastLoginAt;

    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Transient
    @ToString.Exclude
    private UserProfile profile;

    @Transient
    @ToString.Exclude
    private UserPreference preference;

    @Transient
    @ToString.Exclude
    private List<Address> addresses;

}

//CREATE TABLE users
//        (
//                id                            UUID PRIMARY KEY          DEFAULT gen_random_uuid(),
//email                         VARCHAR(255)     NOT NULL UNIQUE,
//username                      VARCHAR(50) UNIQUE,
//password_hash                 VARCHAR(255)     NOT NULL,
//first_name                    VARCHAR(100),
//last_name                     VARCHAR(100),
//phone_number                  VARCHAR(20),
//status                        user_status_enum NOT NULL DEFAULT 'PENDING_VERIFICATION',
//email_verified                BOOLEAN          NOT NULL DEFAULT FALSE,
//phone_verified                BOOLEAN          NOT NULL DEFAULT FALSE,
//email_verification_token      VARCHAR(255),
//email_verification_expires_at TIMESTAMPTZ,
//phone_verification_token      VARCHAR(10),
//phone_verification_expires_at TIMESTAMPTZ,
//password_reset_token          VARCHAR(255),
//password_reset_expires_at     TIMESTAMPTZ,
//last_login_at                 TIMESTAMPTZ,
//created_at                    TIMESTAMPTZ      NOT NULL DEFAULT CURRENT_TIMESTAMP,
//updated_at                    TIMESTAMPTZ      NOT NULL DEFAULT CURRENT_TIMESTAMP
//);
