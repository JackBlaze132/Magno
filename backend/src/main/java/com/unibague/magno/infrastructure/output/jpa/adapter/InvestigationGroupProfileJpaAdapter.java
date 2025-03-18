package com.unibague.magno.infrastructure.output.jpa.adapter;

import com.unibague.magno.domain.exception.investigationgroupprofile.InvestigationGroupProfileNotFoundException;
import com.unibague.magno.domain.model.InvestigationGroupProfile;
import com.unibague.magno.domain.spi.IInvestigationGroupProfilePersistencePort;
import com.unibague.magno.infrastructure.output.jpa.entity.FunctionaryProfileEntity;
import com.unibague.magno.infrastructure.output.jpa.entity.InvestigationGroupProfileEntity;
import com.unibague.magno.infrastructure.output.jpa.mapper.InvestigationGroupProfileEntityMapper;
import com.unibague.magno.infrastructure.output.jpa.repository.IFunctionaryProfileRepository;
import com.unibague.magno.infrastructure.output.jpa.repository.IInvestigationGroupProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
public class InvestigationGroupProfileJpaAdapter implements IInvestigationGroupProfilePersistencePort {

    private final IInvestigationGroupProfileRepository investigationGroupProfileRepository;
    private final InvestigationGroupProfileEntityMapper investigationGroupProfileEntityMapper;

    private final IFunctionaryProfileRepository functionaryProfileRepository;

    @Override
    public Optional<InvestigationGroupProfile> findById(Long id) {
        Optional<InvestigationGroupProfileEntity> investigationGroupProfile
                = investigationGroupProfileRepository.findById(id);
        return investigationGroupProfile.map(investigationGroupProfileEntityMapper::toInvestigationGroupProfile);
    }

    @Override
    public InvestigationGroupProfile save(InvestigationGroupProfile investigationGroupProfile) {
        InvestigationGroupProfileEntity investigationGroupProfileEntity = investigationGroupProfileEntityMapper
                .toInvestigationGroupProfileEntity(investigationGroupProfile);
        InvestigationGroupProfileEntity savedInvestigationGroupProfileEntity = investigationGroupProfileRepository
                .save(investigationGroupProfileEntity);
        return investigationGroupProfileEntityMapper.toInvestigationGroupProfile(savedInvestigationGroupProfileEntity);
    }

    @Override
    public InvestigationGroupProfile update(Long id, InvestigationGroupProfile investigationGroupProfile) {
        InvestigationGroupProfileEntity investigationGroupProfileEntity = investigationGroupProfileEntityMapper
                .toInvestigationGroupProfileEntity(id, investigationGroupProfile);
        InvestigationGroupProfileEntity updatedInvestigationGroupProfileEntity = investigationGroupProfileRepository
                .save(investigationGroupProfileEntity);
        return investigationGroupProfileEntityMapper.toInvestigationGroupProfile(updatedInvestigationGroupProfileEntity);
    }

    @Override
    /**
     * This method uses the repository of FunctionaryProfile to delete the bidirectional relationship between
     * InvestigationGroupProfile and FunctionaryProfile, then deletes the InvestigationGroupProfile to avoid
     * unexpected jpa exceptions.
     */
    public void deleteById(Long id) {
        InvestigationGroupProfileEntity profile = investigationGroupProfileRepository.findById(id)
                .orElseThrow(() -> new InvestigationGroupProfileNotFoundException(
                        String.format("InvestigationGroupProfile with id %s not found", id)));

        if (profile.getCoordinator() != null) {
            FunctionaryProfileEntity coordinator = profile.getCoordinator();
            coordinator.setInvestigationGroup(null);
            functionaryProfileRepository.save(coordinator);
        }

        investigationGroupProfileRepository.delete(profile);
    }

    @Override
    public List<InvestigationGroupProfile> findAll() {
        return investigationGroupProfileEntityMapper
                .toInvestigationGroupProfileList(investigationGroupProfileRepository.findAll());
    }

    @Override
    public List<InvestigationGroupProfile> findAllByAcademicPeriodId(Long academicPeriodId) {
        return investigationGroupProfileEntityMapper.toInvestigationGroupProfileList(
                investigationGroupProfileRepository.findByAcademicPeriodId(academicPeriodId)
        );
    }
}
