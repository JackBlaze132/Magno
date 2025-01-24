package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IRoleServicePort;
import com.unibague.magno.domain.exception.RoleNotFoundException;
import com.unibague.magno.domain.model.Role;
import com.unibague.magno.domain.spi.IRolePersistencePort;

import java.util.List;

public class RoleUseCase implements IRoleServicePort {

    private final IRolePersistencePort rolePersistencePort;

    public RoleUseCase(IRolePersistencePort rolePersistencePort) {
        this.rolePersistencePort = rolePersistencePort;
    }

    @Override
    public Role findById(Long id) {
        return rolePersistencePort.findById(id)
                .orElseThrow(RoleNotFoundException::new);
    }

    @Override
    public Role save(Role role) {
        return rolePersistencePort.save(role);
    }

    @Override
    public Role update(Long id, Role role) {
        return rolePersistencePort.update(id, role);
    }

    @Override
    public void deleteById(Long id) {
        rolePersistencePort.deleteById(id);
    }

    @Override
    public List<Role> findAll() {
        return rolePersistencePort.findAll();
    }
}
