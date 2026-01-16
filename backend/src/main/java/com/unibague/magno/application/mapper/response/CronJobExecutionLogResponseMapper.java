package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.CronJobExecutionLogResponse;
import com.unibague.magno.domain.model.CronJobExecutionLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper interface for converting cron job execution log domain models to response DTOs.
 * Auto-implemented by MapStruct.
 */
@Mapper(componentModel = "spring")
public interface CronJobExecutionLogResponseMapper {

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
    CronJobExecutionLogResponse toResponse(CronJobExecutionLog cronJobExecutionLog);

    List<CronJobExecutionLogResponse> toResponseList(List<CronJobExecutionLog> cronJobExecutionLogs);
}
