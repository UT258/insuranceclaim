package com.claiminsight360.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class NotificationController {

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP", "service", "notification-service");
    }

    @GetMapping("/notifications")
    public List<Map<String, Object>> listNotifications() {
        return List.of(
            Map.of(
                "notificationId", 1L,
                "userId", 1L,
                "message", "High-risk claim detected: CLM-2024-001234",
                "category", "RISK",
                "status", "UNREAD",
                "createdDate", LocalDateTime.now()
            ),
            Map.of(
                "notificationId", 2L,
                "userId", 1L,
                "message", "Denial spike in Product X detected",
                "category", "DENIAL",
                "status", "READ",
                "createdDate", LocalDateTime.now().minusHours(2)
            )
        );
    }

    @PostMapping("/notifications")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> createNotification(@RequestBody Map<String, Object> notificationDto) {
        return Map.of(
            "notificationId", 3L,
            "userId", notificationDto.getOrDefault("userId", 1L),
            "message", notificationDto.getOrDefault("message", "New notification"),
            "category", notificationDto.getOrDefault("category", "RISK"),
            "status", "UNREAD",
            "createdDate", LocalDateTime.now()
        );
    }

    @PutMapping("/notifications/{id}")
    public Map<String, Object> updateNotification(@PathVariable Long id, @RequestBody Map<String, Object> updateDto) {
        return Map.of(
            "notificationId", id,
            "status", updateDto.getOrDefault("status", "READ")
        );
    }
}
