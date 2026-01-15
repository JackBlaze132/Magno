package com.unibague.magno.domain.usecase.integra;

import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.exception.integra.IntegraAcademicProgramNotFoundException;
import com.unibague.magno.domain.exception.integra.IntegraStudentNotFoundException;
import com.unibague.magno.domain.exception.integra.IntegraUserNotFoundException;
import com.unibague.magno.domain.model.enums.AcademicProgramType;
import com.unibague.magno.domain.model.integra.IntegraAcademicProgram;
import com.unibague.magno.domain.model.integra.IntegraDependency;
import com.unibague.magno.domain.model.integra.IntegraFunctionary;
import com.unibague.magno.domain.model.integra.IntegraStudent;
import com.unibague.magno.domain.spi.integra.IIntegraPersistencePort;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.unibague.magno.domain.usecase.ResearchSeedbedStudentProfileUseCase.IDENTIFICATION;

/**
 * Use case implementation for integrating with the university's Integra system.
 * <p>
 * This use case provides business logic for retrieving and validating data from Integra,
 * the university's central information system. Integra is the authoritative source for
 * official records of functionaries, students, academic programs, and dependencies.
 * </p>
 * <p>
 * Key responsibilities:
 * <ul>
 *   <li>Retrieve functionary and student information by various identifiers</li>
 *   <li>Validate student existence before bulk operations (Excel uploads)</li>
 *   <li>Provide academic program information organized by type</li>
 *   <li>Handle missing or invalid data with appropriate exceptions</li>
 * </ul>
 * </p>
 */
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

        if (functionaryOptional.isPresent() && functionaryOptional.get().getProgram().isBlank()) {
            IntegraFunctionary functionary = functionaryOptional.get();
            functionary.setProgram("No definido");
            return functionary;
        }

        return functionaryOptional.orElseThrow(() -> {
            String message = String.
                    format("No fue posible encontrar el funcionario con identificación %s. " +
                            "Esto puede ocurrir porque el funcionario ya no está afiliado a la universidad. " +
                            "Si cree que esto es un error, por favor contacte al departamento DIRI.", identification);
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
                    String.format("Estudiante de Integra con identificación %s no encontrado", identification)
            );
        }
        return students;
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
    public List<Map<String, String>> getCleanedStudentListOfMaps(List<Map<String, String>> researchSeedbedStudentProfiles) {
        List<Map<String, String>> cleanedStudentListOfMaps = researchSeedbedStudentProfiles.stream()
                .filter(map -> map.values().stream().noneMatch(String::isEmpty))
                .toList();

        List<String> studentIdentifications = cleanedStudentListOfMaps.stream()
                .map(studentProfile -> studentProfile.get(IDENTIFICATION))
                .toList();

        List<String> missingIdentifications = findMissingStudentIdentificationsInIntegra(studentIdentifications);
        throwIfStudentsMissing(missingIdentifications);

        return cleanedStudentListOfMaps;
    }

    private void throwIfStudentsMissing(List<String> missingIdentifications) {
        if (!missingIdentifications.isEmpty()) {
            throw new IntegraStudentNotFoundException(
                    String.format(
                            "Las siguientes identificaciones de estudiantes no fueron encontradas en Integra: %s",
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

    @Override
    public List<IntegraAcademicProgram> getIntegraAcademicProgramsByProgramCodes(Set<String> programCodes) {

        List<IntegraAcademicProgram> integraAcademicPrograms = integraPersistencePort
                .getIntegraAcademicProgramsByProgramCodes(programCodes);

        if (integraAcademicPrograms.isEmpty()) {
            throw new IntegraAcademicProgramNotFoundException(
                    String.format("Programas académicos de Integra con códigos %s no encontrados",
                            String.join(", ", programCodes))
            );
        }

        return integraAcademicPrograms;
    }

    @Override
    public Map<AcademicProgramType, List<IntegraAcademicProgram>> getAllAcademicProgramsMappedByType() {
        return integraPersistencePort.getAllAcademicProgramsMappedByType();
    }

    @Override
    public IntegraDependency getIntegraDependencyByDependencyName(String dependencyName) {
        return integraPersistencePort.getIntegraDependencyByDependencyName(dependencyName);
    }

    @Override
    public IntegraFunctionary getIntegraFunctionaryByEmail(String email) {
        return integraPersistencePort.getIntegraFunctionaryByEmail(email)
                .orElseThrow(() -> new IntegraUserNotFoundException(
                        String.format("Funcionario de Integra con correo electrónico %s no encontrado. " +
                                "Esto puede ocurrir porque el funcionario ya no está afiliado a la universidad. " +
                                "Si cree que esto es un error, por favor contacte al departamento DIRI.", email)));
    }

    @Override
    public IntegraStudent getIntegraStudentByEmail(String email) {
        return integraPersistencePort.getIntegraStudentByEmail(email)
                .orElseThrow(() -> new IntegraUserNotFoundException(
                        String.format("Estudiante de Integra con correo electrónico %s no encontrado", email)));
    }

    @Override
    public List<IntegraStudent> getAllStudents() {
        return integraPersistencePort.getAllStudents();
    }
}
