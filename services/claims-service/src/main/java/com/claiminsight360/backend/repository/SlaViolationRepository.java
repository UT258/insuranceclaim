package com.claiminsight360.backend.repository;

import com.claiminsight360.backend.domain.entity.SlaViolation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SlaViolationRepository extends JpaRepository<SlaViolation, Long> {
}
