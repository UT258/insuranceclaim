package com.claiminsight360.denialanalysis.service;

import com.claiminsight360.denialanalysis.domain.DenialPattern;
import com.claiminsight360.denialanalysis.domain.LeakageFlag;
import com.claiminsight360.denialanalysis.repository.DenialPatternRepository;
import com.claiminsight360.denialanalysis.repository.LeakageFlagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class DenialAnalysisService {
    
    private final DenialPatternRepository denialPatternRepository;
    private final LeakageFlagRepository leakageFlagRepository;
    
    @Transactional
    public DenialPattern recordDenial(DenialPattern denialPattern) {
        log.info("Recording denial for claim: {}", denialPattern.getClaimId());
        return denialPatternRepository.save(denialPattern);
    }
    
    @Transactional
    public LeakageFlag flagLeakage(LeakageFlag leakageFlag) {
        log.info("Flagging leakage for claim: {}", leakageFlag.getClaimId());
        return leakageFlagRepository.save(leakageFlag);
    }
    
    public List<DenialPattern> getDenialsByClaimId(String claimId) {
        return denialPatternRepository.findByClaimId(claimId);
    }
    
    public List<LeakageFlag> getLeakagesByClaimId(String claimId) {
        return leakageFlagRepository.findByClaimId(claimId);
    }
    
    public List<DenialPattern> getDenialsByCode(String denialCode) {
        return denialPatternRepository.findByDenialCode(denialCode);
    }
    
    public Map<String, Long> getDenialCodeAnalysis() {
        List<DenialPattern> allDenials = denialPatternRepository.findAll();
        return allDenials.stream()
            .collect(Collectors.groupingBy(DenialPattern::getDenialCode, Collectors.counting()));
    }
    
    public BigDecimal getTotalLeakageAmount() {
        List<LeakageFlag> leakages = leakageFlagRepository.findAll();
        return leakages.stream()
            .map(LeakageFlag::getEstimatedLoss)
            .filter(amount -> amount != null)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public List<LeakageFlag> getUninvestigatedLeakages() {
        return leakageFlagRepository.findByIsInvestigated(false);
    }
    
    @Transactional
    public void markLeakageAsInvestigated(Long leakageId, String notes) {
        LeakageFlag leakage = leakageFlagRepository.findById(leakageId)
            .orElseThrow(() -> new RuntimeException("Leakage not found"));
        leakage.setIsInvestigated(true);
        leakage.setInvestigationNotes(notes);
        leakageFlagRepository.save(leakage);
    }
    
    @Transactional
    public void recordLeakageRecovery(Long leakageId, BigDecimal recoveredAmount) {
        LeakageFlag leakage = leakageFlagRepository.findById(leakageId)
            .orElseThrow(() -> new RuntimeException("Leakage not found"));
        leakage.setIsRecovered(true);
        leakage.setRecoveredAmount(recoveredAmount);
        leakageFlagRepository.save(leakage);
    }
}
