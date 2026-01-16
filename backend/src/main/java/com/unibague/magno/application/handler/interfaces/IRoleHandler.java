package com.unibague.magno.application.handler.interfaces;

import com.unibague.magno.application.dto.request.RoleRequest;
import com.unibague.magno.application.dto.response.RoleResponse;

import java.util.List;

/**
 * Handler interface for role operations.
 * Acts as the application layer bridge between REST controllers and domain services,
 * handling DTO-to-model conversion for role management.
 */
public interface IRoleHandler {
    RoleResponse findById(Long id);
    RoleResponse save(RoleRequest role);
    RoleResponse updateById(Long id, RoleRequest role);
    void deleteById(Long id);
    List<RoleResponse> findAll();
}
