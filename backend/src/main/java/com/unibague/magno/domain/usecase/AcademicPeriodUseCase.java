package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IAcademicPeriodServicePort;
import com.unibague.magno.domain.exception.AcademicPeriodNotFoundException;
import com.unibague.magno.domain.model.AcademicPeriod;
import com.unibague.magno.domain.spi.IAcademicPeriodPersistencePort;

import java.util.List;

public class AcademicPeriodUseCase implements IAcademicPeriodServicePort {

    private final IAcademicPeriodPersistencePort academicPeriodPersistencePort;

    public AcademicPeriodUseCase(IAcademicPeriodPersistencePort academicPeriodPersistencePort) {
        this.academicPeriodPersistencePort = academicPeriodPersistencePort;
    }

    @Override
    public AcademicPeriod findById(Long id) {
        return academicPeriodPersistencePort.findById(id)
                .orElseThrow(AcademicPeriodNotFoundException::new);
    }

    @Override
    public AcademicPeriod save(AcademicPeriod academicPeriod) {
        return academicPeriodPersistencePort.save(academicPeriod);
    }

    @Override
    public void deleteById(Long id) {
        academicPeriodPersistencePort.deleteById(id);
    }

    @Override
    public List<AcademicPeriod> findAll() {
        return academicPeriodPersistencePort.findAll();
    }
}
