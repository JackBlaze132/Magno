package com.unibague.magno.domain.spi;

import com.unibague.magno.domain.model.Role;

import java.util.List;
import java.util.Optional;

public interface IRolePersistencePort {
    Optional<Role> findById(Long id);
    Role save(Role role);
    Role update(Long id, Role role);
    void deleteById(Long id);
    List<Role> findAll();
}
