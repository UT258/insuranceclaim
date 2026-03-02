package com.claiminsight360.backend.domain.entity;

import com.claiminsight360.backend.domain.enums.LeakageType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "leakage_flag")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LeakageFlag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leakageId;

    private String claimId;

    @Enumerated(EnumType.STRING)
    private LeakageType leakageType;

    private BigDecimal estimatedLoss;
    private LocalDate identifiedDate;
}
