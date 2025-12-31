package com.unibague.magno.domain.usecase.helper;

import com.unibague.magno.domain.api.IAcademicPeriodServicePort;
import com.unibague.magno.domain.api.IDependencyServicePort;
import com.unibague.magno.domain.api.IFunctionaryProfileServicePort;
import com.unibague.magno.domain.api.IRoleServicePort;
import com.unibague.magno.domain.model.*;
import com.unibague.magno.domain.model.enums.SeedbedRole;
import com.unibague.magno.domain.model.util.SystemConstants;

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
        // There's no need to validate if the period exists because if not, an exception will be thrown
        // Same for role and dependency
        AcademicPeriod apForDiriUsers = academicPeriodServicePort.findByName(SystemConstants.ADMIN_REGISTRATION_ACADEMIC_PERIOD_NAME);
        Role diriRole = roleServicePort.findByName(SeedbedRole.DIRI);
        Dependency dependency = dependencyServicePort.findByName(SystemConstants.DIRI_DEPENDENCY_NAME);
        FunctionaryProfile functionaryProfile = new FunctionaryProfile(
                null,
                diriUserId,
                apForDiriUsers.getId(),
                dependency.getId(),
                diriRole.getId());
        functionaryProfileServicePort.save(functionaryProfile);
    }
}
