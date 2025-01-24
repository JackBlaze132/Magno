package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.Dependency;

import java.util.List;

public interface IDependencyServicePort {
    Dependency findById(Long id);
    Dependency save(Dependency dependency);
    Dependency update(Long id, Dependency dependency);
    void deleteById(Long id);
    List<Dependency> findAll();
}
