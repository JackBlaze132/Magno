package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.AcademicPeriod;

import java.util.List;

public interface IAcademicPeriodServicePort {
    AcademicPeriod findById(Long id);
    AcademicPeriod save(AcademicPeriod academicPeriod);
    void deleteById(Long id);
    List<AcademicPeriod> findAll();
}
