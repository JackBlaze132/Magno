package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.Role;
import com.unibague.magno.domain.model.User;
import com.unibague.magno.infrastructure.output.jpa.entity.RoleEntity;
import com.unibague.magno.infrastructure.output.jpa.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class RoleEntityMapperImpl implements RoleEntityMapper {

    @Override
    public Role toRole(RoleEntity roleEntity) {
        if (roleEntity == null) {
            return null;
        }

        Role role = new Role();

        role.setId(roleEntity.getId());
        role.setName(roleEntity.getName());
        Set<Long> userIds = roleEntity.getUsers().stream()
                .map(UserEntity::getId)
                .collect(Collectors.toSet());
        role.setUserIds(userIds);

        return role;
    }

    @Override
    public RoleEntity toRoleEntity(Long id, Role role) {
        if (id == null && role == null) {
            return null;
        }

        RoleEntity roleEntity = new RoleEntity();

        if (role != null) {
            roleEntity.setName(role.getName());
            roleEntity.setUsers(getUserEntities(role));
        }

        roleEntity.setId(id);

        return roleEntity;
    }

    @Override
    public RoleEntity toRoleEntity(Role role) {
        if (role == null) {
            return null;
        }

        RoleEntity roleEntity = new RoleEntity();

        roleEntity.setId(role.getId());
        roleEntity.setName(role.getName());
        roleEntity.setUsers(getUserEntities(role));

        return roleEntity;
    }

    @Override
    public List<Role> toRoleList(List<RoleEntity> roleEntities) {
        if (roleEntities == null) {
            return null;
        }

        List<Role> list = new ArrayList<>(roleEntities.size());
        for (RoleEntity roleEntity : roleEntities) {
            list.add(toRole(roleEntity));
        }

        return list;
    }

    private Set<UserEntity> getUserEntities(Role role) {
        return Optional.ofNullable(role.getUserIds())
                .orElse(Collections.emptySet())
                .stream()
                .map(userId -> {
                    UserEntity userEntity = new UserEntity();
                    userEntity.setId(userId);
                    return userEntity;
                })
                .collect(Collectors.toSet());
    }
}