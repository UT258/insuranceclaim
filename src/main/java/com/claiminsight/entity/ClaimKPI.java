package com.claiminsight.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "claim_kpi")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClaimKPI {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kpiId;

    @Column(nullable = false)
    private String claimId;

    @Column(nullable = false)
    private String metricName;

    @Column(nullable = false)
    private Double metricValue;

    @Column(nullable = false)
    private Long metricDate = System.currentTimeMillis();

    @Column
    private String unit;

    @Column
    private String category; // TAT, Severity, Frequency, Loss Ratio, Settlement
}

