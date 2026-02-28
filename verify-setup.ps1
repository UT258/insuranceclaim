rt# ClaimInsight360 - Setup Verification Script
# Checks if all components are properly configured

$ErrorActionPreference = "Continue"

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "ClaimInsight360 Setup Verification" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

$basePath = "D:\New folder\Java SpringBoot\demo"
$allChecks = @()

function Test-Component {
    param(
        [string]$Name,
        [string]$Path,
        [string]$Type
    )

    $fullPath = Join-Path $basePath $Path
    $exists = Test-Path $fullPath

    $status = if ($exists) { "✅" } else { "❌" }
    $color = if ($exists) { "Green" } else { "Red" }

    Write-Host "$status $Name" -ForegroundColor $color

    return @{
        Name = $Name
        Exists = $exists
        Type = $Type
    }
}

# Check Infrastructure Services
Write-Host "`n📦 Infrastructure Services:" -ForegroundColor Yellow
$allChecks += Test-Component "Eureka Server" "eureka-server/pom.xml" "Infrastructure"
$allChecks += Test-Component "API Gateway" "api-gateway/pom.xml" "Infrastructure"
$allChecks += Test-Component "Common Library" "common-lib/pom.xml" "Infrastructure"

# Check Business Services - Complete
Write-Host "`n✅ Completed Business Services:" -ForegroundColor Yellow
$allChecks += Test-Component "IAM Service" "iam-service/pom.xml" "Complete"
$allChecks += Test-Component "Data Ingestion Service" "data-ingestion-service/pom.xml" "Complete"

# Check Business Services - Pending
Write-Host "`n⚙️  Pending Business Services:" -ForegroundColor Yellow
$allChecks += Test-Component "Metrics Engine Service" "metrics-engine-service/pom.xml" "Pending"
$allChecks += Test-Component "Fraud Risk Service" "fraud-risk-service/pom.xml" "Pending"
$allChecks += Test-Component "Denial Analysis Service" "denial-analysis-service/pom.xml" "Pending"
$allChecks += Test-Component "Adjuster Performance Service" "adjuster-performance-service/pom.xml" "Pending"
$allChecks += Test-Component "Cost Reserve Service" "cost-reserve-service/pom.xml" "Pending"
$allChecks += Test-Component "Dashboard Reports Service" "dashboard-reports-service/pom.xml" "Pending"
$allChecks += Test-Component "Notification Service" "notification-service/pom.xml" "Pending"

# Check Documentation
Write-Host "`n📚 Documentation:" -ForegroundColor Yellow
$allChecks += Test-Component "README.md" "README.md" "Documentation"
$allChecks += Test-Component "QUICK-START.md" "QUICK-START.md" "Documentation"
$allChecks += Test-Component "API-DOCUMENTATION.md" "API-DOCUMENTATION.md" "Documentation"
$allChecks += Test-Component "PROJECT-SUMMARY.md" "PROJECT-SUMMARY.md" "Documentation"
$allChecks += Test-Component "QUICK-REFERENCE.md" "QUICK-REFERENCE.md" "Documentation"

# Check DevOps Files
Write-Host "`n🐳 DevOps Configuration:" -ForegroundColor Yellow
$allChecks += Test-Component "Docker Compose" "docker-compose.yml" "DevOps"
$allChecks += Test-Component "Build Script" "build-all.ps1" "DevOps"
$allChecks += Test-Component "Eureka Dockerfile" "eureka-server/Dockerfile" "DevOps"
$allChecks += Test-Component "Gateway Dockerfile" "api-gateway/Dockerfile" "DevOps"
$allChecks += Test-Component "IAM Dockerfile" "iam-service/Dockerfile" "DevOps"

# Check Key Application Files
Write-Host "`n🔧 Key Application Files:" -ForegroundColor Yellow
$allChecks += Test-Component "IAM Application" "iam-service/src/main/java/com/claiminsight/iam/IamServiceApplication.java" "Code"
$allChecks += Test-Component "IAM Controller" "iam-service/src/main/java/com/claiminsight/iam/controller/AuthController.java" "Code"
$allChecks += Test-Component "Data Ingestion Application" "data-ingestion-service/src/main/java/com/claiminsight/dataingestion/DataIngestionServiceApplication.java" "Code"
$allChecks += Test-Component "Data Ingestion Controller" "data-ingestion-service/src/main/java/com/claiminsight/dataingestion/controller/DataIngestionController.java" "Code"
$allChecks += Test-Component "Common API Response" "common-lib/src/main/java/com/claiminsight/common/dto/ApiResponse.java" "Code"

# Summary
Write-Host "`n========================================" -ForegroundColor Cyan
Write-Host "Verification Summary" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan

$totalChecks = $allChecks.Count
$passedChecks = ($allChecks | Where-Object { $_.Exists }).Count
$failedChecks = $totalChecks - $passedChecks

Write-Host "`nTotal Components: $totalChecks" -ForegroundColor White
Write-Host "✅ Exists: $passedChecks" -ForegroundColor Green
Write-Host "❌ Missing: $failedChecks" -ForegroundColor Red

$percentage = [math]::Round(($passedChecks / $totalChecks) * 100, 2)
Write-Host "`nCompletion: $percentage%" -ForegroundColor Cyan

# Categorize results
Write-Host "`n📊 Breakdown by Category:" -ForegroundColor Yellow

$categories = $allChecks | Group-Object -Property Type
foreach ($category in $categories) {
    $catTotal = $category.Count
    $catPassed = ($category.Group | Where-Object { $_.Exists }).Count
    $catPercent = [math]::Round(($catPassed / $catTotal) * 100, 2)
    Write-Host "  $($category.Name): $catPassed/$catTotal ($catPercent%)" -ForegroundColor Gray
}

# Recommendations
Write-Host "`n💡 Recommendations:" -ForegroundColor Yellow

if ($failedChecks -eq 0) {
    Write-Host "  ✨ All components are in place!" -ForegroundColor Green
    Write-Host "  ✨ You can proceed with building and running the services" -ForegroundColor Green
} else {
    Write-Host "  ⚠️  Some components are missing" -ForegroundColor Yellow

    $missingInfra = $allChecks | Where-Object { $_.Type -eq "Infrastructure" -and -not $_.Exists }
    if ($missingInfra) {
        Write-Host "  ❌ Critical: Infrastructure components missing!" -ForegroundColor Red
        Write-Host "     Cannot run services without these components" -ForegroundColor Red
    }

    $missingComplete = $allChecks | Where-Object { $_.Type -eq "Complete" -and -not $_.Exists }
    if ($missingComplete) {
        Write-Host "  ❌ Warning: Completed services missing!" -ForegroundColor Red
        Write-Host "     IAM and Data Ingestion should be fully implemented" -ForegroundColor Red
    }

    $missingPending = $allChecks | Where-Object { $_.Type -eq "Pending" -and -not $_.Exists }
    if ($missingPending) {
        Write-Host "  ℹ️  Info: Pending services not yet implemented" -ForegroundColor Cyan
        Write-Host "     This is expected - implement as needed" -ForegroundColor Cyan
    }
}

Write-Host "`n✨ Next Steps:" -ForegroundColor Cyan
Write-Host "1. Review PROJECT-SUMMARY.md for implementation status" -ForegroundColor Gray
Write-Host "2. Follow QUICK-START.md to run completed services" -ForegroundColor Gray
Write-Host "3. Implement pending services following the established pattern" -ForegroundColor Gray
Write-Host "4. Run build-all.ps1 to build all services" -ForegroundColor Gray
Write-Host ""

# Check Java and Maven
Write-Host "🔍 Environment Check:" -ForegroundColor Yellow
try {
    $javaVersion = java -version 2>&1 | Select-String "version" | Select-Object -First 1
    Write-Host "  ✅ Java: $javaVersion" -ForegroundColor Green
} catch {
    Write-Host "  ❌ Java not found - Please install Java 17 or higher" -ForegroundColor Red
}

try {
    $mavenVersion = mvn -version 2>&1 | Select-String "Apache Maven" | Select-Object -First 1
    Write-Host "  ✅ Maven: $mavenVersion" -ForegroundColor Green
} catch {
    Write-Host "  ❌ Maven not found - Please install Maven 3.6 or higher" -ForegroundColor Red
}

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Verification Complete!" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

