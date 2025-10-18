package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.RoleResponse;
import com.unibague.magno.domain.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleResponseMapper {

    @Mapping(target = "name", expression = "java(role.getFormattedName())")
    RoleResponse toResponse(Role role);

    List<RoleResponse> toResponseList(List<Role> roles);
    Set<RoleResponse> toResponseSet(Set<Role> roles);
}
