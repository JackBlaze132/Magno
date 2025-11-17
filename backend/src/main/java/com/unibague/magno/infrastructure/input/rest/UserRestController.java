package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.CurrentUserInfo;
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
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
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

    @GetMapping(path = "/all-countries", headers = "API-VERSION=1")
    ResponseEntity<List<String>> getAllCountries() {
        List<String> countries = userHandler.findAllCountries();
        return ResponseEntity.ok(countries);
    }

    @GetMapping(path = "/all-functionaries-registered", headers = "API-VERSION=1")
    ResponseEntity<List<UserResponse>> getAllFunctionariesRegistered() {
        List<UserResponse> functionaries = userHandler.findAllFunctionariesRegistered();
        return ResponseEntity.ok(functionaries);
    }

    @GetMapping(path = "/all-students-registered", headers = "API-VERSION=1")
    ResponseEntity<List<UserResponse>> getAllStudentsRegistered() {
        List<UserResponse> students = userHandler.findAllStudentsRegistered();
        return ResponseEntity.ok(students);
    }

    @GetMapping(path = "/all-external-users-registered", headers = "API-VERSION=1")
    ResponseEntity<List<UserResponse>> getAllExternalUsersRegistered() {
        List<UserResponse> externalUsers = userHandler.findAllExternalUsersRegistered();
        return ResponseEntity.ok(externalUsers);
    }

    @GetMapping(path = "/all-internal-users-registered", headers = "API-VERSION=1")
    ResponseEntity<List<UserResponse>> getAllInternalUsersRegistered() {
        List<UserResponse> internalUsers = userHandler.findAllInternalUsersRegistered();
        return ResponseEntity.ok(internalUsers);
    }

    @GetMapping(path = "/me", headers = "API-VERSION=1")
    public ResponseEntity<CurrentUserInfo> getCurrentUserInfo(@CurrentUser CurrentUserInfo currentUser) {
        return ResponseEntity.ok(currentUser);
    }

    @GetMapping(path = "/student-seedbed-certificate", headers = "API-VERSION=1")
    public ResponseEntity<byte[]> getStudentSeedbedCertificate(
            @Valid @RequestBody StudentSeedbedCertificateRequest studentSeedbedCertificateRequest) throws Exception {

        byte[] pdf = userHandler.generateStudentSeedbedCertificate(studentSeedbedCertificateRequest);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=certificado.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @PostMapping(path = "/", headers = "API-VERSION=1")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userHandler.save(userRequest);
        URI location = URI.create(String.format("/api/users/%d", userResponse.getId()));
        return ResponseEntity.created(location).body(userResponse);
    }

    @PostMapping(path = "/integra-user", headers = "API-VERSION=1")
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody IntegraUserRequest integraUserRequest) {
        UserResponse userResponse = userHandler.save(integraUserRequest);
        URI location = URI.create(String.format("/api/users/%d", userResponse.getId()));
        return ResponseEntity.created(location).body(userResponse);
    }

    @PutMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<UserResponse> updateUserById
            (@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse = userHandler.updateById(id, userRequest);
        return ResponseEntity.ok(userResponse);
    }

    @DeleteMapping(path = "/{id}", headers = "API-VERSION=1")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        userHandler.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
