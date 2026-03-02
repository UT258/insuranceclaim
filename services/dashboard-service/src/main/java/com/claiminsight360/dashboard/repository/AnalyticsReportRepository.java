package com.claiminsight360.dashboard.repository;

import com.claiminsight360.dashboard.domain.AnalyticsReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AnalyticsReportRepository extends JpaRepository<AnalyticsReport, Long> {
    List<AnalyticsReport> findByReportType(String reportType);
    List<AnalyticsReport> findByGeneratedBy(String generatedBy);
    List<AnalyticsReport> findByStatus(String status);
}
