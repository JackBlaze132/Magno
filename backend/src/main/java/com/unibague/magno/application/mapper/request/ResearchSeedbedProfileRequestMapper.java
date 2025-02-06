package com.unibague.magno.application.mapper.request;

import com.unibague.magno.application.dto.request.ResearchSeedbedProfileRequest;
import com.unibague.magno.domain.model.ResearchSeedbedProfile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ResearchSeedbedProfileRequestMapper {
    ResearchSeedbedProfile toResearchSeedbedProfile(
            ResearchSeedbedProfileRequest researchSeedbedProfileRequest);
}
