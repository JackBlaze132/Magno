package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IAcademicPeriodServicePort;
import com.unibague.magno.domain.exception.academicperiod.AcademicPeriodNotCurrentException;
import com.unibague.magno.domain.exception.academicperiod.AcademicPeriodNotFoundException;
import com.unibague.magno.domain.exception.academicperiod.EndDateBeforeStartDateException;
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
                        String.format("Período académico con ID %d no encontrado", id)));
    }

    @Override
    public AcademicPeriod save(AcademicPeriod academicPeriod) {
        validationsBeforeSaveOrUpdate(academicPeriod);
        return academicPeriodPersistencePort.save(academicPeriod);
    }

    @Override
    public AcademicPeriod update(Long id, AcademicPeriod academicPeriod) {
        validationsBeforeSaveOrUpdate(academicPeriod);
        if(academicPeriodPersistencePort.findById(id).isEmpty()) {
            throw new AcademicPeriodNotFoundException(
                    String.format("No se pudo actualizar el período académico con ID %d porque no existe", id));
        }
        return academicPeriodPersistencePort.update(id, academicPeriod);
    }

    private void validationsBeforeSaveOrUpdate(AcademicPeriod academicPeriod) {
        if (academicPeriod.getEndDate().isBefore(academicPeriod.getStartDate())){
            throw new EndDateBeforeStartDateException("La fecha de finalización no puede ser anterior a la fecha de inicio");
        }
        verifyAcademicPeriodIsCurrent(academicPeriod,
                "No se puede guardar o actualizar el período académico porque se marca como inactivo"
        );
    }

    @Override
    public void deleteById(Long id) {
        if(academicPeriodPersistencePort.findById(id).isEmpty()) {
            throw new AcademicPeriodNotFoundException(
                    String.format("No se pudo eliminar el período académico con ID %d porque no existe", id));
        }
        AcademicPeriod ap = findById(id);
        verifyAcademicPeriodIsCurrent(ap,
                "No se puede eliminar el período académico porque no está activo"
        );
        academicPeriodPersistencePort.deleteById(id);
    }

    private void verifyAcademicPeriodIsCurrent(AcademicPeriod academicPeriod, String errorMessage) {
        if (!academicPeriod.isCurrent()) {
            throw new AcademicPeriodNotCurrentException(errorMessage);
        }
    }


    @Override
    public List<AcademicPeriod> findAll() {
        return academicPeriodPersistencePort.findAll();
    }
}
