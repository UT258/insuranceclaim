package com.claiminsight360.costreserve.repository;

import com.claiminsight360.costreserve.domain.ClaimReserve;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ClaimReserveRepository extends JpaRepository<ClaimReserve, Long> {
    Optional<ClaimReserve> findByClaimId(String claimId);
    List<ClaimReserve> findByReserveStatus(String reserveStatus);
}
