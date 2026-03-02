package com.claiminsight360.backend.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "sla_violation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SlaViolation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long violationId;

    private String claimId;
    private String violationType;
    private LocalDate violationDate;
}
