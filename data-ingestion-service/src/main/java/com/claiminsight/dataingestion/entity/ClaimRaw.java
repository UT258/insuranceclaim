package com.claiminsight.dataingestion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "claim_raw")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClaimRaw {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rawId;

    @Column(nullable = false, unique = true)
    private String claimId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String payloadJson;

    @Column(nullable = false)
    private Long ingestedDate = System.currentTimeMillis();

    @Column
    private String sourceSystem;

    @Column
    private Boolean processed = false;
}

