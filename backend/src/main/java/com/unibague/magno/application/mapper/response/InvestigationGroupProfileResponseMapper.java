package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.InvestigationGroupProfileResponse;
import com.unibague.magno.domain.model.InvestigationGroupProfile;

import java.util.List;

/**
 * Mapper interface for converting investigation group profile domain models to response DTOs.
 * Manually implemented to resolve nested relationships (group, coordinator, academic period).
 */
public interface InvestigationGroupProfileResponseMapper {
    InvestigationGroupProfileResponse toResponse(InvestigationGroupProfile investigationGroupProfile);
    List<InvestigationGroupProfileResponse> toResponseList(List<InvestigationGroupProfile> investigationGroupProfiles);
}
