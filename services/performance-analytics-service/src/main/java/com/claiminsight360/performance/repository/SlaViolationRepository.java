package com.claiminsight360.performance.repository;

import com.claiminsight360.performance.domain.SlaViolation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SlaViolationRepository extends JpaRepository<SlaViolation, Long> {
    List<SlaViolation> findByClaimId(String claimId);
    List<SlaViolation> findByViolationType(String violationType);
    List<SlaViolation> findByIsResolved(Boolean isResolved);
    List<SlaViolation> findByAssignedTo(String assignedTo);
}
