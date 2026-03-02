package com.claiminsight360.fraudrisk.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "risk_indicator")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiskIndicator {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long indicatorId;
    
    @Column(nullable = false)
    private String claimId;
    
    @Column(nullable = false)
    private String indicatorType; // HighCost/UnusualTiming/Pattern/DuplicateClaim/SuspiciousProvider
    
    @Column(nullable = false)
    private String severity; // Low/Medium/High/Critical
    
    private String description;
    
    private LocalDateTime triggeredDate;
    
    private Boolean isResolved;
    
    private String resolvedBy;
    
    private LocalDateTime resolvedDate;
    
    @PrePersist
    protected void onCreate() {
        triggeredDate = LocalDateTime.now();
        isResolved = false;
    }
}
