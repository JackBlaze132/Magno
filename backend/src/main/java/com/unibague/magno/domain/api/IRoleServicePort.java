package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.Role;

import java.util.List;

public interface IRoleServicePort {
    Role findById(Long id);
    Role save(Role role);
    Role update(Long id, Role role);
    void deleteById(Long id);
    List<Role> findAll();
}
