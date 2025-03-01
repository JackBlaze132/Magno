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

    private final IInvestigationGroupServicePort investigationGroupServicePort;
    private final InvestigationGroupResponseMapper investigationGroupResponseMapper;

    private final IInvestigationGroupProfileServicePort investigationGroupProfileServicePort;
    private final InvestigationGroupProfileResponseMapper investigationGroupProfileResponseMapper;

    private final IAcademicPeriodServicePort academicPeriodServicePort;
    private final AcademicPeriodResponseMapper academicPeriodResponseMapper;
    private final IUserServicePort userServicePort;

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

        ResearchSeedbedProfileResponse response = ResearchSeedbedProfileResponse.builder()
                .id(researchSeedbedProfile.getId())
                .researchSeedbed(researchSeedbedResponse)
                .coordinator(coordinator)
                .tutor(null)
                .investigationGroupProfile(investigationGroupProfile)
                .academicPeriod(academicPeriod)
                .wasActive(researchSeedbedProfile.getWasActive())
                .build();

        Long tutorId = researchSeedbedProfile.getTutorId();
        if (tutorId != null) {
            FunctionaryProfileResponse tutor = functionaryProfileResponseMapper
                    .toResponse(functionaryProfileServicePort
                            .findById(tutorId));

            response.setTutor(tutor);

            return response;
        }

        return response;
    }

    @Override
    public List<ResearchSeedbedProfileResponse> toResponseList(List<ResearchSeedbedProfile> researchSeedbedProfiles) {
        return researchSeedbedProfiles.stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public ResearchSeedbedProfileSummaryResponse toSummaryResponse(ResearchSeedbedProfile researchSeedbedProfile) {

        Long researchSeedbedId = researchSeedbedProfile.getId();
        String researchSeedbedName = researchSeedbedServicePort.findById(researchSeedbedProfile.getResearchSeedbedId()).getName();

        String coordinatorName = userServicePort.findById(
                functionaryProfileServicePort.findById(
                        researchSeedbedProfile.getCoordinatorId())
                        .getUserId()).getFullName();

        String investigationGroupName = investigationGroupServicePort.findById(
                investigationGroupProfileServicePort.findById(
                        researchSeedbedProfile.getInvestigationGroupProfileId())
                        .getInvestigationGroupId()).getName();

        String academicPeriodName = academicPeriodServicePort.findById(
                researchSeedbedProfile.getAcademicPeriodId()).getName();

        Boolean wasActive = researchSeedbedProfile.getWasActive();

        ResearchSeedbedProfileSummaryResponse response = ResearchSeedbedProfileSummaryResponse.builder()
                .id(researchSeedbedId)
                .researchSeedbedName(researchSeedbedName)
                .coordinatorName(coordinatorName)
                .tutorName(null)
                .investigationGroupName(investigationGroupName)
                .academicPeriodName(academicPeriodName)
                .wasActive(wasActive)
                .build();

        Long tutorId = researchSeedbedProfile.getTutorId();
        if (tutorId != null) {
            String tutorName = userServicePort.findById(
                    functionaryProfileServicePort.findById(
                            tutorId)
                            .getUserId()).getFullName();

            response.setTutorName(tutorName);

            return response;
        }

        return response;
    }
}
