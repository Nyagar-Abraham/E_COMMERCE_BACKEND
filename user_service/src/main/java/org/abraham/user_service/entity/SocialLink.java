package org.abraham.user_service.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table("social_links")
@Data
@ToString
public class SocialLink {
    @Id
    private UUID id;

    @Column("user_profile_id")
    private UUID userProfileId;

    private String facebook;
    private String twitter;
    private String instagram;
    private String linkedin;
}
