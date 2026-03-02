package com.claiminsight360.costreserve.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "claim_cost")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaimCost {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long costId;
    
    @Column(nullable = false)
    private String claimId;
    
    @Column(nullable = false)
    private String costType; // Medical/Legal/Repair/Settlement/Administrative/Investigation
    
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;
    
    private LocalDateTime costDate;
    
    private String vendor;
    
    private String approvedBy;
    
    private String costCategory;
    
    private String description;
    
    @PrePersist
    protected void onCreate() {
        costDate = LocalDateTime.now();
    }
}
