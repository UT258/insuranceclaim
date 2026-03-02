package com.claiminsight360.dashboard.service;

import com.claiminsight360.dashboard.domain.AnalyticsReport;
import com.claiminsight360.dashboard.repository.AnalyticsReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DashboardService {
    
    private final AnalyticsReportRepository analyticsReportRepository;
    
    @Transactional
    public AnalyticsReport generateReport(AnalyticsReport report) {
        log.info("Generating report: {}", report.getReportName());
        return analyticsReportRepository.save(report);
    }
    
    public List<AnalyticsReport> getAllReports() {
        return analyticsReportRepository.findAll();
    }
    
    public List<AnalyticsReport> getReportsByType(String reportType) {
        return analyticsReportRepository.findByReportType(reportType);
    }
    
    public AnalyticsReport getReportById(Long reportId) {
        return analyticsReportRepository.findById(reportId).orElse(null);
    }
    
    public List<AnalyticsReport> getReportsByUser(String username) {
        return analyticsReportRepository.findByGeneratedBy(username);
    }
    
    @Transactional
    public void deleteReport(Long reportId) {
        log.info("Deleting report: {}", reportId);
        analyticsReportRepository.deleteById(reportId);
    }
}
