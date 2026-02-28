package com.claiminsight.repository;

import com.claiminsight.entity.ClaimKPI;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimKPIRepository extends JpaRepository<ClaimKPI, Long> {
    List<ClaimKPI> findByClaimId(String claimId);
    List<ClaimKPI> findByMetricName(String metricName);
    List<ClaimKPI> findByCategory(String category);
}

