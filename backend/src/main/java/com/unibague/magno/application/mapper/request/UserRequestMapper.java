package com.unibague.magno.application.mapper.request;

import com.unibague.magno.application.dto.request.integra.IntegraUserRequest;
import com.unibague.magno.application.dto.request.UserRequest;
import com.unibague.magno.domain.model.User;

/**
 * Mapper interface for converting user request DTOs to domain models.
 * Manually implemented to support both direct user creation and
 * user creation from Integra system data.
 */
public interface UserRequestMapper {
    User toUser(UserRequest userRequest);

    /**
     * Maps a user from Integra system data.
     * Fetches complete user information from Integra based on the provided
     * identification and type (student or functionary).
     *
     * @param userRequest the Integra user request containing identification and type
     * @return the mapped user domain model with data from Integra
     */
    User toUser(IntegraUserRequest userRequest);
}
