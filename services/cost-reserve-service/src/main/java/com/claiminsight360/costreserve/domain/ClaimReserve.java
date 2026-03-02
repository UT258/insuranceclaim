package com.claiminsight360.costreserve.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "claim_reserve")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaimReserve {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reserveId;
    
    @Column(nullable = false, unique = true)
    private String claimId;
    
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal reserveAmount;
    
    private LocalDateTime updatedDate;
    
    private String updatedBy;
    
    @Column(precision = 15, scale = 2)
    private BigDecimal initialReserve;
    
    @Column(precision = 15, scale = 2)
    private BigDecimal paidAmount;
    
    @Column(precision = 15, scale = 2)
    private BigDecimal remainingReserve;
    
    private String reserveStatus; // Active/Closed/Adjusted
    
    @PrePersist
    protected void onCreate() {
        updatedDate = LocalDateTime.now();
        reserveStatus = "Active";
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedDate = LocalDateTime.now();
    }
}
