package com.unibague.magno.domain.usecase.helper;

import com.unibague.magno.domain.api.IDependencyServicePort;
import com.unibague.magno.domain.api.IFunctionaryProfileServicePort;
import com.unibague.magno.domain.api.IRoleServicePort;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.model.Dependency;
import com.unibague.magno.domain.model.FunctionaryProfile;
import com.unibague.magno.domain.model.ResearchSeedbedProfile;
import com.unibague.magno.domain.model.User;
import com.unibague.magno.domain.model.enums.SeedbedRole;
import com.unibague.magno.domain.model.integra.IntegraFunctionary;

import java.util.Optional;

public class ResearchSeedbedProfileHelper implements IResearchSeedbedProfileHelper {

    private final IIntegraServicePort integraServicePort;
    private final IUserServicePort userServicePort;
    private final IFunctionaryProfileServicePort functionaryProfileServicePort;
    private final IDependencyServicePort dependencyServicePort;
    private final IRoleServicePort roleServicePort;

    public ResearchSeedbedProfileHelper(IIntegraServicePort integraServicePort, IUserServicePort userServicePort,
                                        IFunctionaryProfileServicePort functionaryProfileServicePort,
                                        IDependencyServicePort dependencyServicePort,
                                        IRoleServicePort roleServicePort) {
        this.integraServicePort = integraServicePort;
        this.userServicePort = userServicePort;
        this.functionaryProfileServicePort = functionaryProfileServicePort;
        this.dependencyServicePort = dependencyServicePort;
        this.roleServicePort = roleServicePort;
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

        if (tutorId != null && !tutorHasProfile) {
            createAndAssignProfile(rsp, tutorId, SeedbedRole.TUTOR_DE_SEMILLERO, false);
        }

        return rsp;
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
}
