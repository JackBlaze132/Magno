package com.unibague.magno.infrastructure.output.jpa.adapter;

import com.unibague.magno.domain.model.ResearchSeedbedStudentProfile;
import com.unibague.magno.domain.spi.IResearchSeedbedStudentProfilePersistencePort;
import com.unibague.magno.infrastructure.output.jpa.entity.ResearchSeedbedStudentProfileEntity;
import com.unibague.magno.infrastructure.output.jpa.mapper.ResearchSeedbedStudentProfileEntityMapper;
import com.unibague.magno.infrastructure.output.jpa.repository.IResearchSeedbedStudentProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
public class ResearchSeedbedStudentProfileJpaAdapter implements IResearchSeedbedStudentProfilePersistencePort {

    private final IResearchSeedbedStudentProfileRepository researchSeedbedStudentProfileRepository;
    private final ResearchSeedbedStudentProfileEntityMapper researchSeedbedStudentProfileMapper;

    @Override
    public Optional<ResearchSeedbedStudentProfile> findById(Long id) {
        Optional<ResearchSeedbedStudentProfileEntity> researchSeedbedStudentProfile
                = researchSeedbedStudentProfileRepository.findById(id);
        return researchSeedbedStudentProfile.map(researchSeedbedStudentProfileMapper::toResearchSeedbedStudentProfile);
    }

    @Override
    public ResearchSeedbedStudentProfile save(ResearchSeedbedStudentProfile researchSeedbedStudentProfile) {
        ResearchSeedbedStudentProfileEntity researchSeedbedStudentProfileEntity = researchSeedbedStudentProfileMapper
                .toResearchSeedbedStudentProfileEntity(researchSeedbedStudentProfile);
        ResearchSeedbedStudentProfileEntity savedResearchSeedbedStudentProfileEntity =
                researchSeedbedStudentProfileRepository.save(researchSeedbedStudentProfileEntity);
        return researchSeedbedStudentProfileMapper.toResearchSeedbedStudentProfile(savedResearchSeedbedStudentProfileEntity);
    }

    @Override
    public ResearchSeedbedStudentProfile update(Long id, ResearchSeedbedStudentProfile researchSeedbedStudentProfile) {
        ResearchSeedbedStudentProfileEntity researchSeedbedStudentProfileEntity = researchSeedbedStudentProfileMapper
                .toResearchSeedbedStudentProfileEntity(id, researchSeedbedStudentProfile);
        ResearchSeedbedStudentProfileEntity updatedResearchSeedbedStudentProfileEntity =
                researchSeedbedStudentProfileRepository.save(researchSeedbedStudentProfileEntity);
        return researchSeedbedStudentProfileMapper.toResearchSeedbedStudentProfile(updatedResearchSeedbedStudentProfileEntity);
    }

    @Override
    public void deleteById(Long id) {
        researchSeedbedStudentProfileRepository.deleteById(id);
    }

    @Override
    public List<ResearchSeedbedStudentProfile> findAll() {
        return researchSeedbedStudentProfileMapper
                .toResearchSeedbedStudentProfileList(researchSeedbedStudentProfileRepository.findAll());
    }
}
