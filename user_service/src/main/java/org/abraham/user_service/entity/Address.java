package org.abraham.user_service.entity;

import lombok.Getter;
import lombok.Setter;
import org.abraham.commondtos.AddressType;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "addresses")
@Getter
@Setter
public class Address {
    @Id
    private Long id;

    @Column("user_id")
    private UUID userId;

    @Transient
    private UserEntity user;

    private AddressType type;

    @Column("is_default")
    private Boolean isDefault;

    @Column("address_line_1")
    private String addressLine1;

    @Column("address_line_2")
    private String addressLine2;

    private String city;
    private String state;

    @Column("postal_code")
    private String postalCode;

    private String country;

    @Column("created_at")
    private LocalDateTime createdAt;
    @Column("updated_at")
    private LocalDateTime updatedAt;
}
//
//CREATE TABLE addresses
//        (
//                id             UUID PRIMARY KEY                  DEFAULT gen_random_uuid(),
//user_id        UUID                     NOT NULL,
//type           address_type_enum        NOT NULL DEFAULT 'BOTH',
//is_default     BOOLEAN                  NOT NULL DEFAULT FALSE,
//address_line_1 VARCHAR(255)             NOT NULL,
//address_line_2 VARCHAR(255),
//city           VARCHAR(100)             NOT NULL,
//state          VARCHAR(100)             NOT NULL,
//postal_code    VARCHAR(20)              NOT NULL,
//country        VARCHAR(2)               NOT NULL, -- ISO 3166-1 alpha-2 country code
//created_at     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
//updated_at     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
//);