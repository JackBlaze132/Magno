package com.unibague.magno.application.handler.impl;

import com.unibague.magno.application.dto.request.ResearchSeedbedProfileRequest;
import com.unibague.magno.application.dto.response.ResearchSeedbedProfileResponse;
import com.unibague.magno.application.handler.interfaces.IResearchSeedbedProfileHandler;
import com.unibague.magno.application.mapper.request.ResearchSeedbedProfileRequestMapper;
import com.unibague.magno.application.mapper.response.ResearchSeedbedProfileResponseMapper;
import com.unibague.magno.domain.api.IResearchSeedbedProfileServicePort;
import com.unibague.magno.domain.api.IResearchSeedbedServicePort;
import com.unibague.magno.domain.model.ResearchSeedbedProfile;
import com.unibague.magno.domain.model.projections.SeedbedReportProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResearchSeedbedProfileHandler implements IResearchSeedbedProfileHandler {

    private final IResearchSeedbedProfileServicePort researchSeedbedProfileServicePort;
    private final ResearchSeedbedProfileRequestMapper researchSeedbedProfileRequestMapper;
    private final ResearchSeedbedProfileResponseMapper researchSeedbedProfileResponseMapper;

    @Override
    public ResearchSeedbedProfileResponse findById(Long id) {
        ResearchSeedbedProfile researchSeedbed = researchSeedbedProfileServicePort.findById(id);
        return researchSeedbedProfileResponseMapper.toResponse(researchSeedbed);
    }

    @Override
    public ResearchSeedbedProfileResponse save(ResearchSeedbedProfileRequest researchSeedbedProfileRequest) {
        return researchSeedbedProfileResponseMapper.toResponse(researchSeedbedProfileServicePort
                .save(researchSeedbedProfileRequestMapper
                        .toResearchSeedbedProfile(researchSeedbedProfileRequest)));
    }

    @Override
    public ResearchSeedbedProfileResponse updateById(Long id, ResearchSeedbedProfileRequest researchSeedbedProfileRequest) {
        return researchSeedbedProfileResponseMapper.toResponse(researchSeedbedProfileServicePort
                .update(id, researchSeedbedProfileRequestMapper
                        .toResearchSeedbedProfile(researchSeedbedProfileRequest)));
    }

    @Override
    public void deleteById(Long id) {
        researchSeedbedProfileServicePort.deleteById(id);
    }

    @Override
    public List<ResearchSeedbedProfileResponse> findAll() {
        return researchSeedbedProfileResponseMapper.toResponseList(researchSeedbedProfileServicePort.findAll());
    }

    @Override
    public List<ResearchSeedbedProfileResponse> findAllByInvestigationGroupProfileId(Long id) {
        return researchSeedbedProfileResponseMapper.toResponseList(
                researchSeedbedProfileServicePort.findAllByInvestigationGroupProfileId(id));
    }

    @Override
    public List<SeedbedReportProjection> getSeedbedReport(Long researchSeedbedProfileId, Long academicPeriodId) {
        return researchSeedbedProfileServicePort.getSeedbedReportById(researchSeedbedProfileId, academicPeriodId);
    }
}
