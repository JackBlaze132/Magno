package com.unibague.magno.application.mapper.request;

import com.unibague.magno.application.dto.request.ResearchSeedbedProfileRequest;
import com.unibague.magno.domain.model.ResearchSeedbedProfile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper interface for converting research seedbed profile request DTOs to domain models.
 * Auto-implemented by MapStruct.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ResearchSeedbedProfileRequestMapper {
    ResearchSeedbedProfile toResearchSeedbedProfile(
            ResearchSeedbedProfileRequest researchSeedbedProfileRequest);
}
