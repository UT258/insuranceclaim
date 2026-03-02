package com.claiminsight360.performance.repository;

import com.claiminsight360.performance.domain.AdjusterPerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AdjusterPerformanceRepository extends JpaRepository<AdjusterPerformance, Long> {
    List<AdjusterPerformance> findByAdjusterId(String adjusterId);
    List<AdjusterPerformance> findByPeriod(String period);
    List<AdjusterPerformance> findByPeriodValue(String periodValue);
}
