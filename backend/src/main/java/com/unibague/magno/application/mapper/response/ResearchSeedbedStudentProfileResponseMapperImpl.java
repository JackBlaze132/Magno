package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.*;
import com.unibague.magno.domain.api.IResearchSeedbedProfileServicePort;
import com.unibague.magno.domain.api.IStudentProfileServicePort;
import com.unibague.magno.domain.model.ResearchSeedbedStudentProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ResearchSeedbedStudentProfileResponseMapperImpl implements ResearchSeedbedStudentProfileResponseMapper{

    private final IResearchSeedbedProfileServicePort researchSeedbedProfileServicePort;
    private final ResearchSeedbedProfileResponseMapper researchSeedbedProfileResponseMapper;

    private final IStudentProfileServicePort studentProfileServicePort;
    private final StudentProfileResponseMapper studentProfileResponseMapper;

    @Override
    public ResearchSeedbedStudentProfileResponse
    toResponse(ResearchSeedbedStudentProfile researchSeedbedStudentProfile) {

        Long researchSeedbedStudentProfileId = researchSeedbedStudentProfile.getId();

        Long researchSeedbedProfileId = researchSeedbedStudentProfile.getResearchSeedbedProfileId();
        ResearchSeedbedProfileResponse researchSeedbedProfile = researchSeedbedProfileResponseMapper
                .toResponse(researchSeedbedProfileServicePort
                .findById(researchSeedbedProfileId));

        Long studentProfileId = researchSeedbedStudentProfile.getStudentProfileId();
        StudentProfileResponse studentProfile = studentProfileResponseMapper
                .toResponse(studentProfileServicePort
                .findById(studentProfileId));

        return ResearchSeedbedStudentProfileResponse.builder()
                .id(researchSeedbedStudentProfileId)
                .researchSeedbedProfile(researchSeedbedProfile)
                .studentProfile(studentProfile)
                .wasActive(researchSeedbedStudentProfile.getWasActive())
                .build();
    }

    @Override
    public List<ResearchSeedbedStudentProfileResponse> toResponseList(
            List<ResearchSeedbedStudentProfile> researchSeedbedStudentProfiles) {
        return researchSeedbedStudentProfiles.stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<ResearchSeedbedStudentProfileSummaryResponse> toSummaryResponseList(
            List<ResearchSeedbedStudentProfile> researchSeedbedStudentProfiles) {
        return researchSeedbedStudentProfiles.stream()
                .map(this::toSummaryResponse)
                .toList();
    }

    private ResearchSeedbedStudentProfileSummaryResponse toSummaryResponse(
            ResearchSeedbedStudentProfile researchSeedbedStudentProfile) {

        Long researchSeedbedStudentProfileId = researchSeedbedStudentProfile.getResearchSeedbedProfileId();
        Long studentProfileId = researchSeedbedStudentProfile.getStudentProfileId();

        ResearchSeedbedProfileSummaryResponse researchSeedbedProfile = researchSeedbedProfileResponseMapper
                .toSummaryResponse(researchSeedbedProfileServicePort
                .findById(researchSeedbedStudentProfileId));

        StudentProfileResponse studentProfile = studentProfileResponseMapper
                .toResponse(studentProfileServicePort
                .findById(studentProfileId));

        return ResearchSeedbedStudentProfileSummaryResponse.builder()
                .id(researchSeedbedStudentProfile.getId())
                .researchSeedbedProfile(researchSeedbedProfile)
                .studentProfile(studentProfile)
                .wasActive(researchSeedbedStudentProfile.getWasActive())
                .build();
    }
}
