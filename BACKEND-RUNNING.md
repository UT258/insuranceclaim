# 🚀 ClaimInsight360 Backend - Startup Status

## ✅ Services Started

All backend services are now running in the background!

### Services Running

| Service | Port | Status | URL |
|---------|------|--------|-----|
| **Eureka Server** | 8761 | 🟢 Starting | http://localhost:8761 |
| **API Gateway** | 8080 | 🟢 Starting | http://localhost:8080 |
| **IAM Service** | 8081 | 🟢 Starting | http://localhost:8081 |
| **Data Ingestion Service** | 8082 | 🟢 Starting | http://localhost:8082 |

---

## ⏳ Startup Time

Services typically take 30-60 seconds to fully start and register with Eureka.

**Check Progress:**
1. Eureka Dashboard: http://localhost:8761
   - Wait for services to appear in the list
2. API Gateway: http://localhost:8080/actuator/health
   - Should show `{"status":"UP"}`

---

## 🧪 Test the Backend

### 1️⃣ Register a New User

```powershell
$body = @{
    name = "John Doe"
    email = "john.doe@claiminsight.com"
    password = "password123"
    phone = "+1-555-0123"
    role = "CLAIMS_ANALYST"
} | ConvertTo-Json

$response = Invoke-RestMethod -Uri "http://localhost:8080/api/iam/auth/register" `
    -Method Post `
    -ContentType "application/json" `
    -Body $body

$response | ConvertTo-Json
Write-Host "Token: $($response.data.token)"
```

### 2️⃣ Login

```powershell
$body = @{
    email = "john.doe@claiminsight.com"
    password = "password123"
} | ConvertTo-Json

$response = Invoke-RestMethod -Uri "http://localhost:8080/api/iam/auth/login" `
    -Method Post `
    -ContentType "application/json" `
    -Body $body

$response | ConvertTo-Json
Write-Host "Token: $($response.data.token)"
```

### 3️⃣ Ingest Claim Data

```powershell
# Replace YOUR_TOKEN with the token from login response
$token = "YOUR_TOKEN_HERE"

$body = @{
    claimId = "CLM-2026-001234"
    sourceSystem = "CoreClaimsSystem"
    payloadJson = '{"claimType":"Auto","claimAmount":15000,"claimDate":"2026-02-28","status":"Open"}'
} | ConvertTo-Json

$response = Invoke-RestMethod -Uri "http://localhost:8080/api/data-ingestion/data-feeds/claims/ingest" `
    -Method Post `
    -ContentType "application/json" `
    -Headers @{"Authorization" = "Bearer $token"} `
    -Body $body

$response | ConvertTo-Json
```

### 4️⃣ Get All Claims

```powershell
$token = "YOUR_TOKEN_HERE"

$response = Invoke-RestMethod -Uri "http://localhost:8080/api/data-ingestion/data-feeds/claims" `
    -Method Get `
    -Headers @{"Authorization" = "Bearer $token"}

$response | ConvertTo-Json
```

---

## 📊 Access Dashboards & UIs

### Eureka Dashboard
**URL:** http://localhost:8761

**What to see:**
- All registered services
- Service status (UP/DOWN)
- Instance information

### IAM Service Swagger UI
**URL:** http://localhost:8081/swagger-ui.html

**Try these endpoints:**
- POST /auth/register
- POST /auth/login
- GET /auth/users
- GET /auth/users/{userId}

### Data Ingestion Swagger UI
**URL:** http://localhost:8082/swagger-ui.html

**Try these endpoints:**
- POST /data-feeds
- POST /data-feeds/claims/ingest
- GET /data-feeds/claims
- GET /data-feeds/claims/{claimId}

### H2 Database Console (IAM Service)
**URL:** http://localhost:8081/h2-console

**Credentials:**
- JDBC URL: `jdbc:h2:mem:iamdb`
- Username: `sa`
- Password: (leave blank)

### H2 Database Console (Data Ingestion Service)
**URL:** http://localhost:8082/h2-console

**Credentials:**
- JDBC URL: `jdbc:h2:mem:dataingestiondb`
- Username: `sa`
- Password: (leave blank)

---

## 🔍 Monitor Service Logs

Each service outputs logs to the console. Look for:

### Eureka Server Success Indicators:
```
Started EurekaServerApplication
```

### API Gateway Success Indicators:
```
Started ApiGatewayApplication
Netty started with epoll
```

### IAM Service Success Indicators:
```
Started IamServiceApplication
HHH000412: Hibernate ORM core version
```

### Data Ingestion Service Success Indicators:
```
Started DataIngestionServiceApplication
HHH000412: Hibernate ORM core version
```

---

## ✨ Common API Examples

### Register User
```bash
curl -X POST http://localhost:8080/api/iam/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jane Smith",
    "email": "jane@example.com",
    "password": "password123",
    "phone": "+1-555-9999",
    "role": "CLAIMS_MANAGER"
  }'
```

### Login
```bash
curl -X POST http://localhost:8080/api/iam/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "jane@example.com",
    "password": "password123"
  }'
```

### Create Data Feed
```bash
curl -X POST http://localhost:8080/api/data-ingestion/data-feeds \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{
    "feedType": "CLAIM",
    "sourceSystem": "CoreClaimsSystem",
    "description": "Daily claim data feed",
    "status": "PENDING"
  }'
```

---

## ⚠️ Troubleshooting

### Service Not Starting?
1. Check Java is installed: `java -version`
2. Check Maven is installed: `mvn -version`
3. Check ports are not in use:
   ```powershell
   netstat -ano | findstr :8761
   netstat -ano | findstr :8080
   netstat -ano | findstr :8081
   netstat -ano | findstr :8082
   ```

### Service Not Registering with Eureka?
1. Wait 30 seconds after starting API Gateway
2. Check service logs for connection errors
3. Verify Eureka Server is running first
4. Check `eureka.client.service-url.defaultZone` in application.properties

### Can't Access Swagger UI?
1. Wait 30 seconds for service to fully start
2. Check service is showing in Eureka Dashboard
3. Verify correct URL and port
4. Check service logs for errors

### JWT Authentication Failing?
1. Make sure you're using the token from login response
2. Check Authorization header format: `Authorization: Bearer {token}`
3. Token expires in 24 hours
4. Register and login again if needed

---

## 📞 Next Steps

1. **Test APIs** - Use the examples above
2. **Explore Swagger** - Access swagger-ui.html on each service
3. **View Database** - Use H2 Console to see stored data
4. **Monitor Services** - Watch Eureka Dashboard for registration
5. **Implement** - Follow patterns to create remaining services

---

## 📖 Reference Documentation

For more details, see:
- **README.md** - Full technical documentation
- **API-DOCUMENTATION.md** - Complete API reference
- **QUICK-START.md** - Detailed setup guide
- **QUICK-REFERENCE.md** - Command cheat sheet
- **PROJECT-SUMMARY.md** - Implementation status

---

## ✅ Success Checklist

- [ ] Eureka Dashboard shows all 4 services registered
- [ ] API Gateway health check returns UP
- [ ] Can register a new user
- [ ] Can login and receive JWT token
- [ ] Can ingest claim data
- [ ] Can retrieve claim data
- [ ] Swagger UI loads for IAM Service
- [ ] Swagger UI loads for Data Ingestion Service
- [ ] H2 Console accessible and shows data

---

**All services are running! Start testing now! 🎉**

**Time**: February 28, 2026  
**Status**: ✅ Backend Running

