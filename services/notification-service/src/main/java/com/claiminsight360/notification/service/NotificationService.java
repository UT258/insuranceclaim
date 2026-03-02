package com.claiminsight360.notification.service;

import com.claiminsight360.notification.domain.Notification;
import com.claiminsight360.notification.dto.NotificationDto;
import com.claiminsight360.notification.repo.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository repository;

    public NotificationService(NotificationRepository repository) {
        this.repository = repository;
    }

    public List<NotificationDto> findAll() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    public NotificationDto create(NotificationDto dto) {
        Notification notification = new Notification();
        notification.setUserId(dto.userId());
        notification.setMessage(dto.message());
        notification.setCategory(dto.category());
        notification.setStatus(dto.status());
        notification.setCreatedDate(dto.createdDate() == null ? LocalDateTime.now() : dto.createdDate());
        return toDto(repository.save(notification));
    }

    private NotificationDto toDto(Notification notification) {
        return new NotificationDto(
                notification.getNotificationId(),
                notification.getUserId(),
                notification.getMessage(),
                notification.getCategory(),
                notification.getStatus(),
                notification.getCreatedDate());
    }
}
