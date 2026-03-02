package com.claiminsight360.denialanalysis.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "denial_pattern")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DenialPattern {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patternId;
    
    @Column(nullable = false)
    private String claimId;
    
    @Column(nullable = false)
    private String denialCode;
    
    @Column(length = 500)
    private String reason;
    
    private LocalDateTime occurrenceDate;
    
    private String denialCategory; // Administrative/Clinical/Payment/Coverage
    
    private Boolean isAppealed;
    
    private String appealStatus;
    
    private String rootCause;
    
    @PrePersist
    protected void onCreate() {
        occurrenceDate = LocalDateTime.now();
        isAppealed = false;
    }
}
