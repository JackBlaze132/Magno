package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.ExternalUserProfileResponse;
import com.unibague.magno.domain.model.ExternalUserProfile;

import java.util.List;

/**
 * Mapper interface for converting external user profile domain models to response DTOs.
 * Manually implemented to resolve nested relationships (user, academic period, seedbed profile).
 */
public interface ExternalUserProfileResponseMapper {
    ExternalUserProfileResponse toResponse(ExternalUserProfile externalUserProfile);
    List<ExternalUserProfileResponse> toResponseList(List<ExternalUserProfile> externalUserProfiles);
}
