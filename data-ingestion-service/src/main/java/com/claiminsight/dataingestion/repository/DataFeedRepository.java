package com.claiminsight.dataingestion.repository;

import com.claiminsight.dataingestion.entity.DataFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataFeedRepository extends JpaRepository<DataFeed, Long> {
    List<DataFeed> findByFeedType(DataFeed.FeedType feedType);
    List<DataFeed> findByStatus(DataFeed.FeedStatus status);
    DataFeed findBySourceSystem(String sourceSystem);
}

