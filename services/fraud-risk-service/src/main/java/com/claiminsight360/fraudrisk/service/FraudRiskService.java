package com.claiminsight360.fraudrisk.service;

import com.claiminsight360.fraudrisk.domain.RiskIndicator;
import com.claiminsight360.fraudrisk.domain.RiskScore;
import com.claiminsight360.fraudrisk.repository.RiskIndicatorRepository;
import com.claiminsight360.fraudrisk.repository.RiskScoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FraudRiskService {
    
    private final RiskIndicatorRepository riskIndicatorRepository;
    private final RiskScoreRepository riskScoreRepository;
    
    @Transactional
    public RiskIndicator createRiskIndicator(RiskIndicator indicator) {
        log.info("Creating risk indicator for claim: {}", indicator.getClaimId());
        return riskIndicatorRepository.save(indicator);
    }
    
    @Transactional
    public RiskScore calculateRiskScore(String claimId) {
        log.info("Calculating risk score for claim: {}", claimId);
        
        List<RiskIndicator> indicators = riskIndicatorRepository.findByClaimId(claimId);
        
        // Simple rule-based scoring
        BigDecimal score = BigDecimal.ZERO;
        for (RiskIndicator indicator : indicators) {
            if (!indicator.getIsResolved()) {
                switch (indicator.getSeverity()) {
                    case "Critical" -> score = score.add(BigDecimal.valueOf(25));
                    case "High" -> score = score.add(BigDecimal.valueOf(15));
                    case "Medium" -> score = score.add(BigDecimal.valueOf(8));
                    case "Low" -> score = score.add(BigDecimal.valueOf(3));
                }
            }
        }
        
        if (score.compareTo(BigDecimal.valueOf(100)) > 0) {
            score = BigDecimal.valueOf(100);
        }
        
        String riskCategory = determineRiskCategory(score);
        
        RiskScore riskScore = riskScoreRepository.findByClaimId(claimId)
            .orElse(new RiskScore());
        
        riskScore.setClaimId(claimId);
        riskScore.setScoreValue(score);
        riskScore.setRiskCategory(riskCategory);
        riskScore.setCalculationMethod("Rule-based");
        riskScore.setAnomalyCount(indicators.size());
        riskScore.setComputedDate(LocalDateTime.now());
        
        return riskScoreRepository.save(riskScore);
    }
    
    private String determineRiskCategory(BigDecimal score) {
        if (score.compareTo(BigDecimal.valueOf(75)) >= 0) {
            return "Critical";
        } else if (score.compareTo(BigDecimal.valueOf(50)) >= 0) {
            return "High";
        } else if (score.compareTo(BigDecimal.valueOf(25)) >= 0) {
            return "Medium";
        } else {
            return "Low";
        }
    }
    
    public List<RiskIndicator> getIndicatorsByClaimId(String claimId) {
        return riskIndicatorRepository.findByClaimId(claimId);
    }
    
    public List<RiskIndicator> getUnresolvedIndicators() {
        return riskIndicatorRepository.findByIsResolved(false);
    }
    
    public RiskScore getRiskScoreByClaimId(String claimId) {
        return riskScoreRepository.findByClaimId(claimId).orElse(null);
    }
    
    public List<RiskScore> getHighRiskClaims() {
        return riskScoreRepository.findByRiskCategory("High");
    }
    
    @Transactional
    public void resolveIndicator(Long indicatorId, String resolvedBy) {
        RiskIndicator indicator = riskIndicatorRepository.findById(indicatorId)
            .orElseThrow(() -> new RuntimeException("Indicator not found"));
        indicator.setIsResolved(true);
        indicator.setResolvedBy(resolvedBy);
        indicator.setResolvedDate(LocalDateTime.now());
        riskIndicatorRepository.save(indicator);
        
        // Recalculate risk score
        calculateRiskScore(indicator.getClaimId());
    }
}
