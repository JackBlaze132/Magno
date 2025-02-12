package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.StudentProfile;
import com.unibague.magno.infrastructure.output.jpa.entity.StudentProfileEntity;
import org.mapstruct.Mapping;

import java.util.List;

public interface StudentProfileEntityMapper {

    StudentProfile toStudentProfile(StudentProfileEntity studentProfileEntity);

    @Mapping(source = "id", target = "id")
    StudentProfileEntity toStudentProfileEntity(Long id, StudentProfile studentProfile);

    StudentProfileEntity toStudentProfileEntity(StudentProfile studentProfile);
    List<StudentProfile> toStudentProfileList(List<StudentProfileEntity> studentProfileEntities);
}
