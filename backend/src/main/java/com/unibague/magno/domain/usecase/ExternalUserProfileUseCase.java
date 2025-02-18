package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IExternalUserProfileServicePort;
import com.unibague.magno.domain.exception.ExternalUserProfileNotFoundException;
import com.unibague.magno.domain.model.ExternalUserProfile;
import com.unibague.magno.domain.spi.IExternalUserProfilePersistencePort;

import java.util.List;

public class ExternalUserProfileUseCase implements IExternalUserProfileServicePort {

    private final IExternalUserProfilePersistencePort externalUserProfilePersistencePort;

    public ExternalUserProfileUseCase(IExternalUserProfilePersistencePort externalUserProfilePersistencePort) {
        this.externalUserProfilePersistencePort = externalUserProfilePersistencePort;
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
        return externalUserProfilePersistencePort.save(externalUserProfile);
    }

    @Override
    public ExternalUserProfile update(Long id, ExternalUserProfile externalUserProfile) {
        if (externalUserProfilePersistencePort.findById(id).isEmpty()) {
            throw new ExternalUserProfileNotFoundException(
                    String.format("ExternalUserProfile with id %s not found", id)
            );
        }
        return externalUserProfilePersistencePort.update(id, externalUserProfile);
    }

    @Override
    public void deleteById(Long id) {
        if (externalUserProfilePersistencePort.findById(id).isEmpty()) {
            throw new ExternalUserProfileNotFoundException(
                    String.format("ExternalUserProfile with id %s not found", id)
            );
        }
        externalUserProfilePersistencePort.deleteById(id);
    }

    @Override
    public List<ExternalUserProfile> findAll() {
        return externalUserProfilePersistencePort.findAll();
    }
}
