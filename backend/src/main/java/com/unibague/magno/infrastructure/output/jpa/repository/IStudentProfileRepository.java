package com.unibague.magno.infrastructure.output.jpa.repository;

import com.unibague.magno.infrastructure.output.jpa.entity.StudentProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStudentProfileRepository extends JpaRepository<StudentProfileEntity, Long> {
}
