package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.exception.functionaryprofile.FunctionaryProfileNotFoundException;
import com.unibague.magno.domain.model.InvestigationGroupProfile;
import com.unibague.magno.infrastructure.output.jpa.entity.AcademicPeriodEntity;
import com.unibague.magno.infrastructure.output.jpa.entity.FunctionaryProfileEntity;
import com.unibague.magno.infrastructure.output.jpa.entity.InvestigationGroupEntity;
import com.unibague.magno.infrastructure.output.jpa.entity.InvestigationGroupProfileEntity;
import com.unibague.magno.infrastructure.output.jpa.repository.IFunctionaryProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InvestigationGroupProfileEntityMapperImpl implements InvestigationGroupProfileEntityMapper{

    private final IFunctionaryProfileRepository functionaryProfileRepository;

    @Override
    public InvestigationGroupProfile toInvestigationGroupProfile(
            InvestigationGroupProfileEntity investigationGroupProfileEntity) {

        if(investigationGroupProfileEntity == null){
            return null;
        }

        InvestigationGroupProfile investigationGroupProfile = new InvestigationGroupProfile();

        investigationGroupProfile.setId(investigationGroupProfileEntity.getId());

        investigationGroupProfile.setInvestigationGroupId(investigationGroupProfileEntity
                .getInvestigationGroup().getId());

        investigationGroupProfile.setCoordinatorId(investigationGroupProfileEntity.getCoordinator().getId());

        investigationGroupProfile.setAcademicPeriodId(investigationGroupProfileEntity
                .getAcademicPeriod().getId());

        return investigationGroupProfile;
    }

    @Override
    public InvestigationGroupProfileEntity toInvestigationGroupProfileEntity(
            Long id, InvestigationGroupProfile investigationGroupProfile) {

        if (id == null && investigationGroupProfile == null){
            return null;
        }

        InvestigationGroupProfileEntity investigationGroupProfileEntity = new InvestigationGroupProfileEntity();
        investigationGroupProfileEntity.setId(id);

        InvestigationGroupEntity investigationGroupEntity = new InvestigationGroupEntity();
        investigationGroupEntity.setId(investigationGroupProfile.getInvestigationGroupId());
        investigationGroupProfileEntity.setInvestigationGroup(investigationGroupEntity);

        AcademicPeriodEntity academicPeriodEntity = new AcademicPeriodEntity();
        academicPeriodEntity.setId(investigationGroupProfile.getAcademicPeriodId());
        investigationGroupProfileEntity.setAcademicPeriod(academicPeriodEntity);


        FunctionaryProfileEntity coordinatorEntity = functionaryProfileRepository
                .findById(investigationGroupProfile.getCoordinatorId())
                .orElseThrow(() -> new FunctionaryProfileNotFoundException(
                        String.format("FunctionaryProfile with ID %d not found", investigationGroupProfile.getCoordinatorId())
                ));

        investigationGroupProfileEntity.setCoordinator(coordinatorEntity);

        return investigationGroupProfileEntity;
    }

    @Override
    public InvestigationGroupProfileEntity toInvestigationGroupProfileEntity(
            InvestigationGroupProfile investigationGroupProfile) {

        if (investigationGroupProfile == null){
            return null;
        }

        InvestigationGroupProfileEntity investigationGroupProfileEntity = new InvestigationGroupProfileEntity();

        InvestigationGroupEntity investigationGroupEntity = new InvestigationGroupEntity();
        investigationGroupEntity.setId(investigationGroupProfile.getInvestigationGroupId());
        investigationGroupProfileEntity.setInvestigationGroup(investigationGroupEntity);

        FunctionaryProfileEntity coordinatorEntity = new FunctionaryProfileEntity();
        coordinatorEntity.setId(investigationGroupProfile.getCoordinatorId());
        investigationGroupProfileEntity.setCoordinator(coordinatorEntity);

        AcademicPeriodEntity academicPeriodEntity = new AcademicPeriodEntity();
        academicPeriodEntity.setId(investigationGroupProfile.getAcademicPeriodId());
        investigationGroupProfileEntity.setAcademicPeriod(academicPeriodEntity);

        return investigationGroupProfileEntity;
    }

    @Override
    public List<InvestigationGroupProfile> toInvestigationGroupProfileList(
            List<InvestigationGroupProfileEntity> investigationGroupProfileEntities) {

        if (investigationGroupProfileEntities == null){
            return null;
        }

        return investigationGroupProfileEntities.stream()
                .map(this::toInvestigationGroupProfile)
                .toList();
    }
}
