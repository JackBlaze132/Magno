package com.unibague.magno.infrastructure.configuration;

import com.unibague.magno.domain.api.*;
import com.unibague.magno.domain.spi.*;
import com.unibague.magno.domain.usecase.*;
import com.unibague.magno.infrastructure.output.jpa.adapter.*;
import com.unibague.magno.infrastructure.output.jpa.mapper.*;
import com.unibague.magno.infrastructure.output.jpa.repository.*;
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

    private final IDependencyRepository dependencyRepository;
    private final DependencyEntityMapper dependencyEntityMapper;

    private final IAcademicProgramRepository academicProgramRepository;
    private final AcademicProgramEntityMapper academicProgramEntityMapper;

    private final IUserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    private final IInvestigationGroupRepository investigationGroupRepository;
    private final InvestigationGroupEntityMapper investigationGroupEntityMapper;

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

    @Bean
    public IDependencyServicePort dependencyServicePort() {
        return new DependencyUseCase(dependencyPersistencePort());
    }

    @Bean
    public IDependencyPersistencePort dependencyPersistencePort() {
        return new DependencyJpaAdapter(dependencyRepository, dependencyEntityMapper);
    }

    @Bean
    public IAcademicProgramServicePort academicProgramServicePort() {
        return new AcademicProgramUseCase(academicProgramPersistencePort());
    }

    @Bean
    public IAcademicProgramPersistencePort academicProgramPersistencePort() {
        return new AcademicProgramJpaAdapter(academicProgramRepository, academicProgramEntityMapper);
    }

    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(userPersistencePort());
    }

    @Bean
    public IUserPersistencePort userPersistencePort() {
        return new UserJpaAdapter(userRepository, userEntityMapper);
    }

    @Bean
    public IInvestigationGroupServicePort investigationGroupServicePort() {
        return new InvestigationGroupUseCase(investigationGroupPersistencePort());
    }

    @Bean
    public IInvestigationGroupPersistencePort investigationGroupPersistencePort() {
        return new InvestigationGroupJpaAdapter(investigationGroupRepository, investigationGroupEntityMapper);
    }
}
