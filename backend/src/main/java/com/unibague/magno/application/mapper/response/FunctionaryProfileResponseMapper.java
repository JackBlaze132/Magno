package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.FunctionaryProfileResponse;
import com.unibague.magno.domain.model.FunctionaryProfile;

import java.util.List;

/**
 * Mapper interface for converting functionary profile domain models to response DTOs.
 * Manually implemented to resolve nested relationships (user, academic period, dependency, role).
 */
public interface FunctionaryProfileResponseMapper {
    FunctionaryProfileResponse toResponse(FunctionaryProfile functionaryProfile);
    List<FunctionaryProfileResponse> toResponseList(List<FunctionaryProfile> functionaryProfiles);
}
