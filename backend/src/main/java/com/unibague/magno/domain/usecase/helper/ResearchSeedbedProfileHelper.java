package com.unibague.magno.domain.usecase.helper;

import com.unibague.magno.domain.api.*;
import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.exception.academicperiod.AcademicPeriodNotCurrentException;
import com.unibague.magno.domain.model.*;
import com.unibague.magno.domain.model.enums.SeedbedRole;
import com.unibague.magno.domain.model.integra.IntegraFunctionary;
import com.unibague.magno.domain.spi.IInvestigationGroupProfilePersistencePort;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ResearchSeedbedProfileHelper implements IResearchSeedbedProfileHelper {

    private final IIntegraServicePort integraServicePort;
    private final IUserServicePort userServicePort;
    private final IFunctionaryProfileServicePort functionaryProfileServicePort;
    private final IDependencyServicePort dependencyServicePort;
    private final IRoleServicePort roleServicePort;
    private final IAcademicPeriodServicePort academicPeriodServicePort;
    private final IInvestigationGroupProfilePersistencePort investigationGroupProfilePersistencePort;

    public ResearchSeedbedProfileHelper(IIntegraServicePort integraServicePort, IUserServicePort userServicePort,
                                        IFunctionaryProfileServicePort functionaryProfileServicePort,
                                        IDependencyServicePort dependencyServicePort,
                                        IRoleServicePort roleServicePort,
                                        IAcademicPeriodServicePort academicPeriodServicePort,
                                        IInvestigationGroupProfilePersistencePort investigationGroupProfilePersistencePort) {
        this.integraServicePort = integraServicePort;
        this.userServicePort = userServicePort;
        this.functionaryProfileServicePort = functionaryProfileServicePort;
        this.dependencyServicePort = dependencyServicePort;
        this.roleServicePort = roleServicePort;
        this.academicPeriodServicePort = academicPeriodServicePort;
        this.investigationGroupProfilePersistencePort = investigationGroupProfilePersistencePort;
    }

    @Override
    public ResearchSeedbedProfile verifyUsersHasFunctionaryProfiles(ResearchSeedbedProfile rsp) {
        Long academicPeriodId = rsp.getAcademicPeriodId();
        Long coordinatorId = rsp.getCoordinatorId();
        Long tutorId = rsp.getTutorId();

        boolean coordinatorHasProfile = hasProfileInAcademicPeriod(coordinatorId, academicPeriodId);
        boolean tutorHasProfile = tutorId != null && hasProfileInAcademicPeriod(tutorId, academicPeriodId);

        if (coordinatorHasProfile && tutorHasProfile) {
            updateExistingProfileIds(rsp, coordinatorId, tutorId, academicPeriodId);
            return rsp;
        }

        if (!coordinatorHasProfile) {
            createAndAssignProfile(rsp, coordinatorId, SeedbedRole.COORDINADOR_DE_SEMILLERO, true);
        }
        else {
            findProfileIdByUserAndPeriod(coordinatorId, academicPeriodId)
                    .ifPresent(rsp::setCoordinatorId);
            checkRoleForExistingProfile(coordinatorId, academicPeriodId);
        }

        if (tutorId != null && !tutorHasProfile) {
            createAndAssignProfile(rsp, tutorId, SeedbedRole.TUTOR_DE_SEMILLERO, false);
        }

        return rsp;
    }

    /**
     * Verifies that the academic period is in current status.
     * Throws an exception otherwise.
     *
     * @param academicPeriodId The ID of the academic period to verify
     * @param errorMessage Custom error message for the exception
     * @throws AcademicPeriodNotCurrentException if the academic period is not current
     */
    @Override
    public void verifyAcademicPeriodIsCurrent(Long academicPeriodId, String errorMessage) {
        AcademicPeriod ap = academicPeriodServicePort.findById(academicPeriodId);
        if (!ap.isCurrent()) {
            throw new AcademicPeriodNotCurrentException(errorMessage);
        }
    }


    private void checkRoleForExistingProfile(Long functionaryUserId, Long academicPeriodId) {
        findProfileInAcademicPeriod(functionaryUserId, academicPeriodId)
                .ifPresent(profile -> {
                    Role role = roleServicePort.findByName(roleServicePort.findById(profile.getRoleId()).getName());
                    if (role.getName().equals(SeedbedRole.TUTOR_DE_SEMILLERO)) {
                        profile.setRoleId(roleServicePort.findByName(SeedbedRole.COORDINADOR_DE_SEMILLERO).getId());
                        functionaryProfileServicePort.update(profile.getId(), profile);
                    }
                });
    }

    private void updateExistingProfileIds(ResearchSeedbedProfile rsp, Long coordinatorId,
                                          Long tutorId, Long academicPeriodId) {
        findProfileIdByUserAndPeriod(coordinatorId, academicPeriodId)
                .ifPresent(rsp::setCoordinatorId);

        if (tutorId != null) {
            findProfileIdByUserAndPeriod(tutorId, academicPeriodId)
                    .ifPresent(rsp::setTutorId);
        }
    }

    private Optional<Long> findProfileIdByUserAndPeriod(Long userId, Long academicPeriodId) {
        return functionaryProfileServicePort.findAllProfilesByUserId(userId).stream()
                .filter(profile -> profile.getAcademicPeriodId().equals(academicPeriodId))
                .findFirst()
                .map(FunctionaryProfile::getId);
    }

    private void createAndAssignProfile(ResearchSeedbedProfile rsp, Long userId,
                                        SeedbedRole role, boolean isCoordinator) {
        FunctionaryProfile functionaryProfile = buildFunctionaryProfile(userId, rsp.getAcademicPeriodId(), role);
        FunctionaryProfile savedProfile = functionaryProfileServicePort.save(functionaryProfile);

        if (isCoordinator) {
            rsp.setCoordinatorId(savedProfile.getId());
        } else {
            rsp.setTutorId(savedProfile.getId());
        }
    }

    private FunctionaryProfile buildFunctionaryProfile(Long userId, Long academicPeriodId, SeedbedRole role) {
        User user = userServicePort.findById(userId);
        IntegraFunctionary integraFunctionary = integraServicePort
                .getIntegraFunctionaryByIdentification(user.getIdentificationNumber());
        Dependency dependency = dependencyServicePort.findByName(integraFunctionary.getProgram());

        FunctionaryProfile profile = new FunctionaryProfile();
        profile.setUserId(userId);
        profile.setAcademicPeriodId(academicPeriodId);
        profile.setDependencyId(dependency.getId());
        profile.setRoleId(roleServicePort.findByName(role).getId());

        return profile;
    }

    private boolean hasProfileInAcademicPeriod(Long userId, Long academicPeriodId) {
        return functionaryProfileServicePort.findAllProfilesByUserId(userId).stream()
                .anyMatch(profile -> profile.getAcademicPeriodId().equals(academicPeriodId));
    }

    private Optional<FunctionaryProfile> findProfileInAcademicPeriod(Long userId, Long academicPeriodId) {
        return functionaryProfileServicePort.findAllProfilesByUserId(userId).stream()
                .filter(profile -> profile.getAcademicPeriodId().equals(academicPeriodId))
                .findFirst();
    }

    @Override
    public void handleFunctionaryProfileChangesOnUpdate
            (List<ResearchSeedbedProfile> researchSeedbedProfiles, Long academicPeriodId,
             Long oldCoordinatorId, Long oldTutorId) {
        
        // Get investigation group profiles once for efficiency
        List<InvestigationGroupProfile> investigationGroupProfiles =
                investigationGroupProfilePersistencePort.findAllByAcademicPeriodId(academicPeriodId);
        
        // Handle old coordinator changes
        handleOldCoordinatorChanges(researchSeedbedProfiles, investigationGroupProfiles, oldCoordinatorId);
        
        // Handle old tutor changes (only if there was a tutor)
        if (oldTutorId != null) {
            handleOldTutorChanges(researchSeedbedProfiles, investigationGroupProfiles, oldTutorId);
        }
    }

    /**
     * Handles the old coordinator functionary profile changes.
     * Determines the appropriate action (keep, update role, or delete) based on current usage.
     */
    private void handleOldCoordinatorChanges(List<ResearchSeedbedProfile> researchSeedbedProfiles,
                                              List<InvestigationGroupProfile> investigationGroupProfiles,
                                              Long oldCoordinatorId) {

        boolean isOldCoordinatorInInvestigationGroups = investigationGroupProfiles.stream()
                .anyMatch(igp -> igp.getCoordinatorId().equals(oldCoordinatorId));
        boolean isOldCoordinatorInOtherSeedbedsAsCoordinator = researchSeedbedProfiles.stream()
                .anyMatch(rsp -> rsp.getCoordinatorId().equals(oldCoordinatorId));
        boolean isOldCoordinatorInOtherSeedbedsAsTutor = researchSeedbedProfiles.stream()
                .anyMatch(rsp -> Objects.equals(rsp.getTutorId(), oldCoordinatorId));

        // If still coordinator of an investigation group, keep the role (do nothing)
        if (isOldCoordinatorInInvestigationGroups) {
            return;
        }

        // No longer in investigation groups, check seedbed usage
        if (isOldCoordinatorInOtherSeedbedsAsCoordinator) {
            updateFunctionaryRole(oldCoordinatorId, SeedbedRole.COORDINADOR_DE_SEMILLERO);
        } else if (isOldCoordinatorInOtherSeedbedsAsTutor) {
            updateFunctionaryRole(oldCoordinatorId, SeedbedRole.TUTOR_DE_SEMILLERO);
        } else {
            // Not used anywhere, delete the functionary profile
            functionaryProfileServicePort.deleteById(oldCoordinatorId);
        }
    }

    /**
     * Handles the old tutor functionary profile changes.
     * Determines the appropriate action (keep, update role, or delete) based on current usage.
     */
    private void handleOldTutorChanges(List<ResearchSeedbedProfile> researchSeedbedProfiles,
                                        List<InvestigationGroupProfile> investigationGroupProfiles,
                                        Long oldTutorId) {

        boolean isOldTutorInInvestigationGroups = investigationGroupProfiles.stream()
                .anyMatch(igp -> igp.getCoordinatorId().equals(oldTutorId));
        boolean isOldTutorInOtherSeedbedsAsCoordinator = researchSeedbedProfiles.stream()
                .anyMatch(rsp -> rsp.getCoordinatorId().equals(oldTutorId));
        boolean isOldTutorInOtherSeedbedsAsTutor = researchSeedbedProfiles.stream()
                .anyMatch(rsp -> Objects.equals(rsp.getTutorId(), oldTutorId));

        // If coordinator of an investigation group, update role to investigation group coordinator
        if (isOldTutorInInvestigationGroups) {
            updateFunctionaryRole(oldTutorId, SeedbedRole.COORDINADOR_DE_GRUPO_DE_INVESTIGACION);
            return;
        }

        // Not in investigation groups, check seedbed usage
        if (isOldTutorInOtherSeedbedsAsCoordinator) {
            updateFunctionaryRole(oldTutorId, SeedbedRole.COORDINADOR_DE_SEMILLERO);
        } else if (isOldTutorInOtherSeedbedsAsTutor) {
            updateFunctionaryRole(oldTutorId, SeedbedRole.TUTOR_DE_SEMILLERO);
        } else {
            // Not used anywhere, delete the functionary profile
            functionaryProfileServicePort.deleteById(oldTutorId);
        }
    }

    /**
     * Updates the role of a functionary profile.
     */
    private void updateFunctionaryRole(Long functionaryId, SeedbedRole roleName) {
        FunctionaryProfile profile = functionaryProfileServicePort.findById(functionaryId);
        Role role = roleServicePort.findByName(roleName);
        profile.setRoleId(role.getId());
        functionaryProfileServicePort.update(profile.getId(), profile);
    }
}
