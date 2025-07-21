package com.unibague.magno.infrastructure.configuration.security;

import com.unibague.magno.domain.api.IRoleServicePort;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.exception.user.UserNotFoundException;
import com.unibague.magno.domain.model.Role;
import com.unibague.magno.domain.model.User;
import com.unibague.magno.domain.model.integra.IntegraFunctionary;
import com.unibague.magno.domain.model.integra.IntegraStudent;
import jakarta.annotation.PostConstruct;
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

@RequiredArgsConstructor
@Service
public class CustomOidcUserService extends OidcUserService {

    private static final List<String> ALLOWED_DOMAINS = List.of("unibague.edu.co", "estudiantesunibague.edu.co");
    private final IUserServicePort userServicePort;
    private final IIntegraServicePort integraServicePort;
    private final IRoleServicePort roleServicePort;

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser user = super.loadUser(userRequest);
        String email = user.getEmail();

        boolean validEmail = ALLOWED_DOMAINS.stream()
                .anyMatch(domain -> email != null && email.endsWith("@" + domain));

        if (!validEmail) {
            System.out.println("Email no permitido: " + email);
            throw new OAuth2AuthenticationException("No autorizado: dominio de email no permitido");
        }

        User existingUser = getUserByEmail(email);

        List<Role> roles = roleServicePort.findAllRolesByUserId(existingUser.getId());

        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> (GrantedAuthority) new SimpleGrantedAuthority(role.getName()))
                .toList();

        return new DefaultOidcUser(authorities, user.getIdToken(), user.getUserInfo());
    }

    private User getUserByEmail(String email) {

        try{
            return userServicePort.findByEmail(email);
        }
        catch (UserNotFoundException e) {

            if (email.endsWith("@" + ALLOWED_DOMAINS.getFirst())){
                IntegraFunctionary integraFunctionary = integraServicePort.getIntegraFunctionaryByEmail(email);
                return userServicePort.getUserByIntegraFunctionary(integraFunctionary);
            }
            else{
                //TODO: Request endpoint to get the first integra student found by email
                //IntegraStudent integraStudent = integraServicePort.getFirstIntegraStudentFoundByEmail(email);
                return null;
            }
        }

    }
}
