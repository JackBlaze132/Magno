package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.Dependency;
import com.unibague.magno.domain.model.integra.IntegraDependency;
import com.unibague.magno.infrastructure.output.jpa.entity.DependencyEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DependencyEntityMapperImpl implements DependencyEntityMapper {

    @Override
    public Dependency toDependency(DependencyEntity dependencyEntity) {
        if ( dependencyEntity == null ) {
            return null;
        }

        Dependency dependency = new Dependency();

        dependency.setId( dependencyEntity.getId() );
        dependency.setName( dependencyEntity.getName() );

        return dependency;
    }

    @Override
    public DependencyEntity toDependencyEntity(Long id, Dependency dependency) {
        if ( id == null && dependency == null ) {
            return null;
        }

        DependencyEntity dependencyEntity = new DependencyEntity();

        if ( dependency != null ) {
            dependencyEntity.setName( dependency.getName() );
        }
        dependencyEntity.setId( id );

        return dependencyEntity;
    }

    @Override
    public DependencyEntity toDependencyEntity(Dependency dependency) {
        if ( dependency == null ) {
            return null;
        }

        DependencyEntity dependencyEntity = new DependencyEntity();

        dependencyEntity.setId( dependency.getId() );
        dependencyEntity.setName( dependency.getName() );

        return dependencyEntity;
    }

    @Override
    public List<Dependency> toDependencyList(List<DependencyEntity> dependencyEntities) {
        return dependencyEntities.stream()
                .map(this::toDependency)
                .toList();
    }

    @Override
    public DependencyEntity toDependencyEntity(IntegraDependency dependency) {
        DependencyEntity dependencyEntity = new DependencyEntity();
        dependencyEntity.setName(dependency.getDepName());
        return dependencyEntity;
    }
}
