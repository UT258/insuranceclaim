package com.claiminsight360.fraudrisk.controller;

import com.claiminsight360.fraudrisk.domain.RiskIndicator;
import com.claiminsight360.fraudrisk.domain.RiskScore;
import com.claiminsight360.fraudrisk.service.FraudRiskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/fraud-risk")
@RequiredArgsConstructor
public class FraudRiskController {
    
    private final FraudRiskService fraudRiskService;
    
    @PostMapping("/indicators")
    public ResponseEntity<RiskIndicator> createRiskIndicator(@RequestBody RiskIndicator indicator) {
        RiskIndicator created = fraudRiskService.createRiskIndicator(indicator);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    @GetMapping("/indicators/claim/{claimId}")
    public ResponseEntity<List<RiskIndicator>> getIndicatorsByClaimId(@PathVariable String claimId) {
        return ResponseEntity.ok(fraudRiskService.getIndicatorsByClaimId(claimId));
    }
    
    @GetMapping("/indicators/unresolved")
    public ResponseEntity<List<RiskIndicator>> getUnresolvedIndicators() {
        return ResponseEntity.ok(fraudRiskService.getUnresolvedIndicators());
    }
    
    @PutMapping("/indicators/{indicatorId}/resolve")
    public ResponseEntity<Void> resolveIndicator(
            @PathVariable Long indicatorId,
            @RequestParam String resolvedBy) {
        fraudRiskService.resolveIndicator(indicatorId, resolvedBy);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/scores/calculate/{claimId}")
    public ResponseEntity<RiskScore> calculateRiskScore(@PathVariable String claimId) {
        RiskScore score = fraudRiskService.calculateRiskScore(claimId);
        return ResponseEntity.ok(score);
    }
    
    @GetMapping("/scores/claim/{claimId}")
    public ResponseEntity<RiskScore> getRiskScoreByClaimId(@PathVariable String claimId) {
        RiskScore score = fraudRiskService.getRiskScoreByClaimId(claimId);
        if (score != null) {
            return ResponseEntity.ok(score);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/scores/high-risk")
    public ResponseEntity<List<RiskScore>> getHighRiskClaims() {
        return ResponseEntity.ok(fraudRiskService.getHighRiskClaims());
    }
}
