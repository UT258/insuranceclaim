package com.claiminsight360.notification.dto;

import com.claiminsight360.notification.domain.NotificationCategory;
import com.claiminsight360.notification.domain.NotificationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record NotificationDto(
        Long notificationId,
        @NotNull Long userId,
        @NotBlank String message,
        NotificationCategory category,
        NotificationStatus status,
        LocalDateTime createdDate
) {
}
