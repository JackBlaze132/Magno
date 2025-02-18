package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.DependencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDependencyRepository extends JpaRepository<DependencyEntity, Long> {
    Optional<DependencyEntity> findByName(String name);
}
