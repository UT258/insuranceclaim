package com.claiminsight360.notification.web;

import com.claiminsight360.notification.dto.NotificationDto;
import com.claiminsight360.notification.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP", "service", "notification-service");
    }

    @GetMapping("/notifications")
    public List<NotificationDto> list() {
        return notificationService.findAll();
    }

    @PostMapping("/notifications")
    @ResponseStatus(HttpStatus.CREATED)
    public NotificationDto create(@Valid @RequestBody NotificationDto dto) {
        return notificationService.create(dto);
    }

    @PutMapping("/notifications/{id}")
    public NotificationDto markAsRead(@PathVariable Long id) {
        return notificationService.markAsRead(id);
    }
}
