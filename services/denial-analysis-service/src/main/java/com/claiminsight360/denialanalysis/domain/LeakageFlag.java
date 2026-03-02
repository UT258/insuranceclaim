package com.claiminsight360.denialanalysis.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "leakage_flag")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeakageFlag {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leakageId;
    
    @Column(nullable = false)
    private String claimId;
    
    @Column(nullable = false)
    private String leakageType; // Overpayment/Delay/Error/UncapturedDiscount/ProcessingError
    
    @Column(precision = 15, scale = 2)
    private BigDecimal estimatedLoss;
    
    private LocalDateTime identifiedDate;
    
    private String identifiedBy;
    
    private Boolean isInvestigated;
    
    private String investigationNotes;
    
    private Boolean isRecovered;
    
    private BigDecimal recoveredAmount;
    
    @PrePersist
    protected void onCreate() {
        identifiedDate = LocalDateTime.now();
        isInvestigated = false;
        isRecovered = false;
    }
}
