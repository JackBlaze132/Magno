package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.UserResponse;
import com.unibague.magno.domain.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserResponseMapper {
    UserResponse toResponse(User userResponse);
    List<UserResponse> toResponseList(List<User> userResponse);
}
