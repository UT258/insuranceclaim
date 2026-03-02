package com.claiminsight360.backend.domain.entity;

import com.claiminsight360.backend.domain.enums.FeedStatus;
import com.claiminsight360.backend.domain.enums.FeedType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "data_feed")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataFeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedId;

    @Enumerated(EnumType.STRING)
    private FeedType feedType;

    private String sourceSystem;
    private LocalDateTime lastSyncDate;

    @Enumerated(EnumType.STRING)
    private FeedStatus status;
}
