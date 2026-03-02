package com.claiminsight360.dataingestion.repository;

import com.claiminsight360.dataingestion.domain.ClaimRaw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClaimRawRepository extends JpaRepository<ClaimRaw, Long> {
    List<ClaimRaw> findByClaimId(String claimId);
    List<ClaimRaw> findByProcessStatus(String processStatus);
    List<ClaimRaw> findBySourceSystem(String sourceSystem);
}
