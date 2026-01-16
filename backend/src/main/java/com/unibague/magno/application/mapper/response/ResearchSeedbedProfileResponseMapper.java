package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.ResearchSeedbedProfileResponse;
import com.unibague.magno.application.dto.response.ResearchSeedbedProfileSummaryResponse;
import com.unibague.magno.domain.model.ResearchSeedbedProfile;

import java.util.List;

/**
 * Mapper interface for converting research seedbed profile domain models to response DTOs.
 * Manually implemented to resolve nested relationships and support summary responses.
 */
public interface ResearchSeedbedProfileResponseMapper {
    ResearchSeedbedProfileResponse toResponse(ResearchSeedbedProfile researchSeedbedProfile);
    List<ResearchSeedbedProfileResponse> toResponseList(List<ResearchSeedbedProfile> researchSeedbedProfiles);

    /**
     * Maps to a summary response containing only essential names instead of full nested objects.
     * Useful for list views and dropdowns where full details are not needed.
     *
     * @param researchSeedbedProfile the domain model to map
     * @return a summarized response with names only
     */
    ResearchSeedbedProfileSummaryResponse toSummaryResponse(ResearchSeedbedProfile researchSeedbedProfile);
}
