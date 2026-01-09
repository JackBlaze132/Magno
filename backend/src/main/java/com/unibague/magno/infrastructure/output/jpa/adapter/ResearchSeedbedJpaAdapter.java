package com.unibague.magno.infrastructure.output.jpa.adapter;

import com.unibague.magno.domain.model.ResearchSeedbed;
import com.unibague.magno.domain.spi.IResearchSeedbedPersistencePort;
import com.unibague.magno.infrastructure.output.jpa.entity.ResearchSeedbedEntity;
import com.unibague.magno.infrastructure.output.jpa.mapper.ResearchSeedbedEntityMapper;
import com.unibague.magno.infrastructure.output.jpa.repository.IResearchSeedbedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
public class ResearchSeedbedJpaAdapter implements IResearchSeedbedPersistencePort {

    private final IResearchSeedbedRepository researchSeedbedRepository;
    private final ResearchSeedbedEntityMapper researchSeedbedEntityMapper;

    @Override
    public Optional<ResearchSeedbed> findById(Long id) {
        Optional<ResearchSeedbedEntity> researchSeedbedEntity = researchSeedbedRepository.findById(id);
        return researchSeedbedEntity.map(researchSeedbedEntityMapper::toResearchSeedbed);
    }

    @Override
    public ResearchSeedbed save(ResearchSeedbed researchSeedbed) {
        ResearchSeedbedEntity researchSeedbedEntity = researchSeedbedEntityMapper
                .toResearchSeedbedEntity(researchSeedbed);
        ResearchSeedbedEntity savedResearchSeedbedEntity = researchSeedbedRepository
                .save(researchSeedbedEntity);
        return researchSeedbedEntityMapper.toResearchSeedbed(savedResearchSeedbedEntity);
    }

    @Override
    public ResearchSeedbed update(Long id, ResearchSeedbed researchSeedbed) {
        ResearchSeedbedEntity researchSeedbedEntity = researchSeedbedEntityMapper
                .toResearchSeedbedEntity(id, researchSeedbed);
        ResearchSeedbedEntity updatedResearchSeedbedEntity = researchSeedbedRepository
                .save(researchSeedbedEntity);
        return researchSeedbedEntityMapper.toResearchSeedbed(updatedResearchSeedbedEntity);
    }

    @Override
    public void deleteById(Long id) {
        researchSeedbedRepository.deleteById(id);
    }

    @Override
    public List<ResearchSeedbed> findAll() {
        return researchSeedbedEntityMapper.toResearchSeedbedList(researchSeedbedRepository.findAll());
    }

    @Override
    public List<ResearchSeedbed> findResearchSeedbedsByUserId(Long id) {
        return researchSeedbedEntityMapper
                .toResearchSeedbedList(researchSeedbedRepository.findResearchSeedbedsByUserId(id));
    }

    @Override
    public List<ResearchSeedbed> findResearchSeedbedsWithAssociatedProfiles() {
        return researchSeedbedEntityMapper
                .toResearchSeedbedList(researchSeedbedRepository.findResearchSeedbedsWithAssociatedProfiles());
    }
}
