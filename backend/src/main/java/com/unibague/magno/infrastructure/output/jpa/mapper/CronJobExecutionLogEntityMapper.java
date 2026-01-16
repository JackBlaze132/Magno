package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.CronJobExecutionLog;
import com.unibague.magno.infrastructure.output.jpa.entity.CronJobExecutionLogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper for converting between {@link CronJobExecutionLog} domain model and {@link CronJobExecutionLogEntity} JPA entity.
 */
@Mapper(componentModel = "spring")
public interface CronJobExecutionLogEntityMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "jobName", source = "jobName")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "startTime", source = "startTime")
    @Mapping(target = "endTime", source = "endTime")
    @Mapping(target = "durationMs", source = "durationMs")
    @Mapping(target = "details", source = "details")
    @Mapping(target = "recordsProcessed", source = "recordsProcessed")
    @Mapping(target = "errorMessage", source = "errorMessage")
    @Mapping(target = "stackTrace", source = "stackTrace")
    @Mapping(target = "timestamp", source = "timestamp")
    CronJobExecutionLog toDomain(CronJobExecutionLogEntity entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "jobName", source = "jobName")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "startTime", source = "startTime")
    @Mapping(target = "endTime", source = "endTime")
    @Mapping(target = "durationMs", source = "durationMs")
    @Mapping(target = "details", source = "details")
    @Mapping(target = "recordsProcessed", source = "recordsProcessed")
    @Mapping(target = "errorMessage", source = "errorMessage")
    @Mapping(target = "stackTrace", source = "stackTrace")
    @Mapping(target = "timestamp", source = "timestamp")
    CronJobExecutionLogEntity toEntity(CronJobExecutionLog domain);
}
