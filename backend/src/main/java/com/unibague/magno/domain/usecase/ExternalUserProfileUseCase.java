package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IExternalUserProfileServicePort;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.exception.externaluser.ExternalUserProfileNotFoundException;
import com.unibague.magno.domain.exception.externaluser.UserIsNotExternalException;
import com.unibague.magno.domain.model.ExternalUserProfile;
import com.unibague.magno.domain.model.User;
import com.unibague.magno.domain.spi.IExternalUserProfilePersistencePort;

import java.util.List;

public class ExternalUserProfileUseCase implements IExternalUserProfileServicePort {

    private final IExternalUserProfilePersistencePort externalUserProfilePersistencePort;
    private final IUserServicePort userServicePort;

    public ExternalUserProfileUseCase(IExternalUserProfilePersistencePort externalUserProfilePersistencePort,
                                      IUserServicePort userServicePort) {
        this.externalUserProfilePersistencePort = externalUserProfilePersistencePort;
        this.userServicePort = userServicePort;
    }

    @Override
    public ExternalUserProfile findById(Long id) {
        return externalUserProfilePersistencePort.findById(id)
                .orElseThrow(() -> new ExternalUserProfileNotFoundException(
                        String.format("ExternalUserProfile with id %s not found", id)
                ));
    }

    @Override
    public ExternalUserProfile save(ExternalUserProfile externalUserProfile) {

        User user = userServicePort.findById(externalUserProfile.getUserId());

        if (!user.isExternalUser()){
            throw new UserIsNotExternalException(
                    String.format("User with id %s is not an external user and cannot have an ExternalUserProfile", user.getId())
            );
        }
        return externalUserProfilePersistencePort.save(externalUserProfile);
    }

    @Override
    public ExternalUserProfile update(Long id, ExternalUserProfile externalUserProfile) {
        if (externalUserProfilePersistencePort.findById(id).isEmpty()) {
            throw new ExternalUserProfileNotFoundException(
                    String.format("ExternalUserProfile with id %s could not be updated because it does not exist", id)
            );
        }
        return externalUserProfilePersistencePort.update(id, externalUserProfile);
    }

    @Override
    public void deleteById(Long id) {
        if (externalUserProfilePersistencePort.findById(id).isEmpty()) {
            throw new ExternalUserProfileNotFoundException(
                    String.format("ExternalUserProfile with id %s could not be deleted because it does not exist", id)
            );
        }
        externalUserProfilePersistencePort.deleteById(id);
    }

    @Override
    public List<ExternalUserProfile> findAll() {
        return externalUserProfilePersistencePort.findAll();
    }

    @Override
    public List<ExternalUserProfile> findAllProfilesByUserId(Long userId) {
        return externalUserProfilePersistencePort.findAllProfilesByUserId(userId);
    }

    @Override
    public List<ExternalUserProfile> findAllByResearchSeedbedProfileId(Long researchSeedbedProfileId) {
        return externalUserProfilePersistencePort.findAllByResearchSeedbedProfileId(researchSeedbedProfileId);
    }
}
