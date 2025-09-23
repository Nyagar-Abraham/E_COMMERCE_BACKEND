package org.abraham.user_service.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "user_preferences")
@Getter
@Setter
public class UserPreference {
    @Id
    private UUID id;

    @Column("user_id")
    private UUID userId;

    @Transient
    private UserEntity user;

    private String currency;
    private String language;

    @Column("marketing_emails")
    private Boolean marketingEmails;

    @Column("order_notifications")
    private Boolean orderNotifications;

    @Column("newsletter")
    private Boolean newsletter;

    @Column("created_at")
    private LocalDateTime createdAt;
    @Column("updated_at")
    private LocalDateTime updatedAt;
}
//
//CREATE TABLE user_preferences
//        (
//                id                  UUID PRIMARY KEY                  DEFAULT gen_random_uuid(),
//user_id             UUID                     NOT NULL,
//currency            VARCHAR(3)               NOT NULL DEFAULT 'KSH',
//language            VARCHAR(5)               NOT NULL DEFAULT 'en-US',
//marketing_emails    BOOLEAN                  NOT NULL DEFAULT TRUE,
//order_notifications BOOLEAN                  NOT NULL DEFAULT TRUE,
//newsletter          BOOLEAN                  NOT NULL DEFAULT FALSE,
//created_at          TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
//updated_at          TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
//UNIQUE (user_id)
//);