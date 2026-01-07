package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.ActionLog;
import com.unibague.magno.infrastructure.output.jpa.entity.ActionLogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ActionLogEntityMapper {

    @Mapping(target = "userId", expression = "java(entity.getUser() != null ? entity.getUser().getId() : null)")
    ActionLog toDomain(ActionLogEntity entity);

    @Mapping(target = "user", ignore = true)
    ActionLogEntity toEntity(ActionLog domain);

    List<ActionLog> toDomainList(List<ActionLogEntity> entities);
}

