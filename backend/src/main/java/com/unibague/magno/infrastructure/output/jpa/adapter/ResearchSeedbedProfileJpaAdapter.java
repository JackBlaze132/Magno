package com.unibague.magno.infrastructure.output.jpa.adapter;

import com.unibague.magno.domain.model.ResearchSeedbedProfile;
import com.unibague.magno.domain.model.projections.SeedbedReportProjection;
import com.unibague.magno.domain.spi.IResearchSeedbedProfilePersistencePort;
import com.unibague.magno.infrastructure.output.jpa.entity.ResearchSeedbedProfileEntity;
import com.unibague.magno.infrastructure.output.jpa.mapper.ResearchSeedbedProfileEntityMapper;
import com.unibague.magno.infrastructure.output.jpa.repository.IResearchSeedbedProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
public class ResearchSeedbedProfileJpaAdapter implements IResearchSeedbedProfilePersistencePort {

    private final IResearchSeedbedProfileRepository researchSeedbedProfileRepository;
    private final ResearchSeedbedProfileEntityMapper researchSeedbedProfileEntityMapper;

    @Override
    public Optional<ResearchSeedbedProfile> findById(Long id) {
        Optional<ResearchSeedbedProfileEntity> researchSeedbedProfile
                = researchSeedbedProfileRepository.findById(id);
        return researchSeedbedProfile.map(researchSeedbedProfileEntityMapper::toResearchSeedbedProfile);
    }

    @Override
    public ResearchSeedbedProfile save(ResearchSeedbedProfile researchSeedbedProfile) {
        ResearchSeedbedProfileEntity researchSeedbedProfileEntity = researchSeedbedProfileEntityMapper
                .toResearchSeedbedProfileEntity(researchSeedbedProfile);
        ResearchSeedbedProfileEntity savedResearchSeedbedProfileEntity = researchSeedbedProfileRepository
                .save(researchSeedbedProfileEntity);
        return researchSeedbedProfileEntityMapper.toResearchSeedbedProfile(savedResearchSeedbedProfileEntity);
    }

    @Override
    public ResearchSeedbedProfile update(Long id, ResearchSeedbedProfile researchSeedbedProfile) {
        ResearchSeedbedProfileEntity researchSeedbedProfileEntity = researchSeedbedProfileEntityMapper
                .toResearchSeedbedProfileEntity(id, researchSeedbedProfile);
        ResearchSeedbedProfileEntity updatedResearchSeedbedProfileEntity = researchSeedbedProfileRepository
                .save(researchSeedbedProfileEntity);
        return researchSeedbedProfileEntityMapper.toResearchSeedbedProfile(updatedResearchSeedbedProfileEntity);
    }

    @Override
    public void deleteById(Long id) {
        researchSeedbedProfileRepository.deleteById(id);
    }

    @Override
    public List<ResearchSeedbedProfile> findAll() {
        return researchSeedbedProfileEntityMapper
                .toResearchSeedbedProfileList(researchSeedbedProfileRepository.findAll());
    }

    @Override
    public List<ResearchSeedbedProfile> findAllByInvestigationGroupProfileId(Long id) {
        return researchSeedbedProfileEntityMapper
                .toResearchSeedbedProfileList(researchSeedbedProfileRepository.findAllByInvestigationGroupProfileId(id));
    }

    @Override
    public List<SeedbedReportProjection> getSeedbedReportById(Long researchSeedbedProfileId, Long academicPeriodId) {
        return researchSeedbedProfileRepository
                .getSeedbedReportById(researchSeedbedProfileId, academicPeriodId);
    }
}
