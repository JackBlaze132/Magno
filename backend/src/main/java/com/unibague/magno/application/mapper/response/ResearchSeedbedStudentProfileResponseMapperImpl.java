package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.ResearchSeedbedProfileResponse;
import com.unibague.magno.application.dto.response.ResearchSeedbedStudentProfileResponse;
import com.unibague.magno.application.dto.response.StudentProfileResponse;
import com.unibague.magno.domain.api.IResearchSeedbedProfileServicePort;
import com.unibague.magno.domain.api.IStudentProfileServicePort;
import com.unibague.magno.domain.model.ResearchSeedbedStudentProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ResearchSeedbedStudentProfileResponseMapperImpl implements ResearchSeedbedStudentProfileResponseMapper{

    IResearchSeedbedProfileServicePort researchSeedbedProfileServicePort;
    ResearchSeedbedProfileResponseMapper researchSeedbedProfileResponseMapper;

    IStudentProfileServicePort studentProfileServicePort;
    StudentProfileResponseMapper studentProfileResponseMapper;

    @Override
    public ResearchSeedbedStudentProfileResponse
    toResponse(ResearchSeedbedStudentProfile researchSeedbedStudentProfile) {

        Long researchSeedbedProfileId = researchSeedbedStudentProfile.getResearchSeedbedProfileId();
        ResearchSeedbedProfileResponse researchSeedbedProfile = researchSeedbedProfileResponseMapper
                .toResponse(researchSeedbedProfileServicePort
                .findById(researchSeedbedProfileId));

        Long studentProfileId = researchSeedbedStudentProfile.getStudentProfileId();
        StudentProfileResponse studentProfile = studentProfileResponseMapper
                .toResponse(studentProfileServicePort
                .findById(studentProfileId));

        return ResearchSeedbedStudentProfileResponse.builder()
                .researchSeedbedProfile(researchSeedbedProfile)
                .studentProfile(studentProfile)
                .wasActive(false)
                .build();
    }

    @Override
    public List<ResearchSeedbedStudentProfileResponse> toResponseList(
            List<ResearchSeedbedStudentProfile> researchSeedbedStudentProfiles) {
        return researchSeedbedStudentProfiles.stream()
                .map(this::toResponse)
                .toList();
    }
}
