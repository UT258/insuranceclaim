package com.claiminsight360.denialanalysis.repository;

import com.claiminsight360.denialanalysis.domain.DenialPattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DenialPatternRepository extends JpaRepository<DenialPattern, Long> {
    List<DenialPattern> findByClaimId(String claimId);
    List<DenialPattern> findByDenialCode(String denialCode);
    List<DenialPattern> findByDenialCategory(String denialCategory);
    List<DenialPattern> findByIsAppealed(Boolean isAppealed);
}
