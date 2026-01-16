package com.unibague.magno.application.mapper.response;

import com.unibague.magno.application.dto.response.UserResponse;
import com.unibague.magno.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of {@link UserResponseMapper}.
 */
@Component
public class UserResponseMapperImpl implements UserResponseMapper {

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
                .typeOfInternalUser(userResponse.getTypeOfInternalUser())
                .build();
    }

    @Override
    public List<UserResponse> toResponseList(List<User> userResponse) {
        return userResponse.stream()
                .map(this::toResponse)
                .toList();
    }
}
