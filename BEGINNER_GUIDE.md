# ClaimInsight360 Beginner Guide

This guide helps you understand the project quickly if you are new to microservices.

## 1) What this project does

ClaimInsight360 is an insurance claims analytics platform.
It collects claim data, analyzes fraud and denials, tracks performance/costs, and shows dashboards/reports.

## 2) How it is structured

- `frontend/` – React web app (UI)
- `services/` – backend microservices (Spring Boot)
- `docker-compose.yml` – runs all services together
- `db/schema.sql` – database schema reference

## 3) Main services (simple view)

- `service-registry` – service discovery
- `api-gateway` – central backend entry point
- `claims-service` – claim operations and dashboard summary
- `identity-service` – user records (used by login screen)
- `analytics-service` – KPI data
- `notification-service` – alerts/notifications
- `data-ingestion-service` – ingesting feed/raw claims
- `fraud-risk-service` – fraud indicators and risk scores
- `denial-analysis-service` – denials and leakage analysis
- `performance-analytics-service` – adjuster and SLA analytics
- `cost-reserve-service` – costs, reserves, aging
- `dashboard-service` – report generation and listing

## 4) Request flow (easy mental model)

1. Browser opens frontend at `http://localhost:3000`
2. Frontend calls `/api/...`
3. Vite proxy forwards each route to the correct backend service
4. Service reads/writes MySQL and returns data
5. UI updates tables/cards/charts

## 5) Run the app

```bash
docker-compose up -d --build
```

Useful URLs:
- Frontend: `http://localhost:3000`
- Gateway health: `http://localhost:8080/api/v1/health`
- Eureka: `http://localhost:8761`
- Swagger UI: `http://localhost:8080/swagger/index.html`

## 6) Where to start reading code

If you are new, start in this order:

1. `frontend/src/App.jsx` – routes and navigation
2. `frontend/src/api.js` – all frontend API calls
3. One page, e.g. `frontend/src/pages/Dashboard.jsx`
4. Matching backend controller (for dashboard):
   `services/claims-service/src/main/java/com/claiminsight360/backend/controller/ClaimsOperationalController.java`
5. Service layer and domain entities of that microservice

## 7) Beginner tips for changes

- Change one page + one backend service at a time.
- Keep endpoint naming consistent (`/api/...`).
- Validate quickly with browser + service logs:
  ```bash
  docker-compose logs -f frontend claims-service
  ```
- If UI shows empty data, test API first with curl.

## 8) Common issues

- **Frontend up but no data**: proxy route mismatch or backend not running.
- **Service exits on startup**: check docker logs for dependency/config error.
- **MySQL problems**: ensure `mysql` container is healthy.

---
If you want, the next step is adding sequence diagrams for each module flow (ingestion → fraud → denial → reporting).
