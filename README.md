# Multi-Warehouse Inventory Control & Stock Reconciliation System

An inventory operations application with a Spring Boot/PostgreSQL REST API and a React/Vite web client. It supports JWT-authenticated master data, inventory movements, stock transfers, alerts, notifications, audit history and a data-driven warehouse visualization.

## Architecture

```text
frontend/  React + TypeScript + Vite + React Three Fiber
backend/   Spring Boot REST API + Spring Security + Flyway
database/  PostgreSQL
```

See [the architecture document](docs/ARCHITECTURE.md) for component and event-flow diagrams.
See [the problem analysis and requirements specification](docs/REQUIREMENTS_SPECIFICATION.md) for the review rubric evidence.

## Prerequisites

- Java 17
- PostgreSQL with permission to create extensions/tables in the target database
- Node.js 20+ and npm

## Configuration

Copy `backend/.env.example` values into your shell or deployment environment. Required variables are `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, and a random `JWT_SECRET` of at least 32 bytes. Set `FRONTEND_URL` to the exact browser origin allowed by CORS.

Create `frontend/.env` from `frontend/.env.example` and set `VITE_API_BASE_URL`, for example `http://localhost:8080/api/v1`. Frontend environment values are public and must never contain credentials or JWT secrets.

## Run locally

```powershell
cd backend
mvnw.cmd spring-boot:run
```

```powershell
cd frontend
npm install
npm run dev
```

Flyway applies the migrations from `backend/src/main/resources/db/migration` at backend startup. JPA validates rather than updates the schema.

## Build and test

```powershell
cd backend
mvnw.cmd clean test
mvnw.cmd clean package
```

```powershell
cd frontend
npm install
npm run build
```

The current frontend does not define `lint` or `test` scripts, so those commands must not be claimed as passing.

## API and operational tools

- Health: `GET /api/v1/health` and `GET /actuator/health`
- Swagger UI: `/swagger-ui/index.html`
- OpenAPI JSON: `/v3/api-docs`
- Postman collections: `backend/postman/`
- Login: `POST /api/v1/auth/login`

## Service architecture and Postman review

The current application is a modular monolith, with planned service boundaries for Gateway, Identity, Catalog, Inventory, Transfer, Notification, and Audit. See [the service architecture and safe extraction plan](docs/MICROSERVICES_ARCHITECTURE.md). Do not represent the current single Spring Boot deployment as already split into independently deployed microservices.

For a reviewer demonstration, import `backend/postman/Reviewer-Microservices-Demo.postman_collection.json` into Postman. The Login request saves the returned JWT and the remaining requests demonstrate the Identity, Catalog, Inventory, Transfer, Notification, and Audit API boundaries. See [the exact Postman demonstration sequence](docs/POSTMAN_REVIEW_DEMO.md).

The runnable discovery/gateway setup is in [the Eureka and API Gateway run guide](docs/RUN_EUREKA_AND_GATEWAY.md). It runs `discovery-server` (Eureka), `api-gateway`, and the existing inventory backend as independently running applications.

## 3D warehouse

From Dashboard or Warehouses, select **Open 3D**. The screen loads warehouse, zone, inventory and active-alert data from the API. Zone colour is computed from the returned operational state; no stock values are hard-coded. When WebGL is unavailable, the page renders a selectable 2D zone fallback.

## PostgreSQL backup and reset

Back up with `pg_dump -Fc -d <database> -f inventory.backup`. For a disposable development reset, drop and recreate the development database, then start the backend to rerun Flyway. Never use this procedure against production without an approved backup and change plan.

## Deployment

1. Provision PostgreSQL and configure the backend variables.
2. Build and run the backend JAR; verify `/actuator/health`.
3. Build the frontend with the production API base URL and serve `frontend/dist` from a static web server.
4. Configure `FRONTEND_URL` to that static-server origin, verify login, inventory, alerts, and the warehouse visualization.

## Security

Passwords use BCrypt and are not returned by APIs. JWT signing keys are environment-only; CORS uses an explicit configured origin. Authorization is permission-based, with additional warehouse checks in operational services. Error responses are standardized and avoid exposing stack traces.

## Known limitations

- Stock-count/reconciliation and analytics database tables exist, but their backend APIs and frontend workflows are not implemented in the current source.
- The 3D layout is a deterministic operational visualization, not surveyed physical geometry.
- Email/SMS delivery and a browser E2E test suite are not implemented.

Use [the manual E2E checklist](docs/MANUAL_E2E_CHECKLIST.md) to record verification against a disposable database.
