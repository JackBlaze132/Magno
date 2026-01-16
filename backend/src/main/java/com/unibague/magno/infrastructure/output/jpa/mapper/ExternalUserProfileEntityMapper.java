package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.ExternalUserProfile;
import com.unibague.magno.infrastructure.output.jpa.entity.ExternalUserProfileEntity;

import java.util.List;

/**
 * Mapper interface for converting between {@link ExternalUserProfile} domain model and {@link ExternalUserProfileEntity} JPA entity.
 */
public interface ExternalUserProfileEntityMapper {

    ExternalUserProfile toExternalUserProfile(ExternalUserProfileEntity externalUserProfileEntity);
    ExternalUserProfileEntity toExternalUserProfileEntity(Long id, ExternalUserProfile externalUserProfile);
    ExternalUserProfileEntity toExternalUserProfileEntity(ExternalUserProfile externalUserProfile);
    List<ExternalUserProfile> toExternalUserProfileList(List<ExternalUserProfileEntity> externalUserProfileEntities);
}
