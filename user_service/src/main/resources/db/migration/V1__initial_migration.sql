-- ===========================================
-- USER SERVICE DATABASE SCHEMA
-- ===========================================

CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- ===========================================
-- ENUMS
-- ===========================================
-- User status enum
CREATE TYPE user_status_enum AS ENUM (
    'ACTIVE',
    'INACTIVE',
    'SUSPENDED',
    'PENDING_VERIFICATION'
    );

-- Address type enum
CREATE TYPE address_type_enum AS ENUM (
    'BILLING',
    'SHIPPING',
    'BOTH'
    );

-- ===========================================
-- MAIN TABLES
-- ===========================================

-- Users table
CREATE TABLE users
(
    id                            UUID PRIMARY KEY          DEFAULT gen_random_uuid(),
    email                         VARCHAR(255)     NOT NULL UNIQUE,
    username                      VARCHAR(50) UNIQUE,
    password_hash                 VARCHAR(255)     NOT NULL,
    first_name                    VARCHAR(100),
    last_name                     VARCHAR(100),
    phone_number                  VARCHAR(20),
    status                        user_status_enum NOT NULL DEFAULT 'PENDING_VERIFICATION',
    email_verified                BOOLEAN          NOT NULL DEFAULT FALSE,
    phone_verified                BOOLEAN          NOT NULL DEFAULT FALSE,
    email_verification_token      VARCHAR(255),
    email_verification_expires_at TIMESTAMPTZ,
    phone_verification_token      VARCHAR(10),
    phone_verification_expires_at TIMESTAMPTZ,
    password_reset_token          VARCHAR(255),
    password_reset_expires_at     TIMESTAMPTZ,
    last_login_at                 TIMESTAMPTZ,
    created_at                    TIMESTAMPTZ      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at                    TIMESTAMPTZ      NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- User profiles table (one-to-one with users)
CREATE TABLE user_profiles
(
    id         UUID PRIMARY KEY     DEFAULT gen_random_uuid(),
    user_id    UUID        NOT NULL,
    avatar     TEXT,
    bio        TEXT,
    website    VARCHAR(255),
    facebook   VARCHAR(255),
    twitter    VARCHAR(255),
    instagram  VARCHAR(255),
    linkedin   VARCHAR(255),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (user_id)
);

-- User preferences table (one-to-one with users)
CREATE TABLE user_preferences
(
    id                  UUID PRIMARY KEY                  DEFAULT gen_random_uuid(),
    user_id             UUID                     NOT NULL,
    currency            VARCHAR(3)               NOT NULL DEFAULT 'KSH',
    language            VARCHAR(5)               NOT NULL DEFAULT 'en-US',
    marketing_emails    BOOLEAN                  NOT NULL DEFAULT TRUE,
    order_notifications BOOLEAN                  NOT NULL DEFAULT TRUE,
    newsletter          BOOLEAN                  NOT NULL DEFAULT FALSE,
    created_at          TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (user_id)
);

-- Addresses table
CREATE TABLE addresses
(
    id             UUID PRIMARY KEY                  DEFAULT gen_random_uuid(),
    user_id        UUID                     NOT NULL,
    type           address_type_enum        NOT NULL DEFAULT 'BOTH',
    is_default     BOOLEAN                  NOT NULL DEFAULT FALSE,
    address_line_1 VARCHAR(255)             NOT NULL,
    address_line_2 VARCHAR(255),
    city           VARCHAR(100)             NOT NULL,
    state          VARCHAR(100)             NOT NULL,
    postal_code    VARCHAR(20)              NOT NULL,
    country        VARCHAR(2)               NOT NULL, -- ISO 3166-1 alpha-2 country code
    created_at     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- ===========================================
-- RELATIONS
-- ===========================================

-- user profile to user(one to one)
ALTER TABLE user_profiles
ADD CONSTRAINT fk_user_profiles_user
FOREIGN KEY (user_id)
REFERENCES users (id)
ON DELETE CASCADE;

-- user preferences to user(one to one)
ALTER TABLE user_preferences
ADD CONSTRAINT fk_user_preferences_user
FOREIGN KEY (user_id)
REFERENCES users (id)
ON DELETE CASCADE ;

-- addresses to user (many to one)
ALTER TABLE addresses
ADD CONSTRAINT fk_addresses
FOREIGN KEY (user_id)
REFERENCES users (id)
ON DELETE CASCADE ;


-- ===========================================
-- INDEXES FOR PERFORMANCE
-- ===========================================

-- Users table indexes
CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_users_username ON users (username) WHERE username IS NOT NULL;
CREATE INDEX idx_users_phone_number ON users (phone_number) WHERE phone_number IS NOT NULL;
CREATE INDEX idx_users_status ON users (status);
CREATE INDEX idx_users_created_at ON users (created_at);
CREATE INDEX idx_users_last_login_at ON users (last_login_at) WHERE last_login_at IS NOT NULL;
CREATE INDEX idx_users_email_verification ON users (email_verification_token) WHERE email_verification_token IS NOT NULL;
CREATE INDEX idx_users_password_reset ON users (password_reset_token) WHERE password_reset_token IS NOT NULL;

-- Addresses table indexes
CREATE INDEX idx_addresses_user_id ON addresses (user_id);
CREATE INDEX idx_addresses_user_id_default ON addresses (user_id, is_default) WHERE is_default = TRUE;
CREATE INDEX idx_addresses_type ON addresses (type);
CREATE INDEX idx_addresses_country ON addresses (country);

-- User profiles table indexes
CREATE INDEX idx_user_profiles_user_id ON user_profiles (user_id);

-- User preferences table indexes
CREATE INDEX idx_user_preferences_user_id ON user_preferences (user_id);


-- ===========================================
-- CONSTRAINTS AND TRIGGERS
-- ===========================================

-- Ensure only one default address per user per type
CREATE UNIQUE INDEX idx_addresses_user_default_billing
    ON addresses (user_id)
    WHERE is_default = TRUE AND type IN ('BILLING', 'BOTH');

CREATE UNIQUE INDEX idx_addresses_user_default_shipping
    ON addresses (user_id)
    WHERE is_default = TRUE AND type IN ('SHIPPING', 'BOTH');

-- Function to update the updated_at timestamp
CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Triggers for updating updated_at column
CREATE TRIGGER update_users_updated_at
    BEFORE UPDATE
    ON users
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_user_profiles_updated_at
    BEFORE UPDATE
    ON user_profiles
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_user_preferences_updated_at
    BEFORE UPDATE
    ON user_preferences
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

CREATE TRIGGER update_addresses_updated_at
    BEFORE UPDATE
    ON addresses
    FOR EACH ROW
EXECUTE FUNCTION update_updated_at_column();

-- Function to create default preferences when a user is created
CREATE OR REPLACE FUNCTION create_default_user_preferences()
    RETURNS TRIGGER AS
$$
BEGIN
    INSERT INTO user_preferences (user_id) VALUES (NEW.id);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger to create default preferences
CREATE TRIGGER create_user_preferences_trigger
    AFTER INSERT
    ON users
    FOR EACH ROW
EXECUTE FUNCTION create_default_user_preferences();


-- ===========================================
-- COMMENTS FOR DOCUMENTATION
-- ===========================================

-- Table comments
COMMENT ON TABLE users IS 'Main users table storing user account information';
COMMENT ON TABLE user_profiles IS 'Extended user profile information and social links';
COMMENT ON TABLE user_preferences IS 'User preferences for currency, language, and notifications';
COMMENT ON TABLE addresses IS 'User addresses for billing and shipping';


-- Column comments for important fields
COMMENT ON COLUMN users.email IS 'User email address (case-insensitive, unique)';
COMMENT ON COLUMN users.password_hash IS 'Bcrypt hashed password';
COMMENT ON COLUMN users.status IS 'Current status of the user account';
COMMENT ON COLUMN addresses.country IS 'ISO 3166-1 alpha-2 country code';
