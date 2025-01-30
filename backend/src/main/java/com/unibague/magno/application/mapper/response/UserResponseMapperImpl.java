package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.UserResponse;
import com.unibague.magno.domain.model.User;
import com.unibague.magno.domain.spi.IRolePersistencePort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserResponseMapperImpl implements UserResponseMapper {

    private final IRolePersistencePort rolePersistencePort;
    private final RoleResponseMapper roleResponseMapper;

    public UserResponseMapperImpl(IRolePersistencePort rolePersistencePort, RoleResponseMapper roleResponseMapper) {
        this.rolePersistencePort = rolePersistencePort;
        this.roleResponseMapper = roleResponseMapper;
    }

    @Override
    public UserResponse toResponse(User userResponse) {
        return UserResponse.builder()
                .id(userResponse.getId())
                .fullName(userResponse.getFullName())
                .identificationNumber(userResponse.getIdentificationNumber())
                .email(userResponse.getEmail())
                .userCode(userResponse.getUserCode())
                .sex(userResponse.getSex())
                .isExternalUser(userResponse.isExternalUser())
                .roles(roleResponseMapper.toResponseSet(rolePersistencePort
                        .findRolesByIds(userResponse.getRoleIds())))
                .build();
    }

    @Override
    public List<UserResponse> toResponseList(List<User> userResponse) {
        return userResponse.stream()
                .map(this::toResponse)
                .toList();
    }
}
