package com.unibague.magno.domain.usecase.helper;

import com.unibague.magno.domain.api.IDependencyServicePort;
import com.unibague.magno.domain.api.IFunctionaryProfileServicePort;
import com.unibague.magno.domain.api.IRoleServicePort;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.model.Dependency;
import com.unibague.magno.domain.model.FunctionaryProfile;
import com.unibague.magno.domain.model.InvestigationGroupProfile;
import com.unibague.magno.domain.model.User;
import com.unibague.magno.domain.model.enums.SeedbedRole;
import com.unibague.magno.domain.model.integra.IntegraFunctionary;

import java.util.List;

public class InvestigationGroupProfileHelper implements IInvestigationGroupProfileHelper {

    private final IIntegraServicePort integraServicePort;
    private final IUserServicePort userServicePort;
    private final IFunctionaryProfileServicePort functionaryProfileServicePort;
    private final IDependencyServicePort dependencyServicePort;
    private final IRoleServicePort roleServicePort;

    public InvestigationGroupProfileHelper(IIntegraServicePort integraServicePort, IUserServicePort userServicePort,
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
    public InvestigationGroupProfile verifyUserHasFunctionaryProfile(InvestigationGroupProfile igp) {

        // Verify if the user has a functionary profile in the given academic period
        boolean hasAProfileInTheAp = functionaryProfileServicePort.findAllProfilesByUserId(igp.getCoordinatorId()).stream()
                .anyMatch(profile -> profile.getAcademicPeriodId().equals(igp.getAcademicPeriodId()));

        if (hasAProfileInTheAp) {

            List<FunctionaryProfile> coordinatorProfiles =
                    functionaryProfileServicePort.findAllProfilesByUserId(igp.getCoordinatorId());

            coordinatorProfiles.stream()
                    .filter(profile -> profile.getAcademicPeriodId().equals(igp.getAcademicPeriodId()))
                    .findFirst()
                    .map(FunctionaryProfile::getId)
                    .ifPresent(igp::setCoordinatorId);
            return igp;
        }

        // If not, whe should create one
        return createFunctionaryProfileForInvestigationGroupProfile(igp);
    }

    private InvestigationGroupProfile createFunctionaryProfileForInvestigationGroupProfile(InvestigationGroupProfile igp) {

        FunctionaryProfile functionaryProfile = new FunctionaryProfile();
        functionaryProfile.setUserId(igp.getCoordinatorId());
        functionaryProfile.setAcademicPeriodId(igp.getAcademicPeriodId());

        User user = userServicePort.findById(igp.getCoordinatorId());
        IntegraFunctionary integraFunctionary = integraServicePort.
                getIntegraFunctionaryByIdentification(user.getIdentificationNumber());
        Dependency dependency = dependencyServicePort.findByName(integraFunctionary.getProgram());

        functionaryProfile.setDependencyId(dependency.getId());
        functionaryProfile.setRoleId(roleServicePort.findByName(SeedbedRole.COORDINADOR_DE_GRUPO_DE_INVESTIGACION).getId());

        FunctionaryProfile savedFunctionaryProfile = functionaryProfileServicePort.save(functionaryProfile);
        igp.setCoordinatorId(savedFunctionaryProfile.getId());
        return igp;
    }

}
