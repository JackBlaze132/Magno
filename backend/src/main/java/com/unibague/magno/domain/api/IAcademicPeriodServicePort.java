package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.AcademicPeriod;

import java.util.List;

public interface IAcademicPeriodServicePort {
    AcademicPeriod findById(Long id);
    AcademicPeriod save(AcademicPeriod academicPeriod);
    AcademicPeriod update(Long id, AcademicPeriod academicPeriod);
    void deleteById(Long id);
    List<AcademicPeriod> findAll();
}
