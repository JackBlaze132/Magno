package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IRoleServicePort;
import com.unibague.magno.domain.exception.role.RoleNotFoundException;
import com.unibague.magno.domain.model.Role;
import com.unibague.magno.domain.model.enums.SeedbedRole;
import com.unibague.magno.domain.spi.IRolePersistencePort;

import java.util.List;
import java.util.Set;

/**
 * Use case implementation for managing roles in the Magno system.
 * <p>
 * Handles business logic for role operations including CRUD operations
 * and role assignment validation. Roles define permissions and responsibilities
 * within research seedbeds and investigation groups.
 * </p>
 */
public class RoleUseCase implements IRoleServicePort {

    private final IRolePersistencePort rolePersistencePort;

    public RoleUseCase(IRolePersistencePort rolePersistencePort) {
        this.rolePersistencePort = rolePersistencePort;
    }

    @Override
    public Role findById(Long id) {
        return rolePersistencePort.findById(id)
                .orElseThrow(() -> new RoleNotFoundException(
                        String.format("Rol con ID %d no encontrado", id)));
    }

    @Override
    public Role findByName(SeedbedRole name) {
        return rolePersistencePort.findByName(name)
                .orElseThrow(() -> new RoleNotFoundException(
                        String.format("Rol con nombre %s no encontrado", name)));
    }


    @Override
    public Role save(Role role) {
        return rolePersistencePort.save(role);
    }

    @Override
    public Role update(Long id, Role role) {
        if (rolePersistencePort.findById(id).isEmpty()) {
            throw new RoleNotFoundException(
                    String.format("No se pudo actualizar el rol con ID %d porque no existe", id));
        }
        return rolePersistencePort.update(id, role);
    }

    @Override
    public void deleteById(Long id) {
        if (rolePersistencePort.findById(id).isEmpty()) {
            throw new RoleNotFoundException(
                    String.format("No se pudo eliminar el rol con ID %d porque no existe", id));
        }
        rolePersistencePort.deleteById(id);
    }

    @Override
    public List<Role> findAll() {
        return rolePersistencePort.findAll();
    }

    @Override
    public Set<Role> findRolesByIds(Set<Long> ids) {
        return rolePersistencePort.findRolesByIds(ids);
    }

    @Override
    public List<Role> findAllRolesByUserId(Long userId) {
        return rolePersistencePort.findAllRolesByUserId(userId);
    }
}
