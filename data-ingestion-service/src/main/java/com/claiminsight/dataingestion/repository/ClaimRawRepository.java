package com.claiminsight.dataingestion.repository;

import com.claiminsight.dataingestion.entity.ClaimRaw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClaimRawRepository extends JpaRepository<ClaimRaw, Long> {
    Optional<ClaimRaw> findByClaimId(String claimId);
    List<ClaimRaw> findByProcessed(Boolean processed);
    List<ClaimRaw> findBySourceSystem(String sourceSystem);
}

