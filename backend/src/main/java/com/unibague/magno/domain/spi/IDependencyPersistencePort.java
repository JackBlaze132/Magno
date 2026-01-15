package com.unibague.magno.domain.spi;

import com.unibague.magno.domain.model.Dependency;

import java.util.List;
import java.util.Optional;

/**
 * Persistence port for managing dependency data.
 * <p>
 * This interface defines the contract for persisting and retrieving dependencies.
 * Dependencies represent organizational units within the university (e.g., faculties,
 * departments) to which investigation groups and functionaries belong.
 * </p>
 */
public interface IDependencyPersistencePort {
    Optional<Dependency> findById(Long id);
    Dependency save(Dependency dependency);
    Dependency update(Long id, Dependency dependency);
    void deleteById(Long id);
    List<Dependency> findAll();

    /**
     * Finds a dependency by its name.
     *
     * @param name the name of the dependency
     * @return an {@link Optional} containing the dependency if found, or empty otherwise
     */
    Optional<Dependency> findByName(String name);
}
