package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.AcademicProgram;
import com.unibague.magno.domain.model.integra.IntegraStudent;

import java.util.List;
import java.util.Set;

public interface IAcademicProgramServicePort {
    AcademicProgram findById(Long id);
    AcademicProgram save(AcademicProgram academicProgram);
    AcademicProgram update(Long id, AcademicProgram academicProgram);
    void deleteById(Long id);
    List<AcademicProgram> findAll();
    Set<AcademicProgram> findAcademicProgramsByIds(Set<Long> ids);
    Set<AcademicProgram> findAcademicProgramsByAcademicProgramCodes(Set<String> academicProgramCodes);
    List<AcademicProgram> saveAll();
    boolean existsByProgramCodeAndProgramName(String programCode, String programName);
}
