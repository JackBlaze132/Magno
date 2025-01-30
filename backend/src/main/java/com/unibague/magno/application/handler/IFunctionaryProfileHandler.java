package com.unibague.magno.application.handler;

import com.unibague.magno.application.dto.request.FunctionaryProfileRequest;
import com.unibague.magno.application.dto.response.FunctionaryProfileResponse;

import java.util.List;

public interface IFunctionaryProfileHandler {
    FunctionaryProfileResponse findById(Long id);
    FunctionaryProfileResponse save(FunctionaryProfileRequest functionaryProfile);
    FunctionaryProfileResponse updateById(Long id, FunctionaryProfileRequest functionaryProfile);
    void deleteById(Long id);
    List<FunctionaryProfileResponse> findAll();
}
