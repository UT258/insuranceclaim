# ClaimInsight360

ClaimInsight360 is a Phase-1 insurance claims analytics platform built with Spring Boot microservices, React, and SQL schema definitions.

## Microservice Architecture

- `services/api-gateway` (Port `8080`): Unified entry point.
- `services/claims-service` (Port `8081`): Claim ingestion and dashboard summary.
- `services/identity-service` (Port `8082`): User and role management APIs.
- `services/analytics-service` (Port `8083`): KPI APIs.
- `services/notification-service` (Port `8084`): In-app alert APIs.
- `services/service-registry` (Port `8761`): Registry service placeholder.

## DTO-Based APIs

- Claims Service: `/api/v1/feeds`, `/api/v1/raw-claims`, `/api/v1/dashboard/summary`
- Identity Service: `/api/v1/users`
- Analytics Service: `/api/v1/kpis`
- Notification Service: `/api/v1/notifications`
- Gateway Routes:
  - `/api/v1/claims/dashboard`
  - `/api/v1/identity/users`
  - `/api/v1/analytics/kpis`
  - `/api/v1/notifications`

## Frontend

- React app located at `frontend`.
- Dashboard reads data from the API gateway at `http://localhost:8080/api/v1`.

## SQL Schema

- Canonical schema file: `db/schema.sql`

## Run Locally

### Backend services

Run each service from its folder:

```bash
./mvnw spring-boot:run
```

Recommended order:
1. `services/service-registry`
2. `services/claims-service`
3. `services/identity-service`
4. `services/analytics-service`
5. `services/notification-service`
6. `services/api-gateway`

### Frontend

```bash
cd frontend
npm install
npm run dev
```

Open: `http://localhost:5173`
