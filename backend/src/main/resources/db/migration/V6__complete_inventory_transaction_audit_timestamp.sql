-- InventoryTransaction inherits BaseEntity. V1 pre-dated that shared timestamp
-- mapping and omitted updated_at, causing Hibernate validation to fail on a
-- clean database after Flyway migration.
ALTER TABLE inventory_transactions
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP;
