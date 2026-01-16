package com.unibague.magno.application.mapper.request.impl;

import com.unibague.magno.application.dto.request.FunctionaryProfileRequest;
import com.unibague.magno.application.mapper.request.FunctionaryProfileRequestMapper;
import com.unibague.magno.domain.api.IDependencyServicePort;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.model.Dependency;
import com.unibague.magno.domain.model.FunctionaryProfile;
import com.unibague.magno.domain.model.User;
import com.unibague.magno.domain.model.integra.IntegraFunctionary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link FunctionaryProfileRequestMapper}.
 * Fetches the functionary's dependency from Integra based on their identification.
 */
@Component
@RequiredArgsConstructor
public class FunctionaryProfileRequestMapperImpl implements FunctionaryProfileRequestMapper {

    private final IIntegraServicePort integraServicePort;
    private final IUserServicePort userServicePort;
    private final IDependencyServicePort dependencyServicePort;

    @Override
    public FunctionaryProfile toFunctionaryProfile(FunctionaryProfileRequest functionaryProfileRequest) {

        if ( functionaryProfileRequest == null ) {
            return null;
        }

        FunctionaryProfile functionaryProfile = new FunctionaryProfile();
        functionaryProfile.setUserId(functionaryProfileRequest.getUserId());
        functionaryProfile.setAcademicPeriodId(functionaryProfileRequest.getAcademicPeriodId());

        User user = userServicePort.findById(functionaryProfileRequest.getUserId());
        IntegraFunctionary integraFunctionary = integraServicePort.
                getIntegraFunctionaryByIdentification(user.getIdentificationNumber());
        Dependency dependency = dependencyServicePort.findByName(integraFunctionary.getProgram());

        functionaryProfile.setDependencyId(dependency.getId());
        functionaryProfile.setRoleId(functionaryProfileRequest.getRoleId());
        return functionaryProfile;
    }
}
