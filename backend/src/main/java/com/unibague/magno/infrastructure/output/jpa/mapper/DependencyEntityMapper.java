package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.Dependency;
import com.unibague.magno.infrastructure.output.jpa.entity.DependencyEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DependencyEntityMapper {

    Dependency toDependency(DependencyEntity dependencyEntity);

    @Mapping(source = "id", target = "id")
    DependencyEntity toDependencyEntity(Long id, Dependency dependency);

    DependencyEntity toDependencyEntity(Dependency dependency);
    List<Dependency> toDependencyList(List<DependencyEntity> dependencyEntities);
}
