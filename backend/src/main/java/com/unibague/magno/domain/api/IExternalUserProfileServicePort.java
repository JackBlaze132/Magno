package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.ExternalUserProfile;

import java.util.List;

public interface IExternalUserProfileServicePort {
    ExternalUserProfile findById(Long id);
    ExternalUserProfile save(ExternalUserProfile externalUserProfile);
    ExternalUserProfile update(Long id, ExternalUserProfile externalUserProfile);
    void deleteById(Long id);
    List<ExternalUserProfile> findAll();

    List<ExternalUserProfile> findAllProfilesByUserId(Long userId);
    List<ExternalUserProfile> findAllByResearchSeedbedProfileId(Long researchSeedbedProfileId);
}
