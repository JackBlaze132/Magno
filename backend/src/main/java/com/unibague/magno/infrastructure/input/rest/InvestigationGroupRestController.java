package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.request.InvestigationGroupRequest;
import com.unibague.magno.application.dto.response.InvestigationGroupResponse;
import com.unibague.magno.application.handler.impl.InvestigationGroupHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * REST controller for managing investigation groups in Magno.
 * Provides endpoints for CRUD operations on investigation groups,
 * which are the main research entities at the university that contain
 * multiple research seedbeds and are led by research directors.
 *
 * @see InvestigationGroupHandler
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/investigation-groups")
public class InvestigationGroupRestController {

    private final InvestigationGroupHandler investigationGroupHandler;

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<InvestigationGroupResponse> getInvestigationGroupById(@PathVariable Long id) {
        InvestigationGroupResponse response = investigationGroupHandler.findById(id);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).USUARIO_SIN_ROL)")
    @GetMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<List<InvestigationGroupResponse>> getAllInvestigationGroups() {
        List<InvestigationGroupResponse> responses = investigationGroupHandler.findAll();
        return ResponseEntity.ok(responses);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @PostMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<InvestigationGroupResponse> createInvestigationGroup
            (@Valid @RequestBody InvestigationGroupRequest investigationGroupRequest) {
        InvestigationGroupResponse created = investigationGroupHandler.save(investigationGroupRequest);
        URI location = URI.create("/api/investigation-groups/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @PutMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<InvestigationGroupResponse> updateInvestigationGroupById
            (@PathVariable Long id, @Valid @RequestBody InvestigationGroupRequest investigationGroupRequest) {
        InvestigationGroupResponse updated = investigationGroupHandler.updateById(id, investigationGroupRequest);
        return ResponseEntity.ok(updated);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @DeleteMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<Void> deleteInvestigationGroupById(@PathVariable Long id) {
        investigationGroupHandler.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
