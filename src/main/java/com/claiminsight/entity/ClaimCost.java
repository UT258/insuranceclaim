package com.claiminsight.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "claim_cost")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClaimCost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long costId;

    @Column(nullable = false)
    private String claimId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CostType costType;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private Long costDate = System.currentTimeMillis();

    @Column
    private String description;

    @Column
    private String vendor;

    public enum CostType {
        MEDICAL,
        LEGAL,
        REPAIR,
        SETTLEMENT,
        INVESTIGATION,
        ADMINISTRATIVE
    }
}

