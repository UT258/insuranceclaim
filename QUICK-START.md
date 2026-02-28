# ClaimInsight360 - Quick Start Guide

## 🚀 Getting Started in 5 Minutes

### Prerequisites
- ✅ Java 17 or higher installed
- ✅ Maven 3.6+ installed
- ✅ Git installed
- ✅ IDE (IntelliJ IDEA, Eclipse, or VS Code)

### Step 1: Build the Project (2 minutes)

Open PowerShell and navigate to the project directory:

```powershell
cd "D:\New folder\Java SpringBoot\demo"

# Run the automated build script
.\build-all.ps1
```

This script will:
1. Build the common library
2. Build Eureka Server
3. Build API Gateway
4. Build all microservices

### Step 2: Start Infrastructure Services (1 minute)

**Terminal 1 - Start Eureka Server:**
```powershell
cd eureka-server
mvn spring-boot:run
```

Wait for the message: `Started EurekaServerApplication`

**Terminal 2 - Start API Gateway:**
```powershell
cd api-gateway
mvn spring-boot:run
```

Wait for the message: `Started ApiGatewayApplication`

### Step 3: Start Business Services (2 minutes)

**Terminal 3 - Start IAM Service:**
```powershell
cd iam-service
mvn spring-boot:run
```

**Terminal 4 - Start Data Ingestion Service:**
```powershell
cd data-ingestion-service
mvn spring-boot:run
```

**Optional - Start other services as needed:**
```powershell
# In separate terminals
cd metrics-engine-service && mvn spring-boot:run
cd fraud-risk-service && mvn spring-boot:run
cd denial-analysis-service && mvn spring-boot:run
# ... and so on
```

### Step 4: Verify Services are Running

1. **Check Eureka Dashboard:** http://localhost:8761
   - You should see all started services registered

2. **Check API Gateway:** http://localhost:8080/actuator/health
   - Should return: `{"status":"UP"}`

3. **Check Swagger UI:**
   - IAM Service: http://localhost:8081/swagger-ui.html
   - Data Ingestion: http://localhost:8082/swagger-ui.html

---

## 🧪 Test the APIs

### Test 1: Register a New User

**Using cURL:**
```bash
curl -X POST http://localhost:8080/api/iam/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john.doe@claiminsight.com",
    "password": "password123",
    "phone": "+1-555-0123",
    "role": "CLAIMS_ANALYST"
  }'
```

**Using PowerShell:**
```powershell
$body = @{
    name = "John Doe"
    email = "john.doe@claiminsight.com"
    password = "password123"
    phone = "+1-555-0123"
    role = "CLAIMS_ANALYST"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/iam/auth/register" `
    -Method Post `
    -ContentType "application/json" `
    -Body $body
```

**Expected Response:**
```json
{
  "success": true,
  "message": "User registered successfully",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "tokenType": "Bearer",
    "user": {
      "userId": 1,
      "name": "John Doe",
      "email": "john.doe@claiminsight.com",
      "role": "CLAIMS_ANALYST",
      "active": true
    }
  }
}
```

**💾 Save the token from the response!**

### Test 2: Login

```bash
curl -X POST http://localhost:8080/api/iam/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john.doe@claiminsight.com",
    "password": "password123"
  }'
```

### Test 3: Ingest Claim Data (Protected - Requires Token)

```bash
curl -X POST http://localhost:8080/api/data-ingestion/data-feeds/claims/ingest \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN_HERE" \
  -d '{
    "claimId": "CLM-2026-001234",
    "sourceSystem": "CoreClaimsSystem",
    "payloadJson": "{\"claimType\":\"Auto\",\"claimAmount\":15000,\"status\":\"Open\"}"
  }'
```

### Test 4: Get All Claims

```bash
curl -X GET http://localhost:8080/api/data-ingestion/data-feeds/claims \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

---

## 📊 Access Dashboards

| Service | URL | Purpose |
|---------|-----|---------|
| Eureka Dashboard | http://localhost:8761 | View all registered services |
| API Gateway Health | http://localhost:8080/actuator/health | Check gateway status |
| IAM Swagger | http://localhost:8081/swagger-ui.html | Test IAM APIs |
| Data Ingestion Swagger | http://localhost:8082/swagger-ui.html | Test Data Ingestion APIs |
| H2 Console (IAM) | http://localhost:8081/h2-console | View IAM database |
| H2 Console (Data) | http://localhost:8082/h2-console | View Data Ingestion database |

**H2 Console Credentials:**
- JDBC URL: `jdbc:h2:mem:iamdb` (or `dataingestiondb`)
- Username: `sa`
- Password: (leave blank)

---

## 🐳 Alternative: Docker Compose Setup

If you prefer Docker:

### Build Docker Images
```powershell
# Ensure all services are built first
.\build-all.ps1

# Build Docker images
docker-compose build
```

### Start All Services
```powershell
docker-compose up -d
```

### Check Status
```powershell
docker-compose ps
```

### View Logs
```powershell
# All services
docker-compose logs -f

# Specific service
docker-compose logs -f iam-service
```

### Stop All Services
```powershell
docker-compose down
```

---

## 🔧 Troubleshooting

### Issue: Service not registering with Eureka

**Solution:**
1. Ensure Eureka Server is running first
2. Check `eureka.client.service-url.defaultZone` in application.properties
3. Wait 30 seconds for registration
4. Refresh Eureka Dashboard

### Issue: Port already in use

**Solution:**
```powershell
# Find process using port
netstat -ano | findstr :8081

# Kill process (replace PID with actual process ID)
taskkill /PID <PID> /F
```

### Issue: JWT Authentication failing

**Solution:**
1. Verify token is not expired (24-hour expiration)
2. Check Authorization header format: `Bearer {token}`
3. Ensure JWT secret matches in IAM Service and API Gateway
4. Re-login to get a fresh token

### Issue: Cannot connect to database

**Solution:**
1. For H2 (dev): Database is in-memory, no action needed
2. For PostgreSQL (prod): Ensure PostgreSQL is running
3. Check connection string in application.properties
4. Verify credentials

---

## 📝 Common Tasks

### Start Minimum Required Services

For basic testing, you only need:
1. Eureka Server (8761)
2. API Gateway (8080)
3. IAM Service (8081)
4. Data Ingestion Service (8082)

### Add a New User Role

Edit `User.java` in IAM Service:
```java
public enum UserRole {
    CLAIMS_ANALYST,
    CLAIMS_MANAGER,
    FRAUD_ANALYST,
    ACTUARY,
    OPERATIONS_EXECUTIVE,
    ADMIN,
    YOUR_NEW_ROLE  // Add here
}
```

### Change Database from H2 to PostgreSQL

Update `application.properties` in each service:
```properties
# Comment out H2 configuration
#spring.datasource.url=jdbc:h2:mem:iamdb
#spring.datasource.driverClassName=org.h2.Driver

# Uncomment PostgreSQL configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/claiminsight_db
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.datasource.driver-class-name=org.postgresql.Driver
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

### View Service Logs

Each service logs to console. To save logs:
```powershell
cd iam-service
mvn spring-boot:run > logs/iam-service.log 2>&1
```

---

## 📚 Next Steps

1. **Explore API Documentation:** Read `API-DOCUMENTATION.md` for complete API reference
2. **Test with Postman:** Import APIs from Swagger UI into Postman
3. **Add Business Logic:** Implement domain-specific rules in service classes
4. **Connect Frontend:** Integrate with React/Angular frontend
5. **Deploy to Production:** Use Docker Compose or Kubernetes for deployment

---

## 🆘 Getting Help

- Check logs in console output
- Review Eureka Dashboard for service registration issues
- Use Swagger UI to test individual endpoints
- Verify JWT tokens using https://jwt.io
- Check actuator health endpoints

---

## ✅ Success Checklist

- [ ] All services build successfully
- [ ] Eureka Server shows all services registered
- [ ] Can register a new user
- [ ] Can login and receive JWT token
- [ ] Can access protected endpoints with token
- [ ] Can ingest claim data
- [ ] Can retrieve claim data
- [ ] Swagger UI accessible for all services
- [ ] H2 console shows data

---

**Congratulations! Your ClaimInsight360 microservices platform is up and running! 🎉**

For detailed API documentation, see: `API-DOCUMENTATION.md`
For architecture details, see: `README.md`

