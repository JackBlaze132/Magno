package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IAcademicPeriodServicePort;
import com.unibague.magno.domain.exception.academicperiod.AcademicPeriodNotFoundException;
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
                .orElseThrow(() -> new AcademicPeriodNotFoundException(
                        String.format("AcademicPeriod with ID %d not found", id)));
    }

    @Override
    public AcademicPeriod save(AcademicPeriod academicPeriod) {
        return academicPeriodPersistencePort.save(academicPeriod);
    }

    @Override
    public AcademicPeriod update(Long id, AcademicPeriod academicPeriod) {
        if(academicPeriodPersistencePort.findById(id).isEmpty()) {
            throw new AcademicPeriodNotFoundException(
                    String.format("AcademicPeriod with ID %d could not be updated because it does not exist", id));
        }
        return academicPeriodPersistencePort.update(id, academicPeriod);
    }

    @Override
    public void deleteById(Long id) {
        if(academicPeriodPersistencePort.findById(id).isEmpty()) {
            throw new AcademicPeriodNotFoundException(
                    String.format("AcademicPeriod with ID %d could not be deleted because it does not exist", id));
        }
        academicPeriodPersistencePort.deleteById(id);
    }

    @Override
    public List<AcademicPeriod> findAll() {
        return academicPeriodPersistencePort.findAll();
    }
}
