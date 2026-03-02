package com.claiminsight360.backend.repository;

import com.claiminsight360.backend.domain.entity.RiskScore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiskScoreRepository extends JpaRepository<RiskScore, Long> {
}
