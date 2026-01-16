package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.request.AcademicProgramRequest;
import com.unibague.magno.application.dto.response.AcademicProgramResponse;
import com.unibague.magno.application.handler.impl.AcademicProgramHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * REST controller for managing academic programs in Magno.
 * Provides endpoints for CRUD operations on academic programs,
 * which represent the university's educational offerings (undergraduate, graduate, etc.)
 * that students can enroll in.
 *
 * @see AcademicProgramHandler
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/academic-programs")
public class AcademicProgramRestController {

    private final AcademicProgramHandler academicProgramHandler;

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<AcademicProgramResponse> getAcademicProgramById(@PathVariable Long id) {
        AcademicProgramResponse response = academicProgramHandler.findById(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<List<AcademicProgramResponse>> getAllAcademicPrograms() {
        List<AcademicProgramResponse> responses = academicProgramHandler.findAll();
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @PostMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<AcademicProgramResponse> createAcademicProgram
            (@Valid @RequestBody AcademicProgramRequest academicProgramRequest) {
        AcademicProgramResponse created = academicProgramHandler.save(academicProgramRequest);
        URI location = URI.create(String.format("/api/academic-programs/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @PutMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<AcademicProgramResponse> updateAcademicProgramById
            (@PathVariable Long id, @Valid @RequestBody AcademicProgramRequest academicProgramRequest) {
        AcademicProgramResponse updated = academicProgramHandler.updateById(id, academicProgramRequest);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @DeleteMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<Void> deleteAcademicProgramById(@PathVariable Long id) {
        academicProgramHandler.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
