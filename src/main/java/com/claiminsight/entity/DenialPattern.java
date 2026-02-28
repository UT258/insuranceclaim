package com.claiminsight.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "denial_pattern")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DenialPattern {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patternId;

    @Column(nullable = false)
    private String claimId;

    @Column(nullable = false)
    private String denialCode;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = false)
    private Long occurrenceDate = System.currentTimeMillis();

    @Column(columnDefinition = "TEXT")
    private String rootCause;

    @Column
    private String category; // Policy, Medical, Documentation, Fraud
}

