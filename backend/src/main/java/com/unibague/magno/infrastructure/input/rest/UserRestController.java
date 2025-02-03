package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.request.integra.IntegraUserRequest;
import com.unibague.magno.application.dto.request.UserRequest;
import com.unibague.magno.application.dto.response.UserResponse;
import com.unibague.magno.application.handler.UserHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserRestController {

    private final UserHandler userHandler;

    @GetMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse userResponse = userHandler.findById(id);
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> userResponse = userHandler.findAll();
        return ResponseEntity.ok(userResponse);
    }

    @PostMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest userRequest) {
        UserResponse userResponse = userHandler.save(userRequest);
        URI location = URI.create(String.format("/api/users/%d", userResponse.getId()));
        return ResponseEntity.created(location).body(userResponse);
    }

    @PostMapping(path = "/integra-user", headers = "API-VERSION=1")
    public ResponseEntity<UserResponse> createUser(@RequestBody IntegraUserRequest integraUserRequest) {
        UserResponse userResponse = userHandler.save(integraUserRequest);
        URI location = URI.create(String.format("/api/users/%d", userResponse.getId()));
        return ResponseEntity.created(location).body(userResponse);
    }

    @PutMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<UserResponse> updateUserById
            (@PathVariable Long id, @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userHandler.updateById(id, userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userHandler.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
