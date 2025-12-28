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
                        String.format("Dependencia con ID %d no encontrada", id)));
    }

    @Override
    public Dependency save(Dependency dependency) {
        return dependencyPersistencePort.save(dependency);
    }

    @Override
    public Dependency update(Long id, Dependency dependency) {
        if(dependencyPersistencePort.findById(id).isEmpty()) {
            throw new DependencyNotFoundException(
                    String.format("No se pudo actualizar la dependencia con ID %d porque no existe", id));
        }
        return dependencyPersistencePort.update(id, dependency);
    }

    @Override
    public void deleteById(Long id) {
        if(dependencyPersistencePort.findById(id).isEmpty()) {
            throw new DependencyNotFoundException(
                    String.format("No se pudo eliminar la dependencia con ID %d porque no existe", id));
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
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }
        return findOrSaveByName(name);
    }

    @Override
    public Optional<Dependency> findByNameOptional(String name) {
        return dependencyPersistencePort.findByName(name);
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
