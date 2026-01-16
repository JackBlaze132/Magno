package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.InvestigationGroupResponse;
import com.unibague.magno.domain.model.InvestigationGroup;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper interface for converting investigation group domain models to response DTOs.
 * Auto-implemented by MapStruct.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InvestigationGroupResponseMapper {
    InvestigationGroupResponse toResponse(InvestigationGroup investigationGroup);
    List<InvestigationGroupResponse> toResponseList(List<InvestigationGroup> investigationGroups);
}
