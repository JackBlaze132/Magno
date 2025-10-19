package com.unibague.magno.domain.usecase.helper;

import com.unibague.magno.domain.api.IDependencyServicePort;
import com.unibague.magno.domain.api.IFunctionaryProfileServicePort;
import com.unibague.magno.domain.api.IRoleServicePort;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.model.*;
import com.unibague.magno.domain.model.enums.SeedbedRole;
import com.unibague.magno.domain.model.integra.IntegraFunctionary;

import java.util.List;
import java.util.Optional;

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

        // Retrieve all functionary profiles for the coordinator user
        List<FunctionaryProfile> coordinatorProfiles =
                functionaryProfileServicePort.findAllProfilesByUserId(igp.getCoordinatorId());

        // Search for a profile matching the academic period
        Optional<FunctionaryProfile> matchingProfile = coordinatorProfiles.stream()
                .filter(profile -> profile.getAcademicPeriodId().equals(igp.getAcademicPeriodId()))
                .findFirst();

        // If a profile exists in the academic period, verify and update the role if needed
        if (matchingProfile.isPresent()) {
            FunctionaryProfile profile = matchingProfile.get();

            // Verify if the profile has the correct role
            ensureCorrectRole(profile);

            // Assign the functionary profile ID to the investigation group
            igp.setCoordinatorId(profile.getId());
            return igp;
        }

        // If no profile exists, create a new functionary profile
        return createFunctionaryProfileForInvestigationGroupProfile(igp);
    }

    /**
     * Ensures the functionary profile has the correct role (COORDINADOR_DE_GRUPO_DE_INVESTIGACION).
     * If the role is different, updates it to the correct one.
     *
     * @param profile The functionary profile to verify
     */
    private void ensureCorrectRole(FunctionaryProfile profile) {

        Long roleId = profile.getRoleId();
        Role currentRole = roleServicePort.findById(roleId);

        // Update role if it doesn't match the required coordinator role
        if (!currentRole.getName().equals(SeedbedRole.COORDINADOR_DE_GRUPO_DE_INVESTIGACION)) {
            Role coordinatorRole = roleServicePort.findByName(SeedbedRole.COORDINADOR_DE_GRUPO_DE_INVESTIGACION);
            profile.setRoleId(coordinatorRole.getId());
            functionaryProfileServicePort.update(profile.getId(), profile);
        }
    }

    /**
     * Creates a new FunctionaryProfile for the given InvestigationGroupProfile.
     * @param igp The InvestigationGroupProfile for which to create the FunctionaryProfile
     * @return The updated InvestigationGroupProfile with the new FunctionaryProfile ID
     */
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
