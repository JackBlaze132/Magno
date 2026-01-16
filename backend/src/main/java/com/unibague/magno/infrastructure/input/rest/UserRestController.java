package com.unibague.magno.infrastructure.input.rest;

import com.unibague.magno.application.dto.util.CurrentUserInfo;
import com.unibague.magno.application.dto.request.StudentSeedbedCertificateRequest;
import com.unibague.magno.application.dto.request.integra.IntegraUserRequest;
import com.unibague.magno.application.dto.request.UserRequest;
import com.unibague.magno.application.dto.response.UserResponse;
import com.unibague.magno.application.handler.impl.UserHandler;
import com.unibague.magno.domain.exception.security.NotAllowedToDoThisActionException;
import com.unibague.magno.infrastructure.configuration.annotation.CurrentUser;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/**
 * REST controller for managing users in Magno.
 * Provides endpoints for CRUD operations on users, as well as specialized
 * endpoints for retrieving users by type (functionaries, students, external users),
 * managing DIRI administrators, and generating student seedbed certificates.
 *
 * @see UserHandler
 */
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

    /**
     * Retrieves a list of all distinct countries from registered users.
     *
     * @return List of country names.
     */
    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).COORDINADOR_DE_SEMILLERO)")
    @GetMapping(path = "/all-countries", headers = "API-VERSION=1")
    ResponseEntity<List<String>> getAllCountries() {
        List<String> countries = userHandler.findAllCountries();
        return ResponseEntity.ok(countries);
    }

    /**
     * Retrieves all registered functionaries (university staff members).
     *
     * @param currentUserInfo The current authenticated user information.
     * @return List of all functionary users.
     */
    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).USUARIO_SIN_ROL)")
    @GetMapping(path = "/all-functionaries-registered", headers = "API-VERSION=1")
    ResponseEntity<List<UserResponse>> getAllFunctionariesRegistered(@CurrentUser CurrentUserInfo currentUserInfo) {
        /**
        if (!currentUserInfo.getEmail().endsWith("@unibague.edu.co")) {
            throw new NotAllowedToDoThisActionException("Solo los funcionarios pueden acceder a esta información.");
        }*/
        List<UserResponse> functionaries = userHandler.findAllFunctionariesRegistered();
        return ResponseEntity.ok(functionaries);
    }

    /**
     * Retrieves all registered students.
     *
     * @return List of all student users.
     */
    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).COORDINADOR_DE_SEMILLERO)")
    @GetMapping(path = "/all-students-registered", headers = "API-VERSION=1")
    ResponseEntity<List<UserResponse>> getAllStudentsRegistered() {
        List<UserResponse> students = userHandler.findAllStudentsRegistered();
        return ResponseEntity.ok(students);
    }

    /**
     * Retrieves all registered external users (non-university participants).
     *
     * @return List of all external users.
     */
    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).COORDINADOR_DE_SEMILLERO)")
    @GetMapping(path = "/all-external-users-registered", headers = "API-VERSION=1")
    ResponseEntity<List<UserResponse>> getAllExternalUsersRegistered() {
        List<UserResponse> externalUsers = userHandler.findAllExternalUsersRegistered();
        return ResponseEntity.ok(externalUsers);
    }

    /**
     * Retrieves all registered internal users (functionaries and students).
     *
     * @return List of all internal university users.
     */
    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).COORDINADOR_DE_SEMILLERO)")
    @GetMapping(path = "/all-internal-users-registered", headers = "API-VERSION=1")
    ResponseEntity<List<UserResponse>> getAllInternalUsersRegistered() {
        List<UserResponse> internalUsers = userHandler.findAllInternalUsersRegistered();
        return ResponseEntity.ok(internalUsers);
    }

    /**
     * Retrieves the current authenticated user's application information.
     *
     * @param currentUser The current user resolved from the security context.
     * @return Current user's information including ID, roles, and email.
     */
    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).USUARIO_SIN_ROL)")
    @GetMapping(path = "/me", headers = "API-VERSION=1")
    public ResponseEntity<CurrentUserInfo> getCurrentUserInfo(@CurrentUser CurrentUserInfo currentUser) {
        return ResponseEntity.ok(currentUser);
    }

    /**
     * Retrieves all users with DIRI (administrator) role.
     *
     * @return List of all DIRI users.
     */
    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @GetMapping(path = "/diri-users", headers = "API-VERSION=1")
    public ResponseEntity<List<UserResponse>> getAllDiriUsers() {
        List<UserResponse> diriUsers = userHandler.findAllDiriUsers();
        return ResponseEntity.ok(diriUsers);
    }

    /**
     * Assigns the DIRI (administrator) role to an existing user.
     *
     * @param diriIdentification The identification number of the user to promote to DIRI.
     * @return The updated user with DIRI role.
     */
    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @PostMapping(path = "/diri-users", headers = "API-VERSION=1")
    public ResponseEntity<UserResponse> addDiriUser(
            @RequestParam("diri-identification")
            @NotBlank(message = "La identificación del usuario DIRI no puede estar vacía")
            String diriIdentification
    ) {
        UserResponse diriUser = userHandler.addDiriUser(diriIdentification);
        return ResponseEntity.ok(diriUser);
    }

    /**
     * Generates a PDF certificate for a student's participation in a research seedbed.
     *
     * @param studentSeedbedCertificateRequest Request containing student and seedbed information.
     * @return PDF file as byte array with the generated certificate.
     * @throws Exception if an error occurs during certificate generation.
     */
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

    /**
     * Creates a new user from Integra system data.
     *
     * @param integraUserRequest Request containing user data from the Integra system.
     * @return The created user response.
     */
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

    /**
     * Removes the DIRI (administrator) role from a user.
     *
     * @param diriIdentification The identification number of the user to remove from DIRI.
     */
    @PreAuthorize("hasRole(T(com.unibague.magno.domain.model.enums.SeedbedRole).DIRI)")
    @DeleteMapping(path = "/diri-users", headers = "API-VERSION=1")
    public ResponseEntity<Void> deleteDiriUser(
            @RequestParam("diri-identification")
            @NotBlank(message = "La identificación del usuario DIRI no puede estar vacía")
            String diriIdentification
    ) {
        userHandler.deleteDiriUser(diriIdentification);
        return ResponseEntity.noContent().build();
    }
}
