# Postman review demonstration

Import `backend/postman/Reviewer-Microservices-Demo.postman_collection.json` into Postman. It contains a short, repeatable reviewer flow and stores the JWT returned by Login automatically.

Before the demo, start the backend and set the collection variable `baseUrl` to the deployed gateway URL (or `http://localhost:8080` while the compatibility backend is used). Enter a valid administrator email and password in the Login body. The database deliberately has no committed default credential.

Run these requests in order:

1. **Gateway / Health** — proves the service is reachable without authentication.
2. **Identity / Login** — `POST /api/v1/auth/login`; the Tests tab stores `accessToken`.
3. **Identity / Current User** — proves JWT-authenticated access.
4. **Catalog / Warehouses** — lists warehouse master data through the protected API.
5. **Inventory / List Inventory** — shows live warehouse stock.
6. **Transfer / List Transfers** — demonstrates the transfer boundary.
7. **Notification / Alerts** — shows operational alerts.
8. **Audit / Recent Events** — demonstrates append-only audit retrieval.

For every request, show the method, URL, Headers (especially `Authorization: Bearer {{accessToken}}`), request body where present, and the JSON response. This is evidence that the frontend/API integration is exercised through HTTP; Postman is not an implementation of a microservice.

For the current compatibility deployment, all prefixes route to the existing Spring Boot application. After extraction, the exact same collection is sent to the API Gateway, which routes each prefix to the named service in `MICROSERVICES_ARCHITECTURE.md`.
