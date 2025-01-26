package com.unibague.magno.infrastructure.output.jpa.adapter;

import com.unibague.magno.domain.exception.AcademicPeriodNotFoundException;
import com.unibague.magno.domain.model.AcademicPeriod;
import com.unibague.magno.domain.spi.IAcademicPeriodPersistencePort;
import com.unibague.magno.infrastructure.output.jpa.entity.AcademicPeriodEntity;
import com.unibague.magno.infrastructure.output.jpa.mapper.AcademicPeriodEntityMapper;
import com.unibague.magno.infrastructure.output.jpa.repository.IAcademicPeriodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public AcademicPeriod save(AcademicPeriod academicPeriod) {
        AcademicPeriodEntity academicPeriodEntity = academicPeriodEntityMapper
                .toAcademicPeriodEntity(academicPeriod);
        AcademicPeriodEntity savedAcademicPeriodEntity = academicPeriodRepository.save(academicPeriodEntity);
        return academicPeriodEntityMapper.toAcademicPeriod(savedAcademicPeriodEntity);
    }

    @Override
    public AcademicPeriod update(Long id, AcademicPeriod academicPeriod) {
        AcademicPeriodEntity academicPeriodEntity = academicPeriodEntityMapper
                .toAcademicPeriodEntity(id, academicPeriod);
        AcademicPeriodEntity savedAcademicPeriodEntity = academicPeriodRepository.save(academicPeriodEntity);
        return academicPeriodEntityMapper.toAcademicPeriod(savedAcademicPeriodEntity);
    }

    @Override
    public void deleteById(Long id) {
        academicPeriodRepository.deleteById(id);
    }

    @Override
    public List<AcademicPeriod> findAll() {
        List<AcademicPeriodEntity> academicPeriodEntities = academicPeriodRepository.findAll();
        return academicPeriodEntityMapper.toAcademicPeriodList(academicPeriodEntities);
    }
}
