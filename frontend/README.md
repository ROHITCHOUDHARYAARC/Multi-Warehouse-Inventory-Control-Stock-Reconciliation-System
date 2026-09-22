# Inventory Command Center

The frontend is a separate React + TypeScript + Vite application. It consumes the Spring Boot REST API and never stores backend secrets.

```powershell
Copy-Item .env.example .env
npm install
npm run dev
```

Set `VITE_API_BASE_URL` to the backend API (for example `http://localhost:8080/api/v1`). The protected `/warehouse-3d/:id` route loads the selected warehouse, zones and inventory from the backend. Zone positions are deterministic visualization coordinates, not surveyed physical measurements. Devices without WebGL receive an accessible 2D fallback.
