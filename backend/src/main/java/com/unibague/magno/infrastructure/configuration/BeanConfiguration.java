package com.unibague.magno.infrastructure.configuration;

import com.unibague.magno.domain.api.IAcademicPeriodServicePort;
import com.unibague.magno.domain.api.IRoleServicePort;
import com.unibague.magno.domain.spi.IAcademicPeriodPersistencePort;
import com.unibague.magno.domain.spi.IRolePersistencePort;
import com.unibague.magno.domain.usecase.AcademicPeriodUseCase;
import com.unibague.magno.domain.usecase.RoleUseCase;
import com.unibague.magno.infrastructure.output.jpa.adapter.AcademicPeriodJpaAdapter;
import com.unibague.magno.infrastructure.output.jpa.adapter.RoleJpaAdapter;
import com.unibague.magno.infrastructure.output.jpa.mapper.AcademicPeriodEntityMapper;
import com.unibague.magno.infrastructure.output.jpa.mapper.RoleEntityMapper;
import com.unibague.magno.infrastructure.output.jpa.repository.IAcademicPeriodRepository;
import com.unibague.magno.infrastructure.output.jpa.repository.IRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IAcademicPeriodRepository academicPeriodRepository;
    private final AcademicPeriodEntityMapper academicPeriodEntityMapper;

    private final IRoleRepository roleRepository;
    private final RoleEntityMapper roleEntityMapper;

    @Bean
    public IAcademicPeriodServicePort academicPeriodServicePort() {
        return new AcademicPeriodUseCase(academicPeriodPersistencePort());
    }

    @Bean
    public IAcademicPeriodPersistencePort academicPeriodPersistencePort() {
        return new AcademicPeriodJpaAdapter(academicPeriodRepository, academicPeriodEntityMapper);
    }

    @Bean
    public IRoleServicePort roleServicePort() {
        return new RoleUseCase(rolePersistencePort());
    }

    @Bean
    public IRolePersistencePort rolePersistencePort() {
        return new RoleJpaAdapter(roleRepository, roleEntityMapper);
    }
}
