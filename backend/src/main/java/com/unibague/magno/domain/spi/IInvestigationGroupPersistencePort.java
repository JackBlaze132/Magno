package com.unibague.magno.domain.spi;

import com.unibague.magno.domain.model.InvestigationGroup;

import java.util.List;
import java.util.Optional;

public interface IInvestigationGroupPersistencePort {
    Optional<InvestigationGroup> findById(Long id);
    InvestigationGroup save(InvestigationGroup investigationGroup);
    InvestigationGroup update(Long id, InvestigationGroup investigationGroup);
    void deleteById(Long id);
    List<InvestigationGroup> findAll();
}
