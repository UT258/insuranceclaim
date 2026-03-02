package com.claiminsight360.denialanalysis.repository;

import com.claiminsight360.denialanalysis.domain.LeakageFlag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LeakageFlagRepository extends JpaRepository<LeakageFlag, Long> {
    List<LeakageFlag> findByClaimId(String claimId);
    List<LeakageFlag> findByLeakageType(String leakageType);
    List<LeakageFlag> findByIsInvestigated(Boolean isInvestigated);
    List<LeakageFlag> findByIsRecovered(Boolean isRecovered);
}
