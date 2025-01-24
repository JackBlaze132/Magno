package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.DependencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDependencyRepository extends JpaRepository<DependencyEntity, Long> {
}
