export type UUID=string;
export type WarehouseStatus='ACTIVE'|'INACTIVE'|'MAINTENANCE'; export type InventoryStatus='NORMAL'|'LOW_STOCK'|'OUT_OF_STOCK'|'OVERSTOCK';
export interface Warehouse{id:UUID;code:string;name:string;city?:string;state?:string;status:WarehouseStatus;capacityQuantity:number}
export interface Zone{id:UUID;code:string;name:string;status:WarehouseStatus;zoneType:string;capacityQuantity:number}
export interface Inventory{id:UUID;warehouseId:UUID;warehouseCode:string;zoneId:UUID;zoneCode:string;productId:UUID;sku:string;productName:string;onHandQuantity:number;reservedQuantity:number;damagedQuantity:number;availableQuantity:number;status:InventoryStatus;updatedAt:string}
export interface Alert{id:UUID;type:string;severity:'INFO'|'SUCCESS'|'WARNING'|'CRITICAL';title:string;message:string;warehouseId?:UUID;productId?:UUID;status:string;createdAt:string}
export interface Notification{id:UUID;type:string;severity:string;title:string;message:string;read:boolean;createdAt:string;warehouseId?:UUID;productId?:UUID}
export interface TransferItem{productId:UUID;sku:string;productName:string;sourceZoneId:UUID;destinationZoneId:UUID;requestedQuantity:number;approvedQuantity:number;pickedQuantity:number;dispatchedQuantity:number;receivedQuantity:number}
export interface Transfer{id:UUID;transferNumber:string;status:'PENDING'|'APPROVED'|'PICKED'|'IN_TRANSIT'|'RECEIVED'|'COMPLETED'|'REJECTED'|'CANCELLED';sourceWarehouseId:UUID;sourceWarehouse:string;destinationWarehouseId:UUID;destinationWarehouse:string;items:TransferItem[];reason:string;notes?:string;requestedAt:string;approvedAt?:string;dispatchedAt?:string;receivedAt?:string;completedAt?:string}
export interface ReplenishmentRecommendation{productId:UUID;sku:string;productName:string;sourceWarehouseId:UUID;sourceWarehouse:string;destinationWarehouseId:UUID;destinationWarehouse:string;suggestedQuantity:number;destinationAvailable:number;reorderLevel:number;priorityScore:number;reason:string}
export interface User{ id:UUID;username:string;email:string;firstName:string;lastName:string;roles:string[];status:string;warehouses?:{id:UUID;code:string;name:string}[] }
export interface Page<T>{content:T[];totalElements:number;totalPages:number;number:number;size:number}
