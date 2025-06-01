package com.unibague.magno.domain.spi;

import com.unibague.magno.domain.model.ResearchSeedbedProfile;
import com.unibague.magno.domain.model.projections.SeedbedReportProjection;

import java.util.List;
import java.util.Optional;

public interface IResearchSeedbedProfilePersistencePort {
    Optional<ResearchSeedbedProfile> findById(Long id);
    ResearchSeedbedProfile save(ResearchSeedbedProfile researchSeedbedProfile);
    ResearchSeedbedProfile update(Long id, ResearchSeedbedProfile researchSeedbedProfile);
    void deleteById(Long id);
    List<ResearchSeedbedProfile> findAll();
    List<ResearchSeedbedProfile> findAllByInvestigationGroupProfileId(Long id);

    List<SeedbedReportProjection> getSeedbedReportById(Long researchSeedbedProfileId, Long academicPeriodId);
}
