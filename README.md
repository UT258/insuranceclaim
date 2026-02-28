# ClaimInsight360 - Microservices Architecture

## Overview
ClaimInsight360 is a comprehensive Insurance Claims Analytics & Intelligence Platform built using Spring Boot microservices architecture. The platform provides real-time operational visibility into claims portfolios with advanced analytics, fraud detection, and performance monitoring capabilities.

## Architecture Components

### Infrastructure Services
1. **Eureka Server** (Port: 8761) - Service Discovery and Registry
2. **API Gateway** (Port: 8080) - Single entry point with routing, load balancing, and JWT authentication
3. **Config Server** (Optional) - Centralized configuration management

### Business Microservices
1. **IAM Service** (Port: 8081) - Identity & Access Management
   - User authentication and authorization
   - JWT token generation
   - Audit logging
   - RBAC implementation

2. **Data Ingestion Service** (Port: 8082) - Claim Data Aggregator
   - Data feed management
   - Claim data ingestion
   - Raw data storage
   - Batch processing support

3. **Metrics Engine Service** (Port: 8083) - Claims KPI Calculator
   - TAT (Turnaround Time) calculation
   - Severity and frequency metrics
   - Loss ratio computation
   - Settlement time analytics

4. **Fraud Risk Service** (Port: 8084) - Fraud Detection & Risk Scoring
   - Risk indicator detection
   - Anomaly flagging
   - Risk score calculation
   - Pattern matching

5. **Denial Analysis Service** (Port: 8085) - Denial & Leakage Analysis
   - Denial pattern detection
   - Root cause analysis
   - Leakage identification
   - Error code tracking

6. **Adjuster Performance Service** (Port: 8086) - Operations Analytics
   - Adjuster productivity tracking
   - Workload management
   - SLA monitoring
   - Quality scoring

7. **Cost Reserve Service** (Port: 8087) - Financial Analytics
   - Cost tracking by category
   - Reserve management
   - Aging analysis
   - Trend analytics

8. **Dashboard Reports Service** (Port: 8088) - Reporting & Visualization
   - Aggregated analytics
   - Custom report generation
   - Cross-service data aggregation
   - Export capabilities

9. **Notification Service** (Port: 8089) - Alerts & Notifications
   - Real-time alerts
   - Anomaly notifications
   - Threshold breach alerts
   - Email/In-app notifications

### Shared Libraries
- **Common Library** - Shared DTOs, exceptions, utilities

## Technology Stack

### Backend
- **Framework**: Spring Boot 3.2.2
- **Language**: Java 17
- **Service Discovery**: Netflix Eureka
- **API Gateway**: Spring Cloud Gateway
- **Security**: Spring Security + JWT
- **Database**: H2 (Dev), PostgreSQL (Prod)
- **ORM**: Spring Data JPA / Hibernate
- **Documentation**: SpringDoc OpenAPI (Swagger)
- **Resilience**: Resilience4j (Circuit Breaker)
- **Build Tool**: Maven

### Frontend (Future)
- React or Angular
- REST API integration

## Project Structure

```
claiminsight360/
├── eureka-server/               # Service Discovery
├── api-gateway/                 # API Gateway
├── config-server/               # Config Server (Optional)
├── common-lib/                  # Shared library
├── iam-service/                 # IAM microservice
├── data-ingestion-service/      # Data Ingestion microservice
├── metrics-engine-service/      # Metrics Engine microservice
├── fraud-risk-service/          # Fraud Risk microservice
├── denial-analysis-service/     # Denial Analysis microservice
├── adjuster-performance-service/# Adjuster Performance microservice
├── cost-reserve-service/        # Cost Reserve microservice
├── dashboard-reports-service/   # Dashboard Reports microservice
├── notification-service/        # Notification microservice
├── docker-compose.yml           # Docker Compose configuration
└── README.md                    # This file
```

## Getting Started

### Prerequisites
- JDK 17 or higher
- Maven 3.6+
- PostgreSQL (for production)
- Docker & Docker Compose (optional)

### Build and Run

#### Option 1: Manual Build

1. **Build Common Library**
```bash
cd common-lib
mvn clean install
```

2. **Start Eureka Server**
```bash
cd eureka-server
mvn spring-boot:run
```
Access Eureka Dashboard: http://localhost:8761

3. **Start API Gateway**
```bash
cd api-gateway
mvn spring-boot:run
```

4. **Start Microservices** (in separate terminals)
```bash
# IAM Service
cd iam-service
mvn spring-boot:run

# Data Ingestion Service
cd data-ingestion-service
mvn spring-boot:run

# Metrics Engine Service
cd metrics-engine-service
mvn spring-boot:run

# Fraud Risk Service
cd fraud-risk-service
mvn spring-boot:run

# Other services...
```

#### Option 2: Docker Compose
```bash
docker-compose up -d
```

### API Documentation

Once services are running, access Swagger UI for each service:

- **IAM Service**: http://localhost:8081/swagger-ui.html
- **Data Ingestion**: http://localhost:8082/swagger-ui.html
- **Metrics Engine**: http://localhost:8083/swagger-ui.html
- **Fraud Risk**: http://localhost:8084/swagger-ui.html
- **Denial Analysis**: http://localhost:8085/swagger-ui.html
- **Adjuster Performance**: http://localhost:8086/swagger-ui.html
- **Cost Reserve**: http://localhost:8087/swagger-ui.html
- **Dashboard Reports**: http://localhost:8088/swagger-ui.html
- **Notification**: http://localhost:8089/swagger-ui.html

### API Gateway Routes

All microservices are accessible through the API Gateway at http://localhost:8080

- **IAM**: `/api/iam/**`
- **Data Ingestion**: `/api/data-ingestion/**`
- **Metrics**: `/api/metrics/**`
- **Fraud Risk**: `/api/fraud-risk/**`
- **Denial**: `/api/denial/**`
- **Adjuster**: `/api/adjuster/**`
- **Cost Reserve**: `/api/cost-reserve/**`
- **Dashboard**: `/api/dashboard/**`
- **Notifications**: `/api/notifications/**`

## Authentication

### Register a User
```bash
POST http://localhost:8080/api/iam/auth/register
Content-Type: application/json

{
  "name": "John Doe",
  "email": "john.doe@example.com",
  "password": "password123",
  "phone": "+1234567890",
  "role": "CLAIMS_ANALYST"
}
```

### Login
```bash
POST http://localhost:8080/api/iam/auth/login
Content-Type: application/json

{
  "email": "john.doe@example.com",
  "password": "password123"
}
```

Response includes JWT token:
```json
{
  "success": true,
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "tokenType": "Bearer",
    "user": { ... }
  }
}
```

### Use Token
Include the token in all subsequent requests:
```bash
Authorization: Bearer {token}
```

## User Roles

- **CLAIMS_ANALYST**: Analyzes claim trends, denials, cost drivers
- **CLAIMS_MANAGER**: Oversees performance metrics, delays, workloads
- **FRAUD_ANALYST**: Uses risk dashboards and red-flag indicators
- **ACTUARY**: Uses severity/frequency insights for pricing/reserving
- **OPERATIONS_EXECUTIVE**: Monitors KPIs, TAT, compliance SLAs
- **ADMIN**: Full system access and configuration

## Database Configuration

### Development (H2)
Each microservice uses an in-memory H2 database by default.
Access H2 Console: http://localhost:{port}/h2-console

### Production (PostgreSQL)
Update `application.properties` in each service:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/{db_name}
spring.datasource.username=postgres
spring.datasource.password=yourpassword
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

## Monitoring & Observability

### Health Checks
- Eureka Dashboard: http://localhost:8761
- Actuator endpoints: http://localhost:{port}/actuator/health

### Circuit Breaker
API Gateway includes Resilience4j circuit breakers for all service routes.

### Logging
Each service logs to console. Configure file logging in `application.properties`:
```properties
logging.file.name=logs/{service-name}.log
logging.level.com.claiminsight=DEBUG
```

## Development Guidelines

### Adding a New Endpoint
1. Create entity in `entity` package
2. Create repository extending `JpaRepository`
3. Implement business logic in `service` package
4. Create REST controller in `controller` package
5. Add route in API Gateway if needed

### Inter-Service Communication
Use Spring Cloud OpenFeign for synchronous REST calls between services.

Example:
```java
@FeignClient(name = "data-ingestion-service")
public interface DataIngestionClient {
    @GetMapping("/claims/{claimId}")
    ClaimRaw getClaimByClaimId(@PathVariable String claimId);
}
```

## Testing

### Run Unit Tests
```bash
mvn test
```

### Run Integration Tests
```bash
mvn verify
```

### Test Coverage
```bash
mvn jacoco:report
```

## Deployment

### Docker Build
```bash
# Build all services
mvn clean package -DskipTests

# Build Docker images
docker-compose build
```

### Kubernetes
Kubernetes manifests will be provided in the `/k8s` directory.

## Troubleshooting

### Service Not Registering with Eureka
- Check Eureka Server is running on port 8761
- Verify `eureka.client.service-url.defaultZone` in application.properties
- Check network connectivity

### JWT Authentication Failing
- Ensure JWT secret is same in IAM Service and API Gateway
- Check token expiration time
- Verify Authorization header format: `Bearer {token}`

### Circuit Breaker Tripping
- Check target service health
- Review circuit breaker configuration
- Check service response times

## Future Enhancements

- [ ] Message broker integration (RabbitMQ/Kafka)
- [ ] Distributed tracing (Spring Cloud Sleuth + Zipkin)
- [ ] Centralized logging (ELK Stack)
- [ ] API rate limiting
- [ ] Caching layer (Redis)
- [ ] ML-based fraud detection
- [ ] Real-time dashboard with WebSocket
- [ ] Multi-tenancy support

## Contributing

1. Create feature branch
2. Implement changes with tests
3. Ensure all tests pass
4. Submit pull request

## License

Proprietary - ClaimInsight360 © 2026

## Support

For support and queries, contact the development team.

---

**Version**: 1.0.0-SNAPSHOT  
**Last Updated**: February 2026  
**Status**: Active Development

#   d e m o  
 