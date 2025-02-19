package com.unibague.magno.domain.spi;

import com.unibague.magno.domain.model.AcademicProgram;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IAcademicProgramPersistencePort {
    Optional<AcademicProgram> findById(Long id);
    AcademicProgram save(AcademicProgram academicProgram);
    AcademicProgram update(Long id, AcademicProgram academicProgram);
    void deleteById(Long id);
    List<AcademicProgram> findAll();
    Set<AcademicProgram> findAcademicProgramsByIds(Set<Long> ids);
    Set<AcademicProgram> findAcademicProgramsByAcademicProgramCodes(Set<String> academicProgramCodes);
    List<AcademicProgram> saveAll();
}
