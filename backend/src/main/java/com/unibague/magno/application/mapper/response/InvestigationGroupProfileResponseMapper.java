package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.InvestigationGroupProfileResponse;
import com.unibague.magno.domain.model.InvestigationGroupProfile;

import java.util.List;

public interface InvestigationGroupProfileResponseMapper {
    InvestigationGroupProfileResponse toResponse(InvestigationGroupProfile investigationGroupProfile);
    List<InvestigationGroupProfileResponse> toResponseList(List<InvestigationGroupProfile> investigationGroupProfiles);
}
