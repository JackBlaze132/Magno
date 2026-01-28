package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.ExternalUserProfile;
import com.unibague.magno.infrastructure.output.jpa.entity.AcademicPeriodEntity;
import com.unibague.magno.infrastructure.output.jpa.entity.ExternalUserProfileEntity;
import com.unibague.magno.infrastructure.output.jpa.entity.ResearchSeedbedProfileEntity;
import com.unibague.magno.infrastructure.output.jpa.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExternalUserProfileEntityMapperImpl implements ExternalUserProfileEntityMapper{


    @Override
    public ExternalUserProfile toExternalUserProfile(ExternalUserProfileEntity externalUserProfileEntity) {

        if ( externalUserProfileEntity == null ) {
            return null;
        }

        ExternalUserProfile externalUserProfile = new ExternalUserProfile();

        externalUserProfile.setId(externalUserProfileEntity.getId());
        externalUserProfile.setUserId(externalUserProfileEntity.getUser().getId());
        externalUserProfile.setAcademicPeriodId(externalUserProfileEntity.getAcademicPeriod().getId());
        externalUserProfile.setResearchSeedbedProfileId(externalUserProfileEntity.getResearchSeedbedProfile().getId());
        externalUserProfile.setCountry(externalUserProfileEntity.getCountry());
        externalUserProfile.setOrganizationName(externalUserProfileEntity.getOrganizationName());
        externalUserProfile.setTypeOfExternalUser(externalUserProfileEntity.getTypeOfExternalUser());

        return externalUserProfile;
    }

    @Override
    public ExternalUserProfileEntity toExternalUserProfileEntity(Long id, ExternalUserProfile externalUserProfile) {

        if ( id == null && externalUserProfile == null ) {
            return null;
        }

        ExternalUserProfileEntity externalUserProfileEntity = new ExternalUserProfileEntity();
        externalUserProfileEntity.setId(id);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(externalUserProfile.getUserId());
        externalUserProfileEntity.setUser(userEntity);

        AcademicPeriodEntity academicPeriodEntity = new AcademicPeriodEntity();
        academicPeriodEntity.setId(externalUserProfile.getAcademicPeriodId());
        externalUserProfileEntity.setAcademicPeriod(academicPeriodEntity);

        ResearchSeedbedProfileEntity researchSeedbedProfileEntity = new ResearchSeedbedProfileEntity();
        researchSeedbedProfileEntity.setId(externalUserProfile.getResearchSeedbedProfileId());
        externalUserProfileEntity.setResearchSeedbedProfile(researchSeedbedProfileEntity);

        externalUserProfileEntity.setCountry(externalUserProfile.getCountry());
        externalUserProfileEntity.setOrganizationName(externalUserProfile.getOrganizationName());
        externalUserProfileEntity.setTypeOfExternalUser(externalUserProfile.getTypeOfExternalUser());

        return externalUserProfileEntity;
    }

    @Override
    public ExternalUserProfileEntity toExternalUserProfileEntity(ExternalUserProfile externalUserProfile) {

        if ( externalUserProfile == null ) {
            return null;
        }

        ExternalUserProfileEntity externalUserProfileEntity = new ExternalUserProfileEntity();

        UserEntity userEntity = new UserEntity();
        userEntity.setId(externalUserProfile.getUserId());
        externalUserProfileEntity.setUser(userEntity);

        AcademicPeriodEntity academicPeriodEntity = new AcademicPeriodEntity();
        academicPeriodEntity.setId(externalUserProfile.getAcademicPeriodId());
        externalUserProfileEntity.setAcademicPeriod(academicPeriodEntity);

        ResearchSeedbedProfileEntity researchSeedbedProfileEntity = new ResearchSeedbedProfileEntity();
        researchSeedbedProfileEntity.setId(externalUserProfile.getResearchSeedbedProfileId());
        externalUserProfileEntity.setResearchSeedbedProfile(researchSeedbedProfileEntity);

        externalUserProfileEntity.setCountry(externalUserProfile.getCountry());
        externalUserProfileEntity.setOrganizationName(externalUserProfile.getOrganizationName());
        externalUserProfileEntity.setTypeOfExternalUser(externalUserProfile.getTypeOfExternalUser());

        return externalUserProfileEntity;
    }

    @Override
    public List<ExternalUserProfile> toExternalUserProfileList(
            List<ExternalUserProfileEntity> externalUserProfileEntities) {
        return externalUserProfileEntities.stream()
                .map(this::toExternalUserProfile)
                .toList();
    }
}
