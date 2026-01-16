package com.unibague.magno.application.handler.interfaces;

import com.unibague.magno.application.dto.request.ExternalUserProfileRequest;
import com.unibague.magno.application.dto.response.ExternalUserProfileResponse;

import java.util.List;

/**
 * Handler interface for external user profile operations.
 * Manages profiles for individuals outside the university who participate
 * in research seedbeds (e.g., visiting researchers, external advisors).
 */
public interface IExternalUserProfileHandler {
    ExternalUserProfileResponse findById(Long id);
    ExternalUserProfileResponse save(ExternalUserProfileRequest externalUserProfile);
    ExternalUserProfileResponse updateById(Long id, ExternalUserProfileRequest externalUserProfile);
    void deleteById(Long id);
    List<ExternalUserProfileResponse> findAll();

    /**
     * Retrieves all external user profiles for a specific user across all periods.
     *
     * @param userId the user identifier
     * @return list of external user profiles for the specified user
     */
    List<ExternalUserProfileResponse> findAllProfilesByUserId(Long userId);

    /**
     * Retrieves all external users participating in a specific research seedbed profile.
     *
     * @param researchSeedbedProfileId the research seedbed profile identifier
     * @return list of external user profiles in the specified seedbed
     */
    List<ExternalUserProfileResponse> findAllByResearchSeedbedProfileId(Long researchSeedbedProfileId);
}
