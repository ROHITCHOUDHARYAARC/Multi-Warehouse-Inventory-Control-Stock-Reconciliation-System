# Microservices architecture and migration plan

## Current implementation

The checked-in application is a **modular monolith**: one Spring Boot deployment owns all business modules and one PostgreSQL schema. It must be described this way during the review. Calling the current source a microservice system would be inaccurate.

## Target service boundaries

The code is organised by the following business capabilities, which are the proposed independently deployable services:

| Service | Port | Owns | Main API prefix |
| --- | ---: | --- | --- |
| API Gateway | 8080 | Routing, CORS, request correlation | `/api/v1/**` |
| Inventory Service (current compatibility service) | 8081 | Current modular backend, including auth, catalog, inventory and operations | `/api/v1/**` |
| Identity Service (extraction shell) | 8082 | Future users, roles, permissions, JWT authentication | `/api/v1/auth`, `/api/v1/users`, `/api/v1/roles` |
| Catalog Service (extraction shell) | 8083 | Future warehouses, zones, categories, suppliers, products | `/api/v1/warehouses`, `/api/v1/products`, `/api/v1/categories`, `/api/v1/suppliers` |
| Transfer Service | 8084 | Inter-warehouse transfer workflow | `/api/v1/transfers` |
| Notification Service (extraction shell) | 8085 | Future alerts, notification preferences and notifications | `/api/v1/alerts`, `/api/v1/notifications` |
| Audit Service | 8086 | Append-only audit records | `/api/v1/audit` |

Each service gets its own PostgreSQL database in the final deployment (`identity_db`, `catalog_db`, `inventory_db`, `transfer_db`, `notification_db`, and `audit_db`). Services do not directly query another service's tables.

## Communication

The frontend calls only the API Gateway. The gateway validates/routs requests and passes the correlation ID. Synchronous REST is suitable for reads and request/response operations. State-change notifications are asynchronous domain events, sent through RabbitMQ or Kafka:

```text
Inventory Service --StockAdjusted--> Notification Service
Inventory Service --StockAdjusted--> Audit Service
Transfer Service  --TransferCompleted--> Inventory Service
Transfer Service  --TransferCompleted--> Notification Service
Identity Service  --UserChanged-------> Audit Service
```

## Safe migration sequence

1. Keep `backend/` running as the compatibility application while the frontend is pointed at the API Gateway.
2. Extract Identity and Catalog first; replace their in-process calls with REST clients.
3. Extract Inventory and Transfer, publishing the events listed above through a transactional outbox.
4. Extract Notification and Audit consumers and give every service its database.
5. Remove the compatibility application only after gateway contract tests and Postman smoke tests pass.

This avoids a dangerous copy-and-paste split that would leave several services sharing mutable JPA entities and tables. The target is therefore a real database-per-service architecture, not several applications pointing to one shared schema.
