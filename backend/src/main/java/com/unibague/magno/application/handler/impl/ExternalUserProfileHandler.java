package com.unibague.magno.application.handler.impl;

import com.unibague.magno.application.dto.request.ExternalUserProfileRequest;
import com.unibague.magno.application.dto.response.ExternalUserProfileResponse;
import com.unibague.magno.application.handler.interfaces.IExternalUserProfileHandler;
import com.unibague.magno.application.mapper.request.ExternalUserProfileRequestMapper;
import com.unibague.magno.application.mapper.response.ExternalUserProfileResponseMapper;
import com.unibague.magno.domain.api.IExternalUserProfileServicePort;
import com.unibague.magno.domain.model.ExternalUserProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of {@link IExternalUserProfileHandler}.
 */
@Service
@RequiredArgsConstructor
public class ExternalUserProfileHandler implements IExternalUserProfileHandler {

    private final IExternalUserProfileServicePort externalUserProfileServicePort;
    private final ExternalUserProfileResponseMapper externalUserProfileResponseMapper;
    private final ExternalUserProfileRequestMapper externalUserProfileRequestMapper;

    @Override
    public ExternalUserProfileResponse findById(Long id) {
        ExternalUserProfile externalUserProfile = externalUserProfileServicePort.findById(id);
        return externalUserProfileResponseMapper.toResponse(externalUserProfile);
    }

    @Override
    public ExternalUserProfileResponse save(ExternalUserProfileRequest externalUserProfile) {
        return externalUserProfileResponseMapper.toResponse(externalUserProfileServicePort
                .save(externalUserProfileRequestMapper.toExternalUserProfile(externalUserProfile)));
    }

    @Override
    public ExternalUserProfileResponse updateById(Long id, ExternalUserProfileRequest externalUserProfile) {
        return externalUserProfileResponseMapper.toResponse(externalUserProfileServicePort
                .update(id, externalUserProfileRequestMapper.toExternalUserProfile(externalUserProfile)));
    }

    @Override
    public void deleteById(Long id) {
        externalUserProfileServicePort.deleteById(id);
    }

    @Override
    public List<ExternalUserProfileResponse> findAll() {
        return externalUserProfileResponseMapper.toResponseList(externalUserProfileServicePort.findAll());
    }

    @Override
    public List<ExternalUserProfileResponse> findAllProfilesByUserId(Long userId) {
        return externalUserProfileResponseMapper.toResponseList(
                externalUserProfileServicePort.findAllProfilesByUserId(userId));
    }

    @Override
    public List<ExternalUserProfileResponse> findAllByResearchSeedbedProfileId(Long researchSeedbedProfileId) {
        List<ExternalUserProfile> externalUserProfiles = externalUserProfileServicePort.findAllByResearchSeedbedProfileId(researchSeedbedProfileId);
        return externalUserProfileResponseMapper.toResponseList(externalUserProfiles);
    }
}
