package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.Role;
import com.unibague.magno.infrastructure.output.jpa.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleEntityMapper {

    Role toRole(RoleEntity roleEntity);

    @Mapping(source = "id", target = "id")
    RoleEntity toRoleEntity(Long id, Role role);

    RoleEntity toRoleEntity(Role role);
    List<Role> toRoleList(List<RoleEntity> roleEntities);
}
