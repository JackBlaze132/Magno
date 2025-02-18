package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.ResearchSeedbedProfile;
import com.unibague.magno.infrastructure.output.jpa.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResearchSeedbedProfileEntityMapperImpl implements ResearchSeedbedProfileEntityMapper{


    @Override
    public ResearchSeedbedProfile toResearchSeedbedProfile(ResearchSeedbedProfileEntity researchSeedbedProfileEntity) {

        if (researchSeedbedProfileEntity == null){
            return null;
        }

        ResearchSeedbedProfile researchSeedbedProfile = new ResearchSeedbedProfile();

        researchSeedbedProfile.setId(researchSeedbedProfileEntity.getId());
        researchSeedbedProfile.setResearchSeedbedId(researchSeedbedProfileEntity.getResearchSeedbed().getId());
        researchSeedbedProfile.setCoordinatorId(researchSeedbedProfileEntity.getCoordinator().getId());

        Long tutorId = researchSeedbedProfileEntity.getTutor() != null ?
                researchSeedbedProfileEntity.getTutor().getId() : null;
        researchSeedbedProfile.setTutorId(tutorId);

        researchSeedbedProfile.setInvestigationGroupProfileId(researchSeedbedProfileEntity
                .getInvestigationGroupProfile().getId());
        researchSeedbedProfile.setAcademicPeriodId(researchSeedbedProfileEntity.getAcademicPeriod().getId());
        researchSeedbedProfile.setWasActive(researchSeedbedProfileEntity.getWasActive());

        return researchSeedbedProfile;
    }

    @Override
    public ResearchSeedbedProfileEntity toResearchSeedbedProfileEntity(Long id, ResearchSeedbedProfile researchSeedbedProfile) {

        if (id == null && researchSeedbedProfile == null){
            return null;
        }

        ResearchSeedbedProfileEntity researchSeedbedProfileEntity = new ResearchSeedbedProfileEntity();

        researchSeedbedProfileEntity.setId(id);

        ResearchSeedbedEntity researchSeedbedEntity = new ResearchSeedbedEntity();
        researchSeedbedEntity.setId(researchSeedbedProfile.getResearchSeedbedId());
        researchSeedbedProfileEntity.setResearchSeedbed(researchSeedbedEntity);

        FunctionaryProfileEntity coordinatorEntity = new FunctionaryProfileEntity();
        coordinatorEntity.setId(researchSeedbedProfile.getCoordinatorId());
        researchSeedbedProfileEntity.setCoordinator(coordinatorEntity);

        Long tutorId = researchSeedbedProfile.getTutorId();
        if ((tutorId != null) && (!tutorId.equals(coordinatorEntity.getId()))){
            FunctionaryProfileEntity tutorEntity = new FunctionaryProfileEntity();
            tutorEntity.setId(researchSeedbedProfile.getTutorId());
            researchSeedbedProfileEntity.setTutor(tutorEntity);
        }

        InvestigationGroupProfileEntity investigationGroupProfileEntity = new InvestigationGroupProfileEntity();
        investigationGroupProfileEntity.setId(researchSeedbedProfile.getInvestigationGroupProfileId());
        researchSeedbedProfileEntity.setInvestigationGroupProfile(investigationGroupProfileEntity);

        AcademicPeriodEntity academicPeriodEntity = new AcademicPeriodEntity();
        academicPeriodEntity.setId(researchSeedbedProfile.getAcademicPeriodId());
        researchSeedbedProfileEntity.setAcademicPeriod(academicPeriodEntity);

        researchSeedbedProfileEntity.setWasActive(researchSeedbedProfile.getWasActive());

        return researchSeedbedProfileEntity;

    }

    @Override
    public ResearchSeedbedProfileEntity toResearchSeedbedProfileEntity(ResearchSeedbedProfile researchSeedbedProfile) {

        if (researchSeedbedProfile == null){
            return null;
        }

        ResearchSeedbedProfileEntity researchSeedbedProfileEntity = new ResearchSeedbedProfileEntity();

        ResearchSeedbedEntity researchSeedbedEntity = new ResearchSeedbedEntity();
        researchSeedbedEntity.setId(researchSeedbedProfile.getResearchSeedbedId());
        researchSeedbedProfileEntity.setResearchSeedbed(researchSeedbedEntity);

        FunctionaryProfileEntity coordinatorEntity = new FunctionaryProfileEntity();
        coordinatorEntity.setId(researchSeedbedProfile.getCoordinatorId());
        researchSeedbedProfileEntity.setCoordinator(coordinatorEntity);

        Long tutorId = researchSeedbedProfile.getTutorId();
        if (tutorId != null){
            FunctionaryProfileEntity tutorEntity = new FunctionaryProfileEntity();
            tutorEntity.setId(researchSeedbedProfile.getTutorId());
            researchSeedbedProfileEntity.setTutor(tutorEntity);
        }

        InvestigationGroupProfileEntity investigationGroupProfileEntity = new InvestigationGroupProfileEntity();
        investigationGroupProfileEntity.setId(researchSeedbedProfile.getInvestigationGroupProfileId());
        researchSeedbedProfileEntity.setInvestigationGroupProfile(investigationGroupProfileEntity);

        AcademicPeriodEntity academicPeriodEntity = new AcademicPeriodEntity();
        academicPeriodEntity.setId(researchSeedbedProfile.getAcademicPeriodId());
        researchSeedbedProfileEntity.setAcademicPeriod(academicPeriodEntity);

        researchSeedbedProfileEntity.setWasActive(researchSeedbedProfile.getWasActive());

        return researchSeedbedProfileEntity;
    }

    @Override
    public List<ResearchSeedbedProfile> toResearchSeedbedProfileList(
            List<ResearchSeedbedProfileEntity> researchSeedbedProfileEntities) {

        return researchSeedbedProfileEntities.stream()
                .map(this::toResearchSeedbedProfile)
                .toList();
    }
}
