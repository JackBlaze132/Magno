package com.unibague.magno.infrastructure.configuration;

import com.unibague.magno.domain.api.IAcademicPeriodServicePort;
import com.unibague.magno.domain.spi.IAcademicPeriodPersistencePort;
import com.unibague.magno.domain.usecase.AcademicPeriodUseCase;
import com.unibague.magno.infrastructure.output.jpa.adapter.AcademicPeriodJpaAdapter;
import com.unibague.magno.infrastructure.output.jpa.mapper.AcademicPeriodEntityMapper;
import com.unibague.magno.infrastructure.output.jpa.repository.IAcademicPeriodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final IAcademicPeriodRepository academicPeriodRepository;
    private final AcademicPeriodEntityMapper academicPeriodEntityMapper;

    @Bean
    public IAcademicPeriodServicePort academicPeriodServicePort() {
        return new AcademicPeriodUseCase(academicPeriodPersistencePort());
    }

    @Bean
    public IAcademicPeriodPersistencePort academicPeriodPersistencePort() {
        return new AcademicPeriodJpaAdapter(academicPeriodRepository, academicPeriodEntityMapper);
    }
}
