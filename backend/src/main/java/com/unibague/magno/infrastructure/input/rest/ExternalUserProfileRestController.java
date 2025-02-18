package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.request.ExternalUserProfileRequest;
import com.unibague.magno.application.dto.response.ExternalUserProfileResponse;
import com.unibague.magno.application.handler.ExternalUserProfileHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/external-user-profiles")
public class ExternalUserProfileRestController {

    private final ExternalUserProfileHandler externalUserProfileHandler;

    @GetMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<ExternalUserProfileResponse> getExternalUserProfileById(@PathVariable Long id) {
        ExternalUserProfileResponse response = externalUserProfileHandler.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<List<ExternalUserProfileResponse>> getAllExternalUserProfiles() {
        List<ExternalUserProfileResponse> responses = externalUserProfileHandler.findAll();
        return ResponseEntity.ok(responses);
    }

    @PostMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<ExternalUserProfileResponse> createExternalUserProfile
            (@Valid @RequestBody ExternalUserProfileRequest externalUserProfileRequest) {
        ExternalUserProfileResponse created = externalUserProfileHandler.save(externalUserProfileRequest);
        URI location = URI.create(String.format("/api/external-user-profiles/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<ExternalUserProfileResponse> updateExternalUserProfileById
            (@PathVariable Long id, @Valid @RequestBody ExternalUserProfileRequest externalUserProfileRequest) {
        ExternalUserProfileResponse updated = externalUserProfileHandler.updateById(id, externalUserProfileRequest);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<Void> deleteExternalUserProfileById(@PathVariable Long id) {
        externalUserProfileHandler.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
