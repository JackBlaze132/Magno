package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.AcademicProgramEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface IAcademicProgramRepository extends JpaRepository<AcademicProgramEntity, Long> {
    List<AcademicProgramEntity> findByProgramCodeIn(Set<String> programCodes);
    boolean existsByProgramCodeAndName(String programCode, String programName);
}
