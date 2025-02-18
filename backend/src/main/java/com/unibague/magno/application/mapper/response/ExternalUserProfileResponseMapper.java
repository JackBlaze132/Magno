package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.ExternalUserProfileResponse;
import com.unibague.magno.domain.model.ExternalUserProfile;

import java.util.List;

public interface ExternalUserProfileResponseMapper {
    ExternalUserProfileResponse toResponse(ExternalUserProfile externalUserProfile);
    List<ExternalUserProfileResponse> toResponseList(List<ExternalUserProfile> externalUserProfiles);
}
