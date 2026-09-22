CREATE TABLE IF NOT EXISTS alerts (id UUID PRIMARY KEY DEFAULT gen_random_uuid(), alert_type VARCHAR(40) NOT NULL, severity VARCHAR(15) NOT NULL CHECK(severity IN ('INFO','SUCCESS','WARNING','CRITICAL')), warehouse_id UUID REFERENCES warehouses(id) ON DELETE SET NULL, product_id UUID REFERENCES products(id) ON DELETE SET NULL, title VARCHAR(200) NOT NULL, message TEXT NOT NULL, status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' CHECK(status IN ('ACTIVE','ACKNOWLEDGED','RESOLVED','DISMISSED')), created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP, resolved_at TIMESTAMPTZ);
CREATE UNIQUE INDEX IF NOT EXISTS ux_alert_active_key ON alerts(alert_type,warehouse_id,product_id) WHERE status IN ('ACTIVE','ACKNOWLEDGED');
CREATE TABLE IF NOT EXISTS notification_preferences (id UUID PRIMARY KEY DEFAULT gen_random_uuid(), user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE, notification_type VARCHAR(40) NOT NULL, enabled BOOLEAN NOT NULL DEFAULT TRUE, UNIQUE(user_id,notification_type));
CREATE INDEX IF NOT EXISTS ix_alerts_scope_status ON alerts(warehouse_id,product_id,status,created_at DESC);
CREATE INDEX IF NOT EXISTS ix_notifications_recipient_read_created ON notifications(recipient_user_id,is_read,created_at DESC);
CREATE INDEX IF NOT EXISTS ix_audit_action_entity_created ON audit_logs(action,entity_type,created_at DESC);
ALTER TABLE notifications ADD COLUMN IF NOT EXISTS warehouse_id UUID REFERENCES warehouses(id) ON DELETE SET NULL;
ALTER TABLE notifications ADD COLUMN IF NOT EXISTS product_id UUID REFERENCES products(id) ON DELETE SET NULL;
