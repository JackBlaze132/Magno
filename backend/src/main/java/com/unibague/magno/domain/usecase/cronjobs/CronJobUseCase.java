package com.unibague.magno.domain.usecase.cronjobs;

import com.unibague.magno.domain.api.IAcademicProgramServicePort;
import com.unibague.magno.domain.api.IDependencyServicePort;
import com.unibague.magno.domain.api.IErrorLogServicePort;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.api.cronjobs.ICronJobServicePort;
import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.model.AcademicProgram;
import com.unibague.magno.domain.model.Dependency;
import com.unibague.magno.domain.model.User;
import com.unibague.magno.domain.model.enums.AcademicProgramType;
import com.unibague.magno.domain.model.enums.Sex;
import com.unibague.magno.domain.model.enums.TypeOfInternalUser;
import com.unibague.magno.domain.model.integra.IntegraAcademicProgram;
import com.unibague.magno.domain.model.integra.IntegraFunctionary;
import com.unibague.magno.domain.model.integra.IntegraStudent;

import java.util.*;
import java.util.stream.Collectors;

public class CronJobUseCase implements ICronJobServicePort {

    private final IIntegraServicePort integraServicePort;
    private final IUserServicePort userServicePort;
    private final IAcademicProgramServicePort academicProgramServicePort;
    private final IDependencyServicePort dependencyServicePort;
    private final IErrorLogServicePort errorLogServicePort;

    public CronJobUseCase(IIntegraServicePort integraServicePort,
                          IUserServicePort userServicePort,
                          IAcademicProgramServicePort academicProgramServicePort,
                          IDependencyServicePort dependencyServicePort,
                          IErrorLogServicePort errorLogServicePort) {
        this.integraServicePort = integraServicePort;
        this.userServicePort = userServicePort;
        this.academicProgramServicePort = academicProgramServicePort;
        this.dependencyServicePort = dependencyServicePort;
        this.errorLogServicePort = errorLogServicePort;
    }

    /**
     * This method is intended to be called by a scheduled cron job from infrastructure/cronjobs
     * to update the information of:
     * ---------------------------------------------------------------------------------------
     * Users (functionaries and students) - Academic Programs - Dependencies
     * ---------------------------------------------------------------------------------------
     * It compares the current information that exists in the system with that which exists in Integra.
     * Its intention is only to add new information that it finds, not to modify or delete it, because the system
     * intends to save the information over time, even if a person is no longer in Integra's databases,
     * an academic program has disappeared, etc.
     * ---------------------------------------------------------------------------------------
     * Although there are endpoints that use information from Integra to return the departments and academic programs
     * that "exist", some of these programs are not in the provided endpoints, so it was necessary to search for all
     * the programs and departments that exist given the list of students and staff, and then compare it with the
     * information that exists in Integra.
     */
    @Override
    public void updateInfoFromIntegra() {

        // ---------------------------------------------------------------------------------------
        // Get the current information from the system
        List<User> currentUsers = userServicePort.findAll();
        List<AcademicProgram> currentAcademicPrograms = academicProgramServicePort.findAll();
        List<Dependency> currentDependencies = dependencyServicePort.findAll();
        // ---------------------------------------------------------------------------------------

        // Get the information from Integra
        List<IntegraFunctionary> integraFunctionaries = integraServicePort.getAllFunctionaries();
        List<IntegraStudent> integraStudents = integraServicePort.getAllStudents();

        // Get academic programs mapped by type
        Map<AcademicProgramType, List<IntegraAcademicProgram>> programsByType = integraServicePort.getAllAcademicProgramsMappedByType();

        // Create a map to quickly find the type of program by its code
        Map<String, AcademicProgramType> programCodeToTypeMap = new HashMap<>();
        for (Map.Entry<AcademicProgramType, List<IntegraAcademicProgram>> entry : programsByType.entrySet()) {
            for (IntegraAcademicProgram program : entry.getValue()) {
                programCodeToTypeMap.put(program.getProgramCode(), entry.getKey());
            }
        }

        // ---------------------------------------------------------------------------------------
        // Extract unique academic programs and dependencies from Integra using Maps
        Map<String, AcademicProgram> integraAcademicProgramsMap = new HashMap<>();
        Map<String, Dependency> integraDependenciesMap = new HashMap<>();

        // From Integra Students → Academic Programs
        for (IntegraStudent student : integraStudents) {
            if (student.getProgram() != null && !student.getProgram().isBlank()) {
                String programName = student.getProgram().trim();
                String programCode = student.getProgramCode() != null ? student.getProgramCode().trim() : null;

                // Determine the type from the map, or use NO_DEFINIDO if not found
                AcademicProgramType type = programCodeToTypeMap.getOrDefault(programCode, AcademicProgramType.NO_DEFINIDO);

                // Only add if not already present (first occurrence wins)
                integraAcademicProgramsMap.putIfAbsent(programCode, new AcademicProgram(
                        null,
                        programName,
                        programCode,
                        type
                ));
            }
        }

        // From Integra Functionaries → Dependencies
        for (IntegraFunctionary functionary : integraFunctionaries) {
            if (functionary.getProgram() != null && !functionary.getProgram().isBlank()) {
                String dependencyName = functionary.getProgram().trim();

                // Only add if not already present
                integraDependenciesMap.putIfAbsent(dependencyName, new Dependency(
                        null,
                        dependencyName
                ));
            }
        }

        // ---------------------------------------------------------------------------------------
        // Add new academic programs that don't exist in the system (by programCode)
        Set<String> existingProgramCodes = currentAcademicPrograms.stream()
                .map(AcademicProgram::getProgramCode)
                .collect(Collectors.toSet());

        List<AcademicProgram> newPrograms = integraAcademicProgramsMap.values().stream()
                .filter(p -> !existingProgramCodes.contains(p.getProgramCode()))
                .toList();

        for (AcademicProgram program : newPrograms) {
            academicProgramServicePort.save(program);
        }

        // ---------------------------------------------------------------------------------------
        // Add new dependencies that don't exist in the system
        Set<String> existingDependencyNames = currentDependencies.stream()
                .map(Dependency::getName)
                .collect(Collectors.toSet());

        List<Dependency> newDependencies = integraDependenciesMap.values().stream()
                .filter(d -> !existingDependencyNames.contains(d.getName()))
                .toList();

        for (Dependency dependency : newDependencies) {
            dependencyServicePort.save(dependency);
        }

        createCustomDependency();

        // ---------------------------------------------------------------------------------------
        // Users - Functionaries
        // ---------------------------------------------------------------------------------------

        // Create map for existing user emails
        Set<String> existingUserEmails = currentUsers.stream()
                .map(User::getEmail)
                .collect(Collectors.toSet());

        // Process Functionaries
        Map<String, User> integraFunctionariesUsersMap = new HashMap<>();

        for (IntegraFunctionary functionary : integraFunctionaries) {
            if (functionary.getEmail() != null && !functionary.getEmail().isBlank()) {
                String userEmail = functionary.getEmail().trim();

                integraFunctionariesUsersMap.putIfAbsent(userEmail, new User(
                        null,
                        functionary.getFullName() != null ? functionary.getFullName().trim() : null,
                        functionary.getIdentification() != null ? functionary.getIdentification().trim() : null,
                        userEmail,
                        functionary.getCodeUser() != null ? functionary.getCodeUser().trim() : null,
                        false,
                        parseSex(functionary.getSex()),
                        TypeOfInternalUser.FUNCIONARIO
                ));
            }
        }

        // Add new functionaries that don't exist in the system (by email)
        List<User> newFunctionaries = integraFunctionariesUsersMap.values().stream()
                .filter(u -> !existingUserEmails.contains(u.getEmail()))
                .toList();

        for (User functionary : newFunctionaries) {
            userServicePort.save(functionary);
            // Update the set with newly added emails
            existingUserEmails.add(functionary.getEmail());
        }

        // ---------------------------------------------------------------------------------------
        // Users - Students
        // ---------------------------------------------------------------------------------------

        // Process Students
        Map<String, User> integraStudentsUsersMap = new HashMap<>();

        for (IntegraStudent student : integraStudents) {
            if (student.getEmail() != null && !student.getEmail().isBlank()) {
                String userEmail = student.getEmail().trim();

                integraStudentsUsersMap.putIfAbsent(userEmail, new User(
                        null,
                        student.getName() != null ? student.getName().trim() : null,
                        student.getIdentification() != null ? student.getIdentification().trim() : null,
                        userEmail,
                        student.getCodeStudent() != null ? student.getCodeStudent().trim() : null,
                        false,
                        parseSex(student.getSexo()),
                        TypeOfInternalUser.ESTUDIANTE
                ));
            }
        }

        // Add new students that don't exist in the system (by email)
        List<User> newStudents = integraStudentsUsersMap.values().stream()
                .filter(u -> !existingUserEmails.contains(u.getEmail()))
                .toList();

        for (User student : newStudents) {
            userServicePort.save(student);
        }
    }

    @Override
    public void deleteOldErrorLogs(int days) {
        errorLogServicePort.deleteLogsOlderThanDays(days);
    }

    /**
     * Helper method to parse sex from string to enum
     */
    private Sex parseSex(String sexString) {

        if (sexString == null || sexString.isBlank()) {
            return null;
        }

        String normalized = sexString.trim().toUpperCase();

        if (normalized.equals("M") || normalized.equals("MASCULINO") || normalized.equals("MALE")) {
            return Sex.MASCULINO;
        } else if (normalized.equals("F") || normalized.equals("FEMENINO") || normalized.equals("FEMALE")) {
            return Sex.FEMENINO;
        }

        return null;
    }

    /**
     * Sometimes, integra return null or empty dependencies,
     * so we need to ensure that at least one dependency exists with the name "No definido"
     */
    private void createCustomDependency() {
        if (dependencyServicePort.findByNameOptional("No definido").isEmpty()){
            Dependency dependency = new Dependency();
            dependency.setName("No definido");
            dependencyServicePort.save(dependency);
        }
    }


}
