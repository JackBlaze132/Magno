package com.unibague.magno.infrastructure.output.jpa.adapter;

import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.model.AcademicProgram;
import com.unibague.magno.domain.spi.IAcademicProgramPersistencePort;
import com.unibague.magno.infrastructure.output.jpa.entity.AcademicProgramEntity;
import com.unibague.magno.infrastructure.output.jpa.mapper.AcademicProgramEntityMapper;
import com.unibague.magno.infrastructure.output.jpa.repository.IAcademicProgramRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
public class AcademicProgramJpaAdapter implements IAcademicProgramPersistencePort {

    private final IAcademicProgramRepository academicProgramRepository;
    private final AcademicProgramEntityMapper academicProgramEntityMapper;
    private final IIntegraServicePort integraServicePort;

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

    @Override
    public Set<AcademicProgram> findAcademicProgramsByIds(Set<Long> ids) {
        return new HashSet<>(academicProgramEntityMapper.toAcademicProgramList(academicProgramRepository.findAllById(ids)));
    }

    @Override
    public Set<AcademicProgram> findAcademicProgramsByAcademicProgramCodes(Set<String> academicProgramCodes) {
        return new HashSet<>(academicProgramEntityMapper.toAcademicProgramList(academicProgramRepository
                .findByProgramCodeIn(academicProgramCodes)));
    }

    @Override
    public List<AcademicProgram> saveAll() {
        List<AcademicProgramEntity> existingPrograms = academicProgramRepository.findAll();
        Set<String> existingNames = extractNames(existingPrograms);
        List<AcademicProgramEntity> newPrograms = fetchNewPrograms(existingNames);
        return savePrograms(newPrograms);
    }

    @Override
    public boolean existsByProgramCodeAndProgramName(String programCode, String programName) {
        return academicProgramRepository.existsByProgramCodeAndName(programCode, programName);
    }

    private Set<String> extractNames(List<AcademicProgramEntity> programs) {
        return programs.stream()
                .map(AcademicProgramEntity::getName)
                .collect(Collectors.toSet());
    }

    private List<AcademicProgramEntity> fetchNewPrograms(Set<String> existingNames) {
        return integraServicePort.getAllAcademicPrograms().stream()
                .map(academicProgramEntityMapper::toAcademicProgramEntity)
                .filter(program -> !existingNames.contains(program.getName()))
                .toList();
    }

    private List<AcademicProgram> savePrograms(List<AcademicProgramEntity> newPrograms) {
        List<AcademicProgramEntity> savedEntities = academicProgramRepository.saveAll(newPrograms);
        return academicProgramEntityMapper.toAcademicProgramList(savedEntities);
    }
}
