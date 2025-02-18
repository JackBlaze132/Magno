package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IDependencyServicePort;
import com.unibague.magno.domain.exception.DependencyNotFoundException;
import com.unibague.magno.domain.model.Dependency;
import com.unibague.magno.domain.spi.IDependencyPersistencePort;

import java.util.List;

public class DependencyUseCase implements IDependencyServicePort {

    private final IDependencyPersistencePort dependencyPersistencePort;

    public DependencyUseCase(IDependencyPersistencePort dependencyPersistencePort) {
        this.dependencyPersistencePort = dependencyPersistencePort;
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
        return dependencyPersistencePort.findByName(name)
                .orElseThrow(() -> new DependencyNotFoundException(
                        String.format("Dependency with name %s not found", name)));
    }
}
