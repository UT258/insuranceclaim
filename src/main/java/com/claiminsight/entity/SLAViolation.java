package com.claiminsight.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sla_violation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SLAViolation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long violationId;

    @Column(nullable = false)
    private String claimId;

    @Column(nullable = false)
    private String violationType;

    @Column(nullable = false)
    private Long violationDate = System.currentTimeMillis();

    @Column
    private String severity; // LOW, MEDIUM, HIGH, CRITICAL

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private String status; // ACTIVE, RESOLVED, ACKNOWLEDGED
}

