package com.unibague.magno.application.mapper.request;

import com.unibague.magno.application.dto.request.FunctionaryProfileRequest;
import com.unibague.magno.domain.model.FunctionaryProfile;

/**
 * Mapper interface for converting functionary profile request DTOs to domain models.
 * Manually implemented to fetch the functionary's dependency from Integra system.
 */
public interface FunctionaryProfileRequestMapper {
    FunctionaryProfile toFunctionaryProfile(FunctionaryProfileRequest functionaryProfileRequest);
}
