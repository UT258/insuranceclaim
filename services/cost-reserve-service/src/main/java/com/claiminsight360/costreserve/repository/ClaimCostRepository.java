package com.claiminsight360.costreserve.repository;

import com.claiminsight360.costreserve.domain.ClaimCost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ClaimCostRepository extends JpaRepository<ClaimCost, Long> {
    List<ClaimCost> findByClaimId(String claimId);
    List<ClaimCost> findByCostType(String costType);
    
    @Query("SELECT SUM(c.amount) FROM ClaimCost c WHERE c.claimId = :claimId")
    BigDecimal getTotalCostByClaimId(String claimId);
}
