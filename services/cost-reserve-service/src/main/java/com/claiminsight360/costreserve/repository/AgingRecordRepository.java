package com.claiminsight360.costreserve.repository;

import com.claiminsight360.costreserve.domain.AgingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AgingRecordRepository extends JpaRepository<AgingRecord, Long> {
    Optional<AgingRecord> findByClaimId(String claimId);
    List<AgingRecord> findByAgingBucket(String agingBucket);
    List<AgingRecord> findByIsStale(Boolean isStale);
}
