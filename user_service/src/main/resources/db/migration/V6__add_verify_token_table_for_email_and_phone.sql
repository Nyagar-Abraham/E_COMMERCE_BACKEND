CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- ==================================================
--     Drop the column from user table
-- ==================================================
ALTER TABLE users
    DROP COLUMN IF EXISTS email_verification_token,
    DROP COLUMN IF EXISTS email_verification_expires_at,
    DROP COLUMN IF EXISTS phone_verification_token,
    DROP COLUMN IF EXISTS phone_verification_expires_at;

-- ===================================================
--     Create the tables
-- ===================================================
CREATE TABLE email_verification_tokens
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id    UUID         NOT NULL,
    token      VARCHAR(400) NOT NULL,
    expires_at TIMESTAMPTZ  NOT NULL,
    UNIQUE (user_id)
);

-- CREATE TABLE phone_verification_tokens
-- (
--     id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
--     user_id    UUID         NOT NULL,
--     token      VARCHAR(400) NOT NULL,
--     expires_at TIMESTAMPTZ  NOT NULL,
--     UNIQUE (user_id)
-- );

-- ========================================
--     RELATIONSHIP
-- ========================================
ALTER TABLE email_verification_tokens
ADD CONSTRAINT fk_email_verification_tokens_user
FOREIGN KEY (user_id)
REFERENCES users (id)
ON DELETE CASCADE;

CREATE INDEX idx_email_verification_tokens_token ON email_verification_tokens (token);


