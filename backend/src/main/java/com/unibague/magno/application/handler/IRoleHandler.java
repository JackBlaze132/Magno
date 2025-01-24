package com.unibague.magno.application.handler;

import com.unibague.magno.application.dto.request.RoleRequest;
import com.unibague.magno.application.dto.response.RoleResponse;

import java.util.List;

public interface IRoleHandler {
    RoleResponse findById(Long id);
    RoleResponse save(RoleRequest role);
    RoleResponse updateById(Long id, RoleRequest role);
    void deleteById(Long id);
    List<RoleResponse> findAll();
}
