package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.*;
import com.unibague.magno.domain.api.*;
import com.unibague.magno.domain.model.ResearchSeedbedProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ResearchSeedbedProfileResponseMapperImpl implements ResearchSeedbedProfileResponseMapper {

    private final IResearchSeedbedServicePort researchSeedbedServicePort;
    private final ResearchSeedbedResponseMapper researchSeedbedResponseMapper;

    private final IFunctionaryProfileServicePort functionaryProfileServicePort;
    private final FunctionaryProfileResponseMapper functionaryProfileResponseMapper;

    private final IInvestigationGroupProfileServicePort investigationGroupProfileServicePort;
    private final InvestigationGroupProfileResponseMapper investigationGroupProfileResponseMapper;

    private final IAcademicPeriodServicePort academicPeriodServicePort;
    private final AcademicPeriodResponseMapper academicPeriodResponseMapper;

    @Override
    public ResearchSeedbedProfileResponse toResponse(ResearchSeedbedProfile researchSeedbedProfile) {

        Long researchSeedbedId = researchSeedbedProfile.getResearchSeedbedId();
        ResearchSeedbedResponse researchSeedbedResponse = researchSeedbedResponseMapper
                .toResponse(researchSeedbedServicePort
                .findById(researchSeedbedId));

        Long coordinatorId = researchSeedbedProfile.getCoordinatorId();
        FunctionaryProfileResponse coordinator = functionaryProfileResponseMapper
                .toResponse(functionaryProfileServicePort
                .findById(coordinatorId));


        Long investigationGroupProfileId = researchSeedbedProfile.getInvestigationGroupProfileId();
        InvestigationGroupProfileResponse investigationGroupProfile = investigationGroupProfileResponseMapper
                .toResponse(investigationGroupProfileServicePort
                .findById(investigationGroupProfileId));

        Long academicPeriodId = researchSeedbedProfile.getAcademicPeriodId();
        AcademicPeriodResponse academicPeriod = academicPeriodResponseMapper
                .toResponse(academicPeriodServicePort
                .findById(academicPeriodId));

        Long tutorId = researchSeedbedProfile.getTutorId();
        if (tutorId != null) {
            FunctionaryProfileResponse tutor = functionaryProfileResponseMapper
                    .toResponse(functionaryProfileServicePort
                            .findById(tutorId));

            return ResearchSeedbedProfileResponse.builder()
                    .id(researchSeedbedProfile.getId())
                    .researchSeedbed(researchSeedbedResponse)
                    .coordinator(coordinator)
                    .tutor(tutor)
                    .investigationGroupProfile(investigationGroupProfile)
                    .academicPeriod(academicPeriod)
                    .wasActive(researchSeedbedProfile.getWasActive())
                    .build();
        }

        return ResearchSeedbedProfileResponse.builder()
                .id(researchSeedbedProfile.getId())
                .researchSeedbed(researchSeedbedResponse)
                .coordinator(coordinator)
                .tutor(null)
                .investigationGroupProfile(investigationGroupProfile)
                .academicPeriod(academicPeriod)
                .wasActive(researchSeedbedProfile.getWasActive())
                .build();

    }

    @Override
    public List<ResearchSeedbedProfileResponse> toResponseList(List<ResearchSeedbedProfile> researchSeedbedProfiles) {
        return researchSeedbedProfiles.stream()
                .map(this::toResponse)
                .toList();
    }
}
