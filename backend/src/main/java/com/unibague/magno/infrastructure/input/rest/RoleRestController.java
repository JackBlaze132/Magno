package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.request.RoleRequest;
import com.unibague.magno.application.dto.response.RoleResponse;
import com.unibague.magno.application.handler.RoleHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
public class RoleRestController {

    private final RoleHandler roleHandler;

    @GetMapping(path = "/{id}", headers = "API-VERSION=1")
    public RoleResponse getRoleById(@PathVariable Long id) {
        return roleHandler.findById(id);
    }

    @GetMapping(path = "/", headers = "API-VERSION=1")
    public List<RoleResponse> getAllRoles() {
        return roleHandler.findAll();
    }

    @PostMapping(path = "/", headers = "API-VERSION=1")
    public RoleResponse createRole(@RequestBody RoleRequest roleRequest) {
        return roleHandler.save(roleRequest);
    }

    @PutMapping(path = "/{id}", headers = "API-VERSION=1")
    public RoleResponse updateRoleById(@PathVariable Long id, @RequestBody RoleRequest roleRequest) {
        return roleHandler.updateById(id, roleRequest);
    }

    @DeleteMapping(path = "/{id}", headers = "API-VERSION=1")
    public void deleteRoleById(@PathVariable Long id) {
        roleHandler.deleteById(id);
    }
}
