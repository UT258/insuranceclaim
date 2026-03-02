package com.claiminsight360.backend.dto;

import java.math.BigDecimal;

public record DashboardSummaryDto(
        long totalFeeds,
        long totalRawClaims,
        long totalRiskScores,
        long totalSlaViolations,
        BigDecimal totalClaimCost,
        BigDecimal totalReserveAmount
) {
}
