package com.claiminsight360.backend.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "claim_raw")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClaimRaw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rawId;

    private String claimId;

    @Column(length = 5000)
    private String payloadJson;

    private LocalDateTime ingestedDate;
}
