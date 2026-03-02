package com.claiminsight360.backend.repository;

import com.claiminsight360.backend.domain.entity.DataFeed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataFeedRepository extends JpaRepository<DataFeed, Long> {
}
