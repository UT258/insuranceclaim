package com.claiminsight.gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/iam")
    public ResponseEntity<Map<String, String>> iamFallback() {
        return buildFallbackResponse("IAM Service");
    }

    @GetMapping("/data-ingestion")
    public ResponseEntity<Map<String, String>> dataIngestionFallback() {
        return buildFallbackResponse("Data Ingestion Service");
    }

    @GetMapping("/metrics")
    public ResponseEntity<Map<String, String>> metricsFallback() {
        return buildFallbackResponse("Metrics Engine Service");
    }

    @GetMapping("/fraud-risk")
    public ResponseEntity<Map<String, String>> fraudRiskFallback() {
        return buildFallbackResponse("Fraud Risk Service");
    }

    @GetMapping("/denial")
    public ResponseEntity<Map<String, String>> denialFallback() {
        return buildFallbackResponse("Denial Analysis Service");
    }

    @GetMapping("/adjuster")
    public ResponseEntity<Map<String, String>> adjusterFallback() {
        return buildFallbackResponse("Adjuster Performance Service");
    }

    @GetMapping("/cost-reserve")
    public ResponseEntity<Map<String, String>> costReserveFallback() {
        return buildFallbackResponse("Cost Reserve Service");
    }

    @GetMapping("/dashboard")
    public ResponseEntity<Map<String, String>> dashboardFallback() {
        return buildFallbackResponse("Dashboard Reports Service");
    }

    @GetMapping("/notification")
    public ResponseEntity<Map<String, String>> notificationFallback() {
        return buildFallbackResponse("Notification Service");
    }

    private ResponseEntity<Map<String, String>> buildFallbackResponse(String serviceName) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Service Unavailable");
        response.put("message", serviceName + " is currently unavailable. Please try again later.");
        response.put("status", "503");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
}

