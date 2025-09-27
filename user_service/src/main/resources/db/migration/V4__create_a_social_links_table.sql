-- =======================================
--     Import uuid extension
-- =======================================
CREATE EXTENSION IF NOT EXISTS "pgcrypto";


-- =======================================
--     Drop Social links columns from user profile
-- =======================================
ALTER TABLE user_profiles
    DROP COLUMN IF EXISTS facebook,
    DROP COLUMN IF EXISTS twitter,
    DROP COLUMN IF EXISTS instagram,
    DROP COLUMN IF EXISTS linkedin;


-- =======================================
--     Stores uses social links
-- =======================================
CREATE TABLE social_links
(
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_profile_id UUID NOT NULL,
    facebook        VARCHAR(255),
    twitter         VARCHAR(255),
    instagram       VARCHAR(255),
    linkedin        VARCHAR(255),
    UNIQUE (user_profile_id)
);


-- =======================================
--     Relate social_links to user_profiles
-- =======================================
ALTER TABLE social_links
    ADD CONSTRAINT fk_social_links_user_profiles
        FOREIGN KEY (user_profile_id)
            REFERENCES user_profiles (id)
            ON DELETE CASCADE;