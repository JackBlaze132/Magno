package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.ResearchSeedbedResponse;
import com.unibague.magno.domain.model.ResearchSeedbed;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper interface for converting research seedbed domain models to response DTOs.
 * Auto-implemented by MapStruct.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ResearchSeedbedResponseMapper {
    ResearchSeedbedResponse toResponse(ResearchSeedbed researchSeeedbed);
    List<ResearchSeedbedResponse> toResponseList(List<ResearchSeedbed> researchSeedbeds);
}
