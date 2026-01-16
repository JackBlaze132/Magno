package com.unibague.magno.application.mapper.request;

import com.unibague.magno.application.dto.request.RoleRequest;
import com.unibague.magno.domain.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper interface for converting role request DTOs to domain models.
 * Auto-implemented by MapStruct.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleRequestMapper {
    Role toRole(RoleRequest role);
}
