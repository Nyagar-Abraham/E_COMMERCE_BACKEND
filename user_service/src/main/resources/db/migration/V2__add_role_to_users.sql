-- ==============================
--     Roles Enum
-- ==============================
CREATE TABLE user_roles AS ENUM (
    'CUSTOMER',
    'MERCHANT',
    'ADMIN',
    'GUEST'
);

-- ================================
--     ADD ROLE TO USER TABLE
-- ================================

ALTER TABLE users
ADD COLUMN role user_roles NOT NULL DEFAULT 'CUSTOMER'