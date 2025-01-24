package com.unibague.magno.application.handler;

import com.unibague.magno.application.dto.request.RoleRequest;
import com.unibague.magno.application.dto.response.RoleResponse;
import com.unibague.magno.application.mapper.request.RoleRequestMapper;
import com.unibague.magno.application.mapper.response.RoleResponseMapper;
import com.unibague.magno.domain.api.IRoleServicePort;
import com.unibague.magno.domain.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleHandler implements IRoleHandler {

    private final IRoleServicePort roleServicePort;
    private final RoleRequestMapper roleRequestMapper;
    private final RoleResponseMapper roleResponseMapper;

    @Override
    public RoleResponse findById(Long id) {
        Role role = roleServicePort.findById(id);
        return roleResponseMapper.toResponse(role);
    }

    @Override
    public RoleResponse save(RoleRequest role) {
        return roleResponseMapper.toResponse(roleServicePort
                .save(roleRequestMapper.toRole(role)));
    }

    @Override
    public RoleResponse updateById(Long id, RoleRequest role) {
        return roleResponseMapper.toResponse(roleServicePort
                .update(id, roleRequestMapper.toRole(role)));
    }

    @Override
    public void deleteById(Long id) {
        roleServicePort.deleteById(id);
    }

    @Override
    public List<RoleResponse> findAll() {
        return roleResponseMapper.toResponseList(roleServicePort.findAll());
    }
}
