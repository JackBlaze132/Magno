package com.unibague.magno.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;

@Configuration
public class RoleHierarchyConfig {

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();

        String hierarchy = """
            ROLE_DIRI > ROLE_COORDINADOR_DE_GRUPO_DE_INVESTIGACION
            ROLE_COORDINADOR_DE_GRUPO_DE_INVESTIGACION > ROLE_COORDINADOR_DE_SEMILLERO
            ROLE_COORDINADOR_DE_SEMILLERO > ROLE_TUTOR_DE_SEMILLERO
            ROLE_TUTOR_DE_SEMILLERO > ROLE_ESTUDIANTE_LIDER
            ROLE_ESTUDIANTE_LIDER > ROLE_ESTUDIANTE
        """;

        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }
}
