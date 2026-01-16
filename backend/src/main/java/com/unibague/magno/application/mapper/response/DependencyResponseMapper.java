package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.DependencyResponse;
import com.unibague.magno.domain.model.Dependency;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper interface for converting dependency domain models to response DTOs.
 * Auto-implemented by MapStruct.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DependencyResponseMapper {
    DependencyResponse toResponse(Dependency dependency);
    List<DependencyResponse> toResponseList(List<Dependency> dependencies);
}
