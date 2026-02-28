# ClaimInsight360 - Quick Reference Card

## 🚀 Start Services

```powershell
# Terminal 1 - Eureka Server
cd eureka-server && mvn spring-boot:run

# Terminal 2 - API Gateway
cd api-gateway && mvn spring-boot:run

# Terminal 3 - IAM Service
cd iam-service && mvn spring-boot:run

# Terminal 4 - Data Ingestion
cd data-ingestion-service && mvn spring-boot:run
```

## 🔑 Quick API Tests

### 1. Register User
```bash
curl -X POST http://localhost:8080/api/iam/auth/register \
  -H "Content-Type: application/json" \
  -d '{"name":"Test User","email":"test@example.com","password":"password123","role":"CLAIMS_ANALYST"}'
```

### 2. Login
```bash
curl -X POST http://localhost:8080/api/iam/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"password123"}'
```

### 3. Ingest Claim (with token)
```bash
curl -X POST http://localhost:8080/api/data-ingestion/data-feeds/claims/ingest \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{"claimId":"CLM-001","sourceSystem":"Core","payloadJson":"{\"amount\":15000}"}'
```

## 📊 Service URLs

| Service | URL |
|---------|-----|
| Eureka Dashboard | http://localhost:8761 |
| API Gateway | http://localhost:8080 |
| IAM Swagger | http://localhost:8081/swagger-ui.html |
| Data Ingestion Swagger | http://localhost:8082/swagger-ui.html |
| H2 Console (IAM) | http://localhost:8081/h2-console |
| H2 Console (Data) | http://localhost:8082/h2-console |

## 🛠️ Build Commands

```powershell
# Build all services
.\build-all.ps1

# Build single service
cd {service-name} && mvn clean package

# Install common library
cd common-lib && mvn clean install

# Skip tests
mvn clean package -DskipTests
```

## 🐳 Docker Commands

```powershell
# Build and start
docker-compose up -d

# View logs
docker-compose logs -f {service-name}

# Stop all
docker-compose down

# Rebuild
docker-compose build
```

## 🔍 Troubleshooting

### Port in Use
```powershell
netstat -ano | findstr :8081
taskkill /PID {PID} /F
```

### Service Not Registering
- Wait 30 seconds
- Check Eureka is running on 8761
- Refresh http://localhost:8761

### JWT Issues
- Re-login to get fresh token
- Check header: `Authorization: Bearer {token}`
- Token expires in 24 hours

## 📱 User Roles

- `CLAIMS_ANALYST`
- `CLAIMS_MANAGER`
- `FRAUD_ANALYST`
- `ACTUARY`
- `OPERATIONS_EXECUTIVE`
- `ADMIN`

## 🗄️ H2 Database Access

**JDBC URL**: `jdbc:h2:mem:iamdb` or `jdbc:h2:mem:dataingestiondb`  
**Username**: `sa`  
**Password**: (empty)

## 📝 Common Endpoints

### IAM Service
- `POST /api/iam/auth/register` - Register user
- `POST /api/iam/auth/login` - Login
- `GET /api/iam/auth/users` - Get all users
- `GET /api/iam/auth/users/{userId}` - Get user by ID

### Data Ingestion
- `POST /api/data-ingestion/data-feeds` - Create feed
- `POST /api/data-ingestion/data-feeds/claims/ingest` - Ingest claim
- `GET /api/data-ingestion/data-feeds/claims` - Get all claims
- `GET /api/data-ingestion/data-feeds/claims/unprocessed` - Unprocessed claims

## 🆘 Quick Help

**Issue**: Service won't start
- Check port not in use
- Ensure Eureka is running first
- Check logs for errors

**Issue**: Can't authenticate
- Check token is valid
- Ensure JWT secret matches
- Re-login if token expired

**Issue**: Database error
- H2 is in-memory (auto-created)
- Check JDBC URL in properties
- Restart service to reset DB

---

**For detailed documentation, see:**
- README.md - Full documentation
- QUICK-START.md - Setup guide
- API-DOCUMENTATION.md - API reference
- PROJECT-SUMMARY.md - Implementation status

