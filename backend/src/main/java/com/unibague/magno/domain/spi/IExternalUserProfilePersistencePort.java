package com.unibague.magno.domain.spi;

import com.unibague.magno.domain.model.ExternalUserProfile;

import java.util.List;
import java.util.Optional;

/**
 * Persistence port for managing external user profile data.
 * <p>
 * This interface defines the contract for persisting and retrieving external user profiles.
 * External users are individuals from outside the university (e.g., external researchers,
 * collaborators) who participate in research seedbeds.
 * </p>
 */
public interface IExternalUserProfilePersistencePort {
    Optional<ExternalUserProfile> findById(Long id);
    ExternalUserProfile save(ExternalUserProfile externalUserProfile);
    ExternalUserProfile update(Long id, ExternalUserProfile externalUserProfile);
    void deleteById(Long id);
    List<ExternalUserProfile> findAll();

    /**
     * Retrieves all profiles associated with a specific external user.
     *
     * @param userId the unique identifier of the external user
     * @return a list of all profiles for the user
     */
    List<ExternalUserProfile> findAllProfilesByUserId(Long userId);

    /**
     * Retrieves all external user profiles participating in a specific research seedbed.
     *
     * @param researchSeedbedProfileId the unique identifier of the research seedbed profile
     * @return a list of external user profiles associated with the seedbed
     */
    List<ExternalUserProfile> findAllByResearchSeedbedProfileId(Long researchSeedbedProfileId);
}
