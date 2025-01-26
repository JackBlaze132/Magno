package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.request.DependencyRequest;
import com.unibague.magno.application.dto.response.DependencyResponse;
import com.unibague.magno.application.handler.DependencyHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/dependencies")
public class DependencyRestController {

    private final DependencyHandler dependencyHandler;

    @GetMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<DependencyResponse> getDependencyById(@PathVariable Long id) {
        DependencyResponse response = dependencyHandler.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<List<DependencyResponse>> getAllDependencies() {
        List<DependencyResponse> responses = dependencyHandler.findAll();
        return ResponseEntity.ok(responses);
    }

    @PostMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<DependencyResponse> createDependency
            (@RequestBody DependencyRequest dependencyRequest) {
        DependencyResponse created = dependencyHandler.save(dependencyRequest);
        URI location = URI.create(String.format("/api/dependencies/%d", created.getId()));
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<DependencyResponse> updateDependencyById
            (@PathVariable Long id, @RequestBody DependencyRequest dependencyRequest) {
        DependencyResponse updated = dependencyHandler.updateById(id, dependencyRequest);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<Void> deleteDependencyById(@PathVariable Long id) {
        dependencyHandler.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
