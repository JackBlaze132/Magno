package com.unibague.magno.application.mapper.request;

import com.unibague.magno.application.dto.request.DependencyRequest;
import com.unibague.magno.domain.model.Dependency;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper interface for converting dependency request DTOs to domain models.
 * Auto-implemented by MapStruct.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DependencyRequestMapper {
    Dependency toDependency(DependencyRequest dependency);
}
