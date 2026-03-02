package com.claiminsight360.backend.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "claim_reserve")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClaimReserve {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reserveId;

    private String claimId;
    private BigDecimal reserveAmount;
    private LocalDate updatedDate;
}
