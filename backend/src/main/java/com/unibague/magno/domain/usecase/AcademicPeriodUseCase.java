package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IAcademicPeriodServicePort;
import com.unibague.magno.domain.exception.academicperiod.AcademicPeriodAlreadyExistsException;
import com.unibague.magno.domain.exception.academicperiod.AcademicPeriodNotCurrentException;
import com.unibague.magno.domain.exception.academicperiod.AcademicPeriodNotFoundException;
import com.unibague.magno.domain.exception.academicperiod.EndDateBeforeStartDateException;
import com.unibague.magno.domain.model.AcademicPeriod;
import com.unibague.magno.domain.spi.IAcademicPeriodPersistencePort;

import java.util.List;

/**
 * Use case implementation for managing academic periods.
 * <p>
 * Handles business logic for academic period operations. Academic periods (semesters)
 * are fundamental organizational units in Magno, as profiles, activities, and reports
 * are all scoped to specific academic periods.
 * </p>
 * <p>
 * Business rules enforced:
 * <ul>
 *   <li>End date must be after start date</li>
 *   <li>Only current (active) periods can be modified or deleted</li>
 *   <li>Period names must be unique (case-insensitive)</li>
 * </ul>
 * </p>
 */
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
    public AcademicPeriod findByName(String name) {
        return academicPeriodPersistencePort.findByName(name)
                .orElseThrow(() -> new AcademicPeriodNotFoundException(
                        String.format("Período académico con nombre %s no encontrado", name)));
    }

    @Override
    public AcademicPeriod save(AcademicPeriod academicPeriod) {
        validationsBeforeSaveOrUpdate(academicPeriod);
        verifyThatAcademicPeriodDoesNotExist(academicPeriod, null);
        return academicPeriodPersistencePort.save(academicPeriod);
    }

    @Override
    public AcademicPeriod update(Long id, AcademicPeriod academicPeriod) {
        validationsBeforeSaveOrUpdate(academicPeriod);
        if(academicPeriodPersistencePort.findById(id).isEmpty()) {
            throw new AcademicPeriodNotFoundException(
                    String.format("No se pudo actualizar el período académico con ID %d porque no existe", id));
        }
        verifyThatAcademicPeriodDoesNotExist(academicPeriod, id);
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

    /**
     * Verifies that an academic period with the same name doesn't already exist.
     * Uses case-insensitive comparison and trims whitespace.
     *
     * @param academicPeriod the academic period to verify
     * @param currentId      the ID of the current academic period (null for save, actual ID for update)
     * @throws AcademicPeriodAlreadyExistsException if an academic period with the same name exists
     */
    private void verifyThatAcademicPeriodDoesNotExist(AcademicPeriod academicPeriod, Long currentId) {
        String normalizedName = academicPeriod.getName().trim().toLowerCase();
        
        List<AcademicPeriod> existingPeriods = academicPeriodPersistencePort.findAll();
        boolean exists = existingPeriods.stream()
                .filter(period -> currentId == null || !period.getId().equals(currentId))
                .anyMatch(period -> period.getName().trim().toLowerCase().equals(normalizedName));
        
        if (exists) {
            throw new AcademicPeriodAlreadyExistsException(
                    String.format("Ya existe un período académico con el nombre '%s'", 
                            academicPeriod.getName().trim())
            );
        }
    }
}
