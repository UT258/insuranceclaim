package com.claiminsight.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "leakage_flag")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeakageFlag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leakageId;

    @Column(nullable = false)
    private String claimId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LeakageType leakageType;

    @Column(nullable = false)
    private Double estimatedLoss;

    @Column(nullable = false)
    private Long identifiedDate = System.currentTimeMillis();

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private String status; // IDENTIFIED, INVESTIGATING, RESOLVED

    public enum LeakageType {
        OVERPAYMENT,
        DELAY,
        ERROR,
        DUPLICATE,
        UNDERPAYMENT
    }
}

