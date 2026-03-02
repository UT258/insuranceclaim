package com.claiminsight360.dataingestion.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "data_feed")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataFeed {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedId;
    
    @Column(nullable = false)
    private String feedType; // Claim/Policy/Payment/Reserve
    
    @Column(nullable = false)
    private String sourceSystem;
    
    private LocalDateTime lastSyncDate;
    
    @Column(nullable = false)
    private String status; // Active/Failed/Pending
    
    private String errorMessage;
    
    private Integer recordsProcessed;
    
    private LocalDateTime createdDate;
    
    @PrePersist
    protected void onCreate() {
        createdDate = LocalDateTime.now();
    }
}
