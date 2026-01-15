package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.Role;
import com.unibague.magno.domain.model.enums.SeedbedRole;

import java.util.List;
import java.util.Set;

/**
 * Service port interface that defines the contract for role management operations.
 * <p>
 * This interface provides methods for CRUD operations and queries related to roles,
 * which define permissions and responsibilities for users within research seedbeds
 * (e.g., coordinator, member, leader, etc.).
 * </p>
 *
 * @see Role
 * @see SeedbedRole
 */
public interface IRoleServicePort {
    
    /**
     * Retrieves a role by its unique identifier.
     *
     * @param id the unique identifier of the role
     * @return the role with the specified ID
     */
    Role findById(Long id);
    
    /**
     * Retrieves a role by its enum name.
     *
     * @param name the seedbed role enum value
     * @return the role with the specified name
     */
    Role findByName(SeedbedRole name);
    
    /**
     * Persists a new role.
     *
     * @param role the role to save
     * @return the saved role
     */
    Role save(Role role);
    
    /**
     * Updates an existing role.
     *
     * @param id the unique identifier of the role to update
     * @param role the role data to update
     * @return the updated role
     */
    Role update(Long id, Role role);
    
    /**
     * Deletes a role by its unique identifier.
     *
     * @param id the unique identifier of the role to delete
     */
    void deleteById(Long id);
    
    /**
     * Retrieves all roles in the system.
     *
     * @return a list of all roles
     */
    List<Role> findAll();
    
    /**
     * Retrieves multiple roles by their unique identifiers.
     *
     * @param ids a set of role identifiers
     * @return a set of roles matching the provided IDs
     */
    Set<Role> findRolesByIds(Set<Long> ids);

    /**
     * Retrieves all roles assigned to a specific user.
     * <p>
     * This method returns all roles that a user has across different profiles
     * and research seedbed participations.
     * </p>
     *
     * @param userId the unique identifier of the user
     * @return a list of roles assigned to the specified user
     */
    List<Role> findAllRolesByUserId(Long userId);
}
