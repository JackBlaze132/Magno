package com.unibague.magno.infrastructure.output.jpa.adapter;

import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.model.Dependency;
import com.unibague.magno.domain.spi.IDependencyPersistencePort;
import com.unibague.magno.infrastructure.output.jpa.entity.DependencyEntity;
import com.unibague.magno.infrastructure.output.jpa.mapper.DependencyEntityMapper;
import com.unibague.magno.infrastructure.output.jpa.repository.IDependencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
public class DependencyJpaAdapter implements IDependencyPersistencePort {

    private final IDependencyRepository dependencyRepository;
    private final DependencyEntityMapper dependencyEntityMapper;
    private final IIntegraServicePort integraServicePort;

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

    @Override
    public Optional<Dependency> findByName(String name) {
        return dependencyRepository.findByName(name)
                .map(dependencyEntityMapper::toDependency);
    }

    @Override
    public List<Dependency> saveAllFromIntegra() {
        List<DependencyEntity> existingDependencies = dependencyRepository.findAll();
        Set<String> existingDependenciesNames = extractNames(existingDependencies);
        List<DependencyEntity> newDependencies = fetchDependencies(existingDependenciesNames);
        return saveDependencies(newDependencies);
    }

    private Set<String> extractNames(List<DependencyEntity> existingDependencies) {
        return existingDependencies.stream()
                .map(DependencyEntity::getName)
                .collect(Collectors.toSet());
    }

    private List<DependencyEntity> fetchDependencies(Set<String> existingDependenciesNames) {
        return integraServicePort.getAllDependencies().stream()
                .filter(dependency -> !existingDependenciesNames.contains(dependency.getDepName()))
                .map(dependencyEntityMapper::toDependencyEntity)
                .collect(Collectors.toList());
    }

    private List<Dependency> saveDependencies(List<DependencyEntity> newDependencies) {
        List<DependencyEntity> savedDependencies = dependencyRepository.saveAll(newDependencies);
        return dependencyEntityMapper.toDependencyList(savedDependencies);
    }
}
