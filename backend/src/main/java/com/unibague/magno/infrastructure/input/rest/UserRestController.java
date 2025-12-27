package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.util.CurrentUserInfo;
import com.unibague.magno.application.dto.request.StudentSeedbedCertificateRequest;
import com.unibague.magno.application.dto.request.integra.IntegraUserRequest;
import com.unibague.magno.application.dto.request.UserRequest;
import com.unibague.magno.application.dto.response.UserResponse;
import com.unibague.magno.application.handler.impl.UserHandler;
import com.unibague.magno.infrastructure.configuration.annotation.CurrentUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserRestController {

    private final UserHandler userHandler;

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {
        UserResponse userResponse = userHandler.findById(id);
        return ResponseEntity.ok(userResponse);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> userResponse = userHandler.findAll();
        return ResponseEntity.ok(userResponse);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).COORDINADOR_DE_SEMILLERO)")
    @GetMapping(path = "/all-countries", headers = "API-VERSION=1")
    ResponseEntity<List<String>> getAllCountries() {
        List<String> countries = userHandler.findAllCountries();
        return ResponseEntity.ok(countries);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).COORDINADOR_DE_SEMILLERO)")
    @GetMapping(path = "/all-functionaries-registered", headers = "API-VERSION=1")
    ResponseEntity<List<UserResponse>> getAllFunctionariesRegistered() {
        List<UserResponse> functionaries = userHandler.findAllFunctionariesRegistered();
        return ResponseEntity.ok(functionaries);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).COORDINADOR_DE_SEMILLERO)")
    @GetMapping(path = "/all-students-registered", headers = "API-VERSION=1")
    ResponseEntity<List<UserResponse>> getAllStudentsRegistered() {
        List<UserResponse> students = userHandler.findAllStudentsRegistered();
        return ResponseEntity.ok(students);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/all-external-users-registered", headers = "API-VERSION=1")
    ResponseEntity<List<UserResponse>> getAllExternalUsersRegistered() {
        List<UserResponse> externalUsers = userHandler.findAllExternalUsersRegistered();
        return ResponseEntity.ok(externalUsers);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).COORDINADOR_DE_SEMILLERO)")
    @GetMapping(path = "/all-internal-users-registered", headers = "API-VERSION=1")
    ResponseEntity<List<UserResponse>> getAllInternalUsersRegistered() {
        List<UserResponse> internalUsers = userHandler.findAllInternalUsersRegistered();
        return ResponseEntity.ok(internalUsers);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).ESTUDIANTE)")
    @GetMapping(path = "/me", headers = "API-VERSION=1")
    public ResponseEntity<CurrentUserInfo> getCurrentUserInfo(@CurrentUser CurrentUserInfo currentUser) {
        return ResponseEntity.ok(currentUser);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).ESTUDIANTE)")
    @PostMapping(path = "/student-seedbed-certificate", headers = "API-VERSION=1")
    public ResponseEntity<byte[]> getStudentSeedbedCertificate(
            @Valid @RequestBody StudentSeedbedCertificateRequest studentSeedbedCertificateRequest) throws Exception {

        byte[] pdf = userHandler.generateByteStudentSeedbedCertificate(studentSeedbedCertificateRequest);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=certificado.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @PostMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userHandler.save(userRequest);
        URI location = URI.create(String.format("/api/users/%d", userResponse.getId()));
        return ResponseEntity.created(location).body(userResponse);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @PostMapping(path = "/integra-user", headers = "API-VERSION=1")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody IntegraUserRequest integraUserRequest) {
        UserResponse userResponse = userHandler.save(integraUserRequest);
        URI location = URI.create(String.format("/api/users/%d", userResponse.getId()));
        return ResponseEntity.created(location).body(userResponse);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @PutMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<UserResponse> updateUserById
            (@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userHandler.updateById(id, userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @DeleteMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userHandler.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
