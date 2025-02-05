package com.unibague.magno.infrastructure.configuration;

import com.unibague.magno.domain.api.*;
import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.spi.*;
import com.unibague.magno.domain.spi.integra.IIntegraPersistencePort;
import com.unibague.magno.domain.usecase.*;
import com.unibague.magno.domain.usecase.integra.IntegraUseCase;
import com.unibague.magno.infrastructure.output.jpa.adapter.*;
import com.unibague.magno.infrastructure.output.jpa.adapter.integra.IntegraUserClient;
import com.unibague.magno.infrastructure.output.jpa.mapper.*;
import com.unibague.magno.infrastructure.output.jpa.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

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

    private final IFunctionaryProfileRepository functionaryProfileRepository;
    private final FunctionaryProfileEntityMapper functionaryProfileEntityMapper;

    private final IInvestigationGroupProfileRepository investigationGroupProfileRepository;
    private final InvestigationGroupProfileEntityMapper investigationGroupProfileEntityMapper;

    private final IResearchSeedbedRepository researchSeedbedRepository;
    private final ResearchSeedbedEntityMapper researchSeedbedEntityMapper;

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

    @Bean
    public IEnumServicePort enumServicePort() {
        return new EnumUseCase();
    }

    @Bean
    public IFunctionaryProfileServicePort functionaryProfileServicePort() {
        return new FunctionaryProfileUseCase(functionaryProfilePersistencePort());
    }

    @Bean
    public IFunctionaryProfilePersistencePort functionaryProfilePersistencePort() {
        return new FunctionaryProfileJpaAdapter(functionaryProfileRepository, functionaryProfileEntityMapper);
    }

    @Bean
    public IInvestigationGroupProfileServicePort investigationGroupProfileServicePort() {
        return new InvestigationGroupProfileUseCase(investigationGroupProfilePersistencePort());
    }

    @Bean
    public IInvestigationGroupProfilePersistencePort investigationGroupProfilePersistencePort() {
        return new InvestigationGroupProfileJpaAdapter(
                investigationGroupProfileRepository, investigationGroupProfileEntityMapper, functionaryProfileRepository);
    }

    @Bean
    public IResearchSeedbedServicePort researchSeedbedServicePort() {
        return new ResearchSeedbedUseCase(researchSeedbedPersistencePort());
    }

    @Bean
    public IResearchSeedbedPersistencePort researchSeedbedPersistencePort() {
        return new ResearchSeedbedJpaAdapter(researchSeedbedRepository, researchSeedbedEntityMapper);
    }

    // "Integra" and related beans
    private final RestTemplate restTemplate;

    @Bean
    public IIntegraPersistencePort integraPersistencePort() {
        return new IntegraUserClient(restTemplate);
    }

    @Bean
    public IIntegraServicePort integraServicePort() {
        return new IntegraUseCase(integraPersistencePort());
    }

}
