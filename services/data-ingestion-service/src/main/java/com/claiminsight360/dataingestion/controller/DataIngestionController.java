package com.claiminsight360.dataingestion.controller;

import com.claiminsight360.dataingestion.domain.ClaimRaw;
import com.claiminsight360.dataingestion.domain.DataFeed;
import com.claiminsight360.dataingestion.service.DataIngestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/data-ingestion")
@RequiredArgsConstructor
public class DataIngestionController {
    
    private final DataIngestionService dataIngestionService;
    
    @PostMapping("/feeds")
    public ResponseEntity<DataFeed> createDataFeed(@RequestBody DataFeed dataFeed) {
        DataFeed created = dataIngestionService.createDataFeed(dataFeed);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    @GetMapping("/feeds")
    public ResponseEntity<List<DataFeed>> getAllDataFeeds() {
        return ResponseEntity.ok(dataIngestionService.getAllDataFeeds());
    }
    
    @GetMapping("/feeds/status/{status}")
    public ResponseEntity<List<DataFeed>> getDataFeedsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(dataIngestionService.getDataFeedsByStatus(status));
    }
    
    @PostMapping("/claims/raw")
    public ResponseEntity<ClaimRaw> ingestClaimData(@RequestBody ClaimRaw claimRaw) {
        ClaimRaw ingested = dataIngestionService.ingestClaimData(claimRaw);
        return new ResponseEntity<>(ingested, HttpStatus.CREATED);
    }
    
    @GetMapping("/claims/raw/pending")
    public ResponseEntity<List<ClaimRaw>> getPendingClaims() {
        return ResponseEntity.ok(dataIngestionService.getPendingClaims());
    }
    
    @GetMapping("/claims/raw/{claimId}")
    public ResponseEntity<List<ClaimRaw>> getClaimsByClaimId(@PathVariable String claimId) {
        return ResponseEntity.ok(dataIngestionService.getClaimsByClaimId(claimId));
    }
    
    @PutMapping("/feeds/{feedId}/status")
    public ResponseEntity<Void> updateFeedStatus(
            @PathVariable Long feedId,
            @RequestParam String status,
            @RequestParam(required = false) String errorMessage) {
        dataIngestionService.updateFeedStatus(feedId, status, errorMessage);
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/claims/raw/{rawId}/process")
    public ResponseEntity<Void> markClaimAsProcessed(@PathVariable Long rawId) {
        dataIngestionService.markClaimAsProcessed(rawId);
        return ResponseEntity.ok().build();
    }
}
