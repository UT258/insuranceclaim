package com.claiminsight360.dashboard.controller;

import com.claiminsight360.dashboard.domain.AnalyticsReport;
import com.claiminsight360.dashboard.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    
    private final DashboardService dashboardService;
    
    @PostMapping("/reports")
    public ResponseEntity<AnalyticsReport> generateReport(@RequestBody AnalyticsReport report) {
        AnalyticsReport generated = dashboardService.generateReport(report);
        return new ResponseEntity<>(generated, HttpStatus.CREATED);
    }
    
    @GetMapping("/reports")
    public ResponseEntity<List<AnalyticsReport>> getAllReports() {
        return ResponseEntity.ok(dashboardService.getAllReports());
    }
    
    @GetMapping("/reports/{reportId}")
    public ResponseEntity<AnalyticsReport> getReportById(@PathVariable Long reportId) {
        AnalyticsReport report = dashboardService.getReportById(reportId);
        if (report != null) {
            return ResponseEntity.ok(report);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/reports/type/{reportType}")
    public ResponseEntity<List<AnalyticsReport>> getReportsByType(@PathVariable String reportType) {
        return ResponseEntity.ok(dashboardService.getReportsByType(reportType));
    }
    
    @GetMapping("/reports/user/{username}")
    public ResponseEntity<List<AnalyticsReport>> getReportsByUser(@PathVariable String username) {
        return ResponseEntity.ok(dashboardService.getReportsByUser(username));
    }
    
    @DeleteMapping("/reports/{reportId}")
    public ResponseEntity<Void> deleteReport(@PathVariable Long reportId) {
        dashboardService.deleteReport(reportId);
        return ResponseEntity.noContent().build();
    }
}
