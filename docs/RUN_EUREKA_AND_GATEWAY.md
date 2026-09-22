# Run Eureka, the API Gateway, and Inventory service

Prerequisite: Java 17, PostgreSQL, Maven 3.9+, and the backend environment variables described below. Run each command in a **separate VS Code PowerShell terminal**. The first run downloads Spring Cloud dependencies.

## Terminal 1 — Eureka discovery server

```powershell
Set-Location -LiteralPath "discovery-server"
mvn spring-boot:run
```

Open `http://localhost:8761`. The Eureka dashboard initially shows no registered instances.

## Terminal 2 — Inventory service

```powershell
Set-Location -LiteralPath "backend"
$env:DB_URL = "jdbc:postgresql://localhost:5432/multi_warehouse_inventory_db"
$env:DB_USERNAME = "inventory_app"
$env:DB_PASSWORD = "Rohit123"
$env:JWT_SECRET = "development-secret-key-change-this-2026!"
$env:FRONTEND_URL = "http://localhost:5173"
$env:SERVER_PORT = "8081"
mvn spring-boot:run
```

Refresh the Eureka dashboard. Under **Instances currently registered with Eureka**, it should show `INVENTORY-COMMAND-CENTER` with one instance.

## Terminal 3 — API Gateway

```powershell
Set-Location -LiteralPath "api-gateway"
mvn spring-boot:run
```

Refresh Eureka. It should now show both `API-GATEWAY` and `INVENTORY-COMMAND-CENTER`.

## Optional service registrations for the review

The following are independently runnable Eureka client applications. Start each in its own terminal after Eureka, using the same Maven executable. They expose health endpoints and register the planned extraction boundaries; the working business endpoints remain in `INVENTORY-COMMAND-CENTER` until each module is fully extracted.

```powershell
$env:EUREKA_DEFAULT_ZONE = "http://localhost:8761/eureka/"
Set-Location ".\identity-service"; & (Join-Path $env:TEMP "inventory-maven-3.9.11\bin\mvn.cmd") "-Dmaven.repo.local=../api-gateway/.m2/repository" spring-boot:run
```

Repeat in separate terminals, changing the folder:

```text
catalog-service       (8083)
transfer-service      (8084)
notification-service  (8085)
```

After they start, Eureka displays `IDENTITY-SERVICE`, `CATALOG-SERVICE`, `TRANSFER-SERVICE`, and `NOTIFICATION-SERVICE` alongside the Gateway and Inventory service.

## Verify the gateway

In Postman set `baseUrl` to `http://localhost:8080`, then send `GET {{baseUrl}}/api/v1/health`. The request enters API Gateway, uses Eureka service discovery to find `INVENTORY-COMMAND-CENTER`, and returns the backend health response.

The gateway is intentionally the only public API endpoint. Do not call the inventory service directly on port 8081 during the demonstration.
