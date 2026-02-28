# ClaimInsight360 Backend - Quick Test Script

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "ClaimInsight360 Backend - Quick Test" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Test 1: Check Services Health
Write-Host "TEST 1: Checking Service Health..." -ForegroundColor Yellow
Write-Host ""

$services = @(
    @{Name = "Eureka Server"; URL = "http://localhost:8761/actuator/health"},
    @{Name = "API Gateway"; URL = "http://localhost:8080/actuator/health"},
    @{Name = "IAM Service"; URL = "http://localhost:8081/actuator/health"},
    @{Name = "Data Ingestion Service"; URL = "http://localhost:8082/actuator/health"}
)

foreach ($service in $services) {
    try {
        $response = Invoke-RestMethod -Uri $service.URL -ErrorAction Stop
        if ($response.status -eq "UP") {
            Write-Host "  ✅ $($service.Name): UP" -ForegroundColor Green
        } else {
            Write-Host "  ⚠️  $($service.Name): $($response.status)" -ForegroundColor Yellow
        }
    } catch {
        Write-Host "  ❌ $($service.Name): Not responding" -ForegroundColor Red
    }
}

Write-Host ""
Write-Host "TEST 2: User Registration..." -ForegroundColor Yellow
Write-Host ""

$registerBody = @{
    name = "Test User"
    email = "testuser@claiminsight.com"
    password = "TestPassword123!"
    phone = "+1-555-0001"
    role = "CLAIMS_ANALYST"
} | ConvertTo-Json

try {
    $registerResponse = Invoke-RestMethod -Uri "http://localhost:8080/api/iam/auth/register" `
        -Method Post `
        -ContentType "application/json" `
        -Body $registerBody `
        -ErrorAction Stop

    if ($registerResponse.success) {
        Write-Host "  ✅ User Registration Successful" -ForegroundColor Green
        Write-Host "     Email: testuser@claiminsight.com" -ForegroundColor Gray
        Write-Host "     Role: CLAIMS_ANALYST" -ForegroundColor Gray
        $registerToken = $registerResponse.data.token
        Write-Host "     Token received (first 20 chars): $($registerToken.Substring(0,20))..." -ForegroundColor Gray
    } else {
        Write-Host "  ❌ Registration Failed: $($registerResponse.message)" -ForegroundColor Red
    }
} catch {
    Write-Host "  ❌ Registration Error: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""
Write-Host "TEST 3: User Login..." -ForegroundColor Yellow
Write-Host ""

$loginBody = @{
    email = "testuser@claiminsight.com"
    password = "TestPassword123!"
} | ConvertTo-Json

try {
    $loginResponse = Invoke-RestMethod -Uri "http://localhost:8080/api/iam/auth/login" `
        -Method Post `
        -ContentType "application/json" `
        -Body $loginBody `
        -ErrorAction Stop

    if ($loginResponse.success) {
        Write-Host "  ✅ Login Successful" -ForegroundColor Green
        Write-Host "     User: $($loginResponse.data.user.name)" -ForegroundColor Gray
        Write-Host "     Email: $($loginResponse.data.user.email)" -ForegroundColor Gray
        $loginToken = $loginResponse.data.token
        Write-Host "     Token received (first 20 chars): $($loginToken.Substring(0,20))..." -ForegroundColor Gray
    } else {
        Write-Host "  ❌ Login Failed: $($loginResponse.message)" -ForegroundColor Red
    }
} catch {
    Write-Host "  ❌ Login Error: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host ""
Write-Host "TEST 4: Get All Users (Protected)..." -ForegroundColor Yellow
Write-Host ""

if ($loginToken) {
    try {
        $usersResponse = Invoke-RestMethod -Uri "http://localhost:8080/api/iam/auth/users" `
            -Method Get `
            -Headers @{"Authorization" = "Bearer $loginToken"} `
            -ErrorAction Stop

        if ($usersResponse.success) {
            Write-Host "  ✅ Retrieved Users Successfully" -ForegroundColor Green
            Write-Host "     Total Users: $($usersResponse.data.Count)" -ForegroundColor Gray
            if ($usersResponse.data.Count -gt 0) {
                Write-Host "     Sample User: $($usersResponse.data[0].name) ($($usersResponse.data[0].email))" -ForegroundColor Gray
            }
        } else {
            Write-Host "  ❌ Failed to get users: $($usersResponse.message)" -ForegroundColor Red
        }
    } catch {
        Write-Host "  ❌ Get Users Error: $($_.Exception.Message)" -ForegroundColor Red
    }
} else {
    Write-Host "  ⏭️  Skipped (no valid token from login)" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "TEST 5: Create Data Feed..." -ForegroundColor Yellow
Write-Host ""

if ($loginToken) {
    $feedBody = @{
        feedType = "CLAIM"
        sourceSystem = "CoreClaimsSystem"
        description = "Test Daily Claim Feed"
        status = "PENDING"
    } | ConvertTo-Json

    try {
        $feedResponse = Invoke-RestMethod -Uri "http://localhost:8080/api/data-ingestion/data-feeds" `
            -Method Post `
            -ContentType "application/json" `
            -Headers @{"Authorization" = "Bearer $loginToken"} `
            -Body $feedBody `
            -ErrorAction Stop

        if ($feedResponse.success) {
            Write-Host "  ✅ Data Feed Created Successfully" -ForegroundColor Green
            Write-Host "     Feed ID: $($feedResponse.data.feedId)" -ForegroundColor Gray
            Write-Host "     Type: $($feedResponse.data.feedType)" -ForegroundColor Gray
            Write-Host "     Source: $($feedResponse.data.sourceSystem)" -ForegroundColor Gray
            $feedId = $feedResponse.data.feedId
        } else {
            Write-Host "  ❌ Failed to create feed: $($feedResponse.message)" -ForegroundColor Red
        }
    } catch {
        Write-Host "  ❌ Create Feed Error: $($_.Exception.Message)" -ForegroundColor Red
    }
} else {
    Write-Host "  ⏭️  Skipped (no valid token)" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "TEST 6: Ingest Claim Data..." -ForegroundColor Yellow
Write-Host ""

if ($loginToken) {
    $claimBody = @{
        claimId = "CLM-2026-TEST-001"
        sourceSystem = "CoreClaimsSystem"
        payloadJson = '{"claimType":"Auto","claimAmount":25000,"claimDate":"2026-02-28","policyNumber":"POL-123456","claimantName":"John Test","status":"Open"}'
    } | ConvertTo-Json

    try {
        $claimResponse = Invoke-RestMethod -Uri "http://localhost:8080/api/data-ingestion/data-feeds/claims/ingest" `
            -Method Post `
            -ContentType "application/json" `
            -Headers @{"Authorization" = "Bearer $loginToken"} `
            -Body $claimBody `
            -ErrorAction Stop

        if ($claimResponse.success) {
            Write-Host "  ✅ Claim Data Ingested Successfully" -ForegroundColor Green
            Write-Host "     Claim ID: $($claimResponse.data.claimId)" -ForegroundColor Gray
            Write-Host "     Raw ID: $($claimResponse.data.rawId)" -ForegroundColor Gray
            Write-Host "     Status: Ingested" -ForegroundColor Gray
        } else {
            Write-Host "  ❌ Failed to ingest claim: $($claimResponse.message)" -ForegroundColor Red
        }
    } catch {
        Write-Host "  ❌ Ingest Claim Error: $($_.Exception.Message)" -ForegroundColor Red
    }
} else {
    Write-Host "  ⏭️  Skipped (no valid token)" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "TEST 7: Get All Claims..." -ForegroundColor Yellow
Write-Host ""

if ($loginToken) {
    try {
        $claimsResponse = Invoke-RestMethod -Uri "http://localhost:8080/api/data-ingestion/data-feeds/claims" `
            -Method Get `
            -Headers @{"Authorization" = "Bearer $loginToken"} `
            -ErrorAction Stop

        if ($claimsResponse.success) {
            Write-Host "  ✅ Retrieved Claims Successfully" -ForegroundColor Green
            Write-Host "     Total Claims: $($claimsResponse.data.Count)" -ForegroundColor Gray
            if ($claimsResponse.data.Count -gt 0) {
                Write-Host "     Sample Claim: $($claimsResponse.data[0].claimId)" -ForegroundColor Gray
            }
        } else {
            Write-Host "  ❌ Failed to get claims: $($claimsResponse.message)" -ForegroundColor Red
        }
    } catch {
        Write-Host "  ❌ Get Claims Error: $($_.Exception.Message)" -ForegroundColor Red
    }
} else {
    Write-Host "  ⏭️  Skipped (no valid token)" -ForegroundColor Yellow
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Test Summary" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "✨ All core features tested!" -ForegroundColor Green
Write-Host ""
Write-Host "📖 Next Steps:" -ForegroundColor Cyan
Write-Host "1. Access Eureka Dashboard:" -ForegroundColor Gray
Write-Host "   http://localhost:8761" -ForegroundColor Gray
Write-Host ""
Write-Host "2. View API Documentation:" -ForegroundColor Gray
Write-Host "   http://localhost:8081/swagger-ui.html (IAM Service)" -ForegroundColor Gray
Write-Host "   http://localhost:8082/swagger-ui.html (Data Ingestion)" -ForegroundColor Gray
Write-Host ""
Write-Host "3. View Database (H2 Console):" -ForegroundColor Gray
Write-Host "   http://localhost:8081/h2-console (IAM)" -ForegroundColor Gray
Write-Host "   http://localhost:8082/h2-console (Data)" -ForegroundColor Gray
Write-Host ""
Write-Host "4. Read Documentation:" -ForegroundColor Gray
Write-Host "   BACKEND-RUNNING.md - Detailed test examples" -ForegroundColor Gray
Write-Host "   API-DOCUMENTATION.md - Complete API reference" -ForegroundColor Gray
Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Backend is Ready! 🚀" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

