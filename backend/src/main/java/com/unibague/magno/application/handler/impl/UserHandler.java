package com.unibague.magno.application.handler.impl;

import com.unibague.magno.application.dto.request.integra.IntegraUserRequest;
import com.unibague.magno.application.dto.request.UserRequest;
import com.unibague.magno.application.dto.response.UserResponse;
import com.unibague.magno.application.handler.interfaces.IUserHandler;
import com.unibague.magno.application.mapper.request.UserRequestMapper;
import com.unibague.magno.application.mapper.response.UserResponseMapper;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserHandler implements IUserHandler {

    private final IUserServicePort userServicePort;
    private final UserRequestMapper userRequestMapper;
    private final UserResponseMapper userResponseMapper;

    @Override
    public UserResponse findById(Long id) {
        User user = userServicePort.findById(id);
        return userResponseMapper.toResponse(user);
    }

    @Override
    public UserResponse save(UserRequest user) {
        return userResponseMapper.toResponse(userServicePort.save(userRequestMapper.toUser(user)));
    }

    @Override
    public UserResponse save(IntegraUserRequest user) {
        return userResponseMapper.toResponse(userServicePort.save(userRequestMapper.toUser(user)));
    }

    @Override
    public UserResponse updateById(Long id, UserRequest user) {
        return userResponseMapper.toResponse(userServicePort.update(id, userRequestMapper.toUser(user)));
    }

    @Override
    public void deleteById(Long id) {
        userServicePort.deleteById(id);
    }

    @Override
    public List<UserResponse> findAll() {
        return userResponseMapper.toResponseList(userServicePort.findAll());
    }

    @Override
    public List<String> findAllCountries() {
        return userServicePort.findAllCountries();
    }
}
