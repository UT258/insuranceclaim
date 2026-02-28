package com.claiminsight.dataingestion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "data_feed")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DataFeed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FeedType feedType;

    @Column(nullable = false)
    private String sourceSystem;

    @Column
    private Long lastSyncDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FeedStatus status;

    @Column(nullable = false)
    private Long createdDate = System.currentTimeMillis();

    @Column
    private String description;

    @Column
    private Integer recordCount = 0;

    public enum FeedType {
        CLAIM, POLICY, PAYMENT, RESERVE, DENIAL, FRAUD, ADJUSTER
    }

    public enum FeedStatus {
        PENDING, IN_PROGRESS, COMPLETED, FAILED
    }
}

