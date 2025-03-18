package com.unibague.magno.domain.spi;

import com.unibague.magno.domain.model.InvestigationGroupProfile;

import java.util.List;
import java.util.Optional;

public interface IInvestigationGroupProfilePersistencePort {
    Optional<InvestigationGroupProfile> findById(Long id);
    InvestigationGroupProfile save(InvestigationGroupProfile investigationGroupProfile);
    InvestigationGroupProfile update(Long id, InvestigationGroupProfile investigationGroupProfile);
    void deleteById(Long id);
    List<InvestigationGroupProfile> findAll();
    List<InvestigationGroupProfile> findAllByAcademicPeriodId(Long academicPeriodId);
}
