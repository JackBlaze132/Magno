package com.unibague.magno.domain.spi;

import com.unibague.magno.domain.model.Role;
import com.unibague.magno.domain.model.enums.SeedbedRole;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IRolePersistencePort {
    Optional<Role> findById(Long id);
    Optional<Role> findByName(SeedbedRole name);
    Role save(Role role);
    Role update(Long id, Role role);
    void deleteById(Long id);
    List<Role> findAll();
    Set<Role> findRolesByIds(Set<Long> ids);

    List<Role> findAllRolesByUserId(Long userId);
}
