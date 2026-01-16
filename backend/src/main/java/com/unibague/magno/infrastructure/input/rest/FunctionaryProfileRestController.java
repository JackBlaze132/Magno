package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.request.FunctionaryProfileRequest;
import com.unibague.magno.application.dto.response.FunctionaryProfileResponse;
import com.unibague.magno.application.handler.impl.FunctionaryProfileHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * REST controller for managing functionary profiles in Magno.
 * Provides endpoints for CRUD operations on functionary profiles,
 * which represent university staff members (professors, researchers, administrators)
 * and their roles within research groups and seedbeds for specific academic periods.
 *
 * @see FunctionaryProfileHandler
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/functionary-profiles")
public class FunctionaryProfileRestController {

    private final FunctionaryProfileHandler functionaryProfileHandler;

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<FunctionaryProfileResponse> getFunctionaryProfileById(@PathVariable Long id) {
        FunctionaryProfileResponse response = functionaryProfileHandler.findById(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<List<FunctionaryProfileResponse>> getAllFunctionaryProfiles() {
        List<FunctionaryProfileResponse> responses = functionaryProfileHandler.findAll();
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).TUTOR_DE_SEMILLERO)")
    @GetMapping(path = "/find-all-profiles/{userId}", headers = "API-VERSION=1")
    public ResponseEntity<List<FunctionaryProfileResponse>> getAllFunctionaryProfilesByUserId(@PathVariable Long userId) {
        List<FunctionaryProfileResponse> responses = functionaryProfileHandler.findAllProfilesByUserId(userId);
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/find-all-profiles-by-academic-period/{academicPeriodId}", headers = "API-VERSION=1")
    public ResponseEntity<List<FunctionaryProfileResponse>> getAllFunctionaryProfilesByAcademicPeriodId(@PathVariable Long academicPeriodId) {
        List<FunctionaryProfileResponse> responses = functionaryProfileHandler.findAllProfilesByAcademicPeriodId(academicPeriodId);
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @PostMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<FunctionaryProfileResponse> createFunctionaryProfile
            (@Valid @RequestBody FunctionaryProfileRequest functionaryProfileRequest) {
        FunctionaryProfileResponse created = functionaryProfileHandler.save(functionaryProfileRequest);
        URI location = URI.create(String.format("/api/functionary-profiles/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @PutMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<FunctionaryProfileResponse> updateFunctionaryProfileById
            (@PathVariable Long id, @Valid @RequestBody FunctionaryProfileRequest functionaryProfileRequest) {
        FunctionaryProfileResponse updated = functionaryProfileHandler.updateById(id, functionaryProfileRequest);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @DeleteMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<Void> deleteFunctionaryProfileById(@PathVariable Long id) {
        functionaryProfileHandler.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
