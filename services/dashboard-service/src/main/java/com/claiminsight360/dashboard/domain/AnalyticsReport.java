package com.claiminsight360.dashboard.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "analytics_report")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsReport {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;
    
    @Column(nullable = false)
    private String reportName;
    
    @Column(nullable = false)
    private String reportType; // KPI/Fraud/Denial/Performance/Cost/Aging
    
    @Column(length = 2000)
    private String scope; // Product/Region/ClaimType/Period
    
    @Column(columnDefinition = "TEXT")
    private String metricsJson; // JSON data of metrics
    
    private LocalDateTime generatedDate;
    
    private String generatedBy;
    
    private String periodStart;
    
    private String periodEnd;
    
    private String status; // Generated/Scheduled/Failed
    
    @PrePersist
    protected void onCreate() {
        generatedDate = LocalDateTime.now();
        status = "Generated";
    }
}
