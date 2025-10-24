package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.ActionLog;
import com.unibague.magno.infrastructure.output.jpa.entity.ActionLogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ActionLogEntityMapper {

    ActionLogEntity toEntity(ActionLog actionLog);

    ActionLog toDomain(ActionLogEntity actionLogEntity);

    List<ActionLog> toDomainList(List<ActionLogEntity> actionLogEntities);
}

