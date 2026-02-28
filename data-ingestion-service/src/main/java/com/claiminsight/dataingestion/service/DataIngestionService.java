package com.claiminsight.dataingestion.service;

import com.claiminsight.common.exception.BadRequestException;
import com.claiminsight.common.exception.ResourceNotFoundException;
import com.claiminsight.dataingestion.entity.ClaimRaw;
import com.claiminsight.dataingestion.entity.DataFeed;
import com.claiminsight.dataingestion.repository.ClaimRawRepository;
import com.claiminsight.dataingestion.repository.DataFeedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataIngestionService {

    private final DataFeedRepository dataFeedRepository;
    private final ClaimRawRepository claimRawRepository;

    @Transactional
    public DataFeed createDataFeed(DataFeed dataFeed) {
        dataFeed.setCreatedDate(System.currentTimeMillis());
        dataFeed.setStatus(DataFeed.FeedStatus.PENDING);
        return dataFeedRepository.save(dataFeed);
    }

    public DataFeed getDataFeedById(Long feedId) {
        return dataFeedRepository.findById(feedId)
                .orElseThrow(() -> new ResourceNotFoundException("DataFeed", "feedId", feedId));
    }

    public List<DataFeed> getAllDataFeeds() {
        return dataFeedRepository.findAll();
    }

    public List<DataFeed> getDataFeedsByType(DataFeed.FeedType feedType) {
        return dataFeedRepository.findByFeedType(feedType);
    }

    public List<DataFeed> getDataFeedsByStatus(DataFeed.FeedStatus status) {
        return dataFeedRepository.findByStatus(status);
    }

    @Transactional
    public DataFeed updateDataFeedStatus(Long feedId, DataFeed.FeedStatus status) {
        DataFeed dataFeed = getDataFeedById(feedId);
        dataFeed.setStatus(status);
        dataFeed.setLastSyncDate(System.currentTimeMillis());
        return dataFeedRepository.save(dataFeed);
    }

    @Transactional
    public ClaimRaw ingestClaimData(String claimId, String payloadJson, String sourceSystem) {
        // Check if claim already exists
        if (claimRawRepository.findByClaimId(claimId).isPresent()) {
            throw new BadRequestException("Claim with ID " + claimId + " already exists");
        }

        ClaimRaw claimRaw = ClaimRaw.builder()
                .claimId(claimId)
                .payloadJson(payloadJson)
                .sourceSystem(sourceSystem)
                .ingestedDate(System.currentTimeMillis())
                .processed(false)
                .build();

        log.info("Ingesting claim data for claimId: {}", claimId);
        return claimRawRepository.save(claimRaw);
    }

    public ClaimRaw getClaimRawById(Long rawId) {
        return claimRawRepository.findById(rawId)
                .orElseThrow(() -> new ResourceNotFoundException("ClaimRaw", "rawId", rawId));
    }

    public ClaimRaw getClaimRawByClaimId(String claimId) {
        return claimRawRepository.findByClaimId(claimId)
                .orElseThrow(() -> new ResourceNotFoundException("ClaimRaw", "claimId", claimId));
    }

    public List<ClaimRaw> getAllClaimRaw() {
        return claimRawRepository.findAll();
    }

    public List<ClaimRaw> getUnprocessedClaims() {
        return claimRawRepository.findByProcessed(false);
    }

    @Transactional
    public ClaimRaw markClaimAsProcessed(String claimId) {
        ClaimRaw claimRaw = getClaimRawByClaimId(claimId);
        claimRaw.setProcessed(true);
        return claimRawRepository.save(claimRaw);
    }
}

