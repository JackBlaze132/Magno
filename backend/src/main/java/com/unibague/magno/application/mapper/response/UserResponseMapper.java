package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.UserResponse;
import com.unibague.magno.domain.model.User;

import java.util.List;

/**
 * Mapper interface for converting user domain models to response DTOs.
 */
public interface UserResponseMapper {
    UserResponse toResponse(User userResponse);
    List<UserResponse> toResponseList(List<User> userResponse);
}
