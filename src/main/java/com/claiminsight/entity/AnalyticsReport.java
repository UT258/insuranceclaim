package com.claiminsight.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "analytics_report")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnalyticsReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    @Column(nullable = false)
    private String reportName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportScope scope;

    @Column(columnDefinition = "TEXT")
    private String metrics;

    @Column(nullable = false)
    private Long generatedDate = System.currentTimeMillis();

    @Column
    private Long generatedBy;

    @Column(columnDefinition = "TEXT")
    private String filters; // JSON filters applied

    @Column(columnDefinition = "LONGTEXT")
    private String reportData; // JSON report data

    @Column
    private String format; // PDF, EXCEL, JSON

    public enum ReportScope {
        PRODUCT,
        REGION,
        CLAIM_TYPE,
        PERIOD,
        ADJUSTER,
        CUSTOM
    }
}

