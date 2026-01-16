package com.unibague.magno.application.mapper.request;

import com.unibague.magno.application.dto.request.ResearchSeedbedRequest;
import com.unibague.magno.domain.model.ResearchSeedbed;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper interface for converting research seedbed request DTOs to domain models.
 * Auto-implemented by MapStruct.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ResearchSeedbedRequestMapper {
    ResearchSeedbed toResearchSeedbed(ResearchSeedbedRequest researchSeedbedRequest);
}
