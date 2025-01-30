package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.Role;
import com.unibague.magno.infrastructure.output.jpa.entity.RoleEntity;
import com.unibague.magno.infrastructure.output.jpa.entity.UserEntity;
import com.unibague.magno.infrastructure.output.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class RoleEntityMapperImpl implements RoleEntityMapper {

    private final IUserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public Role toRole(RoleEntity roleEntity) {
        if (roleEntity == null) {
            return null;
        }

        Role role = new Role();

        role.setId(roleEntity.getId());
        role.setName(roleEntity.getName());

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
            roleEntity.setUsers(getUserEntities(id));
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
        roleEntity.setUsers(Collections.emptySet());

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

    private Set<UserEntity> getUserEntities(Long roleId) {
        return new HashSet<>(userRepository.findByRolesId(roleId));
    }
}