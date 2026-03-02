package com.claiminsight360.dataingestion.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "claim_raw")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaimRaw {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rawId;
    
    @Column(nullable = false)
    private String claimId;
    
    @Column(columnDefinition = "TEXT")
    private String payloadJson;
    
    private LocalDateTime ingestedDate;
    
    private String sourceSystem;
    
    private String processStatus; // Pending/Processed/Failed
    
    @PrePersist
    protected void onCreate() {
        ingestedDate = LocalDateTime.now();
        processStatus = "Pending";
    }
}
