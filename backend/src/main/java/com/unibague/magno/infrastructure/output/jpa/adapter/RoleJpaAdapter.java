package com.unibague.magno.infrastructure.output.jpa.adapter;

import com.unibague.magno.domain.model.Role;
import com.unibague.magno.domain.model.enums.SeedbedRole;
import com.unibague.magno.domain.spi.IRolePersistencePort;
import com.unibague.magno.infrastructure.output.jpa.entity.RoleEntity;
import com.unibague.magno.infrastructure.output.jpa.mapper.RoleEntityMapper;
import com.unibague.magno.infrastructure.output.jpa.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * JPA implementation of {@link IRolePersistencePort} for managing role persistence.
 * Handles database operations for seedbed roles using Spring Data JPA.
 */
@RequiredArgsConstructor
@Transactional
public class RoleJpaAdapter implements IRolePersistencePort {

    private final IRoleRepository roleRepository;
    private final RoleEntityMapper roleEntityMapper;

    @Override
    public Optional<Role> findById(Long id) {
        Optional<RoleEntity> role = roleRepository.findById(id);
        return role.map(roleEntityMapper::toRole);
    }

    @Override
    public Optional<Role> findByName(SeedbedRole name) {
        Optional<RoleEntity> role = roleRepository.findByName(name);
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
        RoleEntity roleEntity = roleEntityMapper.toRoleEntity(id, role);
        RoleEntity updatedRoleEntity = roleRepository.save(roleEntity);
        return roleEntityMapper.toRole(updatedRoleEntity);
    }

    @Override
    public void deleteById(Long id) {
        roleRepository.deleteById(id);
    }

    @Override
    public List<Role> findAll() {
        List<RoleEntity> roleEntities = roleRepository.findAll();
        return roleEntityMapper.toRoleList(roleEntities);
    }

    @Override
    public Set<Role> findRolesByIds(Set<Long> ids) {
        return new HashSet<>(roleEntityMapper.toRoleList(roleRepository.findAllById(ids)));
    }

    @Override
    public List<Role> findAllRolesByUserId(Long userId) {
        return roleEntityMapper.toRoleList(roleRepository.findAllRolesByUserId(userId));
    }
}
