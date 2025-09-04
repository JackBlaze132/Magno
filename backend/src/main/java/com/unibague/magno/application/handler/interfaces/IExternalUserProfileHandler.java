package com.unibague.magno.application.handler.interfaces;

import com.unibague.magno.application.dto.request.ExternalUserProfileRequest;
import com.unibague.magno.application.dto.response.ExternalUserProfileResponse;

import java.util.List;

public interface IExternalUserProfileHandler {
    ExternalUserProfileResponse findById(Long id);
    ExternalUserProfileResponse save(ExternalUserProfileRequest externalUserProfile);
    ExternalUserProfileResponse updateById(Long id, ExternalUserProfileRequest externalUserProfile);
    void deleteById(Long id);
    List<ExternalUserProfileResponse> findAll();

    List<ExternalUserProfileResponse> findAllProfilesByUserId(Long userId);
    List<ExternalUserProfileResponse> findAllByResearchSeedbedProfileId(Long researchSeedbedProfileId);
}
