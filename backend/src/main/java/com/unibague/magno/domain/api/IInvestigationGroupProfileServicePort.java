package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.InvestigationGroupProfile;

import java.util.List;

public interface IInvestigationGroupProfileServicePort {
    InvestigationGroupProfile findById(Long id);
    InvestigationGroupProfile save(InvestigationGroupProfile investigationGroupProfile);
    InvestigationGroupProfile update(Long id, InvestigationGroupProfile investigationGroupProfile);
    void deleteById(Long id);
    List<InvestigationGroupProfile> findAll();
    List<InvestigationGroupProfile> findAllByAcademicPeriodId(Long academicPeriodId);
}
