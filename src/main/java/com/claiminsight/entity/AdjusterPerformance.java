package com.claiminsight.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "adjuster_performance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdjusterPerformance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long perfId;

    @Column(nullable = false)
    private String adjusterId;

    @Column(nullable = false)
    private String adjusterName;

    @Column(nullable = false)
    private Integer claimsHandled;

    @Column(nullable = false)
    private Double avgTat; // Average Turnaround Time in days

    @Column(nullable = false)
    private Double qualityScore; // 0-100

    @Column(nullable = false)
    private Long period; // Month/Year timestamp

    @Column
    private Double productivityScore;

    @Column
    private Integer pendingClaims;

    @Column
    private String team;
}

