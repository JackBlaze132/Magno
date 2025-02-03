package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.AcademicPeriodResponse;
import com.unibague.magno.application.dto.response.DependencyResponse;
import com.unibague.magno.application.dto.response.FunctionaryProfileResponse;
import com.unibague.magno.application.dto.response.UserResponse;
import com.unibague.magno.domain.exception.UserNotFoundException;
import com.unibague.magno.domain.model.FunctionaryProfile;
import com.unibague.magno.domain.spi.IAcademicPeriodPersistencePort;
import com.unibague.magno.domain.spi.IDependencyPersistencePort;
import com.unibague.magno.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FunctionaryProfileResponseMapperImpl implements FunctionaryProfileResponseMapper{

    private final IUserPersistencePort userPersistencePort;
    private final UserResponseMapper userResponseMapper;

    private final IAcademicPeriodPersistencePort academicPeriodPersistencePort;
    private final AcademicPeriodResponseMapper academicPeriodResponseMapper;

    private final IDependencyPersistencePort dependencyPersistencePort;
    private final DependencyResponseMapper dependencyResponseMapper;

    @Override
    public FunctionaryProfileResponse toResponse(FunctionaryProfile functionaryProfile) {

        Long userId = functionaryProfile.getUserId();
        UserResponse userResponse = userResponseMapper.toResponse(userPersistencePort
                .findById(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User with id %s not found", userId))));

        Long academicPeriodId = functionaryProfile.getAcademicPeriodId();
        AcademicPeriodResponse academicPeriodResponse = academicPeriodResponseMapper
                .toResponse(academicPeriodPersistencePort
                .findById(academicPeriodId)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("Academic period with id %s not found", academicPeriodId))));

        Long dependencyId = functionaryProfile.getDependencyId();
        DependencyResponse dependencyResponse = dependencyResponseMapper
                .toResponse(dependencyPersistencePort
                .findById(dependencyId)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("Dependency with id %s not found", dependencyId))));

        return FunctionaryProfileResponse.builder()
                .id(functionaryProfile.getId())
                .user(userResponse)
                .academicPeriod(academicPeriodResponse)
                .dependency(dependencyResponse)
                .build();
    }

    @Override
    public List<FunctionaryProfileResponse> toResponseList(List<FunctionaryProfile> functionaryProfiles) {
        return functionaryProfiles.stream()
                .map(this::toResponse)
                .toList();
    }
}
