package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.ResearchSeedbedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IResearchSeedbedRepository extends JpaRepository<ResearchSeedbedEntity, Long> {
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
}
