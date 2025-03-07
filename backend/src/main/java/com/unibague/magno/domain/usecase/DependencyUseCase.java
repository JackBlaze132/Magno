package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IDependencyServicePort;
import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.exception.dependency.DependencyNotFoundException;
import com.unibague.magno.domain.model.Dependency;
import com.unibague.magno.domain.model.integra.IntegraDependency;
import com.unibague.magno.domain.spi.IDependencyPersistencePort;

import java.util.List;
import java.util.Optional;

public class DependencyUseCase implements IDependencyServicePort {

    private final IDependencyPersistencePort dependencyPersistencePort;
    private final IIntegraServicePort integraServicePort;

    public DependencyUseCase(IDependencyPersistencePort dependencyPersistencePort,
                             IIntegraServicePort integraServicePort) {
        this.dependencyPersistencePort = dependencyPersistencePort;
        this.integraServicePort = integraServicePort;
    }

    @Override
    public Dependency findById(Long id) {
        return dependencyPersistencePort.findById(id)
                .orElseThrow(() -> new DependencyNotFoundException(
                        String.format("Dependency with ID %d not found", id)));
    }

    @Override
    public Dependency save(Dependency dependency) {
        return dependencyPersistencePort.save(dependency);
    }

    @Override
    public Dependency update(Long id, Dependency dependency) {
        if(dependencyPersistencePort.findById(id).isEmpty()) {
            throw new DependencyNotFoundException(
                    String.format("Dependency with ID %d could not be updated because it does not exist", id));
        }
        return dependencyPersistencePort.update(id, dependency);
    }

    @Override
    public void deleteById(Long id) {
        if(dependencyPersistencePort.findById(id).isEmpty()) {
            throw new DependencyNotFoundException(
                    String.format("Dependency with ID %d could not be deleted because it does not exist", id));
        }
        dependencyPersistencePort.deleteById(id);
    }

    @Override
    public List<Dependency> findAll() {
        return dependencyPersistencePort.findAll();
    }

    @Override
    public Dependency findByName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        return findOrSaveByName(name);
    }

    private Dependency findOrSaveByName(String name) {
        Optional<Dependency> dependency = dependencyPersistencePort.findByName(name);
        if (dependency.isPresent()) {
            return dependency.get();
        }
        IntegraDependency integraDependency = integraServicePort.getIntegraDependencyByDependencyName(name);
        return save(mapFromIntegraDependency(integraDependency));
    }

    private Dependency mapFromIntegraDependency(IntegraDependency integraDependency) {
        Dependency dependency = new Dependency();
        dependency.setName(integraDependency.getDepName());
        return dependency;
    }
}
