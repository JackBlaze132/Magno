package com.unibague.magno.domain.api;

import com.unibague.magno.domain.model.InvestigationGroup;

import java.util.List;

public interface IInvestigationGroupServicePort {
    InvestigationGroup findById(Long id);
    InvestigationGroup save(InvestigationGroup investigationGroup);
    InvestigationGroup update(Long id, InvestigationGroup investigationGroup);
    void deleteById(Long id);
    List<InvestigationGroup> findAll();
}
