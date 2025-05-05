package com.unibague.magno.application.mapper.request;

import com.unibague.magno.application.dto.request.integra.IntegraUserRequest;
import com.unibague.magno.application.dto.request.UserRequest;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.exception.integra.IntegraInvalidTypeException;
import com.unibague.magno.domain.model.User;
import com.unibague.magno.domain.model.enums.JSONIntegraType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserRequestMapperImpl implements UserRequestMapper {

    private final IUserServicePort userServicePort;

    @Override
    public User toUser(UserRequest userRequest) {
        if ( userRequest == null ) {
            return null;
        }

        User user = new User();

        user.setFullName( userRequest.getFullName() );
        user.setIdentificationNumber( userRequest.getIdentificationNumber() );
        user.setEmail( userRequest.getEmail() );
        user.setUserCode( userRequest.getUserCode() );
        user.setExternalUser( userRequest.getIsExternalUser() );
        user.setSex( userRequest.getSex() );

        return user;
    }

    @Override
    public User toUser(IntegraUserRequest userRequest) {

        if (userRequest.getType().equals(JSONIntegraType.FUNCIONARIO)){
            return userServicePort.mapFromIntegraFunctionary(userRequest);
        }
        else if (userRequest.getType().equals(JSONIntegraType.ESTUDIANTE)){
            return userServicePort.mapFromIntegraStudent(userRequest);
        }
        else {
            throw new IntegraInvalidTypeException("The type of the integra user is not valid");
        }
    }

}
