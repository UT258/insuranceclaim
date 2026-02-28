package com.claiminsight.repository;

import com.claiminsight.entity.ClaimRaw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimRawRepository extends JpaRepository<ClaimRaw, Long> {
    List<ClaimRaw> findByClaimId(String claimId);
    List<ClaimRaw> findByStatus(String status);
}

