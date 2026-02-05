package com.unibague.magno.domain.usecase.helper;

import com.unibague.magno.domain.api.IAcademicPeriodServicePort;
import com.unibague.magno.domain.api.IDependencyServicePort;
import com.unibague.magno.domain.api.IFunctionaryProfileServicePort;
import com.unibague.magno.domain.api.IRoleServicePort;
import com.unibague.magno.domain.model.*;
import com.unibague.magno.domain.model.enums.SeedbedRole;
import com.unibague.magno.domain.model.util.SystemConstants;

import java.util.List;

/**
 * Implementation of {@link IUserHelper}.
 * <p>
 * Provides auxiliary operations for managing DIRI (administrative) users,
 * including creating and removing their special functionary profiles.
 * </p>
 *
 * @see IUserHelper
 */
public class UserHelper implements IUserHelper {

    private final IFunctionaryProfileServicePort functionaryProfileServicePort;
    private final IAcademicPeriodServicePort academicPeriodServicePort;
    private final IRoleServicePort roleServicePort;
    private final IDependencyServicePort dependencyServicePort;

    public UserHelper(IFunctionaryProfileServicePort functionaryProfileServicePort,
                      IAcademicPeriodServicePort academicPeriodServicePort,
                      IRoleServicePort roleServicePort,
                      IDependencyServicePort dependencyServicePort) {
        this.functionaryProfileServicePort = functionaryProfileServicePort;
        this.academicPeriodServicePort = academicPeriodServicePort;
        this.roleServicePort = roleServicePort;
        this.dependencyServicePort = dependencyServicePort;
    }

    @Override
    public void addDiriUser(String diriIdentification, Long diriUserId) {
        AcademicPeriod apForDiriUsers = academicPeriodServicePort.findByName(SystemConstants.ADMIN_REGISTRATION_ACADEMIC_PERIOD_NAME);
        Role diriRole = roleServicePort.findByName(SeedbedRole.DIRI);
        Dependency dependency = dependencyServicePort.findByName(SystemConstants.DIRI_DEPENDENCY_NAME);
        FunctionaryProfile functionaryProfile = new FunctionaryProfile(
                null,
                diriUserId,
                apForDiriUsers.getId(),
                dependency.getId(),
                diriRole.getId());
        // Use saveIgnoringPeriodVisibility because DIRI users are created in a special
        // academic period that may not be visible
        functionaryProfileServicePort.saveIgnoringPeriodVisibility(functionaryProfile);
    }

    @Override
    public void deleteDiriUser(String diriIdentification, Long diriUserId) {
        Role diriRole = roleServicePort.findByName(SeedbedRole.DIRI);
        List<FunctionaryProfile> profilesOfDiriFunctionary =
                functionaryProfileServicePort.findAllProfilesByUserId(diriUserId).stream()
                        .filter(profile -> profile.getRoleId().equals(diriRole.getId()))
                        .toList();
        for (FunctionaryProfile profile : profilesOfDiriFunctionary) {
            functionaryProfileServicePort.deleteById(profile.getId());
        }
    }
}
