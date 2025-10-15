package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.*;
import com.unibague.magno.domain.api.IAcademicPeriodServicePort;
import com.unibague.magno.domain.api.IDependencyServicePort;
import com.unibague.magno.domain.api.IRoleServicePort;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.model.FunctionaryProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FunctionaryProfileResponseMapperImpl implements FunctionaryProfileResponseMapper{

    private final IUserServicePort userServicePort;
    private final UserResponseMapper userResponseMapper;

    private final IAcademicPeriodServicePort academicPeriodServicePort;
    private final AcademicPeriodResponseMapper academicPeriodResponseMapper;

    private final IDependencyServicePort dependencyServicePort;
    private final DependencyResponseMapper dependencyResponseMapper;

    private final IRoleServicePort roleServicePort;
    private final RoleResponseMapper roleResponseMapper;

    @Override
    public FunctionaryProfileResponse toResponse(FunctionaryProfile functionaryProfile) {

        Long userId = functionaryProfile.getUserId();
        UserResponse userResponse = userResponseMapper.toResponse(userServicePort
                .findById(userId));

        Long academicPeriodId = functionaryProfile.getAcademicPeriodId();
        AcademicPeriodResponse academicPeriodResponse = academicPeriodResponseMapper
                .toResponse(academicPeriodServicePort
                .findById(academicPeriodId));

        Long dependencyId = functionaryProfile.getDependencyId();
        DependencyResponse dependencyResponse = dependencyResponseMapper
                .toResponse(dependencyServicePort
                .findById(dependencyId));

        Long roleId = functionaryProfile.getRoleId();
        RoleResponse roleResponse = roleResponseMapper
                .toResponse(roleServicePort
                .findById(roleId));

        return FunctionaryProfileResponse.builder()
                .id(functionaryProfile.getId())
                .user(userResponse)
                .academicPeriod(academicPeriodResponse)
                .dependency(dependencyResponse)
                .role(roleResponse)
                .build();
    }

    @Override
    public List<FunctionaryProfileResponse> toResponseList(List<FunctionaryProfile> functionaryProfiles) {
        return functionaryProfiles.stream()
                .map(this::toResponse)
                .toList();
    }
}
