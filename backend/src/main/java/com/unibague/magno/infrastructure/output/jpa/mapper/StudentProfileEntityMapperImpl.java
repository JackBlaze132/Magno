package com.unibague.magno.infrastructure.output.jpa.mapper;

import com.unibague.magno.domain.model.StudentProfile;
import com.unibague.magno.infrastructure.output.jpa.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentProfileEntityMapperImpl implements StudentProfileEntityMapper{


    @Override
    public StudentProfile toStudentProfile(StudentProfileEntity studentProfileEntity) {

        if ( studentProfileEntity == null ) {
            return null;
        }

        StudentProfile studentProfile = new StudentProfile();

        studentProfile.setId(studentProfileEntity.getId());
        studentProfile.setUserId(studentProfileEntity.getUser().getId());
        studentProfile.setAcademicPeriodId(studentProfileEntity.getAcademicPeriod().getId());
        studentProfile.setSemester(studentProfileEntity.getSemester());
        studentProfile.setAcademicProgramsIds(studentProfileEntity.getAcademicPrograms()
                .stream()
                .map(AcademicProgramEntity::getId)
                .collect(Collectors.toSet()));
        studentProfile.setRoleId(studentProfileEntity.getRole().getId());

        return studentProfile;
    }

    @Override
    public StudentProfileEntity toStudentProfileEntity(Long id, StudentProfile studentProfile) {

        if ( id == null && studentProfile == null ) {
            return null;
        }

        StudentProfileEntity studentProfileEntity = new StudentProfileEntity();
        studentProfileEntity.setId( id );
        studentProfileEntity.setSemester(studentProfile.getSemester());

        UserEntity userEntity = new UserEntity();
        userEntity.setId(studentProfile.getUserId());
        studentProfileEntity.setUser(userEntity);

        AcademicPeriodEntity academicPeriodEntity = new AcademicPeriodEntity();
        academicPeriodEntity.setId(studentProfile.getAcademicPeriodId());
        studentProfileEntity.setAcademicPeriod(academicPeriodEntity);

        studentProfileEntity.setAcademicPrograms(studentProfile.getAcademicProgramsIds()
                .stream()
                .map(academicProgramId ->{
                    AcademicProgramEntity academicProgramEntity = new AcademicProgramEntity();
                    academicProgramEntity.setId(academicProgramId);
                    return academicProgramEntity;
                })
                .collect(Collectors.toSet()));

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(studentProfile.getRoleId());
        studentProfileEntity.setRole(roleEntity);

        return studentProfileEntity;
    }

    @Override
    public StudentProfileEntity toStudentProfileEntity(StudentProfile studentProfile) {

        if ( studentProfile == null ) {
            return null;
        }

        StudentProfileEntity studentProfileEntity = new StudentProfileEntity();
        studentProfileEntity.setId(studentProfile.getId());
        studentProfileEntity.setSemester(studentProfile.getSemester());

        UserEntity userEntity = new UserEntity();
        userEntity.setId(studentProfile.getUserId());
        studentProfileEntity.setUser(userEntity);

        AcademicPeriodEntity academicPeriodEntity = new AcademicPeriodEntity();
        academicPeriodEntity.setId(studentProfile.getAcademicPeriodId());
        studentProfileEntity.setAcademicPeriod(academicPeriodEntity);

        studentProfileEntity.setAcademicPrograms(studentProfile.getAcademicProgramsIds()
                .stream()
                .map(academicProgramId ->{
                    AcademicProgramEntity academicProgramEntity = new AcademicProgramEntity();
                    academicProgramEntity.setId(academicProgramId);
                    return academicProgramEntity;
                })
                .collect(Collectors.toSet()));

        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setId(studentProfile.getRoleId());
        studentProfileEntity.setRole(roleEntity);

        return studentProfileEntity;
    }

    @Override
    public List<StudentProfile> toStudentProfileList(List<StudentProfileEntity> studentProfileEntities) {
        if ( studentProfileEntities == null ) {
            return null;
        }

        return studentProfileEntities.stream()
                .map(this::toStudentProfile)
                .toList();
    }
}
