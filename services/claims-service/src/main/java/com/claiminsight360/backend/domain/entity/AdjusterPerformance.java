package com.claiminsight360.backend.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "adjuster_performance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdjusterPerformance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long perfId;

    private String adjusterId;
    private Integer claimsHandled;
    private BigDecimal avgTat;
    private BigDecimal qualityScore;
    private String period;
}
