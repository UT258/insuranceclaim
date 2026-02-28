package com.claiminsight.dataingestion.controller;

import com.claiminsight.common.dto.ApiResponse;
import com.claiminsight.dataingestion.entity.ClaimRaw;
import com.claiminsight.dataingestion.entity.DataFeed;
import com.claiminsight.dataingestion.service.DataIngestionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/data-feeds")
@RequiredArgsConstructor
@Tag(name = "Data Ingestion", description = "Data ingestion and claim data aggregator APIs")
public class DataIngestionController {

    private final DataIngestionService dataIngestionService;

    @PostMapping
    @Operation(summary = "Create a new data feed")
    public ResponseEntity<ApiResponse<DataFeed>> createDataFeed(@RequestBody DataFeed dataFeed) {
        DataFeed created = dataIngestionService.createDataFeed(dataFeed);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(created, "Data feed created successfully"));
    }

    @GetMapping("/{feedId}")
    @Operation(summary = "Get data feed by ID")
    public ResponseEntity<ApiResponse<DataFeed>> getDataFeedById(@PathVariable Long feedId) {
        DataFeed dataFeed = dataIngestionService.getDataFeedById(feedId);
        return ResponseEntity.ok(ApiResponse.success(dataFeed));
    }

    @GetMapping
    @Operation(summary = "Get all data feeds")
    public ResponseEntity<ApiResponse<List<DataFeed>>> getAllDataFeeds() {
        List<DataFeed> dataFeeds = dataIngestionService.getAllDataFeeds();
        return ResponseEntity.ok(ApiResponse.success(dataFeeds));
    }

    @GetMapping("/type/{feedType}")
    @Operation(summary = "Get data feeds by type")
    public ResponseEntity<ApiResponse<List<DataFeed>>> getDataFeedsByType(@PathVariable DataFeed.FeedType feedType) {
        List<DataFeed> dataFeeds = dataIngestionService.getDataFeedsByType(feedType);
        return ResponseEntity.ok(ApiResponse.success(dataFeeds));
    }

    @PatchMapping("/{feedId}/status")
    @Operation(summary = "Update data feed status")
    public ResponseEntity<ApiResponse<DataFeed>> updateDataFeedStatus(
            @PathVariable Long feedId,
            @RequestParam DataFeed.FeedStatus status) {
        DataFeed updated = dataIngestionService.updateDataFeedStatus(feedId, status);
        return ResponseEntity.ok(ApiResponse.success(updated, "Status updated successfully"));
    }

    @PostMapping("/claims/ingest")
    @Operation(summary = "Ingest claim data")
    public ResponseEntity<ApiResponse<ClaimRaw>> ingestClaimData(@RequestBody Map<String, String> payload) {
        String claimId = payload.get("claimId");
        String payloadJson = payload.get("payloadJson");
        String sourceSystem = payload.get("sourceSystem");

        ClaimRaw claimRaw = dataIngestionService.ingestClaimData(claimId, payloadJson, sourceSystem);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(claimRaw, "Claim data ingested successfully"));
    }

    @GetMapping("/claims")
    @Operation(summary = "Get all claims")
    public ResponseEntity<ApiResponse<List<ClaimRaw>>> getAllClaims() {
        List<ClaimRaw> claims = dataIngestionService.getAllClaimRaw();
        return ResponseEntity.ok(ApiResponse.success(claims));
    }

    @GetMapping("/claims/{claimId}")
    @Operation(summary = "Get claim by claim ID")
    public ResponseEntity<ApiResponse<ClaimRaw>> getClaimByClaimId(@PathVariable String claimId) {
        ClaimRaw claim = dataIngestionService.getClaimRawByClaimId(claimId);
        return ResponseEntity.ok(ApiResponse.success(claim));
    }

    @GetMapping("/claims/unprocessed")
    @Operation(summary = "Get unprocessed claims")
    public ResponseEntity<ApiResponse<List<ClaimRaw>>> getUnprocessedClaims() {
        List<ClaimRaw> claims = dataIngestionService.getUnprocessedClaims();
        return ResponseEntity.ok(ApiResponse.success(claims));
    }

    @PatchMapping("/claims/{claimId}/processed")
    @Operation(summary = "Mark claim as processed")
    public ResponseEntity<ApiResponse<ClaimRaw>> markClaimAsProcessed(@PathVariable String claimId) {
        ClaimRaw claim = dataIngestionService.markClaimAsProcessed(claimId);
        return ResponseEntity.ok(ApiResponse.success(claim, "Claim marked as processed"));
    }
}

