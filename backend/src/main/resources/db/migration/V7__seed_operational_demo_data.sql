-- Idempotent demonstration data. Operational UI values remain API-derived.
INSERT INTO products (sku,name,description,barcode,category_id,unit_of_measure,cost_price,selling_price,minimum_stock,reorder_level,maximum_stock,status)
SELECT 'LAP-14-PRO','Latitude 14 Pro','Business laptop','890100000001',c.id,'UNIT',52000,64999,5,10,80,'ACTIVE' FROM categories c WHERE c.name='Electronics'
ON CONFLICT (sku) DO NOTHING;
INSERT INTO products (sku,name,description,barcode,category_id,unit_of_measure,cost_price,selling_price,minimum_stock,reorder_level,maximum_stock,status)
SELECT 'ROUTER-AX','AX Wi-Fi Router','Dual-band network router','890100000002',c.id,'UNIT',4200,5999,8,15,60,'ACTIVE' FROM categories c WHERE c.name='Networking'
ON CONFLICT (sku) DO NOTHING;
INSERT INTO products (sku,name,description,barcode,category_id,unit_of_measure,cost_price,selling_price,minimum_stock,reorder_level,maximum_stock,status)
SELECT 'PAPER-A4','A4 Copy Paper','500-sheet office paper','890100000003',c.id,'REAM',210,310,20,40,250,'ACTIVE' FROM categories c WHERE c.name='Office Supplies'
ON CONFLICT (sku) DO NOTHING;

INSERT INTO inventory (warehouse_id,zone_id,product_id,on_hand_quantity,reserved_quantity,damaged_quantity)
SELECT w.id,z.id,p.id, CASE p.sku WHEN 'LAP-14-PRO' THEN 28 WHEN 'ROUTER-AX' THEN 6 ELSE 120 END,0,0
FROM warehouses w JOIN warehouse_zones z ON z.warehouse_id=w.id AND z.code='Z-STORAGE'
JOIN products p ON p.sku IN ('LAP-14-PRO','ROUTER-AX','PAPER-A4')
ON CONFLICT (warehouse_id,zone_id,product_id) DO NOTHING;

INSERT INTO alerts (alert_type,severity,warehouse_id,product_id,title,message,status,created_at,resolved_at,updated_at)
SELECT 'LOW_STOCK','WARNING',w.id,p.id,'Router stock is below reorder level','Only 6 AX Wi-Fi Routers are available in Delhi Central Warehouse.','ACTIVE',CURRENT_TIMESTAMP,NULL,CURRENT_TIMESTAMP
FROM warehouses w JOIN products p ON p.sku='ROUTER-AX' WHERE w.code='WH-DEL-001'
ON CONFLICT DO NOTHING;

INSERT INTO notifications (recipient_user_id,notification_type,severity,title,message,warehouse_id,product_id,is_read,created_at,updated_at)
SELECT u.id,'LOW_STOCK','WARNING','Router stock needs attention','Delhi Central Warehouse is below the reorder point for AX Wi-Fi Router.',w.id,p.id,FALSE,CURRENT_TIMESTAMP,CURRENT_TIMESTAMP
FROM users u JOIN warehouses w ON w.code='WH-DEL-001' JOIN products p ON p.sku='ROUTER-AX'
WHERE u.status='ACTIVE'
ON CONFLICT DO NOTHING;
