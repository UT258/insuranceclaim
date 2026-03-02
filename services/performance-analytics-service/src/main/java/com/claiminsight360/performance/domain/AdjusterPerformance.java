package com.claiminsight360.performance.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "adjuster_performance")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdjusterPerformance {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long perfId;
    
    @Column(nullable = false)
    private String adjusterId;
    
    @Column(nullable = false)
    private String adjusterName;
    
    private Integer claimsHandled;
    
    @Column(precision = 10, scale = 2)
    private BigDecimal avgTat; // Average Turnaround Time in days
    
    @Column(precision = 5, scale = 2)
    private BigDecimal qualityScore; // 0-100
    
    @Column(nullable = false)
    private String period; // Monthly/Quarterly/Yearly
    
    private String periodValue; // 2026-01, Q1-2026, 2026
    
    private Integer claimsClosed;
    
    private Integer claimsPending;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal productivityScore;
    
    private Integer slaViolations;
}
