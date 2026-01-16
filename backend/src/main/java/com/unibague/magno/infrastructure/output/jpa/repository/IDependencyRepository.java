package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.DependencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data JPA repository for {@link DependencyEntity}.
 */
public interface IDependencyRepository extends JpaRepository<DependencyEntity, Long> {

    /**
     * Finds a dependency by its name.
     */
    Optional<DependencyEntity> findByName(String name);
}
