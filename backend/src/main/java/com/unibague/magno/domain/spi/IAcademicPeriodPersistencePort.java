package com.unibague.magno.domain.spi;

import com.unibague.magno.domain.model.AcademicPeriod;

import java.util.List;
import java.util.Optional;

public interface IAcademicPeriodPersistencePort {
    Optional<AcademicPeriod> findById(Long id);
    Optional<AcademicPeriod> findByName(String name);
    AcademicPeriod save(AcademicPeriod academicPeriod);
    AcademicPeriod update(Long id, AcademicPeriod academicPeriod);
    void deleteById(Long id);
    List<AcademicPeriod> findAll();
}
