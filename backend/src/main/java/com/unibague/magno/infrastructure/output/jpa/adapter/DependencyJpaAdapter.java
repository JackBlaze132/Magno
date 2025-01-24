package com.unibague.magno.infrastructure.output.jpa.adapter;

import com.unibague.magno.domain.model.Dependency;
import com.unibague.magno.domain.spi.IDependencyPersistencePort;
import com.unibague.magno.infrastructure.output.jpa.entity.DependencyEntity;
import com.unibague.magno.infrastructure.output.jpa.mapper.DependencyEntityMapper;
import com.unibague.magno.infrastructure.output.jpa.repository.IDependencyRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DependencyJpaAdapter implements IDependencyPersistencePort {

    private final IDependencyRepository dependencyRepository;
    private final DependencyEntityMapper dependencyEntityMapper;

    @Override
    public Optional<Dependency> findById(Long id) {
        Optional<DependencyEntity> dependency = dependencyRepository.findById(id);
        return dependency.map(dependencyEntityMapper::toDependency);
    }

    @Override
    public Dependency save(Dependency dependency) {
        DependencyEntity dependencyEntity = dependencyEntityMapper.toDependencyEntity(dependency);
        DependencyEntity savedDependencyEntity = dependencyRepository.save(dependencyEntity);
        return dependencyEntityMapper.toDependency(savedDependencyEntity);
    }

    @Override
    public Dependency update(Long id, Dependency dependency) {
        DependencyEntity dependencyEntity = dependencyEntityMapper.toDependencyEntity(id, dependency);
        DependencyEntity updatedDependencyEntity = dependencyRepository.save(dependencyEntity);
        return dependencyEntityMapper.toDependency(updatedDependencyEntity);
    }

    @Override
    public void deleteById(Long id) {
        dependencyRepository.deleteById(id);
    }

    @Override
    public List<Dependency> findAll() {
        return dependencyEntityMapper.toDependencyList(dependencyRepository.findAll());
    }
}
