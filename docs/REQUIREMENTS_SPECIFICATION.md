# Problem analysis and requirement specification

## Problem

Inventory is distributed across warehouses, so operators need a single view of stock, transfers, alerts, and audit history. Manual spreadsheets make stock movements difficult to authorize and reconcile, and they do not provide a reliable record of who changed stock.

## Functional requirements

- Users authenticate with email/password and receive a short-lived JWT.
- Roles and explicit permissions control every protected operation.
- Administrators manage users, roles, warehouses, zones, products, categories, and suppliers.
- Authorized operators record stock-in, stock-out, reservations, releases, and adjustments.
- Managers create and approve inter-warehouse transfers through a state machine.
- The system calculates low-stock/out-of-stock alerts and displays notifications.
- Audit records capture security and operational changes.
- A React client consumes REST APIs through the API Gateway.

## Non-functional requirements

- Java 17 and Spring Boot 3.5 services.
- PostgreSQL persistence with Flyway-forward migrations.
- Stateless JWT authentication; secrets are environment variables and never committed.
- Explicit CORS origin, validation, request IDs, standardized errors, and health endpoints.
- Eureka service discovery and load-balanced Gateway routing.
- Postman collection for repeatable HTTP acceptance demonstrations.

## Rubric evidence map

| Rubric item | Evidence in this repository |
| --- | --- |
| Problem analysis / requirements | This document and `docs/ARCHITECTURE.md` |
| Service identification / discovery | `discovery-server`, `api-gateway`, backend Eureka client, `docs/MICROSERVICES_ARCHITECTURE.md` |
| JWT authentication | `backend/src/main/java/.../security`, login request in the reviewer Postman collection, edge bearer guard |
| API Gateway | `api-gateway/src/main/resources/application.yml`, load-balanced `lb://inventory-command-center` routes |
| HTTP demonstration | `backend/postman/Reviewer-Microservices-Demo.postman_collection.json` |
