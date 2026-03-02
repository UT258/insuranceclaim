package com.claiminsight360.fraudrisk.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "risk_score")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RiskScore {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scoreId;
    
    @Column(nullable = false, unique = true)
    private String claimId;
    
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal scoreValue; // 0.00 to 100.00
    
    private String riskCategory; // Low/Medium/High/Critical
    
    private String calculationMethod;
    
    private LocalDateTime computedDate;
    
    private Integer anomalyCount;
    
    @PrePersist
    protected void onCreate() {
        computedDate = LocalDateTime.now();
    }
}
