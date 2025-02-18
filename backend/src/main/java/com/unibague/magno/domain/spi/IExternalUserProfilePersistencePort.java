package com.unibague.magno.domain.spi;

import com.unibague.magno.domain.model.ExternalUserProfile;

import java.util.List;
import java.util.Optional;

public interface IExternalUserProfilePersistencePort {
    Optional<ExternalUserProfile> findById(Long id);
    ExternalUserProfile save(ExternalUserProfile externalUserProfile);
    ExternalUserProfile update(Long id, ExternalUserProfile externalUserProfile);
    void deleteById(Long id);
    List<ExternalUserProfile> findAll();
}
