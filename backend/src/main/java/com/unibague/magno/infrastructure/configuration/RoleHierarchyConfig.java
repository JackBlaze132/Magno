package com.unibague.magno.infrastructure.configuration;

import com.unibague.magno.domain.model.enums.SeedbedRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;

/**
 * Configuration class for Spring Security role hierarchy.
 * Defines the privilege hierarchy where higher roles inherit permissions from lower roles.
 * The hierarchy follows: DIRI > Coordinator > Tutor > Student Leader > Student > No Role.
 */
@Configuration
public class RoleHierarchyConfig {

    @Bean
    public RoleHierarchy roleHierarchy() {
        String hierarchy = buildHierarchy(
                SeedbedRole.DIRI,
                SeedbedRole.COORDINADOR_DE_GRUPO_DE_INVESTIGACION,
                SeedbedRole.COORDINADOR_DE_SEMILLERO,
                SeedbedRole.TUTOR_DE_SEMILLERO,
                SeedbedRole.ESTUDIANTE_LIDER,
                SeedbedRole.ESTUDIANTE,
                SeedbedRole.USUARIO_SIN_ROL
        );
        return RoleHierarchyImpl.fromHierarchy(hierarchy);
    }

    /**
     * Builds the role hierarchy automatically.
     * The first role has the most privileges, the last has the least.
     *
     * @param roles Array of roles ordered from highest to lowest privilege
     * @return String with the hierarchy in Spring Security format
     */
    private String buildHierarchy(SeedbedRole... roles) {
        StringBuilder hierarchy = new StringBuilder();

        for (int i = 0; i < roles.length - 1; i++) {
            hierarchy.append(roles[i].getAuthority())
                    .append(" > ")
                    .append(roles[i + 1].getAuthority())
                    .append("\n");
        }

        return hierarchy.toString();
    }
}
