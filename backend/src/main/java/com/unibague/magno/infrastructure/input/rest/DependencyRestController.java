package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.request.DependencyRequest;
import com.unibague.magno.application.dto.response.DependencyResponse;
import com.unibague.magno.application.handler.impl.DependencyHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dependencies")
public class DependencyRestController {

    private final DependencyHandler dependencyHandler;

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<DependencyResponse> getDependencyById(@PathVariable Long id) {
        DependencyResponse response = dependencyHandler.findById(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<List<DependencyResponse>> getAllDependencies() {
        List<DependencyResponse> responses = dependencyHandler.findAll();
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @PostMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<DependencyResponse> createDependency
            (@Valid @RequestBody DependencyRequest dependencyRequest) {
        DependencyResponse created = dependencyHandler.save(dependencyRequest);
        URI location = URI.create(String.format("/api/dependencies/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @PutMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<DependencyResponse> updateDependencyById
            (@PathVariable Long id, @Valid @RequestBody DependencyRequest dependencyRequest) {
        DependencyResponse updated = dependencyHandler.updateById(id, dependencyRequest);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @DeleteMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<Void> deleteDependencyById(@PathVariable Long id) {
        dependencyHandler.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
