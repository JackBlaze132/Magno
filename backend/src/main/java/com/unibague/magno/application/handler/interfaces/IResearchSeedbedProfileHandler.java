package com.unibague.magno.application.handler.interfaces;

import com.unibague.magno.application.dto.request.ResearchSeedbedProfileRequest;
import com.unibague.magno.application.dto.response.ResearchSeedbedProfileResponse;
import com.unibague.magno.domain.model.projections.SeedbedReportProjection;

import java.util.List;

public interface IResearchSeedbedProfileHandler {
    ResearchSeedbedProfileResponse findById(Long id);
    ResearchSeedbedProfileResponse save(ResearchSeedbedProfileRequest researchSeedbedProfileRequest);
    ResearchSeedbedProfileResponse updateById(Long id, ResearchSeedbedProfileRequest researchSeedbedProfileRequest);
    void deleteById(Long id);
    List<ResearchSeedbedProfileResponse> findAll();
    List<ResearchSeedbedProfileResponse> findAllByInvestigationGroupProfileId(Long id);

    List<SeedbedReportProjection> getSeedbedReport(Long researchSeedbedProfileId, Long academicPeriodId);
}
