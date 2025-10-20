package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.ErrorLogResponse;
import com.unibague.magno.domain.model.ErrorLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ErrorLogResponseMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "errorCode", source = "errorCode")
    @Mapping(target = "errorMessage", source = "errorMessage")
    @Mapping(target = "details", source = "details")
    @Mapping(target = "exceptionClassName", source = "exceptionClassName")
    @Mapping(target = "stackTrace", source = "stackTrace")
    @Mapping(target = "timestamp", source = "timestamp")
    @Mapping(target = "httpMethod", source = "httpMethod")
    @Mapping(target = "requestUrl", source = "requestUrl")
    @Mapping(target = "requestParams", source = "requestParams")
    @Mapping(target = "clientIp", source = "clientIp")
    @Mapping(target = "userEmail", source = "userEmail")
    @Mapping(target = "userId", source = "userId")
    @Mapping(target = "userAgent", source = "userAgent")
    @Mapping(target = "sessionId", source = "sessionId")
    ErrorLogResponse toResponse(ErrorLog errorLog);
}
