package com.claiminsight.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notification")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus status;

    @Column(nullable = false)
    private Long createdDate = System.currentTimeMillis();

    @Column
    private Long readDate;

    @Column
    private String sourceId; // Link to claim or report

    @Column
    private String sourceType; // CLAIM, REPORT, ALERT

    public enum NotificationCategory {
        RISK,
        DENIAL,
        COST,
        PERFORMANCE,
        SLA_VIOLATION,
        SYSTEM_ALERT,
        CUSTOM
    }

    public enum NotificationStatus {
        UNREAD,
        READ,
        DISMISSED
    }
}

