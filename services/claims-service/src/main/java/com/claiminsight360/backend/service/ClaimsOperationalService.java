package com.claiminsight360.backend.service;

import com.claiminsight360.backend.domain.entity.ClaimRaw;
import com.claiminsight360.backend.domain.entity.DataFeed;
import com.claiminsight360.backend.dto.ClaimRawDto;
import com.claiminsight360.backend.dto.DashboardSummaryDto;
import com.claiminsight360.backend.dto.DataFeedDto;
import com.claiminsight360.backend.repository.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ClaimsOperationalService {

    private final DataFeedRepository dataFeedRepository;
    private final ClaimRawRepository claimRawRepository;
    private final ClaimCostRepository claimCostRepository;
    private final ClaimReserveRepository claimReserveRepository;
    private final RiskScoreRepository riskScoreRepository;
    private final SlaViolationRepository slaViolationRepository;

    public ClaimsOperationalService(
            DataFeedRepository dataFeedRepository,
            ClaimRawRepository claimRawRepository,
            ClaimCostRepository claimCostRepository,
            ClaimReserveRepository claimReserveRepository,
            RiskScoreRepository riskScoreRepository,
            SlaViolationRepository slaViolationRepository) {
        this.dataFeedRepository = dataFeedRepository;
        this.claimRawRepository = claimRawRepository;
        this.claimCostRepository = claimCostRepository;
        this.claimReserveRepository = claimReserveRepository;
        this.riskScoreRepository = riskScoreRepository;
        this.slaViolationRepository = slaViolationRepository;
    }

    public List<DataFeedDto> findFeeds() {
        return dataFeedRepository.findAll().stream().map(this::toDto).toList();
    }

    public DataFeedDto createFeed(DataFeedDto dto) {
        DataFeed feed = DataFeed.builder()
                .feedType(dto.feedType())
                .sourceSystem(dto.sourceSystem())
                .lastSyncDate(dto.lastSyncDate() == null ? LocalDateTime.now() : dto.lastSyncDate())
                .status(dto.status())
                .build();
        return toDto(dataFeedRepository.save(feed));
    }

    public List<ClaimRawDto> findRawClaims() {
        return claimRawRepository.findAll().stream().map(this::toDto).toList();
    }

    public ClaimRawDto createRawClaim(ClaimRawDto dto) {
        ClaimRaw raw = ClaimRaw.builder()
                .claimId(dto.claimId())
                .payloadJson(dto.payloadJson())
                .ingestedDate(dto.ingestedDate() == null ? LocalDateTime.now() : dto.ingestedDate())
                .build();
        return toDto(claimRawRepository.save(raw));
    }

    public DashboardSummaryDto dashboardSummary() {
        BigDecimal totalCost = claimCostRepository.findAll().stream()
                .map(c -> c.getAmount() == null ? BigDecimal.ZERO : c.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalReserve = claimReserveRepository.findAll().stream()
                .map(r -> r.getReserveAmount() == null ? BigDecimal.ZERO : r.getReserveAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new DashboardSummaryDto(
                dataFeedRepository.count(),
                claimRawRepository.count(),
                riskScoreRepository.count(),
                slaViolationRepository.count(),
                totalCost,
                totalReserve
        );
    }

    private DataFeedDto toDto(DataFeed feed) {
        return new DataFeedDto(feed.getFeedId(), feed.getFeedType(), feed.getSourceSystem(), feed.getLastSyncDate(), feed.getStatus());
    }

    private ClaimRawDto toDto(ClaimRaw raw) {
        return new ClaimRawDto(raw.getRawId(), raw.getClaimId(), raw.getPayloadJson(), raw.getIngestedDate());
    }
}
