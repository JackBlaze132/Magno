package com.unibague.magno.application.handler;

import com.unibague.magno.application.dto.request.UserRequest;
import com.unibague.magno.application.dto.request.integra.IntegraUserRequest;
import com.unibague.magno.application.dto.response.UserResponse;

import java.util.List;

public interface IUserHandler {
    UserResponse findById(Long id);
    UserResponse save(UserRequest user);
    UserResponse save(IntegraUserRequest user);
    UserResponse updateById(Long id, UserRequest user);
    void deleteById(Long id);
    List<UserResponse> findAll();
}
