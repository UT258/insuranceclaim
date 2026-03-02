package com.claiminsight360.costreserve.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "aging_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgingRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agingId;
    
    @Column(nullable = false)
    private String claimId;
    
    @Column(nullable = false)
    private Integer agingDays;
    
    @Column(nullable = false)
    private String agingBucket; // 0-30/31-60/61-90/91-180/180+
    
    private String claimStatus;
    
    private String assignedAdjuster;
    
    private Boolean isStale; // If aging > 180 days
    
    private String priority; // Low/Medium/High/Urgent
}
