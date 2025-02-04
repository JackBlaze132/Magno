package com.unibague.magno.application.mapper.request;

import com.unibague.magno.application.dto.request.InvestigationGroupProfileRequest;
import com.unibague.magno.domain.model.InvestigationGroupProfile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface InvestigationGroupProfileRequestMapper {
    InvestigationGroupProfile toInvestigationGroupProfile(
            InvestigationGroupProfileRequest investigationGroupProfileRequest);
}
