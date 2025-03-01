package com.unibague.magno.domain.usecase.integra;

import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.exception.integra.IntegraStudentNotFoundException;
import com.unibague.magno.domain.exception.integra.IntegraUserNotFoundException;
import com.unibague.magno.domain.model.integra.IntegraAcademicProgram;
import com.unibague.magno.domain.model.integra.IntegraDependency;
import com.unibague.magno.domain.model.integra.IntegraFunctionary;
import com.unibague.magno.domain.model.integra.IntegraStudent;
import com.unibague.magno.domain.spi.integra.IIntegraPersistencePort;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.unibague.magno.domain.usecase.ResearchSeedbedStudentProfileUseCase.IDENTIFICATION;

public class IntegraUseCase implements IIntegraServicePort {

    private final IIntegraPersistencePort integraPersistencePort;

    public IntegraUseCase(IIntegraPersistencePort integraPersistencePort) {
        this.integraPersistencePort = integraPersistencePort;
    }

    @Override
    public List<IntegraFunctionary> getAllFunctionaries() {
        return integraPersistencePort.getAllFunctionaries();
    }

    @Override
    public IntegraFunctionary getIntegraFunctionaryByIdentification(String identification) {

        List<IntegraFunctionary> functionaries = getAllFunctionaries();

        Optional<IntegraFunctionary> functionaryOptional = functionaries.stream()
                .filter(f -> f.getIdentification().equals(identification))
                .findFirst();

        return functionaryOptional.orElseThrow(() -> {
            String message = String.
                    format("It wasn't possible to find the functionary with identification %s " +
                            "in the returned list", identification);
            return new IntegraUserNotFoundException(message);
        });

    }

    @Override
    public List<IntegraStudent> getIntegraStudentRecordsByIdentification(String identification) {
        return getIntegraStudentRecordsOrThrow(identification);
    }

    private List<IntegraStudent> getIntegraStudentRecordsOrThrow(String identification) {
        List<IntegraStudent> students = integraPersistencePort.getIntegraStudentRecordsByIdentification(identification);
        if (students.isEmpty()) {
            throw new IntegraStudentNotFoundException(
                    String.format("IntegraStudent with identification %s not found", identification)
            );
        }
        return students;
    }

    @Override
    public List<IntegraAcademicProgram> getAllAcademicPrograms() {
        return integraPersistencePort.getAllAcademicPrograms();
    }

    @Override
    public List<IntegraDependency> getAllDependencies() {
        return integraPersistencePort.getAllDependencies();
    }

    @Override
    public List<String> findMissingStudentIdentificationsInIntegra(List<String> identifications) {
        return identifications.stream()
                .filter(identification -> {
                    try {
                        return getIntegraStudentRecordsByIdentification(identification).isEmpty();
                    } catch (IntegraStudentNotFoundException e) {
                        return true; // If exception is thrown, we consider that the student is missing
                    }
                })
                .toList();
    }

    @Override
    public IntegraStudent getFirstIntegraStudentFound(String identification) {
        return getIntegraStudentRecordsByIdentification(identification)
                .stream()
                .findFirst()
                .orElseThrow(() -> new IntegraStudentNotFoundException(
                        String.format("IntegraStudent with identification %s not found", identification)
                ));
    }

    @Override
    // This method filters out the student profiles that have empty values and checks if the students exist in Integra
    public List<Map<String, String>> getCleanedStudentListOfMaps(List<Map<String, String>> researchSeedbedStudentProfiles) {
        List<Map<String, String>> cleanedStudentListOfMaps = researchSeedbedStudentProfiles.stream()
                .filter(map -> map.values().stream().noneMatch(String::isEmpty))
                .toList();

        List<String> studentIdentifications = cleanedStudentListOfMaps.stream()
                .map(studentProfile -> studentProfile.get(IDENTIFICATION))
                .toList();

        List<String> missingIdentifications = findMissingStudentIdentificationsInIntegra(studentIdentifications);
        throwIfStudentsMissing(missingIdentifications); // If there are missing students, we throw an exception

        return cleanedStudentListOfMaps;
    }

    private void throwIfStudentsMissing(List<String> missingIdentifications) {
        if (!missingIdentifications.isEmpty()) {
            throw new IntegraStudentNotFoundException(
                    String.format(
                            "The following student identifications were not found in Integra: %s",
                            String.join(", ", missingIdentifications)
                    )
            );
        }
    }

    @Override
    public byte getMaxSemester(List<IntegraStudent> studentRecords) {
        return (byte) studentRecords.stream()
                .map(IntegraStudent::getSemester)
                .map(s -> (s == null || s.isEmpty()) ? "0" : s)
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(-1);
    }
}
