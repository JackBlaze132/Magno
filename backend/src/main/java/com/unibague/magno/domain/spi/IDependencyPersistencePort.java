package com.unibague.magno.domain.spi;

import com.unibague.magno.domain.model.Dependency;

import java.util.List;
import java.util.Optional;

public interface IDependencyPersistencePort {
    Optional<Dependency> findById(Long id);
    Dependency save(Dependency dependency);
    Dependency update(Long id, Dependency dependency);
    void deleteById(Long id);
    List<Dependency> findAll();
    Optional<Dependency> findByName(String name);
}
