package com.unibague.magno.infrastructure.configuration;

import com.unibague.magno.domain.api.*;
import com.unibague.magno.domain.api.cronjobs.ICronJobServicePort;
import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.spi.*;
import com.unibague.magno.domain.spi.integra.IIntegraPersistencePort;
import com.unibague.magno.domain.usecase.*;
import com.unibague.magno.domain.usecase.cronjobs.CronJobUseCase;
import com.unibague.magno.domain.usecase.integra.IntegraUseCase;
import com.unibague.magno.infrastructure.configuration.security.CustomOidcUserService;
import com.unibague.magno.infrastructure.configuration.security.GoogleIdTokenAuthenticationFilter;
import com.unibague.magno.infrastructure.configuration.security.SecurityConfig;
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

    private final IResearchSeedbedProfileRepository researchSeedbedProfileRepository;
    private final ResearchSeedbedProfileEntityMapper researchSeedbedProfileEntityMapper;

    private final IStudentProfileRepository studentProfileRepository;
    private final StudentProfileEntityMapper studentProfileEntityMapper;

    private final IResearchSeedbedStudentProfileRepository researchSeedbedStudentProfileRepository;
    private final ResearchSeedbedStudentProfileEntityMapper researchSeedbedStudentProfileEntityMapper;

    private final IExternalUserProfileRepository externalUserProfileRepository;
    private final ExternalUserProfileEntityMapper externalUserProfileEntityMapper;

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
        return new DependencyUseCase(dependencyPersistencePort(), integraServicePort());
    }

    @Bean
    public IDependencyPersistencePort dependencyPersistencePort() {
        return new DependencyJpaAdapter(dependencyRepository, dependencyEntityMapper, integraServicePort());
    }

    @Bean
    public IAcademicProgramServicePort academicProgramServicePort() {
        return new AcademicProgramUseCase(academicProgramPersistencePort(), integraServicePort());
    }

    @Bean
    public IAcademicProgramPersistencePort academicProgramPersistencePort() {
        return new AcademicProgramJpaAdapter(academicProgramRepository, academicProgramEntityMapper, integraServicePort());
    }

    @Bean
    public IUserServicePort userServicePort() {
        return new UserUseCase(userPersistencePort(), integraServicePort());
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
        return new EnumUseCase(investigationGroupServicePort(), researchSeedbedServicePort());
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

    @Bean
    public IResearchSeedbedProfileServicePort researchSeedbedProfileServicePort() {
        return new ResearchSeedbedProfileUseCase(researchSeedbedProfilePersistencePort());
    }

    @Bean
    public IResearchSeedbedProfilePersistencePort researchSeedbedProfilePersistencePort() {
        return new ResearchSeedbedProfileJpaAdapter(researchSeedbedProfileRepository, researchSeedbedProfileEntityMapper);
    }

    @Bean
    public IStudentProfileServicePort studentProfileServicePort() {
        return new StudentProfileUseCase(studentProfilePersistencePort(), userServicePort(),
                integraServicePort(), academicProgramServicePort(), roleServicePort());
    }

    @Bean
    public IStudentProfilePersistencePort studentProfilePersistencePort() {
        return new StudentProfileJpaAdapter(studentProfileRepository, studentProfileEntityMapper);
    }

    @Bean
    public IResearchSeedbedStudentProfileServicePort researchSeedbedStudentProfileServicePort() {
        return new ResearchSeedbedStudentProfileUseCase(researchSeedbedStudentProfilePersistencePort(),
                userServicePort(), integraServicePort(), studentProfileServicePort(),
                researchSeedbedProfileServicePort());
    }

    @Bean
    public IResearchSeedbedStudentProfilePersistencePort researchSeedbedStudentProfilePersistencePort() {
        return new ResearchSeedbedStudentProfileJpaAdapter(researchSeedbedStudentProfileRepository, researchSeedbedStudentProfileEntityMapper);
    }

    @Bean
    public IExternalUserProfileServicePort externalUserProfileServicePort() {
        return new ExternalUserProfileUseCase(externalUserProfilePersistencePort(),
                userServicePort());
    }

    @Bean
    public IExternalUserProfilePersistencePort externalUserProfilePersistencePort() {
        return new ExternalUserProfileJpaAdapter(externalUserProfileRepository, externalUserProfileEntityMapper);
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

    // CronJob Beans

    @Bean
    public ICronJobServicePort cronJobServicePort() {
        return new CronJobUseCase(integraServicePort(), userServicePort(),
                academicProgramServicePort(), dependencyServicePort());
    }

    // Security Beans

    @Bean
    SecurityConfig securityConfig() {
        return new SecurityConfig(customOidcUserService(), userServicePort(), googleIdTokenAuthenticationFilter());
    }

    @Bean
    CustomOidcUserService customOidcUserService() {
        return new CustomOidcUserService(userServicePort(), integraServicePort(), roleServicePort());
    }

    @Bean
    GoogleIdTokenAuthenticationFilter googleIdTokenAuthenticationFilter() {
        return new GoogleIdTokenAuthenticationFilter(userServicePort(), roleServicePort());
    }

}
