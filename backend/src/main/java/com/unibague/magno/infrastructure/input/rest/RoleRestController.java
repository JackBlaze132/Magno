package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.request.RoleRequest;
import com.unibague.magno.application.dto.response.RoleResponse;
import com.unibague.magno.application.handler.impl.RoleHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleRestController {

    private final RoleHandler roleHandler;

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable Long id) {
        RoleResponse roleResponse = roleHandler.findById(id);
        return ResponseEntity.ok(roleResponse);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        List<RoleResponse> roleResponses = roleHandler.findAll();
        return ResponseEntity.ok(roleResponses);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @PostMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<RoleResponse> createRole(@Valid @RequestBody RoleRequest roleRequest) {
        RoleResponse roleResponse = roleHandler.save(roleRequest);
        URI location = URI.create(String.format("/api/roles/%d", roleResponse.getId()));
        return ResponseEntity.created(location).body(roleResponse);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @PutMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<RoleResponse> updateRoleById
            (@PathVariable Long id, @Valid @RequestBody RoleRequest roleRequest) {
        RoleResponse roleResponse = roleHandler.updateById(id, roleRequest);
        return ResponseEntity.ok(roleResponse);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @DeleteMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<Void> deleteRoleById(@PathVariable Long id) {
        roleHandler.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
