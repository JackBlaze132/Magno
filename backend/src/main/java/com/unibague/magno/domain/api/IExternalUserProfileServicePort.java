package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.ExternalUserProfile;

import java.util.List;

/**
 * Service port interface that defines the contract for external user profile management operations.
 * <p>
 * This interface provides methods for CRUD operations and queries related to external user profiles,
 * which represent profiles of users from outside the institution (exchange students, visiting researchers, etc.)
 * participating in research activities for specific academic periods.
 * </p>
 *
 * @see ExternalUserProfile
 */
public interface IExternalUserProfileServicePort {
    
    /**
     * Retrieves an external user profile by its unique identifier.
     *
     * @param id the unique identifier of the external user profile
     * @return the external user profile with the specified ID
     */
    ExternalUserProfile findById(Long id);
    
    /**
     * Persists a new external user profile.
     *
     * @param externalUserProfile the external user profile to save
     * @return the saved external user profile
     */
    ExternalUserProfile save(ExternalUserProfile externalUserProfile);
    
    /**
     * Updates an existing external user profile.
     *
     * @param id the unique identifier of the external user profile to update
     * @param externalUserProfile the external user profile data to update
     * @return the updated external user profile
     */
    ExternalUserProfile update(Long id, ExternalUserProfile externalUserProfile);
    
    /**
     * Deletes an external user profile by its unique identifier.
     *
     * @param id the unique identifier of the external user profile to delete
     */
    void deleteById(Long id);
    
    /**
     * Retrieves all external user profiles in the system.
     *
     * @return a list of all external user profiles
     */
    List<ExternalUserProfile> findAll();

    /**
     * Retrieves all external user profiles associated with a specific user.
     *
     * @param userId the unique identifier of the user
     * @return a list of external user profiles for the specified user
     */
    List<ExternalUserProfile> findAllProfilesByUserId(Long userId);
    
    /**
     * Retrieves all external user profiles associated with a specific research seedbed profile.
     *
     * @param researchSeedbedProfileId the unique identifier of the research seedbed profile
     * @return a list of external user profiles associated with the specified seedbed profile
     */
    List<ExternalUserProfile> findAllByResearchSeedbedProfileId(Long researchSeedbedProfileId);
}
