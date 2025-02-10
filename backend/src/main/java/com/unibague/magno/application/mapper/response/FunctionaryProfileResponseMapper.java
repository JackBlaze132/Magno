package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.FunctionaryProfileResponse;
import com.unibague.magno.domain.model.FunctionaryProfile;

import java.util.List;

public interface FunctionaryProfileResponseMapper {
    FunctionaryProfileResponse toResponse(FunctionaryProfile functionaryProfile);
    List<FunctionaryProfileResponse> toResponseList(List<FunctionaryProfile> functionaryProfiles);
}
