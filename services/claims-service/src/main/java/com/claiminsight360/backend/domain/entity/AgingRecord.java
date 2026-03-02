package com.claiminsight360.backend.domain.entity;

import com.claiminsight360.backend.domain.enums.AgingBucket;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "aging_record")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agingId;

    private String claimId;
    private Integer agingDays;

    @Enumerated(EnumType.STRING)
    private AgingBucket agingBucket;
}
