# System Architecture

The application is a two-process system: a React/Vite client and a Spring Boot REST API backed by PostgreSQL. Flyway owns schema changes; JPA uses `ddl-auto=validate` so the application never mutates the production schema implicitly.

```mermaid
flowchart TD
  U[User] --> F[React Frontend]
  F -->|REST / JSON + Bearer JWT| B[Spring Boot API]
  B --> S[Security, RBAC and warehouse scope]
  S --> D[(PostgreSQL)]
  B --> E[Domain events]
  E --> A[Audit, alerts and notifications]
  F --> V[Three.js warehouse visualization]
  V -->|warehouse, zone, inventory and alert APIs| B
```

## Module boundaries

- Authentication and authorization: JWT, BCrypt, Spring Security authorities and CORS.
- Master data: users, roles, warehouses, zones, categories, suppliers and products.
- Operations: inventory movements, immutable inventory transactions and stock transfers.
- Operational intelligence: alerts, in-app notifications and append-only audit history.
- Presentation: authenticated React routes and the warehouse 3D/2D visualization.

## Data flow

```mermaid
flowchart LR
  O[Inventory or transfer operation] --> T[Database transaction]
  T --> X[Inventory transaction + audit record]
  X --> E[After-commit domain event]
  E --> A[Alert evaluation]
  A --> N[Recipient preference + notification]
  T --> V[Inventory and visualization APIs]
  V --> UI[Dashboard and warehouse view]
```

## Deployment

The frontend is built to static files with Vite and must be served by a static web server. The backend is packaged as a Spring Boot JAR and reads database, JWT and frontend-origin settings from environment variables. PostgreSQL must be reachable before the backend starts so Flyway can validate and migrate the schema.
