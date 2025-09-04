package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.ExternalUserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IExternalUserProfileRepository extends JpaRepository<ExternalUserProfileEntity, Long> {
    List<ExternalUserProfileEntity> findAllByUser_Id(Long userId);
    List<ExternalUserProfileEntity> findAllByResearchSeedbedProfileId(Long researchSeedbedProfileId);
}
