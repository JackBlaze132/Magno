package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.ErrorLogResponse;
import com.unibague.magno.domain.model.ErrorLog;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper interface for converting error log domain models to response DTOs.
 * Auto-implemented by MapStruct.
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ErrorLogResponseMapper {

    ErrorLogResponse toResponse(ErrorLog errorLog);

    List<ErrorLogResponse> toResponseList(List<ErrorLog> errorLogs);
}
