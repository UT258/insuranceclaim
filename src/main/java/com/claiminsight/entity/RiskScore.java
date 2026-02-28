package com.claiminsight.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "risk_score")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiskScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scoreId;

    @Column(nullable = false)
    private String claimId;

    @Column(nullable = false)
    private Double scoreValue; // 0-100

    @Column(nullable = false)
    private Long computedDate = System.currentTimeMillis();

    @Column
    private String riskLevel; // LOW, MEDIUM, HIGH, CRITICAL

    @Column(columnDefinition = "TEXT")
    private String scoringFactors;
}

