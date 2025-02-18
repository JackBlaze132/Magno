package com.unibague.magno.application.mapper.request;

import com.unibague.magno.application.dto.request.FunctionaryProfileRequest;
import com.unibague.magno.domain.model.FunctionaryProfile;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

public interface FunctionaryProfileRequestMapper {
    FunctionaryProfile toFunctionaryProfile(FunctionaryProfileRequest functionaryProfileRequest);
}
