package com.claiminsight360.performance.controller;

import com.claiminsight360.performance.domain.AdjusterPerformance;
import com.claiminsight360.performance.domain.SlaViolation;
import com.claiminsight360.performance.service.PerformanceAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/performance")
@RequiredArgsConstructor
public class PerformanceAnalyticsController {
    
    private final PerformanceAnalyticsService performanceAnalyticsService;
    
    @PostMapping("/adjuster")
    public ResponseEntity<AdjusterPerformance> recordPerformance(@RequestBody AdjusterPerformance performance) {
        AdjusterPerformance recorded = performanceAnalyticsService.recordPerformance(performance);
        return new ResponseEntity<>(recorded, HttpStatus.CREATED);
    }
    
    @GetMapping("/adjuster/{adjusterId}")
    public ResponseEntity<List<AdjusterPerformance>> getPerformanceByAdjuster(@PathVariable String adjusterId) {
        return ResponseEntity.ok(performanceAnalyticsService.getPerformanceByAdjuster(adjusterId));
    }
    
    @GetMapping("/adjuster/period/{periodValue}")
    public ResponseEntity<List<AdjusterPerformance>> getPerformanceByPeriod(@PathVariable String periodValue) {
        return ResponseEntity.ok(performanceAnalyticsService.getPerformanceByPeriod(periodValue));
    }
    
    @GetMapping("/adjuster/all")
    public ResponseEntity<List<AdjusterPerformance>> getAllPerformanceRecords() {
        return ResponseEntity.ok(performanceAnalyticsService.getAllPerformanceRecords());
    }
    
    @PostMapping("/sla-violations")
    public ResponseEntity<SlaViolation> recordSlaViolation(@RequestBody SlaViolation violation) {
        SlaViolation recorded = performanceAnalyticsService.recordSlaViolation(violation);
        return new ResponseEntity<>(recorded, HttpStatus.CREATED);
    }
    
    @GetMapping("/sla-violations/claim/{claimId}")
    public ResponseEntity<List<SlaViolation>> getViolationsByClaimId(@PathVariable String claimId) {
        return ResponseEntity.ok(performanceAnalyticsService.getViolationsByClaimId(claimId));
    }
    
    @GetMapping("/sla-violations/unresolved")
    public ResponseEntity<List<SlaViolation>> getUnresolvedViolations() {
        return ResponseEntity.ok(performanceAnalyticsService.getUnresolvedViolations());
    }
    
    @GetMapping("/sla-violations/adjuster/{adjusterId}")
    public ResponseEntity<List<SlaViolation>> getViolationsByAdjuster(@PathVariable String adjusterId) {
        return ResponseEntity.ok(performanceAnalyticsService.getViolationsByAdjuster(adjusterId));
    }
    
    @PutMapping("/sla-violations/{violationId}/resolve")
    public ResponseEntity<Void> resolveSlaViolation(
            @PathVariable Long violationId,
            @RequestParam String notes) {
        performanceAnalyticsService.resolveSlaViolation(violationId, notes);
        return ResponseEntity.ok().build();
    }
}
