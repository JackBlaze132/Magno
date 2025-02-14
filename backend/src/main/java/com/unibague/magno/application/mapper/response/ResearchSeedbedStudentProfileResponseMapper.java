package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.ResearchSeedbedStudentProfileResponse;
import com.unibague.magno.domain.model.ResearchSeedbedStudentProfile;

import java.util.List;

public interface ResearchSeedbedStudentProfileResponseMapper {
    ResearchSeedbedStudentProfileResponse toResponse(
            ResearchSeedbedStudentProfile researchSeedbedStudentProfile);
    List<ResearchSeedbedStudentProfileResponse> toResponseList(
            List<ResearchSeedbedStudentProfile> researchSeedbedStudentProfiles);
}
