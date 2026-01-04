package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.request.StudentProfileRequest;
import com.unibague.magno.application.dto.response.StudentProfileResponse;
import com.unibague.magno.application.handler.impl.StudentProfileHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student-profiles")
public class StudentProfileRestController {

    private final StudentProfileHandler studentProfileHandler;

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<StudentProfileResponse> getStudentProfileById(@PathVariable Long id) {
        StudentProfileResponse response = studentProfileHandler.findById(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<List<StudentProfileResponse>> getAllStudentProfiles() {
        List<StudentProfileResponse> responses = studentProfileHandler.findAll();
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).ESTUDIANTE)")
    @GetMapping(path = "/find-all-profiles/{userId}", headers = "API-VERSION=1")
    public ResponseEntity<List<StudentProfileResponse>> getAllStudentProfilesByUserId(@PathVariable Long userId) {
        List<StudentProfileResponse> responses = studentProfileHandler.findAllProfilesByUserId(userId);
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/academic-period/{academicPeriodId}", headers = "API-VERSION=1")
    public ResponseEntity<List<StudentProfileResponse>> getAllStudentProfilesByAcademicPeriodId(@PathVariable Long academicPeriodId) {
        List<StudentProfileResponse> responses = studentProfileHandler.findAllProfilesByAcademicPeriodId(academicPeriodId);
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @PostMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<StudentProfileResponse> createStudentProfile
            (@Valid @RequestBody StudentProfileRequest studentProfileRequest) {
        StudentProfileResponse created = studentProfileHandler.save(studentProfileRequest);
        URI location = URI.create(String.format("/api/student-profiles/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @PutMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<StudentProfileResponse> updateStudentProfileById
            (@PathVariable Long id, @Valid @RequestBody StudentProfileRequest studentProfileRequest) {
        StudentProfileResponse updated = studentProfileHandler.updateById(id, studentProfileRequest);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @DeleteMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<Void> deleteStudentProfileById(@PathVariable Long id) {
        studentProfileHandler.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
