package com.unibague.magno.application.handler.impl;

import com.unibague.magno.application.dto.request.FunctionaryProfileRequest;
import com.unibague.magno.application.dto.response.FunctionaryProfileResponse;
import com.unibague.magno.application.handler.interfaces.IFunctionaryProfileHandler;
import com.unibague.magno.application.mapper.request.FunctionaryProfileRequestMapper;
import com.unibague.magno.application.mapper.response.FunctionaryProfileResponseMapper;
import com.unibague.magno.domain.api.IFunctionaryProfileServicePort;
import com.unibague.magno.domain.model.FunctionaryProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation of {@link IFunctionaryProfileHandler}.
 */
@Service
@RequiredArgsConstructor
public class FunctionaryProfileHandler implements IFunctionaryProfileHandler {

    private final IFunctionaryProfileServicePort functionaryProfileServicePort;
    private final FunctionaryProfileRequestMapper functionaryProfileRequestMapper;
    private final FunctionaryProfileResponseMapper functionaryProfileResponseMapper;

    @Override
    public FunctionaryProfileResponse findById(Long id) {
        FunctionaryProfile functionaryProfile = functionaryProfileServicePort.findById(id);
        return functionaryProfileResponseMapper.toResponse(functionaryProfile);
    }

    @Override
    public FunctionaryProfileResponse save(FunctionaryProfileRequest functionaryProfile) {
        return functionaryProfileResponseMapper.toResponse(functionaryProfileServicePort
                .save(functionaryProfileRequestMapper.toFunctionaryProfile(functionaryProfile)));
    }

    @Override
    public FunctionaryProfileResponse updateById(Long id, FunctionaryProfileRequest functionaryProfile) {
        return functionaryProfileResponseMapper.toResponse(functionaryProfileServicePort
                .update(id, functionaryProfileRequestMapper.toFunctionaryProfile(functionaryProfile)));
    }

    @Override
    public void deleteById(Long id) {
        functionaryProfileServicePort.deleteById(id);
    }

    @Override
    public List<FunctionaryProfileResponse> findAll() {
        return functionaryProfileResponseMapper.toResponseList(functionaryProfileServicePort.findAll());
    }

    @Override
    public List<FunctionaryProfileResponse> findAllProfilesByUserId(Long userId) {
        return functionaryProfileResponseMapper.toResponseList(
                functionaryProfileServicePort.findAllProfilesByUserId(userId));
    }

    @Override
    public List<FunctionaryProfileResponse> findAllProfilesByAcademicPeriodId(Long academicPeriodId) {
        return functionaryProfileResponseMapper.toResponseList(
                functionaryProfileServicePort.findAllProfilesByAcademicPeriodId(academicPeriodId));
    }
}
