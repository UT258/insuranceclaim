package com.claiminsight360.performance.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "sla_violation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlaViolation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long violationId;
    
    @Column(nullable = false)
    private String claimId;
    
    @Column(nullable = false)
    private String violationType; // ResponseTime/ResolutionTime/DocumentSubmission/ApprovalDelay
    
    private LocalDateTime violationDate;
    
    private String assignedTo;
    
    private String department;
    
    private Integer delayDays;
    
    private String severity; // Low/Medium/High/Critical
    
    private Boolean isResolved;
    
    private LocalDateTime resolvedDate;
    
    private String resolutionNotes;
    
    @PrePersist
    protected void onCreate() {
        violationDate = LocalDateTime.now();
        isResolved = false;
    }
}
