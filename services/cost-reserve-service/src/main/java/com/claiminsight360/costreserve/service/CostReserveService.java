package com.claiminsight360.costreserve.service;

import com.claiminsight360.costreserve.domain.AgingRecord;
import com.claiminsight360.costreserve.domain.ClaimCost;
import com.claiminsight360.costreserve.domain.ClaimReserve;
import com.claiminsight360.costreserve.repository.AgingRecordRepository;
import com.claiminsight360.costreserve.repository.ClaimCostRepository;
import com.claiminsight360.costreserve.repository.ClaimReserveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CostReserveService {
    
    private final ClaimCostRepository claimCostRepository;
    private final ClaimReserveRepository claimReserveRepository;
    private final AgingRecordRepository agingRecordRepository;
    
    @Transactional
    public ClaimCost recordCost(ClaimCost cost) {
        log.info("Recording cost for claim: {}", cost.getClaimId());
        return claimCostRepository.save(cost);
    }
    
    @Transactional
    public ClaimReserve updateReserve(ClaimReserve reserve) {
        log.info("Updating reserve for claim: {}", reserve.getClaimId());
        
        // Calculate remaining reserve
        if (reserve.getReserveAmount() != null && reserve.getPaidAmount() != null) {
            BigDecimal remaining = reserve.getReserveAmount().subtract(reserve.getPaidAmount());
            reserve.setRemainingReserve(remaining);
        }
        
        return claimReserveRepository.save(reserve);
    }
    
    @Transactional
    public AgingRecord updateAgingRecord(AgingRecord agingRecord) {
        log.info("Updating aging record for claim: {}", agingRecord.getClaimId());
        
        // Determine aging bucket
        Integer days = agingRecord.getAgingDays();
        if (days <= 30) {
            agingRecord.setAgingBucket("0-30");
        } else if (days <= 60) {
            agingRecord.setAgingBucket("31-60");
        } else if (days <= 90) {
            agingRecord.setAgingBucket("61-90");
        } else if (days <= 180) {
            agingRecord.setAgingBucket("91-180");
        } else {
            agingRecord.setAgingBucket("180+");
            agingRecord.setIsStale(true);
        }
        
        return agingRecordRepository.save(agingRecord);
    }
    
    public List<ClaimCost> getCostsByClaimId(String claimId) {
        return claimCostRepository.findByClaimId(claimId);
    }
    
    public BigDecimal getTotalCostByClaimId(String claimId) {
        BigDecimal total = claimCostRepository.getTotalCostByClaimId(claimId);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    public ClaimReserve getReserveByClaimId(String claimId) {
        return claimReserveRepository.findByClaimId(claimId).orElse(null);
    }
    
    public AgingRecord getAgingByClaimId(String claimId) {
        return agingRecordRepository.findByClaimId(claimId).orElse(null);
    }
    
    public List<AgingRecord> getStaleClaims() {
        return agingRecordRepository.findByIsStale(true);
    }
    
    public List<AgingRecord> getClaimsByAgingBucket(String bucket) {
        return agingRecordRepository.findByAgingBucket(bucket);
    }
}
