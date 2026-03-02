package com.claiminsight360.dataingestion.repository;

import com.claiminsight360.dataingestion.domain.DataFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DataFeedRepository extends JpaRepository<DataFeed, Long> {
    List<DataFeed> findByStatus(String status);
    List<DataFeed> findByFeedType(String feedType);
    List<DataFeed> findBySourceSystem(String sourceSystem);
}
