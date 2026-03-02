package com.claiminsight360.backend.controller;

import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class AnalyticsController {

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP", "service", "analytics-service");
    }

    @GetMapping("/kpis")
    public List<Map<String, Object>> listKpis() {
        return List.of(
            Map.of(
                "kpiId", 1L,
                "claimId", "CLM-2024-001",
                "metricName", "TAT",
                "metricValue", new BigDecimal("5.2"),
                "metricDate", LocalDate.now()
            ),
            Map.of(
                "kpiId", 2L,
                "claimId", "CLM-2024-002",
                "metricName", "Severity",
                "metricValue", new BigDecimal("8.5"),
                "metricDate", LocalDate.now()
            ),
            Map.of(
                "kpiId", 3L,
                "claimId", "CLM-2024-003",
                "metricName", "LossRatio",
                "metricValue", new BigDecimal("0.65"),
                "metricDate", LocalDate.now()
            )
        );
    }

    @GetMapping("/trends")
    public Map<String, Object> getTrends() {
        return Map.of(
            "period", "2024-Q1",
            "claimVolume", 1250,
            "avgSeverity", new BigDecimal("6.8"),
            "denialRate", new BigDecimal("0.12"),
            "totalReserve", new BigDecimal("2500000.00")
        );
    }

    @GetMapping("/risk-summary")
    public Map<String, Object> getRiskSummary() {
        return Map.of(
            "highRiskClaims", 23,
            "anomaliesDetected", 15,
            "avgRiskScore", new BigDecimal("6.2")
        );
    }
}
