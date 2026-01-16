package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.ResearchSeedbedStudentProfileResponse;
import com.unibague.magno.application.dto.response.ResearchSeedbedStudentProfileSummaryResponse;
import com.unibague.magno.domain.model.ResearchSeedbedStudentProfile;

import java.util.List;

/**
 * Mapper interface for converting research seedbed student profile domain models to response DTOs.
 * Manually implemented to resolve nested relationships and support summary responses.
 */
public interface ResearchSeedbedStudentProfileResponseMapper {
    ResearchSeedbedStudentProfileResponse toResponse(
            ResearchSeedbedStudentProfile researchSeedbedStudentProfile);
    List<ResearchSeedbedStudentProfileResponse> toResponseList(
            List<ResearchSeedbedStudentProfile> researchSeedbedStudentProfiles);

    /**
     * Maps to a list of summary responses with condensed seedbed profile information.
     * Useful for displaying student participation across multiple seedbeds.
     *
     * @param researchSeedbedStudentProfiles the domain models to map
     * @return list of summarized responses
     */
    List<ResearchSeedbedStudentProfileSummaryResponse> toSummaryResponseList(
            List<ResearchSeedbedStudentProfile> researchSeedbedStudentProfiles);
}
