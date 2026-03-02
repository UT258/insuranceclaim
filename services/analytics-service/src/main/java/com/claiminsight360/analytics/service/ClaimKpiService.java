package com.claiminsight360.analytics.service;

import com.claiminsight360.analytics.domain.ClaimKpi;
import com.claiminsight360.analytics.dto.ClaimKpiDto;
import com.claiminsight360.analytics.repo.ClaimKpiRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClaimKpiService {

    private final ClaimKpiRepository repository;

    public ClaimKpiService(ClaimKpiRepository repository) {
        this.repository = repository;
    }

    public List<ClaimKpiDto> findAll() {
        return repository.findAll().stream().map(this::toDto).toList();
    }

    public ClaimKpiDto create(ClaimKpiDto dto) {
        ClaimKpi kpi = new ClaimKpi();
        kpi.setClaimId(dto.claimId());
        kpi.setMetricName(dto.metricName());
        kpi.setMetricValue(dto.metricValue());
        kpi.setMetricDate(dto.metricDate());
        return toDto(repository.save(kpi));
    }

    private ClaimKpiDto toDto(ClaimKpi kpi) {
        return new ClaimKpiDto(kpi.getKpiId(), kpi.getClaimId(), kpi.getMetricName(), kpi.getMetricValue(), kpi.getMetricDate());
    }
}
