package com.claiminsight.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "aging_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgingRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agingId;

    @Column(nullable = false)
    private String claimId;

    @Column(nullable = false)
    private Integer agingDays;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AgingBucket agingBucket;

    @Column(nullable = false)
    private Long recordDate = System.currentTimeMillis();

    @Column
    private String claimStatus;

    @Column
    private String product;

    public enum AgingBucket {
        ZERO_TO_30_DAYS,
        THIRTY_ONE_TO_60_DAYS,
        SIXTY_ONE_TO_90_DAYS,
        NINETY_PLUS_DAYS
    }
}

