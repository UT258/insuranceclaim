package com.claiminsight.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "claim_reserve")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClaimReserve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reserveId;

    @Column(nullable = false)
    private String claimId;

    @Column(nullable = false)
    private Double reserveAmount;

    @Column(nullable = false)
    private Long updatedDate = System.currentTimeMillis();

    @Column
    private String reserveType; // Initial, Updated, Adjusted

    @Column
    private String category; // Medical, Non-Medical, Litigation

    @Column
    private Long createdDate = System.currentTimeMillis();
}

