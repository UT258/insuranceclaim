package com.claiminsight360.backend.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record ClaimRawDto(
        Long rawId,
        @NotBlank String claimId,
        @NotBlank String payloadJson,
        LocalDateTime ingestedDate
) {
}
