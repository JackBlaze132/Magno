package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.Dependency;

import java.util.List;
import java.util.Optional;

/**
 * Service port interface that defines the contract for dependency management operations.
 * <p>
 * This interface provides methods for CRUD operations and queries related to institutional
 * dependencies (departments, faculties, administrative units, etc.).
 * </p>
 *
 * @see Dependency
 */
public interface IDependencyServicePort {
    
    /**
     * Retrieves a dependency by its unique identifier.
     *
     * @param id the unique identifier of the dependency
     * @return the dependency with the specified ID
     */
    Dependency findById(Long id);
    
    /**
     * Persists a new dependency.
     *
     * @param dependency the dependency to save
     * @return the saved dependency
     */
    Dependency save(Dependency dependency);
    
    /**
     * Updates an existing dependency.
     *
     * @param id the unique identifier of the dependency to update
     * @param dependency the dependency data to update
     * @return the updated dependency
     */
    Dependency update(Long id, Dependency dependency);
    
    /**
     * Deletes a dependency by its unique identifier.
     *
     * @param id the unique identifier of the dependency to delete
     */
    void deleteById(Long id);
    
    /**
     * Retrieves all dependencies in the system.
     *
     * @return a list of all dependencies
     */
    List<Dependency> findAll();
    
    /**
     * Retrieves a dependency by its name.
     *
     * @param name the name of the dependency
     * @return the dependency with the specified name
     */
    Dependency findByName(String name);

    /**
     * Retrieves a dependency by its name as an Optional.
     * <p>
     * This method is useful when the dependency might not exist and you want to
     * handle the absence explicitly.
     * </p>
     *
     * @param name the name of the dependency
     * @return an Optional containing the dependency if found, or empty if not found
     */
    Optional<Dependency> findByNameOptional(String name);
}
