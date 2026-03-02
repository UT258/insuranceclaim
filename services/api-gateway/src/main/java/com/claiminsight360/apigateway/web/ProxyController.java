package com.claiminsight360.apigateway.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ProxyController {

    private final RestClient restClient;
    private final String claimsServiceBase;
    private final String identityServiceBase;
    private final String analyticsServiceBase;
    private final String notificationServiceBase;

    public ProxyController(
            RestClient restClient,
            @Value("${services.claims.base-url}") String claimsServiceBase,
            @Value("${services.identity.base-url}") String identityServiceBase,
            @Value("${services.analytics.base-url}") String analyticsServiceBase,
            @Value("${services.notification.base-url}") String notificationServiceBase) {
        this.restClient = restClient;
        this.claimsServiceBase = claimsServiceBase;
        this.identityServiceBase = identityServiceBase;
        this.analyticsServiceBase = analyticsServiceBase;
        this.notificationServiceBase = notificationServiceBase;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP", "service", "api-gateway");
    }

    @GetMapping("/claims/dashboard")
    public ResponseEntity<String> claimsDashboard() {
        String body = restClient.get().uri(claimsServiceBase + "/api/v1/dashboard/summary").retrieve().body(String.class);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/identity/users")
    public ResponseEntity<String> users() {
        String body = restClient.get().uri(identityServiceBase + "/api/v1/users").retrieve().body(String.class);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/analytics/kpis")
    public ResponseEntity<String> kpis() {
        String body = restClient.get().uri(analyticsServiceBase + "/api/v1/kpis").retrieve().body(String.class);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/notifications")
    public ResponseEntity<String> notifications() {
        String body = restClient.get().uri(notificationServiceBase + "/api/v1/notifications").retrieve().body(String.class);
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{service}/{path}")
    public ResponseEntity<String> simpleRoute(@PathVariable String service, @PathVariable String path) {
        String base = switch (service) {
            case "claims" -> claimsServiceBase;
            case "identity" -> identityServiceBase;
            case "analytics" -> analyticsServiceBase;
            case "notification" -> notificationServiceBase;
            default -> "";
        };
        if (base.isBlank()) {
            return ResponseEntity.badRequest().body("Unknown service: " + service);
        }
        String body = restClient.get().uri(base + "/" + path).retrieve().body(String.class);
        return ResponseEntity.ok(body);
    }
}
