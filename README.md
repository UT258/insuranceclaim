# ClaimInsight360 - Insurance Claims Analytics Platform

ClaimInsight360 is a comprehensive insurance claims analytics platform built with **9 microservices architecture**, Spring Boot 3.2.0, MySQL 8.0, and React 19. It provides end-to-end claim processing, fraud detection, denial analysis, performance tracking, and cost management capabilities.

## 🏗️ Architecture Overview

### Microservices (9 Services)

1. **service-registry** (Port 8761) - Eureka Service Discovery
2. **api-gateway** (Port 8080) - Unified API Gateway with routing
3. **identity-service** (Port 8082) - User & Role Management
4. **claims-service** (Port 8081) - Core Claims Management
5. **analytics-service** (Port 8083) - Business Intelligence & KPIs
6. **notification-service** (Port 8084) - Alerts & Notifications
7. **data-ingestion-service** (Port 8085) - Data Feed Management
8. **fraud-risk-service** (Port 8086) - Risk Scoring & Fraud Detection
9. **denial-analysis-service** (Port 8087) - Denial Pattern Analysis
10. **performance-analytics-service** (Port 8088) - Adjuster Performance & SLA
11. **cost-reserve-service** (Port 8089) - Cost Tracking & Reserves
12. **dashboard-service** (Port 8090) - Analytics Reports

### Technology Stack

- **Backend**: Spring Boot 3.2.0, Java 17
- **Service Discovery**: Spring Cloud Netflix Eureka
- **Database**: MySQL 8.0 (separate schema per service)
- **Frontend**: React 19, Vite 7, React Router 6
- **Container**: Docker & Docker Compose
- **Build Tool**: Maven 3.9+

## 📦 Prerequisites

- Java 17 or higher
- Maven 3.9+
- Node.js 18+ and npm
- MySQL 8.0
- Docker & Docker Compose (for containerized deployment)

## 🗄️ Database Setup

### Create MySQL Databases

Each microservice uses its own database. Create them using:

```sql
CREATE DATABASE claiminsight_identity;
CREATE DATABASE claiminsight_claims;
CREATE DATABASE claiminsight_analytics;
CREATE DATABASE claiminsight_notification;
CREATE DATABASE claiminsight_data_ingestion;
CREATE DATABASE claiminsight_fraud_risk;
CREATE DATABASE claiminsight_denial_analysis;
CREATE DATABASE claiminsight_performance;
CREATE DATABASE claiminsight_cost_reserve;
CREATE DATABASE claiminsight_dashboard;
```

### Configure Database Connection

Update `application.properties` in each service with your MySQL credentials:

**Local Development** (localhost):
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/claiminsight_<service_name>
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

**Docker Deployment** (uses Docker MySQL container):
```properties
spring.datasource.url=jdbc:mysql://mysql:3306/claiminsight_<service_name>
spring.datasource.username=root
spring.datasource.password=root
```

## 🚀 Running the Application

### Option 1: Docker Compose (Recommended)

Run all services together with MySQL:

```bash
# Start all services
docker-compose up -d

# View logs
docker-compose logs -f

# Stop all services
docker-compose down
```

Services will be available at:
- Frontend: http://localhost:3000
- API Gateway: http://localhost:8080
- Eureka Dashboard: http://localhost:8761

### Option 2: Local Development

#### 1. Start MySQL
Ensure MySQL is running on localhost:3306

#### 2. Start Backend Services

Run in this order:

```bash
# 1. Service Registry (Eureka)
cd services/service-registry
./mvnw spring-boot:run

# 2. Domain Services (in separate terminals)
cd services/identity-service && ./mvnw spring-boot:run
cd services/claims-service && ./mvnw spring-boot:run
cd services/analytics-service && ./mvnw spring-boot:run
cd services/notification-service && ./mvnw spring-boot:run
cd services/data-ingestion-service && ./mvnw spring-boot:run
cd services/fraud-risk-service && ./mvnw spring-boot:run
cd services/denial-analysis-service && ./mvnw spring-boot:run
cd services/performance-analytics-service && ./mvnw spring-boot:run
cd services/cost-reserve-service && ./mvnw spring-boot:run
cd services/dashboard-service && ./mvnw spring-boot:run

# 3. API Gateway (last)
cd services/api-gateway && ./mvnw spring-boot:run
```

#### 3. Start Frontend

```bash
cd frontend
npm install
npm run dev
```

Frontend will be available at http://localhost:3000

## 📋 API Documentation

### API Gateway Routes

All requests go through http://localhost:8080

#### Data Ingestion Service
- `GET/POST /api/v1/data-ingestion/feeds` - Manage data feeds
- `GET/POST /api/v1/data-ingestion/claims-raw` - Raw claim data

#### Fraud & Risk Service
- `GET/POST /api/v1/fraud-risk/indicators` - Risk indicators
- `GET/POST /api/v1/fraud-risk/scores` - Risk scores
- `GET /api/v1/fraud-risk/scores/claim/{claimId}` - Calculate risk score

#### Denial Analysis Service
- `GET/POST /api/v1/denial-analysis/patterns` - Denial patterns
- `GET/POST /api/v1/denial-analysis/leakages` - Leakage flags
- `GET /api/v1/denial-analysis/leakages/total` - Total leakage amount

#### Performance Analytics Service
- `GET/POST /api/v1/performance/adjuster` - Adjuster performance
- `GET/POST /api/v1/performance/sla-violations` - SLA violations

#### Cost & Reserve Service
- `GET/POST /api/v1/cost-reserve/costs` - Claim costs
- `GET/POST /api/v1/cost-reserve/reserves` - Claim reserves
- `GET/POST /api/v1/cost-reserve/aging` - Aging records

#### Dashboard Service
- `GET/POST /api/v1/dashboard/reports` - Analytics reports

#### Identity Service
- `GET/POST /api/v1/identity/users` - User management

#### Notification Service
- `GET/POST /api/v1/notifications` - Alerts and notifications

## 🎨 Frontend Features

- **Responsive Dashboard** with key metrics and charts
- **Data Ingestion Management** - Monitor data feeds
- **Fraud & Risk Detection** - View risk indicators and scores
- **Denial Analysis** - Track leakages and denial patterns
- **Performance Tracking** - Monitor adjuster productivity and SLA violations
- **Cost & Reserve Analytics** - Financial tracking and aging analysis
- **Report Generation** - Create and view analytics reports
- **Notification Center** - Alert management

## 🔧 Configuration

### Service Registry (Eureka)
- Dashboard: http://localhost:8761
- All microservices register with Eureka for service discovery

### API Gateway Routes
The gateway uses Spring Cloud Gateway to route requests to appropriate microservices based on URL patterns.

## 📊 Database Schema

Each service uses JPA/Hibernate with `spring.jpa.hibernate.ddl-auto=update` to auto-create tables. The schema includes:

- **DataFeed, ClaimRaw** - Data ingestion entities
- **RiskIndicator, RiskScore** - Fraud detection entities
- **DenialPattern, LeakageFlag** - Denial analysis entities
- **AdjusterPerformance, SLAViolation** - Performance tracking
- **ClaimCost, ClaimReserve, AgingRecord** - Cost management
- **AnalyticsReport** - Dashboard reports
- **User, Role** - Identity management
- **Notification** - Alert entities

## 🐳 Docker Configuration

The `docker-compose.yml` includes:
- MySQL 8.0 with persistent volume
- All 11 microservices with health checks
- Frontend React application
- Service dependencies and networking
- Environment variables for database URLs

## 🛠️ Development

### Build All Services

```bash
# Build all backend services
mvn clean install -DskipTests

# Build frontend
cd frontend && npm run build
```

### Run Tests

```bash
# Backend tests
mvn test

# Frontend tests
cd frontend && npm test
```

## 📝 Notes

- Each microservice has its own database for data isolation
- Services use Eureka for service discovery and load balancing
- API Gateway provides single entry point for all client requests
- Frontend uses Vite proxy to avoid CORS issues during development
- All services include health check endpoints for Docker monitoring

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 License

This project is part of ClaimInsight360 insurance analytics platform.

---

**Happy Claiming! 🎉**
