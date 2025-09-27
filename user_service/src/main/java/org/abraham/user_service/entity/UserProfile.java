package org.abraham.user_service.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "user_profiles")
@Getter
@Setter
@ToString
public class UserProfile {
    @Id
    private UUID id;

    @Column("user_id")
    private UUID userId;

    @Transient
    private UserEntity user;

    private String avatar;

    private String bio;

    private String website;

    @Column("created_at")
    private LocalDateTime createdAt;
    @Column("updated_at")
    private LocalDateTime updatedAt;

    @Transient
    @ToString.Exclude
    private SocialLink socialLink;
}
//CREATE TABLE user_profiles
//        (
//                id         UUID PRIMARY KEY     DEFAULT gen_random_uuid(),
//user_id    UUID        NOT NULL,
//avatar     TEXT,
//bio        TEXT,
//website    VARCHAR(255),
//facebook   VARCHAR(255),
//twitter    VARCHAR(255),
//instagram  VARCHAR(255),
//linkedin   VARCHAR(255),
//created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
//updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
//UNIQUE (user_id)
//);