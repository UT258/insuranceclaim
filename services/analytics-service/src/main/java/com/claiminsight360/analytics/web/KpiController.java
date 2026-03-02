package com.claiminsight360.analytics.web;

import com.claiminsight360.analytics.dto.ClaimKpiDto;
import com.claiminsight360.analytics.service.ClaimKpiService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class KpiController {

    private final ClaimKpiService claimKpiService;

    public KpiController(ClaimKpiService claimKpiService) {
        this.claimKpiService = claimKpiService;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP", "service", "analytics-service");
    }

    @GetMapping("/kpis")
    public List<ClaimKpiDto> list() {
        return claimKpiService.findAll();
    }

    @PostMapping("/kpis")
    @ResponseStatus(HttpStatus.CREATED)
    public ClaimKpiDto create(@Valid @RequestBody ClaimKpiDto dto) {
        return claimKpiService.create(dto);
    }
}
