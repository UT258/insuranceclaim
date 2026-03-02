package com.claiminsight360.dataingestion.service;

import com.claiminsight360.dataingestion.domain.ClaimRaw;
import com.claiminsight360.dataingestion.domain.DataFeed;
import com.claiminsight360.dataingestion.repository.ClaimRawRepository;
import com.claiminsight360.dataingestion.repository.DataFeedRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataIngestionService {
    
    private final DataFeedRepository dataFeedRepository;
    private final ClaimRawRepository claimRawRepository;
    
    @Transactional
    public DataFeed createDataFeed(DataFeed dataFeed) {
        log.info("Creating data feed for source: {}", dataFeed.getSourceSystem());
        return dataFeedRepository.save(dataFeed);
    }
    
    @Transactional
    public ClaimRaw ingestClaimData(ClaimRaw claimRaw) {
        log.info("Ingesting claim data for claim ID: {}", claimRaw.getClaimId());
        return claimRawRepository.save(claimRaw);
    }
    
    public List<DataFeed> getAllDataFeeds() {
        return dataFeedRepository.findAll();
    }
    
    public List<DataFeed> getDataFeedsByStatus(String status) {
        return dataFeedRepository.findByStatus(status);
    }
    
    public List<ClaimRaw> getPendingClaims() {
        return claimRawRepository.findByProcessStatus("Pending");
    }
    
    public List<ClaimRaw> getClaimsByClaimId(String claimId) {
        return claimRawRepository.findByClaimId(claimId);
    }
    
    @Transactional
    public void updateFeedStatus(Long feedId, String status, String errorMessage) {
        DataFeed feed = dataFeedRepository.findById(feedId)
            .orElseThrow(() -> new RuntimeException("Feed not found"));
        feed.setStatus(status);
        feed.setLastSyncDate(LocalDateTime.now());
        if (errorMessage != null) {
            feed.setErrorMessage(errorMessage);
        }
        dataFeedRepository.save(feed);
    }
    
    @Transactional
    public void markClaimAsProcessed(Long rawId) {
        ClaimRaw claimRaw = claimRawRepository.findById(rawId)
            .orElseThrow(() -> new RuntimeException("Claim raw not found"));
        claimRaw.setProcessStatus("Processed");
        claimRawRepository.save(claimRaw);
    }
}
