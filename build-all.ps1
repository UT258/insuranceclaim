# ClaimInsight360 - Complete Setup and Build Script
# This script builds all microservices in the correct order

$ErrorActionPreference = "Stop"

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "ClaimInsight360 Microservices Setup" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

$basePath = "D:\New folder\Java SpringBoot\demo"
Set-Location $basePath

# Step 1: Build Common Library
Write-Host "[1/12] Building Common Library..." -ForegroundColor Yellow
Set-Location "$basePath\common-lib"
mvn clean install -DskipTests
if ($LASTEXITCODE -ne 0) {
    Write-Host "Failed to build common-lib" -ForegroundColor Red
    exit 1
}
Write-Host "✓ Common Library built successfully" -ForegroundColor Green
Write-Host ""

# Step 2: Build Eureka Server
Write-Host "[2/12] Building Eureka Server..." -ForegroundColor Yellow
Set-Location "$basePath\eureka-server"
mvn clean package -DskipTests
if ($LASTEXITCODE -ne 0) {
    Write-Host "Failed to build eureka-server" -ForegroundColor Red
    exit 1
}
Write-Host "✓ Eureka Server built successfully" -ForegroundColor Green
Write-Host ""

# Step 3: Build API Gateway
Write-Host "[3/12] Building API Gateway..." -ForegroundColor Yellow
Set-Location "$basePath\api-gateway"
mvn clean package -DskipTests
if ($LASTEXITCODE -ne 0) {
    Write-Host "Failed to build api-gateway" -ForegroundColor Red
    exit 1
}
Write-Host "✓ API Gateway built successfully" -ForegroundColor Green
Write-Host ""

# Step 4-11: Build all business microservices
$services = @(
    "iam-service",
    "data-ingestion-service",
    "metrics-engine-service",
    "fraud-risk-service",
    "denial-analysis-service",
    "adjuster-performance-service",
    "cost-reserve-service",
    "dashboard-reports-service",
    "notification-service"
)

$counter = 4
foreach ($service in $services) {
    Write-Host "[$counter/12] Building $service..." -ForegroundColor Yellow
    $servicePath = Join-Path $basePath $service
    if (Test-Path $servicePath) {
        Set-Location $servicePath
        if (Test-Path "pom.xml") {
            mvn clean package -DskipTests
            if ($LASTEXITCODE -ne 0) {
                Write-Host "Failed to build $service" -ForegroundColor Red
                exit 1
            }
            Write-Host "✓ $service built successfully" -ForegroundColor Green
        } else {
            Write-Host "⚠ $service pom.xml not found - skipping" -ForegroundColor Yellow
        }
    } else {
        Write-Host "⚠ $service not found - skipping" -ForegroundColor Yellow
    }
    Write-Host ""
    $counter++
}

# Summary
Set-Location $basePath
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Build Complete!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Next Steps:" -ForegroundColor Cyan
Write-Host "1. Start Eureka Server:" -ForegroundColor White
Write-Host "   cd eureka-server && mvn spring-boot:run" -ForegroundColor Gray
Write-Host ""
Write-Host "2. Start API Gateway:" -ForegroundColor White
Write-Host "   cd api-gateway && mvn spring-boot:run" -ForegroundColor Gray
Write-Host ""
Write-Host "3. Start Microservices (in separate terminals):" -ForegroundColor White
Write-Host "   cd iam-service && mvn spring-boot:run" -ForegroundColor Gray
Write-Host "   cd data-ingestion-service && mvn spring-boot:run" -ForegroundColor Gray
Write-Host "   ... and so on" -ForegroundColor Gray
Write-Host ""
Write-Host "OR use Docker Compose:" -ForegroundColor Cyan
Write-Host "   docker-compose up -d" -ForegroundColor Gray
Write-Host ""
Write-Host "Access Points:" -ForegroundColor Cyan
Write-Host "- Eureka Dashboard: http://localhost:8761" -ForegroundColor Gray
Write-Host "- API Gateway: http://localhost:8080" -ForegroundColor Gray
Write-Host "- Swagger UI: http://localhost:{port}/swagger-ui.html" -ForegroundColor Gray
Write-Host ""

