package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.ResearchSeedbedStudentProfile;
import com.unibague.magno.infrastructure.output.jpa.entity.ResearchSeedbedProfileEntity;
import com.unibague.magno.infrastructure.output.jpa.entity.ResearchSeedbedStudentProfileEntity;
import com.unibague.magno.infrastructure.output.jpa.entity.StudentProfileEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ResearchSeedbedStudentProfileEntityMapperImpl implements ResearchSeedbedStudentProfileEntityMapper{


    @Override
    public ResearchSeedbedStudentProfile toResearchSeedbedStudentProfile(
            ResearchSeedbedStudentProfileEntity researchSeedbedStudentProfileEntity) {

        if (researchSeedbedStudentProfileEntity == null){
            return null;
        }

        ResearchSeedbedStudentProfile researchSeedbedStudentProfile = new ResearchSeedbedStudentProfile();
        researchSeedbedStudentProfile.setId(researchSeedbedStudentProfileEntity.getId());
        researchSeedbedStudentProfile.setResearchSeedbedProfileId(researchSeedbedStudentProfileEntity
                .getResearchSeedbedProfile().getId());
        researchSeedbedStudentProfile.setStudentProfileId(researchSeedbedStudentProfileEntity
                .getStudentProfile().getId());
        researchSeedbedStudentProfile.setWasActive(researchSeedbedStudentProfileEntity.getWasActive());
        researchSeedbedStudentProfile.setLeader(researchSeedbedStudentProfileEntity.getIsLeader());

        return researchSeedbedStudentProfile;

    }

    @Override
    public ResearchSeedbedStudentProfileEntity toResearchSeedbedStudentProfileEntity(
            Long id, ResearchSeedbedStudentProfile researchSeedbedStudentProfile) {

        if (id == null && researchSeedbedStudentProfile == null){
            return null;
        }

        ResearchSeedbedStudentProfileEntity researchSeedbedStudentProfileEntity =
                new ResearchSeedbedStudentProfileEntity();
        researchSeedbedStudentProfileEntity.setId(id);

        ResearchSeedbedProfileEntity researchSeedbedProfileEntity = new ResearchSeedbedProfileEntity();
        researchSeedbedProfileEntity.setId(researchSeedbedStudentProfile.getResearchSeedbedProfileId());
        researchSeedbedStudentProfileEntity.setResearchSeedbedProfile(researchSeedbedProfileEntity);

        StudentProfileEntity studentProfileEntity = new StudentProfileEntity();
        studentProfileEntity.setId(researchSeedbedStudentProfile.getStudentProfileId());
        researchSeedbedStudentProfileEntity.setStudentProfile(studentProfileEntity);

        researchSeedbedStudentProfileEntity.setWasActive(researchSeedbedStudentProfile.getWasActive());
        researchSeedbedStudentProfileEntity.setIsLeader(researchSeedbedStudentProfile.getLeader());
        return researchSeedbedStudentProfileEntity;
    }

    @Override
    public ResearchSeedbedStudentProfileEntity toResearchSeedbedStudentProfileEntity(
            ResearchSeedbedStudentProfile researchSeedbedStudentProfile) {

        ResearchSeedbedStudentProfileEntity researchSeedbedStudentProfileEntity =
                new ResearchSeedbedStudentProfileEntity();

        ResearchSeedbedProfileEntity researchSeedbedProfileEntity = new ResearchSeedbedProfileEntity();
        researchSeedbedProfileEntity.setId(researchSeedbedStudentProfile.getResearchSeedbedProfileId());
        researchSeedbedStudentProfileEntity.setResearchSeedbedProfile(researchSeedbedProfileEntity);

        StudentProfileEntity studentProfileEntity = new StudentProfileEntity();
        studentProfileEntity.setId(researchSeedbedStudentProfile.getStudentProfileId());
        researchSeedbedStudentProfileEntity.setStudentProfile(studentProfileEntity);

        researchSeedbedStudentProfileEntity.setWasActive(researchSeedbedStudentProfile.getWasActive());
        researchSeedbedStudentProfileEntity.setIsLeader(researchSeedbedStudentProfile.getLeader());
        return researchSeedbedStudentProfileEntity;
    }

    @Override
    public List<ResearchSeedbedStudentProfile> toResearchSeedbedStudentProfileList(
            List<ResearchSeedbedStudentProfileEntity> researchSeedbedStudentProfileEntities) {
        return researchSeedbedStudentProfileEntities
                .stream()
                .map(this::toResearchSeedbedStudentProfile)
                .toList();
    }
}
