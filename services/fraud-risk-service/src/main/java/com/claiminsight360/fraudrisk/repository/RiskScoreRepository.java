package com.claiminsight360.fraudrisk.repository;

import com.claiminsight360.fraudrisk.domain.RiskScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RiskScoreRepository extends JpaRepository<RiskScore, Long> {
    Optional<RiskScore> findByClaimId(String claimId);
    List<RiskScore> findByRiskCategory(String riskCategory);
}
