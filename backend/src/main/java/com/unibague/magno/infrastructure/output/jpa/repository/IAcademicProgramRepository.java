package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.AcademicProgramEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAcademicProgramRepository extends JpaRepository<AcademicProgramEntity, Long> {
}
