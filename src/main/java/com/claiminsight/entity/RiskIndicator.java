package com.claiminsight.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "risk_indicator")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiskIndicator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long indicatorId;

    @Column(nullable = false)
    private String claimId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private IndicatorType indicatorType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeverityLevel severity;

    @Column(nullable = false)
    private Long triggeredDate = System.currentTimeMillis();

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private Boolean acknowledged = false;

    public enum IndicatorType {
        HIGH_COST,
        UNUSUAL_TIMING,
        PATTERN_MATCH,
        FRAUD_SIGNAL,
        ANOMALY
    }

    public enum SeverityLevel {
        LOW,
        MEDIUM,
        HIGH,
        CRITICAL
    }
}

