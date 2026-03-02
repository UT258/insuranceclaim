package com.claiminsight360.backend.domain.entity;

import com.claiminsight360.backend.domain.enums.IndicatorType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "risk_indicator")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RiskIndicator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long indicatorId;

    private String claimId;

    @Enumerated(EnumType.STRING)
    private IndicatorType indicatorType;

    private String severity;
    private LocalDateTime triggeredDate;
}
