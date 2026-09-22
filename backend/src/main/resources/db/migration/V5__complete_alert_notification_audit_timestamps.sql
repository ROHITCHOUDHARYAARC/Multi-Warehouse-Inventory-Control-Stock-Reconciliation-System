-- Alert, notification and preference entities inherit BaseEntity, which requires
-- both immutable creation and last-modified timestamps. V4 created these tables
-- without the latter fields; add them forward-only so clean and existing schemas
-- validate consistently under Hibernate ddl-auto=validate.
ALTER TABLE alerts
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE notifications
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE notification_preferences
    ADD COLUMN IF NOT EXISTS created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN IF NOT EXISTS updated_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP;
