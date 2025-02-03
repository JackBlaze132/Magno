package com.unibague.magno.application.mapper.request;

import com.unibague.magno.application.dto.request.integra.IntegraUserRequest;
import com.unibague.magno.application.dto.request.UserRequest;
import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.model.User;
import com.unibague.magno.domain.model.enums.Sex;
import com.unibague.magno.domain.model.integra.IntegraFunctionary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserRequestMapperImpl implements UserRequestMapper {

    private final IIntegraServicePort integraServicePort;

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
        Set<Long> set = userRequest.getRoleIds();
        if ( set != null ) {
            user.setRoleIds(new LinkedHashSet<>(set) );
        }

        return user;
    }

    @Override
    public User toUser(IntegraUserRequest userRequest) {

        IntegraFunctionary integraFunctionary = integraServicePort.
                getIntegraFunctionaryByIdentification(userRequest.getIdentification());

        User user = new User();
        user.setFullName(integraFunctionary.getFullName());
        user.setIdentificationNumber(integraFunctionary.getIdentification());
        user.setEmail(integraFunctionary.getEmail());
        user.setUserCode(integraFunctionary.getCodeUser());
        user.setExternalUser(false);

        Sex sex = integraFunctionary.getSex().equalsIgnoreCase("M") ? Sex.MASCULINO : Sex.FEMENINO;
        user.setSex(sex);

        user.setRoleIds(userRequest.getRoleIds());
        return user;
    }
}
