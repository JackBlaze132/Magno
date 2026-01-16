package com.unibague.magno.application.mapper.request;

import com.unibague.magno.application.dto.request.InvestigationGroupProfileRequest;
import com.unibague.magno.domain.model.InvestigationGroupProfile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper interface for converting investigation group profile request DTOs to domain models.
 * Auto-implemented by MapStruct.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InvestigationGroupProfileRequestMapper {
    InvestigationGroupProfile toInvestigationGroupProfile(
            InvestigationGroupProfileRequest investigationGroupProfileRequest);
}
