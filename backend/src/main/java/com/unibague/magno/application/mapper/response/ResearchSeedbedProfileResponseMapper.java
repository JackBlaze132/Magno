package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.ResearchSeedbedProfileResponse;
import com.unibague.magno.application.dto.response.ResearchSeedbedResponse;
import com.unibague.magno.domain.model.ResearchSeedbedProfile;

import java.util.List;

public interface ResearchSeedbedProfileResponseMapper {
    ResearchSeedbedProfileResponse toResponse(ResearchSeedbedProfile researchSeedbedProfile);
    List<ResearchSeedbedProfileResponse> toResponseList(List<ResearchSeedbedProfile> researchSeedbedProfiles);
}
