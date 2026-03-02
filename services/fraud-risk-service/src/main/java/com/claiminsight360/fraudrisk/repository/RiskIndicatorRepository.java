package com.claiminsight360.fraudrisk.repository;

import com.claiminsight360.fraudrisk.domain.RiskIndicator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RiskIndicatorRepository extends JpaRepository<RiskIndicator, Long> {
    List<RiskIndicator> findByClaimId(String claimId);
    List<RiskIndicator> findBySeverity(String severity);
    List<RiskIndicator> findByIndicatorType(String indicatorType);
    List<RiskIndicator> findByIsResolved(Boolean isResolved);
}
