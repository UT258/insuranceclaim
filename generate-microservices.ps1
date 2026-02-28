# ClaimInsight360 Microservices Generation Script
# This script creates all remaining microservice structures

$basePath = "D:\New folder\Java SpringBoot\demo"

# Define all services
$services = @(
    @{
        Name = "metrics-engine-service"
        Port = 8083
        Package = "metrics"
        Entities = @("ClaimKPI")
    },
    @{
        Name = "fraud-risk-service"
        Port = 8084
        Package = "fraudrisk"
        Entities = @("RiskIndicator", "RiskScore")
    },
    @{
        Name = "denial-analysis-service"
        Port = 8085
        Package = "denial"
        Entities = @("DenialPattern", "LeakageFlag")
    },
    @{
        Name = "adjuster-performance-service"
        Port = 8086
        Package = "adjuster"
        Entities = @("AdjusterPerformance", "SLAViolation")
    },
    @{
        Name = "cost-reserve-service"
        Port = 8087
        Package = "costreserve"
        Entities = @("ClaimCost", "ClaimReserve", "AgingRecord")
    },
    @{
        Name = "dashboard-reports-service"
        Port = 8088
        Package = "dashboard"
        Entities = @("AnalyticsReport")
    },
    @{
        Name = "notification-service"
        Port = 8089
        Package = "notification"
        Entities = @("Notification")
    }
)

Write-Host "Creating remaining microservices structure..." -ForegroundColor Green

foreach ($service in $services) {
    $servicePath = Join-Path $basePath $service.Name
    $javaPath = Join-Path $servicePath "src\main\java\com\claiminsight\$($service.Package)"
    $resourcePath = Join-Path $servicePath "src\main\resources"

    Write-Host "`nCreating $($service.Name)..." -ForegroundColor Yellow

    # Create directory structure
    $dirs = @("entity", "repository", "service", "controller", "dto", "config")
    foreach ($dir in $dirs) {
        $dirPath = Join-Path $javaPath $dir
        New-Item -ItemType Directory -Force -Path $dirPath | Out-Null
    }

    New-Item -ItemType Directory -Force -Path $resourcePath | Out-Null

    Write-Host "  ✓ Directory structure created" -ForegroundColor Gray
}

Write-Host "`n✓ All microservice structures created successfully!" -ForegroundColor Green
Write-Host "`nNext steps:" -ForegroundColor Cyan
Write-Host "1. Review the generated structure"
Write-Host "2. Build common-lib first: cd common-lib && mvn clean install"
Write-Host "3. Start Eureka Server: cd eureka-server && mvn spring-boot:run"
Write-Host "4. Start API Gateway: cd api-gateway && mvn spring-boot:run"
Write-Host "5. Start individual microservices"

