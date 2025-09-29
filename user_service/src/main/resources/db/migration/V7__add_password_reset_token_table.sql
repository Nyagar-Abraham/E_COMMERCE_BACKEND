CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- ========================================
--     Drop password reset token fields from users table
-- ========================================
ALTER TABLE users
    DROP COLUMN IF EXISTS password_reset_token,
    DROP COLUMN IF EXISTS password_reset_expires_at;

-- =============================================
--     Create the password reset token table
-- =============================================
CREATE TABLE IF NOT EXISTS password_reset_tokens
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id    UUID         NOT NULL,
    token      VARCHAR(400) NOT NULL,
    expires_at TIMESTAMPTZ  NOT NULL,
    UNIQUE (user_id, token)
);

-- ============================================
--     ADD THE RELATIONSHIP
-- ============================================
ALTER TABLE password_reset_tokens
    ADD CONSTRAINT fk_password_reset_tokens_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE CASCADE;

-- =====================================
--  Add index to token field and unique key to email_verification_tokens token fields
-- =====================================
CREATE INDEX idx_password_reset_tokens_token ON password_reset_tokens (token);

ALTER TABLE email_verification_tokens
    ADD CONSTRAINT unq_email_verification_tokens_token
        UNIQUE (token);





