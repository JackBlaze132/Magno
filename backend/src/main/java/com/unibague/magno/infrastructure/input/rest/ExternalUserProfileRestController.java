package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.request.ExternalUserProfileRequest;
import com.unibague.magno.application.dto.response.ExternalUserProfileResponse;
import com.unibague.magno.application.handler.impl.ExternalUserProfileHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * REST controller for managing external user profiles in Magno.
 * Provides endpoints for CRUD operations on profiles of users who are not part
 * of the University of Ibagué but participate in research seedbeds
 * (e.g., external collaborators, visiting researchers).
 *
 * @see ExternalUserProfileHandler
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/external-user-profiles")
public class ExternalUserProfileRestController {

    private final ExternalUserProfileHandler externalUserProfileHandler;

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<ExternalUserProfileResponse> getExternalUserProfileById(@PathVariable Long id) {
        ExternalUserProfileResponse response = externalUserProfileHandler.findById(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<List<ExternalUserProfileResponse>> getAllExternalUserProfiles() {
        List<ExternalUserProfileResponse> responses = externalUserProfileHandler.findAll();
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/find-all-profiles/{userId}", headers = "API-VERSION=1")
    public ResponseEntity<List<ExternalUserProfileResponse>> getAllExternalUserProfilesByUserId(@PathVariable Long userId) {
        List<ExternalUserProfileResponse> responses = externalUserProfileHandler.findAllProfilesByUserId(userId);
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).USUARIO_SIN_ROL)")
    @GetMapping(path = "/research-seedbed-profile/{researchSeedbedProfileId}", headers = "API-VERSION=1")
    public ResponseEntity<List<ExternalUserProfileResponse>> getAllExternalUserProfilesByResearchSeedbedProfileId(@PathVariable Long researchSeedbedProfileId) {
        List<ExternalUserProfileResponse> responses = externalUserProfileHandler.findAllByResearchSeedbedProfileId(researchSeedbedProfileId);
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).COORDINADOR_DE_SEMILLERO)")
    @PostMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<ExternalUserProfileResponse> createExternalUserProfile
            (@Valid @RequestBody ExternalUserProfileRequest externalUserProfileRequest) {
        ExternalUserProfileResponse created = externalUserProfileHandler.save(externalUserProfileRequest);
        URI location = URI.create(String.format("/api/external-user-profiles/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).COORDINADOR_DE_SEMILLERO)")
    @PutMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<ExternalUserProfileResponse> updateExternalUserProfileById
            (@PathVariable Long id, @Valid @RequestBody ExternalUserProfileRequest externalUserProfileRequest) {
        ExternalUserProfileResponse updated = externalUserProfileHandler.updateById(id, externalUserProfileRequest);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).COORDINADOR_DE_SEMILLERO)")
    @DeleteMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<Void> deleteExternalUserProfileById(@PathVariable Long id) {
        externalUserProfileHandler.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
