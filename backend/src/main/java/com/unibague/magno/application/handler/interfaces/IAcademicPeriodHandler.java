package com.unibague.magno.application.handler.interfaces;

import com.unibague.magno.application.dto.request.AcademicPeriodRequest;
import com.unibague.magno.application.dto.response.AcademicPeriodResponse;

import java.util.List;

/**
 * Handler interface for academic period operations.
 * Acts as the application layer bridge between REST controllers and domain services,
 * handling DTO-to-model conversion for academic period management.
 */
public interface IAcademicPeriodHandler {
    AcademicPeriodResponse findById(Long id);
    AcademicPeriodResponse save(AcademicPeriodRequest academicPeriod);
    AcademicPeriodResponse updateById(Long id, AcademicPeriodRequest academicPeriod);
    void deleteById(Long id);
    List<AcademicPeriodResponse> findAll();
    
    /**
     * Retrieves the single active academic period.
     *
     * @return the active academic period response
     */
    AcademicPeriodResponse findActiveAcademicPeriod();
}
