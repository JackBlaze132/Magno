package com.unibague.magno.infrastructure.configuration.security;

import com.unibague.magno.domain.api.IRoleServicePort;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.exception.security.InvalidEmailException;
import com.unibague.magno.domain.exception.security.NullEmailException;
import com.unibague.magno.domain.exception.user.UserNotFoundException;
import com.unibague.magno.domain.model.Role;
import com.unibague.magno.domain.model.User;
import com.unibague.magno.domain.model.enums.SeedbedRole;
import com.unibague.magno.domain.model.integra.IntegraFunctionary;
import com.unibague.magno.domain.model.integra.IntegraStudent;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Custom OIDC user service for Google OAuth2 authentication.
 * Handles user loading during OAuth2 login, validates university email domain,
 * creates new users from Integra system if not existing, and assigns roles.
 */
@RequiredArgsConstructor
@Service
public class CustomOidcUserService extends OidcUserService {

    private static final String DOMAIN_FUNCTIONARIES = "@unibague.edu.co";

    private final IUserServicePort userServicePort;
    private final IIntegraServicePort integraServicePort;
    private final IRoleServicePort roleServicePort;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser user = super.loadUser(userRequest);
        String email = user.getEmail();

        verifyEmail(email);

        User existingUser = getUserByEmail(email);

        List<Role> roles = roleServicePort.findAllRolesByUserId(existingUser.getId());

        if (roles.isEmpty()) {
            roles.add(roleServicePort.findByName(SeedbedRole.USUARIO_SIN_ROL));
        }

        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role.getName().getAuthority()))
                .toList();

        DefaultOidcUser oidcUser = new DefaultOidcUser(authorities, user.getIdToken(), user.getUserInfo());

        // Return a custom OidcUser that includes the application's user ID
        return new CustomOidcUserWithUserId(oidcUser, existingUser.getId());
    }

    private User getUserByEmail(String email) {

        try{
            return userServicePort.findByEmail(email);
        }
        catch (UserNotFoundException e) {

            if (email.endsWith(DOMAIN_FUNCTIONARIES)) {
                IntegraFunctionary integraFunctionary = integraServicePort.getIntegraFunctionaryByEmail(email);
                User functionaryUser =  userServicePort.getUserByIntegraFunctionary(integraFunctionary);
                return userServicePort.save(functionaryUser);
            }
            else{
                IntegraStudent integraStudent = integraServicePort.getIntegraStudentByEmail(email);
                User studentUser = userServicePort.getUserByIntegraStudent(integraStudent);
                return userServicePort.save(studentUser);
            }
        }

    }

    private void verifyEmail(String email) {
        if (email == null) {
            throw new NullEmailException();
        }
        String ALLOWED_DOMAIN = "unibague.edu.co";
        if (email.isBlank() || (!email.endsWith(ALLOWED_DOMAIN))){
            throw new InvalidEmailException();
        }
    }
}
