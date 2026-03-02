package com.claiminsight360.performance.service;

import com.claiminsight360.performance.domain.AdjusterPerformance;
import com.claiminsight360.performance.domain.SlaViolation;
import com.claiminsight360.performance.repository.AdjusterPerformanceRepository;
import com.claiminsight360.performance.repository.SlaViolationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PerformanceAnalyticsService {
    
    private final AdjusterPerformanceRepository adjusterPerformanceRepository;
    private final SlaViolationRepository slaViolationRepository;
    
    @Transactional
    public AdjusterPerformance recordPerformance(AdjusterPerformance performance) {
        log.info("Recording performance for adjuster: {}", performance.getAdjusterId());
        return adjusterPerformanceRepository.save(performance);
    }
    
    @Transactional
    public SlaViolation recordSlaViolation(SlaViolation violation) {
        log.info("Recording SLA violation for claim: {}", violation.getClaimId());
        return slaViolationRepository.save(violation);
    }
    
    public List<AdjusterPerformance> getPerformanceByAdjuster(String adjusterId) {
        return adjusterPerformanceRepository.findByAdjusterId(adjusterId);
    }
    
    public List<AdjusterPerformance> getPerformanceByPeriod(String periodValue) {
        return adjusterPerformanceRepository.findByPeriodValue(periodValue);
    }
    
    public List<SlaViolation> getViolationsByClaimId(String claimId) {
        return slaViolationRepository.findByClaimId(claimId);
    }
    
    public List<SlaViolation> getUnresolvedViolations() {
        return slaViolationRepository.findByIsResolved(false);
    }
    
    public List<SlaViolation> getViolationsByAdjuster(String adjusterId) {
        return slaViolationRepository.findByAssignedTo(adjusterId);
    }
    
    @Transactional
    public void resolveSlaViolation(Long violationId, String notes) {
        SlaViolation violation = slaViolationRepository.findById(violationId)
            .orElseThrow(() -> new RuntimeException("SLA Violation not found"));
        violation.setIsResolved(true);
        violation.setResolvedDate(LocalDateTime.now());
        violation.setResolutionNotes(notes);
        slaViolationRepository.save(violation);
    }
    
    public List<AdjusterPerformance> getAllPerformanceRecords() {
        return adjusterPerformanceRepository.findAll();
    }
}
