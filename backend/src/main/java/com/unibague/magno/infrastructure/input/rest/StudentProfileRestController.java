package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.request.StudentProfileRequest;
import com.unibague.magno.application.dto.response.StudentProfileResponse;
import com.unibague.magno.application.handler.impl.StudentProfileHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student-profiles")
public class StudentProfileRestController {

    private final StudentProfileHandler studentProfileHandler;

    @GetMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<StudentProfileResponse> getStudentProfileById(@PathVariable Long id) {
        StudentProfileResponse response = studentProfileHandler.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<List<StudentProfileResponse>> getAllStudentProfiles() {
        List<StudentProfileResponse> responses = studentProfileHandler.findAll();
        return ResponseEntity.ok(responses);
    }

    @GetMapping(path = "/find-all-profiles/{userId}", headers = "API-VERSION=1")
    public ResponseEntity<List<StudentProfileResponse>> getAllStudentProfilesByUserId(@PathVariable Long userId) {
        List<StudentProfileResponse> responses = studentProfileHandler.findAllProfilesByUserId(userId);
        return ResponseEntity.ok(responses);
    }

    @GetMapping(path = "/academic-period/{academicPeriodId}", headers = "API-VERSION=1")
    public ResponseEntity<List<StudentProfileResponse>> getAllStudentProfilesByAcademicPeriodId(@PathVariable Long academicPeriodId) {
        List<StudentProfileResponse> responses = studentProfileHandler.findAllProfilesByAcademicPeriodId(academicPeriodId);
        return ResponseEntity.ok(responses);
    }

    @PostMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<StudentProfileResponse> createStudentProfile
            (@Valid @RequestBody StudentProfileRequest studentProfileRequest) {
        StudentProfileResponse created = studentProfileHandler.save(studentProfileRequest);
        URI location = URI.create(String.format("/api/student-profiles/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<StudentProfileResponse> updateStudentProfileById
            (@PathVariable Long id, @Valid @RequestBody StudentProfileRequest studentProfileRequest) {
        StudentProfileResponse updated = studentProfileHandler.updateById(id, studentProfileRequest);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<Void> deleteStudentProfileById(@PathVariable Long id) {
        studentProfileHandler.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
