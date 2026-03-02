package com.claiminsight360.backend.dto;

import com.claiminsight360.backend.domain.enums.FeedStatus;
import com.claiminsight360.backend.domain.enums.FeedType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DataFeedDto(
        Long feedId,
        @NotNull FeedType feedType,
        @NotBlank String sourceSystem,
        LocalDateTime lastSyncDate,
        @NotNull FeedStatus status
) {
}
