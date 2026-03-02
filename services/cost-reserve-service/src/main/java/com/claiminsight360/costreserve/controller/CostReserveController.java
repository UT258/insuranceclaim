package com.claiminsight360.costreserve.controller;

import com.claiminsight360.costreserve.domain.AgingRecord;
import com.claiminsight360.costreserve.domain.ClaimCost;
import com.claiminsight360.costreserve.domain.ClaimReserve;
import com.claiminsight360.costreserve.service.CostReserveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/cost-reserve")
@RequiredArgsConstructor
public class CostReserveController {
    
    private final CostReserveService costReserveService;
    
    @PostMapping("/costs")
    public ResponseEntity<ClaimCost> recordCost(@RequestBody ClaimCost cost) {
        ClaimCost recorded = costReserveService.recordCost(cost);
        return new ResponseEntity<>(recorded, HttpStatus.CREATED);
    }
    
    @GetMapping("/costs/claim/{claimId}")
    public ResponseEntity<List<ClaimCost>> getCostsByClaimId(@PathVariable String claimId) {
        return ResponseEntity.ok(costReserveService.getCostsByClaimId(claimId));
    }
    
    @GetMapping("/costs/total/{claimId}")
    public ResponseEntity<BigDecimal> getTotalCostByClaimId(@PathVariable String claimId) {
        return ResponseEntity.ok(costReserveService.getTotalCostByClaimId(claimId));
    }
    
    @PostMapping("/reserves")
    public ResponseEntity<ClaimReserve> updateReserve(@RequestBody ClaimReserve reserve) {
        ClaimReserve updated = costReserveService.updateReserve(reserve);
        return ResponseEntity.ok(updated);
    }
    
    @GetMapping("/reserves/claim/{claimId}")
    public ResponseEntity<ClaimReserve> getReserveByClaimId(@PathVariable String claimId) {
        ClaimReserve reserve = costReserveService.getReserveByClaimId(claimId);
        if (reserve != null) {
            return ResponseEntity.ok(reserve);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/aging")
    public ResponseEntity<AgingRecord> updateAgingRecord(@RequestBody AgingRecord agingRecord) {
        AgingRecord updated = costReserveService.updateAgingRecord(agingRecord);
        return ResponseEntity.ok(updated);
    }
    
    @GetMapping("/aging/claim/{claimId}")
    public ResponseEntity<AgingRecord> getAgingByClaimId(@PathVariable String claimId) {
        AgingRecord aging = costReserveService.getAgingByClaimId(claimId);
        if (aging != null) {
            return ResponseEntity.ok(aging);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/aging/stale")
    public ResponseEntity<List<AgingRecord>> getStaleClaims() {
        return ResponseEntity.ok(costReserveService.getStaleClaims());
    }
    
    @GetMapping("/aging/bucket/{bucket}")
    public ResponseEntity<List<AgingRecord>> getClaimsByAgingBucket(@PathVariable String bucket) {
        return ResponseEntity.ok(costReserveService.getClaimsByAgingBucket(bucket));
    }
}
