package com.claiminsight360.backend.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "denial_pattern")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DenialPattern {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patternId;

    private String claimId;
    private String denialCode;
    private String reason;
    private LocalDate occurrenceDate;
}
