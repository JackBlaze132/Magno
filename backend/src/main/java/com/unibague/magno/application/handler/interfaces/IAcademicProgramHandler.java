package com.unibague.magno.application.handler.interfaces;

import com.unibague.magno.application.dto.request.AcademicProgramRequest;
import com.unibague.magno.application.dto.response.AcademicProgramResponse;

import java.util.List;

/**
 * Handler interface for academic program operations.
 * Acts as the application layer bridge between REST controllers and domain services,
 * handling DTO-to-model conversion for academic program management.
 */
public interface IAcademicProgramHandler {
    AcademicProgramResponse findById(Long id);
    AcademicProgramResponse save(AcademicProgramRequest academicProgram);
    AcademicProgramResponse updateById(Long id, AcademicProgramRequest academicProgram);
    void deleteById(Long id);
    List<AcademicProgramResponse> findAll();
}
