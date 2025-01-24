package com.unibague.magno.infrastructure.output.jpa.adapter;

import com.unibague.magno.domain.exception.RoleNotFoundException;
import com.unibague.magno.domain.model.Role;
import com.unibague.magno.domain.spi.IRolePersistencePort;
import com.unibague.magno.infrastructure.output.jpa.entity.RoleEntity;
import com.unibague.magno.infrastructure.output.jpa.mapper.RoleEntityMapper;
import com.unibague.magno.infrastructure.output.jpa.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class RoleJpaAdapter implements IRolePersistencePort {

    private final IRoleRepository roleRepository;
    private final RoleEntityMapper roleEntityMapper;

    @Override
    public Optional<Role> findById(Long id) {
        Optional<RoleEntity> role = roleRepository.findById(id);
        return role.map(roleEntityMapper::toRole);
    }

    @Override
    public Role save(Role role) {
        RoleEntity roleEntity = roleEntityMapper.toRoleEntity(role);
        RoleEntity savedRoleEntity = roleRepository.save(roleEntity);
        return roleEntityMapper.toRole(savedRoleEntity);
    }

    @Override
    public Role update(Long id, Role role) {
        if(roleRepository.existsById(id)) {
            RoleEntity roleEntity = roleEntityMapper.toRoleEntity(id, role);
            RoleEntity updatedRoleEntity = roleRepository.save(roleEntity);
            return roleEntityMapper.toRole(updatedRoleEntity);
        }
        else{
            throw new RoleNotFoundException(
                    String.format("Role with ID %d could not be updated", role.getId()));
        }
    }

    @Override
    public void deleteById(Long id) {
        if (roleRepository.existsById(id)) {
            roleRepository.deleteById(id);
        } else {
            throw new RoleNotFoundException(
                    String.format("Role with ID %d could not be deleted", id));
        }
    }

    @Override
    public List<Role> findAll() {
        List<RoleEntity> roleEntities = roleRepository.findAll();
        return roleEntityMapper.toRoleList(roleEntities);
    }
}
