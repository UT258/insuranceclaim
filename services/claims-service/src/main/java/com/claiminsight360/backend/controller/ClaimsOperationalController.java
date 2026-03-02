package com.claiminsight360.backend.controller;

import com.claiminsight360.backend.dto.ClaimRawDto;
import com.claiminsight360.backend.dto.DashboardSummaryDto;
import com.claiminsight360.backend.dto.DataFeedDto;
import com.claiminsight360.backend.service.ClaimsOperationalService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ClaimsOperationalController {

    private final ClaimsOperationalService claimsOperationalService;

    public ClaimsOperationalController(ClaimsOperationalService claimsOperationalService) {
        this.claimsOperationalService = claimsOperationalService;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP", "service", "claims-service");
    }

    @GetMapping("/feeds")
    public List<DataFeedDto> listFeeds() {
        return claimsOperationalService.findFeeds();
    }

    @PostMapping("/feeds")
    @ResponseStatus(HttpStatus.CREATED)
    public DataFeedDto createFeed(@Valid @RequestBody DataFeedDto dto) {
        return claimsOperationalService.createFeed(dto);
    }

    @GetMapping("/raw-claims")
    public List<ClaimRawDto> listRawClaims() {
        return claimsOperationalService.findRawClaims();
    }

    @PostMapping("/raw-claims")
    @ResponseStatus(HttpStatus.CREATED)
    public ClaimRawDto createRawClaim(@Valid @RequestBody ClaimRawDto dto) {
        return claimsOperationalService.createRawClaim(dto);
    }

    @GetMapping("/dashboard/summary")
    public DashboardSummaryDto dashboardSummary() {
        return claimsOperationalService.dashboardSummary();
    }
}
