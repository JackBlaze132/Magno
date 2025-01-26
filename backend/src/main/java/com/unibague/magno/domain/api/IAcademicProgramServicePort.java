package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.AcademicProgram;

import java.util.List;

public interface IAcademicProgramServicePort {
    AcademicProgram findById(Long id);
    AcademicProgram save(AcademicProgram academicProgram);
    AcademicProgram update(Long id, AcademicProgram academicProgram);
    void deleteById(Long id);
    List<AcademicProgram> findAll();
}
