package com.unibague.magno.application.mapper.request;

import com.unibague.magno.application.dto.request.UserRequest;
import com.unibague.magno.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserRequestMapper {
    User toUser(UserRequest userRequest);
}
