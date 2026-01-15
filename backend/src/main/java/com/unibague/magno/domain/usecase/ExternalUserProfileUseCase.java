package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IAcademicPeriodServicePort;
import com.unibague.magno.domain.api.IExternalUserProfileServicePort;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.exception.academicperiod.AcademicPeriodNotCurrentException;
import com.unibague.magno.domain.exception.externaluser.ExternalUserProfileNotFoundException;
import com.unibague.magno.domain.exception.externaluser.UserIsNotExternalException;
import com.unibague.magno.domain.model.AcademicPeriod;
import com.unibague.magno.domain.model.ExternalUserProfile;
import com.unibague.magno.domain.model.User;
import com.unibague.magno.domain.spi.IExternalUserProfilePersistencePort;

import java.util.List;

/**
 * Use case implementation for managing external user profiles.
 * <p>
 * Handles business logic for external user profile operations. External users are
 * individuals from outside the university (researchers, collaborators) who participate
 * in research seedbeds. All operations validate that the academic period is current
 * before allowing modifications.
 * </p>
 */
public class ExternalUserProfileUseCase implements IExternalUserProfileServicePort {

    private final IExternalUserProfilePersistencePort externalUserProfilePersistencePort;
    private final IUserServicePort userServicePort;
    private final IAcademicPeriodServicePort academicPeriodServicePort;

    public ExternalUserProfileUseCase(IExternalUserProfilePersistencePort externalUserProfilePersistencePort,
                                      IUserServicePort userServicePort,
                                      IAcademicPeriodServicePort academicPeriodServicePort) {
        this.externalUserProfilePersistencePort = externalUserProfilePersistencePort;
        this.userServicePort = userServicePort;
        this.academicPeriodServicePort = academicPeriodServicePort;
    }

    @Override
    public ExternalUserProfile findById(Long id) {
        return externalUserProfilePersistencePort.findById(id)
                .orElseThrow(() -> new ExternalUserProfileNotFoundException(
                        String.format("Perfil de usuario externo con ID %s no encontrado", id)
                ));
    }

    @Override
    public ExternalUserProfile save(ExternalUserProfile externalUserProfile) {

        User user = userServicePort.findById(externalUserProfile.getUserId());

        if (!user.isExternalUser()){
            throw new UserIsNotExternalException(
                    String.format("El usuario con ID %s no es un usuario externo y no puede tener un perfil de usuario externo", user.getId())
            );
        }

        verifyAcademicPeriodIsCurrent(externalUserProfile.getAcademicPeriodId(),
                "No se puede guardar el perfil de usuario externo porque el período académico no está activo"
        );

        return externalUserProfilePersistencePort.save(externalUserProfile);
    }

    @Override
    public ExternalUserProfile update(Long id, ExternalUserProfile externalUserProfile) {
        if (externalUserProfilePersistencePort.findById(id).isEmpty()) {
            throw new ExternalUserProfileNotFoundException(
                    String.format("No se pudo actualizar el perfil de usuario externo con ID %s porque no existe", id)
            );
        }
        verifyAcademicPeriodIsCurrent(externalUserProfile.getAcademicPeriodId(),
                "No se puede actualizar el perfil de usuario externo porque el período académico no está activo"
        );
        return externalUserProfilePersistencePort.update(id, externalUserProfile);
    }

    @Override
    public void deleteById(Long id) {
        if (externalUserProfilePersistencePort.findById(id).isEmpty()) {
            throw new ExternalUserProfileNotFoundException(
                    String.format("No se pudo eliminar el perfil de usuario externo con ID %s porque no existe", id)
            );
        }
        ExternalUserProfile existingProfile = findById(id);
        verifyAcademicPeriodIsCurrent(existingProfile.getAcademicPeriodId(),
                "No se puede eliminar el perfil de usuario externo porque el período académico no está activo"
        );
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

    /**
     * Verifies that the academic period is in current status.
     *
     * @param academicPeriodId the ID of the academic period to verify
     * @param errorMessage     custom error message for the exception
     * @throws AcademicPeriodNotCurrentException if the academic period is not current
     */
    private void verifyAcademicPeriodIsCurrent(Long academicPeriodId, String errorMessage) {
        AcademicPeriod ap = academicPeriodServicePort.findById(academicPeriodId);
        if (!ap.isCurrent()) {
            throw new AcademicPeriodNotCurrentException(errorMessage);
        }
    }

}
