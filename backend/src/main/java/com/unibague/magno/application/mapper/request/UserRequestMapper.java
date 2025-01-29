package com.unibague.magno.application.mapper.request;

import com.unibague.magno.application.dto.request.UserRequest;
import com.unibague.magno.domain.model.User;

public interface UserRequestMapper {
    User toUser(UserRequest userRequest);
}
