package com.claiminsight360.backend.domain.entity;

import com.claiminsight360.backend.domain.enums.CostType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "claim_cost")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClaimCost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long costId;

    private String claimId;

    @Enumerated(EnumType.STRING)
    private CostType costType;

    private BigDecimal amount;
    private LocalDate costDate;
}
