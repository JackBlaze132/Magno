package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.ErrorLog;
import com.unibague.magno.infrastructure.output.jpa.entity.ErrorLogEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ErrorLogEntityMapper {

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
    ErrorLog toDomain(ErrorLogEntity entity);

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
    ErrorLogEntity toEntity(ErrorLog domain);

    List<ErrorLog> toDomainList(List<ErrorLogEntity> entities);
}
