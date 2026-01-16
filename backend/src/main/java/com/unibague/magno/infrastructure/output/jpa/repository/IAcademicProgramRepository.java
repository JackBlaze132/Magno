package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.AcademicProgramEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Spring Data JPA repository for {@link AcademicProgramEntity}.
 */
public interface IAcademicProgramRepository extends JpaRepository<AcademicProgramEntity, Long> {

    /**
     * Finds academic programs by a set of program codes.
     */
    List<AcademicProgramEntity> findByProgramCodeIn(Set<String> programCodes);

    /**
     * Finds an academic program by its program code.
     */
    Optional<AcademicProgramEntity> findByProgramCode(String programCode);

    /**
     * Checks if an academic program exists with the given code and name.
     */
    boolean existsByProgramCodeAndName(String programCode, String programName);
}
