package org.abraham.user_service.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Table("password_reset_tokens")
@Data
public class PasswordResetToken {
    @Id
    private UUID id;

    @Column("user_id")
    private UUID userId;

    @Column("token")
    private String token;

    @Column("expires_at")
    private LocalDateTime expiresAt;

    public boolean  expired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }
}
