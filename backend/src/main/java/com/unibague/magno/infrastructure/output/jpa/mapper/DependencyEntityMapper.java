package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.Dependency;
import com.unibague.magno.domain.model.integra.IntegraDependency;
import com.unibague.magno.infrastructure.output.jpa.entity.DependencyEntity;

import java.util.List;

public interface DependencyEntityMapper {

    Dependency toDependency(DependencyEntity dependencyEntity);
    DependencyEntity toDependencyEntity(Long id, Dependency dependency);
    DependencyEntity toDependencyEntity(Dependency dependency);
    List<Dependency> toDependencyList(List<DependencyEntity> dependencyEntities);
    DependencyEntity toDependencyEntity(IntegraDependency dependency);
}
