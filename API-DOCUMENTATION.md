# ClaimInsight360 - Complete API Documentation

## Table of Contents
1. [Authentication Flow](#authentication-flow)
2. [IAM Service APIs](#iam-service-apis)
3. [Data Ingestion Service APIs](#data-ingestion-service-apis)
4. [Metrics Engine Service APIs](#metrics-engine-service-apis)
5. [Fraud Risk Service APIs](#fraud-risk-service-apis)
6. [Denial Analysis Service APIs](#denial-analysis-service-apis)
7. [Adjuster Performance Service APIs](#adjuster-performance-service-apis)
8. [Cost Reserve Service APIs](#cost-reserve-service-apis)
9. [Dashboard Reports Service APIs](#dashboard-reports-service-apis)
10. [Notification Service APIs](#notification-service-apis)

---

## Authentication Flow

### 1. Register New User
**Endpoint**: `POST /api/iam/auth/register`

**Request Body**:
```json
{
  "name": "John Doe",
  "email": "john.doe@claiminsight.com",
  "password": "SecurePass123!",
  "phone": "+1-555-0123",
  "role": "CLAIMS_ANALYST"
}
```

**Available Roles**:
- `CLAIMS_ANALYST`
- `CLAIMS_MANAGER`
- `FRAUD_ANALYST`
- `ACTUARY`
- `OPERATIONS_EXECUTIVE`
- `ADMIN`

**Response**:
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
      "phone": "+1-555-0123",
      "role": "CLAIMS_ANALYST",
      "active": true,
      "createdDate": 1709164800000,
      "updatedDate": 1709164800000
    }
  },
  "timestamp": "2026-02-28T10:00:00",
  "statusCode": 201
}
```

### 2. Login
**Endpoint**: `POST /api/iam/auth/login`

**Request Body**:
```json
{
  "email": "john.doe@claiminsight.com",
  "password": "SecurePass123!"
}
```

**Response**: Same as registration response with new token

### 3. Using JWT Token
Include the token in all subsequent API calls:
```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

---

## IAM Service APIs

### User Management

#### Get User by ID
```
GET /api/iam/auth/users/{userId}
Authorization: Bearer {token}
```

#### Get User by Email
```
GET /api/iam/auth/users/email/{email}
Authorization: Bearer {token}
```

#### Get All Users
```
GET /api/iam/auth/users
Authorization: Bearer {token}
```

#### Get Users by Role
```
GET /api/iam/auth/users/role/{role}
Authorization: Bearer {token}
```

#### Update User
```
PUT /api/iam/auth/users/{userId}
Authorization: Bearer {token}
Content-Type: application/json

{
  "name": "John Updated",
  "email": "john.doe@claiminsight.com",
  "password": "NewPass123!",
  "phone": "+1-555-9999",
  "role": "CLAIMS_MANAGER"
}
```

#### Deactivate User
```
DELETE /api/iam/auth/users/{userId}
Authorization: Bearer {token}
```

---

## Data Ingestion Service APIs

### Data Feed Management

#### Create Data Feed
```
POST /api/data-ingestion/data-feeds
Authorization: Bearer {token}
Content-Type: application/json

{
  "feedType": "CLAIM",
  "sourceSystem": "CoreClaimsSystem",
  "description": "Daily claim data feed",
  "status": "PENDING"
}
```

**Feed Types**: `CLAIM`, `POLICY`, `PAYMENT`, `RESERVE`, `DENIAL`, `FRAUD`, `ADJUSTER`

**Feed Status**: `PENDING`, `IN_PROGRESS`, `COMPLETED`, `FAILED`

#### Get All Data Feeds
```
GET /api/data-ingestion/data-feeds
Authorization: Bearer {token}
```

#### Get Data Feed by ID
```
GET /api/data-ingestion/data-feeds/{feedId}
Authorization: Bearer {token}
```

#### Get Data Feeds by Type
```
GET /api/data-ingestion/data-feeds/type/{feedType}
Authorization: Bearer {token}
```

#### Update Data Feed Status
```
PATCH /api/data-ingestion/data-feeds/{feedId}/status?status=COMPLETED
Authorization: Bearer {token}
```

### Claim Data Ingestion

#### Ingest Claim Data
```
POST /api/data-ingestion/data-feeds/claims/ingest
Authorization: Bearer {token}
Content-Type: application/json

{
  "claimId": "CLM-2026-001234",
  "sourceSystem": "CoreClaimsSystem",
  "payloadJson": "{\"claimType\":\"Auto\",\"claimAmount\":15000,\"claimDate\":\"2026-02-28\",\"policyNumber\":\"POL-123456\",\"claimantName\":\"Jane Smith\",\"status\":\"Open\"}"
}
```

#### Get All Claims
```
GET /api/data-ingestion/data-feeds/claims
Authorization: Bearer {token}
```

#### Get Claim by Claim ID
```
GET /api/data-ingestion/data-feeds/claims/{claimId}
Authorization: Bearer {token}
```

#### Get Unprocessed Claims
```
GET /api/data-ingestion/data-feeds/claims/unprocessed
Authorization: Bearer {token}
```

#### Mark Claim as Processed
```
PATCH /api/data-ingestion/data-feeds/claims/{claimId}/processed
Authorization: Bearer {token}
```

---

## Metrics Engine Service APIs

### KPI Calculation and Retrieval

#### Calculate Claim KPIs
```
POST /api/metrics/kpis/calculate
Authorization: Bearer {token}
Content-Type: application/json

{
  "claimId": "CLM-2026-001234",
  "metricName": "TAT",
  "metricValue": 15.5,
  "unit": "days",
  "category": "Turnaround Time"
}
```

**Metric Names**: `TAT`, `SEVERITY`, `FREQUENCY`, `LOSS_RATIO`, `SETTLEMENT_TIME`, `CYCLE_TIME`

#### Get All KPIs
```
GET /api/metrics/kpis
Authorization: Bearer {token}
```

#### Get KPI by ID
```
GET /api/metrics/kpis/{kpiId}
Authorization: Bearer {token}
```

#### Get KPIs by Claim ID
```
GET /api/metrics/kpis/claim/{claimId}
Authorization: Bearer {token}
```

#### Get KPIs by Metric Name
```
GET /api/metrics/kpis/metric/{metricName}
Authorization: Bearer {token}
```

#### Get KPIs by Category
```
GET /api/metrics/kpis/category/{category}
Authorization: Bearer {token}
```

#### Get KPI Summary Statistics
```
GET /api/metrics/kpis/summary?metricName=TAT&startDate=1709164800000&endDate=1709251200000
Authorization: Bearer {token}
```

---

## Fraud Risk Service APIs

### Risk Indicators

#### Create Risk Indicator
```
POST /api/fraud-risk/indicators
Authorization: Bearer {token}
Content-Type: application/json

{
  "claimId": "CLM-2026-001234",
  "indicatorType": "HIGH_COST",
  "severity": "HIGH",
  "description": "Claim amount significantly exceeds average for claim type",
  "acknowledged": false
}
```

**Indicator Types**: `HIGH_COST`, `UNUSUAL_TIMING`, `PATTERN_MATCH`, `FRAUD_SIGNAL`, `ANOMALY`

**Severity Levels**: `LOW`, `MEDIUM`, `HIGH`, `CRITICAL`

#### Get All Risk Indicators
```
GET /api/fraud-risk/indicators
Authorization: Bearer {token}
```

#### Get Risk Indicators by Claim ID
```
GET /api/fraud-risk/indicators/claim/{claimId}
Authorization: Bearer {token}
```

#### Get Risk Indicators by Severity
```
GET /api/fraud-risk/indicators/severity/{severity}
Authorization: Bearer {token}
```

#### Get Unacknowledged Risk Indicators
```
GET /api/fraud-risk/indicators/unacknowledged
Authorization: Bearer {token}
```

#### Acknowledge Risk Indicator
```
PATCH /api/fraud-risk/indicators/{indicatorId}/acknowledge
Authorization: Bearer {token}
```

### Risk Scores

#### Calculate Risk Score
```
POST /api/fraud-risk/scores/calculate
Authorization: Bearer {token}
Content-Type: application/json

{
  "claimId": "CLM-2026-001234",
  "scoreValue": 75.5,
  "riskLevel": "HIGH"
}
```

#### Get Risk Score by Claim ID
```
GET /api/fraud-risk/scores/claim/{claimId}
Authorization: Bearer {token}
```

#### Get High Risk Claims
```
GET /api/fraud-risk/scores/high-risk?threshold=70
Authorization: Bearer {token}
```

---

## Denial Analysis Service APIs

### Denial Patterns

#### Create Denial Pattern
```
POST /api/denial/patterns
Authorization: Bearer {token}
Content-Type: application/json

{
  "claimId": "CLM-2026-001234",
  "denialCode": "D001",
  "reason": "Missing documentation",
  "rootCause": "Incomplete claim submission",
  "category": "Documentation"
}
```

**Categories**: `Policy`, `Medical`, `Documentation`, `Fraud`

#### Get All Denial Patterns
```
GET /api/denial/patterns
Authorization: Bearer {token}
```

#### Get Denial Patterns by Claim ID
```
GET /api/denial/patterns/claim/{claimId}
Authorization: Bearer {token}
```

#### Get Denial Patterns by Code
```
GET /api/denial/patterns/code/{denialCode}
Authorization: Bearer {token}
```

#### Get Denial Pattern Analysis
```
GET /api/denial/patterns/analysis?startDate=1709164800000&endDate=1709251200000
Authorization: Bearer {token}
```

### Leakage Flags

#### Create Leakage Flag
```
POST /api/denial/leakages
Authorization: Bearer {token}
Content-Type: application/json

{
  "claimId": "CLM-2026-001234",
  "leakageType": "OVERPAYMENT",
  "estimatedLoss": 2500.00,
  "description": "Duplicate payment detected"
}
```

**Leakage Types**: `OVERPAYMENT`, `DELAY`, `ERROR`, `PROCESS_FAILURE`

#### Get All Leakage Flags
```
GET /api/denial/leakages
Authorization: Bearer {token}
```

#### Get Leakage Flags by Claim ID
```
GET /api/denial/leakages/claim/{claimId}
Authorization: Bearer {token}
```

#### Get Total Leakage Amount
```
GET /api/denial/leakages/total-loss?startDate=1709164800000&endDate=1709251200000
Authorization: Bearer {token}
```

---

## Adjuster Performance Service APIs

### Adjuster Performance

#### Record Adjuster Performance
```
POST /api/adjuster/performance
Authorization: Bearer {token}
Content-Type: application/json

{
  "adjusterId": "ADJ-001",
  "claimsHandled": 45,
  "avgTAT": 12.5,
  "qualityScore": 92.0,
  "period": "2026-02"
}
```

#### Get Performance by Adjuster ID
```
GET /api/adjuster/performance/adjuster/{adjusterId}
Authorization: Bearer {token}
```

#### Get Top Performers
```
GET /api/adjuster/performance/top?limit=10&period=2026-02
Authorization: Bearer {token}
```

#### Get Performance Summary
```
GET /api/adjuster/performance/summary?period=2026-02
Authorization: Bearer {token}
```

### SLA Violations

#### Record SLA Violation
```
POST /api/adjuster/sla-violations
Authorization: Bearer {token}
Content-Type: application/json

{
  "claimId": "CLM-2026-001234",
  "violationType": "TAT_EXCEEDED",
  "description": "Claim processing exceeded 15-day SLA"
}
```

**Violation Types**: `TAT_EXCEEDED`, `RESPONSE_TIME`, `DOCUMENTATION_DELAY`, `APPROVAL_DELAY`

#### Get All SLA Violations
```
GET /api/adjuster/sla-violations
Authorization: Bearer {token}
```

#### Get SLA Violations by Claim ID
```
GET /api/adjuster/sla-violations/claim/{claimId}
Authorization: Bearer {token}
```

#### Get SLA Violation Summary
```
GET /api/adjuster/sla-violations/summary?startDate=1709164800000&endDate=1709251200000
Authorization: Bearer {token}
```

---

## Cost Reserve Service APIs

### Claim Costs

#### Record Claim Cost
```
POST /api/cost-reserve/costs
Authorization: Bearer {token}
Content-Type: application/json

{
  "claimId": "CLM-2026-001234",
  "costType": "MEDICAL",
  "amount": 12500.00,
  "costDate": 1709164800000
}
```

**Cost Types**: `MEDICAL`, `LEGAL`, `REPAIR`, `SETTLEMENT`, `ADMINISTRATIVE`

#### Get Costs by Claim ID
```
GET /api/cost-reserve/costs/claim/{claimId}
Authorization: Bearer {token}
```

#### Get Costs by Type
```
GET /api/cost-reserve/costs/type/{costType}
Authorization: Bearer {token}
```

#### Get Total Costs
```
GET /api/cost-reserve/costs/total?startDate=1709164800000&endDate=1709251200000&costType=MEDICAL
Authorization: Bearer {token}
```

### Claim Reserves

#### Update Claim Reserve
```
POST /api/cost-reserve/reserves
Authorization: Bearer {token}
Content-Type: application/json

{
  "claimId": "CLM-2026-001234",
  "reserveAmount": 50000.00
}
```

#### Get Reserve by Claim ID
```
GET /api/cost-reserve/reserves/claim/{claimId}
Authorization: Bearer {token}
```

#### Get Total Reserves
```
GET /api/cost-reserve/reserves/total
Authorization: Bearer {token}
```

### Aging Records

#### Create Aging Record
```
POST /api/cost-reserve/aging
Authorization: Bearer {token}
Content-Type: application/json

{
  "claimId": "CLM-2026-001234",
  "agingDays": 45,
  "agingBucket": "31_60"
}
```

**Aging Buckets**: `0_30`, `31_60`, `61_90`, `90_PLUS`

#### Get Aging by Claim ID
```
GET /api/cost-reserve/aging/claim/{claimId}
Authorization: Bearer {token}
```

#### Get Aging Distribution
```
GET /api/cost-reserve/aging/distribution
Authorization: Bearer {token}
```

---

## Dashboard Reports Service APIs

### Analytics Reports

#### Generate Analytics Report
```
POST /api/dashboard/reports
Authorization: Bearer {token}
Content-Type: application/json

{
  "scope": "{\"product\":\"Auto\",\"region\":\"Northeast\",\"period\":\"2026-02\"}",
  "metrics": "{\"totalClaims\":150,\"avgTAT\":14.2,\"totalCost\":1250000}"
}
```

#### Get All Reports
```
GET /api/dashboard/reports
Authorization: Bearer {token}
```

#### Get Report by ID
```
GET /api/dashboard/reports/{reportId}
Authorization: Bearer {token}
```

#### Get Dashboard Summary
```
GET /api/dashboard/summary?product=Auto&region=Northeast&startDate=1709164800000&endDate=1709251200000
Authorization: Bearer {token}
```

**Response includes aggregated metrics from all services**

---

## Notification Service APIs

### Notifications

#### Create Notification
```
POST /api/notifications
Authorization: Bearer {token}
Content-Type: application/json

{
  "userId": 1,
  "message": "High-risk claim detected: CLM-2026-001234",
  "category": "RISK",
  "status": "UNREAD"
}
```

**Categories**: `RISK`, `DENIAL`, `COST`, `PERFORMANCE`, `SLA`, `GENERAL`

**Status**: `UNREAD`, `READ`, `DISMISSED`

#### Get Notifications by User ID
```
GET /api/notifications/user/{userId}
Authorization: Bearer {token}
```

#### Get Unread Notifications
```
GET /api/notifications/user/{userId}/unread
Authorization: Bearer {token}
```

#### Mark Notification as Read
```
PATCH /api/notifications/{notificationId}/read
Authorization: Bearer {token}
```

#### Dismiss Notification
```
DELETE /api/notifications/{notificationId}
Authorization: Bearer {token}
```

---

## Common Response Format

All APIs return responses in this format:

**Success Response**:
```json
{
  "success": true,
  "message": "Request successful",
  "data": { ... },
  "timestamp": "2026-02-28T10:00:00",
  "statusCode": 200
}
```

**Error Response**:
```json
{
  "success": false,
  "message": "Error description",
  "data": null,
  "timestamp": "2026-02-28T10:00:00",
  "statusCode": 400
}
```

---

## Testing with cURL

### Example: Register and Login
```bash
# Register
curl -X POST http://localhost:8080/api/iam/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Test User",
    "email": "test@example.com",
    "password": "password123",
    "role": "CLAIMS_ANALYST"
  }'

# Login
curl -X POST http://localhost:8080/api/iam/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123"
  }'

# Use token in subsequent requests
curl -X GET http://localhost:8080/api/data-ingestion/data-feeds \
  -H "Authorization: Bearer YOUR_JWT_TOKEN_HERE"
```

---

## Postman Collection

Import the following into Postman to test all APIs:

1. Create a new Postman Collection named "ClaimInsight360"
2. Add environment variables:
   - `base_url`: http://localhost:8080
   - `token`: (will be set after login)
3. Import endpoints from Swagger UI or use the API documentation above

---

## Rate Limiting

API Gateway implements rate limiting:
- 100 requests per minute per IP for unauthenticated endpoints
- 1000 requests per minute per user for authenticated endpoints

---

## Error Codes

- **200**: Success
- **201**: Created
- **400**: Bad Request
- **401**: Unauthorized
- **403**: Forbidden
- **404**: Not Found
- **500**: Internal Server Error
- **503**: Service Unavailable

---

**Last Updated**: February 2026  
**API Version**: 1.0.0

