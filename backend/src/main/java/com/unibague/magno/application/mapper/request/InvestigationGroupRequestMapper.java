package com.unibague.magno.application.mapper.request;

import com.unibague.magno.application.dto.request.InvestigationGroupRequest;
import com.unibague.magno.domain.model.InvestigationGroup;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper interface for converting investigation group request DTOs to domain models.
 * Auto-implemented by MapStruct.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InvestigationGroupRequestMapper {
    InvestigationGroup toInvestigationGroup(InvestigationGroupRequest investigationGroupRequest);
}
