package com.unibague.magno.infrastructure.output.jpa.adapter;

import com.unibague.magno.domain.model.InvestigationGroup;
import com.unibague.magno.domain.spi.IInvestigationGroupPersistencePort;
import com.unibague.magno.infrastructure.output.jpa.entity.InvestigationGroupEntity;
import com.unibague.magno.infrastructure.output.jpa.mapper.InvestigationGroupEntityMapper;
import com.unibague.magno.infrastructure.output.jpa.repository.IInvestigationGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * JPA implementation of {@link IInvestigationGroupPersistencePort} for managing investigation group persistence.
 * Handles database operations for research groups using Spring Data JPA.
 */
@RequiredArgsConstructor
@Transactional
public class InvestigationGroupJpaAdapter implements IInvestigationGroupPersistencePort {

    private final IInvestigationGroupRepository investigationGroupRepository;
    private final InvestigationGroupEntityMapper investigationGroupEntityMapper;

    @Override
    public Optional<InvestigationGroup> findById(Long id) {
        Optional<InvestigationGroupEntity> investigationGroup = investigationGroupRepository.findById(id);
        return investigationGroup.map(investigationGroupEntityMapper::toInvestigationGroup);
    }

    @Override
    public InvestigationGroup save(InvestigationGroup investigationGroup) {
        InvestigationGroupEntity investigationGroupEntity = investigationGroupEntityMapper
                .toInvestigationGroupEntity(investigationGroup);
        InvestigationGroupEntity savedInvestigationGroupEntity = investigationGroupRepository
                .save(investigationGroupEntity);
        return investigationGroupEntityMapper.toInvestigationGroup(savedInvestigationGroupEntity);
    }

    @Override
    public InvestigationGroup update(Long id, InvestigationGroup investigationGroup) {
        InvestigationGroupEntity investigationGroupEntity = investigationGroupEntityMapper
                .toInvestigationGroupEntity(id, investigationGroup);
        InvestigationGroupEntity updatedInvestigationGroupEntity = investigationGroupRepository
                .save(investigationGroupEntity);
        return investigationGroupEntityMapper.toInvestigationGroup(updatedInvestigationGroupEntity);
    }

    @Override
    public void deleteById(Long id) {
        investigationGroupRepository.deleteById(id);
    }

    @Override
    public List<InvestigationGroup> findAll() {
        return investigationGroupEntityMapper.toInvestigationGroupList(investigationGroupRepository.findAll());
    }

    @Override
    public List<InvestigationGroup> findInvestigationGroupsWithAssociatedProfiles() {
        return investigationGroupEntityMapper
                .toInvestigationGroupList(investigationGroupRepository.findInvestigationGroupsWithAssociatedProfiles());
    }
}
