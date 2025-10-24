package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.ActionLogResponse;
import com.unibague.magno.domain.model.ActionLog;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ActionLogResponseMapper {

    ActionLogResponse toResponse(ActionLog actionLog);

    List<ActionLogResponse> toResponseList(List<ActionLog> actionLogs);
}

