package com.unibague.magno.application.mapper.request;

import com.unibague.magno.application.dto.request.UserRequest;
import com.unibague.magno.domain.model.Role;
import com.unibague.magno.domain.model.User;
import com.unibague.magno.domain.spi.IRolePersistencePort;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserRequestMapperImpl implements UserRequestMapper {

    private final IRolePersistencePort rolePersistencePort;

    public UserRequestMapperImpl(IRolePersistencePort roleServicePort) {
        this.rolePersistencePort = roleServicePort;
    }

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
        user.setExternalUser( userRequest.isExternalUser() );
        user.setSex( userRequest.getSex() );

        Set<Role> roles = rolePersistencePort.findRolesByIds(userRequest.getRoleIds());
        user.setRoles(roles);

        return user;
    }
}
