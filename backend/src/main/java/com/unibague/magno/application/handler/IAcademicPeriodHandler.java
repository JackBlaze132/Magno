package com.unibague.magno.application.handler;

import com.unibague.magno.application.dto.request.AcademicPeriodRequest;
import com.unibague.magno.application.dto.response.AcademicPeriodResponse;

import java.util.List;

public interface IAcademicPeriodHandler {
    AcademicPeriodResponse findById(Long id);
    AcademicPeriodResponse save(AcademicPeriodRequest academicPeriod);
    void deleteById(Long id);
    List<AcademicPeriodResponse> findAll();
}
