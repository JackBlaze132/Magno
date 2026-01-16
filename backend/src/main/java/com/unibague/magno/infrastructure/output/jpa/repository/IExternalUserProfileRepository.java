package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.ExternalUserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Spring Data JPA repository for {@link ExternalUserProfileEntity}.
 */
public interface IExternalUserProfileRepository extends JpaRepository<ExternalUserProfileEntity, Long> {

    /**
     * Finds all external user profiles by user ID.
     */
    List<ExternalUserProfileEntity> findAllByUser_Id(Long userId);

    /**
     * Finds all external user profiles by research seedbed profile ID.
     */
    List<ExternalUserProfileEntity> findAllByResearchSeedbedProfileId(Long researchSeedbedProfileId);
}
