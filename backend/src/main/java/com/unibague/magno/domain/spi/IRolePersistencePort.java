package com.unibague.magno.domain.spi;

import com.unibague.magno.domain.model.Role;
import com.unibague.magno.domain.model.enums.SeedbedRole;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Persistence port for managing role data.
 * <p>
 * This interface defines the contract for persisting and retrieving roles.
 * Roles define the permissions and responsibilities of users within research seedbeds
 * and investigation groups (e.g., coordinator, tutor, student member).
 * </p>
 */
public interface IRolePersistencePort {
    Optional<Role> findById(Long id);

    /**
     * Finds a role by its seedbed role enum value.
     *
     * @param name the seedbed role enum value
     * @return an {@link Optional} containing the role if found, or empty otherwise
     */
    Optional<Role> findByName(SeedbedRole name);

    Role save(Role role);
    Role update(Long id, Role role);
    void deleteById(Long id);
    List<Role> findAll();

    /**
     * Retrieves multiple roles by their IDs.
     *
     * @param ids the set of role IDs to retrieve
     * @return a set of roles matching the provided IDs
     */
    Set<Role> findRolesByIds(Set<Long> ids);

    /**
     * Retrieves all roles assigned to a specific user across all their profiles.
     *
     * @param userId the unique identifier of the user
     * @return a list of roles assigned to the user
     */
    List<Role> findAllRolesByUserId(Long userId);
}
