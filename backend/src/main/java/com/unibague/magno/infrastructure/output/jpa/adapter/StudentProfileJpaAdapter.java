package com.unibague.magno.infrastructure.output.jpa.adapter;

import com.unibague.magno.domain.model.StudentProfile;
import com.unibague.magno.domain.spi.IStudentProfilePersistencePort;
import com.unibague.magno.infrastructure.output.jpa.entity.StudentProfileEntity;
import com.unibague.magno.infrastructure.output.jpa.mapper.StudentProfileEntityMapper;
import com.unibague.magno.infrastructure.output.jpa.repository.IStudentProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
public class StudentProfileJpaAdapter implements IStudentProfilePersistencePort {

    private final IStudentProfileRepository studentProfileRepository;
    private final StudentProfileEntityMapper studentProfileEntityMapper;

    @Override
    public Optional<StudentProfile> findById(Long id) {
        Optional<StudentProfileEntity> studentProfile = studentProfileRepository.findById(id);
        return studentProfile.map(studentProfileEntityMapper::toStudentProfile);
    }

    @Override
    public StudentProfile save(StudentProfile studentProfile) {
        StudentProfileEntity studentProfileEntity = studentProfileEntityMapper
                .toStudentProfileEntity(studentProfile);
        StudentProfileEntity savedStudentProfileEntity = studentProfileRepository
                .save(studentProfileEntity);
        return studentProfileEntityMapper.toStudentProfile(savedStudentProfileEntity);
    }

    @Override
    public StudentProfile update(Long id, StudentProfile studentProfile) {
        StudentProfileEntity studentProfileEntity = studentProfileEntityMapper
                .toStudentProfileEntity(id, studentProfile);
        StudentProfileEntity updatedStudentProfileEntity = studentProfileRepository
                .save(studentProfileEntity);
        return studentProfileEntityMapper.toStudentProfile(updatedStudentProfileEntity);
    }

    @Override
    public Optional<StudentProfile> findByStudentProfileIdentificationAndResearchSeedbedProfileId(String identification, Long researchSeedbedProfileId) {
        Optional<StudentProfileEntity> studentProfile = studentProfileRepository
                .findByStudentProfileIdentificationAndResearchSeedbedProfileId(identification, researchSeedbedProfileId);
        return studentProfile.map(studentProfileEntityMapper::toStudentProfile);
    }

    @Override
    public void deleteById(Long id) {
        studentProfileRepository.deleteById(id);
    }

    @Override
    public List<StudentProfile> findAll() {
        return studentProfileEntityMapper.toStudentProfileList(studentProfileRepository.findAll());
    }
}
