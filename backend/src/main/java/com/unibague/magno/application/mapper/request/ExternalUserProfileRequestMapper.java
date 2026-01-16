package com.unibague.magno.application.mapper.request;

import com.unibague.magno.application.dto.request.ExternalUserProfileRequest;
import com.unibague.magno.domain.model.ExternalUserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper interface for converting external user profile request DTOs to domain models.
 * Auto-implemented by MapStruct.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExternalUserProfileRequestMapper {
    ExternalUserProfile toExternalUserProfile(ExternalUserProfileRequest externalUserProfileRequest);
}
