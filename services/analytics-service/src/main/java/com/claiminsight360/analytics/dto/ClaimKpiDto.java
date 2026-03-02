package com.claiminsight360.analytics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ClaimKpiDto(
        Long kpiId,
        @NotBlank String claimId,
        @NotBlank String metricName,
        @NotNull BigDecimal metricValue,
        @NotNull LocalDate metricDate
) {
}
