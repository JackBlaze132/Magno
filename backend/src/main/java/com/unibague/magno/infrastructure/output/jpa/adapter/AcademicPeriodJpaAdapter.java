package com.unibague.magno.infrastructure.output.jpa.adapter;

import com.unibague.magno.domain.exception.academicperiod.AcademicPeriodHasInvestigationGroupProfilesException;
import com.unibague.magno.domain.model.AcademicPeriod;
import com.unibague.magno.domain.spi.IAcademicPeriodPersistencePort;
import com.unibague.magno.infrastructure.output.jpa.entity.AcademicPeriodEntity;
import com.unibague.magno.infrastructure.output.jpa.mapper.AcademicPeriodEntityMapper;
import com.unibague.magno.infrastructure.output.jpa.repository.IAcademicPeriodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * JPA implementation of {@link IAcademicPeriodPersistencePort} for managing academic period persistence.
 * Handles database operations for academic periods using Spring Data JPA.
 */
@RequiredArgsConstructor
@Transactional
public class AcademicPeriodJpaAdapter implements IAcademicPeriodPersistencePort {

    private final IAcademicPeriodRepository academicPeriodRepository;
    private final AcademicPeriodEntityMapper academicPeriodEntityMapper;

    @Override
    public Optional<AcademicPeriod> findById(Long id) {
        Optional<AcademicPeriodEntity> academicPeriod = academicPeriodRepository.findById(id);
        return academicPeriod.map(academicPeriodEntityMapper::toAcademicPeriod);
    }

    @Override
    public Optional<AcademicPeriod> findByName(String name) {
        return academicPeriodRepository.findByName(name)
                .map(academicPeriodEntityMapper::toAcademicPeriod);
    }

    @Override
    public AcademicPeriod save(AcademicPeriod academicPeriod) {
        AcademicPeriodEntity academicPeriodEntity = academicPeriodEntityMapper
                .toAcademicPeriodEntity(academicPeriod);
        AcademicPeriodEntity savedAcademicPeriodEntity = academicPeriodRepository.save(academicPeriodEntity);
        return academicPeriodEntityMapper.toAcademicPeriod(savedAcademicPeriodEntity);
    }

    @Override
    public AcademicPeriod update(Long id, AcademicPeriod academicPeriod) {
        AcademicPeriodEntity existingEntity = academicPeriodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Período académico con ID " + id + " no encontrado"));
        
        existingEntity.setName(academicPeriod.getName());
        existingEntity.setStartDate(academicPeriod.getStartDate());
        existingEntity.setEndDate(academicPeriod.getEndDate());
        existingEntity.setCurrent(academicPeriod.isCurrent());
        
        AcademicPeriodEntity savedAcademicPeriodEntity = academicPeriodRepository.save(existingEntity);
        return academicPeriodEntityMapper.toAcademicPeriod(savedAcademicPeriodEntity);
    }

    @Override
    public void deleteById(Long id) {
        AcademicPeriodEntity existingEntity = academicPeriodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Período académico con ID " + id + " no encontrado"));
        if (!existingEntity.getInvestigationGroupProfiles().isEmpty()) {
            throw new AcademicPeriodHasInvestigationGroupProfilesException
                    ("No se puede eliminar el período académico " + existingEntity.getName() +
                            " porque tiene perfiles de grupo de investigación asociados");
        }
        academicPeriodRepository.deleteById(id);
    }

    @Override
    public List<AcademicPeriod> findAll() {
        List<AcademicPeriodEntity> academicPeriodEntities = academicPeriodRepository.findAll();
        return academicPeriodEntityMapper.toAcademicPeriodList(academicPeriodEntities);
    }

    @Override
    public List<AcademicPeriod> findAllActiveAndVisible() {
        List<AcademicPeriodEntity> academicPeriodEntities = academicPeriodRepository.findAllActiveAndVisible();
        return academicPeriodEntityMapper.toAcademicPeriodList(academicPeriodEntities);
    }

    @Override
    public List<AcademicPeriod> findAllVisible() {
        List<AcademicPeriodEntity> academicPeriodEntities = academicPeriodRepository.findAllVisible();
        return academicPeriodEntityMapper.toAcademicPeriodList(academicPeriodEntities);
    }

    @Override
    public List<AcademicPeriod> findAllNotVisible() {
        List<AcademicPeriodEntity> academicPeriodEntities = academicPeriodRepository.findAllNotVisible();
        return academicPeriodEntityMapper.toAcademicPeriodList(academicPeriodEntities);
    }
}
