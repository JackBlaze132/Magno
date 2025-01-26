package com.unibague.magno.infrastructure.output.jpa.adapter;

import com.unibague.magno.domain.model.AcademicProgram;
import com.unibague.magno.domain.spi.IAcademicProgramPersistencePort;
import com.unibague.magno.infrastructure.output.jpa.entity.AcademicProgramEntity;
import com.unibague.magno.infrastructure.output.jpa.mapper.AcademicProgramEntityMapper;
import com.unibague.magno.infrastructure.output.jpa.repository.IAcademicProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
public class AcademicProgramJpaAdapter implements IAcademicProgramPersistencePort {

    private final IAcademicProgramRepository academicProgramRepository;
    private final AcademicProgramEntityMapper academicProgramEntityMapper;

    @Override
    public Optional<AcademicProgram> findById(Long id) {
        Optional<AcademicProgramEntity> academicProgram = academicProgramRepository.findById(id);
        return academicProgram.map(academicProgramEntityMapper::toAcademicProgram);
    }

    @Override
    public AcademicProgram save(AcademicProgram academicProgram) {
        AcademicProgramEntity academicProgramEntity = academicProgramEntityMapper
                .toAcademicProgramEntity(academicProgram);
        AcademicProgramEntity savedAcademicProgramEntity = academicProgramRepository.save(academicProgramEntity);
        return academicProgramEntityMapper.toAcademicProgram(savedAcademicProgramEntity);
    }

    @Override
    public AcademicProgram update(Long id, AcademicProgram academicProgram) {
        AcademicProgramEntity academicProgramEntity = academicProgramEntityMapper
                .toAcademicProgramEntity(id, academicProgram);
        AcademicProgramEntity savedAcademicProgramEntity = academicProgramRepository.save(academicProgramEntity);
        return academicProgramEntityMapper.toAcademicProgram(savedAcademicProgramEntity);
    }

    @Override
    public void deleteById(Long id) {
        academicProgramRepository.deleteById(id);
    }

    @Override
    public List<AcademicProgram> findAll() {
        return academicProgramEntityMapper.toAcademicProgramList(academicProgramRepository.findAll());
    }
}
