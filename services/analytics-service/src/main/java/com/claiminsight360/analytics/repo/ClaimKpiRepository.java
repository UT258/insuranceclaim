package com.claiminsight360.analytics.repo;

import com.claiminsight360.analytics.domain.ClaimKpi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClaimKpiRepository extends JpaRepository<ClaimKpi, Long> {
}
