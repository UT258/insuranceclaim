package com.claiminsight360.denialanalysis.controller;

import com.claiminsight360.denialanalysis.domain.DenialPattern;
import com.claiminsight360.denialanalysis.domain.LeakageFlag;
import com.claiminsight360.denialanalysis.service.DenialAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/denial-analysis")
@RequiredArgsConstructor
public class DenialAnalysisController {
    
    private final DenialAnalysisService denialAnalysisService;
    
    @PostMapping("/denials")
    public ResponseEntity<DenialPattern> recordDenial(@RequestBody DenialPattern denialPattern) {
        DenialPattern recorded = denialAnalysisService.recordDenial(denialPattern);
        return new ResponseEntity<>(recorded, HttpStatus.CREATED);
    }
    
    @GetMapping("/denials/claim/{claimId}")
    public ResponseEntity<List<DenialPattern>> getDenialsByClaimId(@PathVariable String claimId) {
        return ResponseEntity.ok(denialAnalysisService.getDenialsByClaimId(claimId));
    }
    
    @GetMapping("/denials/code/{denialCode}")
    public ResponseEntity<List<DenialPattern>> getDenialsByCode(@PathVariable String denialCode) {
        return ResponseEntity.ok(denialAnalysisService.getDenialsByCode(denialCode));
    }
    
    @GetMapping("/denials/analysis")
    public ResponseEntity<Map<String, Long>> getDenialCodeAnalysis() {
        return ResponseEntity.ok(denialAnalysisService.getDenialCodeAnalysis());
    }
    
    @PostMapping("/leakages")
    public ResponseEntity<LeakageFlag> flagLeakage(@RequestBody LeakageFlag leakageFlag) {
        LeakageFlag flagged = denialAnalysisService.flagLeakage(leakageFlag);
        return new ResponseEntity<>(flagged, HttpStatus.CREATED);
    }
    
    @GetMapping("/leakages/claim/{claimId}")
    public ResponseEntity<List<LeakageFlag>> getLeakagesByClaimId(@PathVariable String claimId) {
        return ResponseEntity.ok(denialAnalysisService.getLeakagesByClaimId(claimId));
    }
    
    @GetMapping("/leakages/total-amount")
    public ResponseEntity<BigDecimal> getTotalLeakageAmount() {
        return ResponseEntity.ok(denialAnalysisService.getTotalLeakageAmount());
    }
    
    @GetMapping("/leakages/uninvestigated")
    public ResponseEntity<List<LeakageFlag>> getUninvestigatedLeakages() {
        return ResponseEntity.ok(denialAnalysisService.getUninvestigatedLeakages());
    }
    
    @PutMapping("/leakages/{leakageId}/investigate")
    public ResponseEntity<Void> markLeakageAsInvestigated(
            @PathVariable Long leakageId,
            @RequestParam String notes) {
        denialAnalysisService.markLeakageAsInvestigated(leakageId, notes);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/leakages/{leakageId}/recover")
    public ResponseEntity<Void> recordLeakageRecovery(
            @PathVariable Long leakageId,
            @RequestParam BigDecimal recoveredAmount) {
        denialAnalysisService.recordLeakageRecovery(leakageId, recoveredAmount);
        return ResponseEntity.ok().build();
    }
}
