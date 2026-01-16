package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.ResearchSeedbedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for {@link ResearchSeedbedEntity}.
 */
public interface IResearchSeedbedRepository extends JpaRepository<ResearchSeedbedEntity, Long> {

    /**
     * Finds research seedbeds where the specified user is a participant.
     */
    @Query("""
        SELECT DISTINCT rs
        FROM ResearchSeedbedEntity rs
        JOIN rs.researchSeedbedProfiles rsp
        JOIN rsp.researchSeedbedProfiles rssp
        JOIN rssp.studentProfile sp
        JOIN sp.user u
        WHERE u.id = :userId
        """)
    List<ResearchSeedbedEntity> findResearchSeedbedsByUserId(@Param("userId") Long userId);

    /**
     * Finds research seedbeds that have at least one associated profile.
     */
    @Query("SELECT DISTINCT rs FROM ResearchSeedbedEntity rs " +
            "JOIN rs.researchSeedbedProfiles rsp")
    List<ResearchSeedbedEntity> findResearchSeedbedsWithAssociatedProfiles();
}
