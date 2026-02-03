package com.unibague.magno.infrastructure.output.jpa.adapter.integra;

import com.unibague.magno.domain.exception.integra.IntegraDependencyNotFoundException;
import com.unibague.magno.domain.exception.integra.NullIntegraResponseException;
import com.unibague.magno.domain.model.enums.AcademicProgramType;
import com.unibague.magno.domain.model.integra.*;
import com.unibague.magno.domain.spi.integra.IIntegraPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * REST client implementation of {@link IIntegraPersistencePort} for fetching data from the external Integra system.
 * Connects to the University of Ibagué's central Integra API to retrieve information about
 * functionaries, students, academic programs, and organizational dependencies.
 */
@Component
@RequiredArgsConstructor
public class IntegraUserClient implements IIntegraPersistencePort {

    private final RestTemplate restTemplate;

    @Value("${integra.api.token}")
    private String integraApiToken;

    @Value("${integra.base.url}")
    private String baseUrl;

    @Value("${integra.all.functionaries.url}")
    private String allFunctionariesUrl;

    @Value("${integra.student.url1}")
    private String studentsUrl1;

    @Value("${integra.student.url2}")
    private String studentsUrl2;

    @Value("${integra.students.all}")
    private String allStudentsUrl;

    @Value("${integra.academic.programs.url}")
    private String academicProgramsUrl;

    @Value("${integra.all.dependencies.url}")
    private String dependenciesUrl;

    @Value("${integra.academic.programs.url.academia}")
    private String academiaAcademicProgramsGeneralUrl;

    private static final String UNDERGRADUATE = "1";
    private static final String POSTGRADUATE = "2";

    public List<AcademiaAcademicProgram> getAcademiaAcademicPrograms1(){
        String path = academiaAcademicProgramsGeneralUrl + UNDERGRADUATE;
        ResponseEntity<List<AcademiaAcademicProgram>> response = restTemplate.exchange(
                path,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AcademiaAcademicProgram>>() {}
        );

        if (response.getBody() == null) {
            throw new NullIntegraResponseException();
        }

        return response.getBody();
    }

    public List<AcademiaAcademicProgram> getAcademiaAcademicPrograms2(){
        String path = academiaAcademicProgramsGeneralUrl + POSTGRADUATE;
        ResponseEntity<List<AcademiaAcademicProgram>> response = restTemplate.exchange(
                path,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<AcademiaAcademicProgram>>() {}
        );

        if (response.getBody() == null) {
            throw new NullIntegraResponseException();
        }

        return response.getBody();
    }

    @Override
    public List<IntegraFunctionary> getAllFunctionaries() {
        final String url = baseUrl + allFunctionariesUrl + integraApiToken;

        ResponseEntity<List<IntegraFunctionary>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<IntegraFunctionary>>() {}
        );
        if (response.getBody() == null) {
            throw new NullIntegraResponseException();
        }
        return response.getBody();
    }

    @Override
    public List<IntegraStudent> getIntegraStudentRecordsByIdentification(String identification) {
        final String url = baseUrl + studentsUrl1 + integraApiToken + "&code_user=" + identification + studentsUrl2;

        ResponseEntity<List<IntegraStudent>> response = getListResponseEntity(url);
        if (response.getBody() == null) {
            throw new NullIntegraResponseException();
        }
        return response.getBody();
    }

    public List<IntegraAcademicProgram> getAllAcademicPrograms() {

        List<IntegraAcademicProgram> integraPrograms = getAllAcademicProgramsDeprecated();
        List<AcademiaAcademicProgram> academiaUndergrad = getAcademiaAcademicPrograms1();
        List<AcademiaAcademicProgram> academiaPostgrad = getAcademiaAcademicPrograms2();

        List<IntegraAcademicProgram> academiaPrograms = Stream.concat(
                        academiaUndergrad.stream(),
                        academiaPostgrad.stream()
                ).map(this::mapAcademiaToIntegra)
                .toList();

        List<IntegraAcademicProgram> allPrograms = new ArrayList<>();
        allPrograms.addAll(integraPrograms);
        allPrograms.addAll(academiaPrograms);

        return allPrograms.stream()
                .collect(Collectors.toMap(
                        IntegraAcademicProgram::getProgramCode,
                        program -> program,
                        (existing, replacement) -> existing
                ))
                .values()
                .stream()
                .toList();
    }

    private IntegraAcademicProgram mapAcademiaToIntegra(AcademiaAcademicProgram academia) {
        return new IntegraAcademicProgram(
                academia.getProgramCode(),
                academia.getProgramName(),
                null,
                academia.getSedeName(),
                academia.getMethodology(),
                academia.getModality(),
                null
        );
    }

    private List<IntegraAcademicProgram> getAllAcademicProgramsDeprecated() {
        final String url = baseUrl + academicProgramsUrl + integraApiToken;

        ResponseEntity<List<IntegraAcademicProgram>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<IntegraAcademicProgram>>() {}
        );

        if (response.getBody() == null) {
            throw new NullIntegraResponseException();
        }
        return response.getBody();
    }

    private List<IntegraDependency> getAllDependencies() {
        final String url = baseUrl + dependenciesUrl + integraApiToken;

        ResponseEntity<List<IntegraDependency>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<IntegraDependency>>() {}
        );
        if (response.getBody() == null) {
            throw new NullIntegraResponseException();
        }
        return response.getBody();
    }

    public List<IntegraStudent> getAllStudents() {
        final String url = baseUrl + allStudentsUrl + integraApiToken;

        ResponseEntity<List<IntegraStudent>> response = getListResponseEntity(url);
        if (response.getBody() == null) {
            throw new NullIntegraResponseException();
        }
        return response.getBody();
    }

    /**
     * Combines academic programs from both Integra and Academia sources, and maps them by type.
     * @return A map where the key is the AcademicProgramType (PREGRADO or POSGRADO) and the value
     * is a list of unique IntegraAcademicProgram objects.
     */
    @Override
    public Map<AcademicProgramType, List<IntegraAcademicProgram>> getAllAcademicProgramsMappedByType() {
        List<IntegraAcademicProgram> integraPrograms = getAllAcademicProgramsDeprecated();
        List<AcademiaAcademicProgram> academiaUndergrad = getAcademiaAcademicPrograms1();
        List<AcademiaAcademicProgram> academiaPostgrad = getAcademiaAcademicPrograms2();

        // Undergraduate programs
        List<IntegraAcademicProgram> undergradPrograms = new ArrayList<>();
        undergradPrograms.addAll(integraPrograms);
        undergradPrograms.addAll(academiaUndergrad.stream()
                .map(this::mapAcademiaToIntegra)
                .toList());

        // Postgraduate programs
        List<IntegraAcademicProgram> postgradPrograms = academiaPostgrad.stream()
                .map(this::mapAcademiaToIntegra)
                .toList();

        // Delete duplicates within each type, keeping the first occurrence
        Map<String, IntegraAcademicProgram> uniqueUndergrad = undergradPrograms.stream()
                .collect(Collectors.toMap(
                        IntegraAcademicProgram::getProgramCode,
                        program -> program,
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));

        Map<String, IntegraAcademicProgram> uniquePostgrad = postgradPrograms.stream()
                .filter(p -> !uniqueUndergrad.containsKey(p.getProgramCode())) // Avoid duplicates across types
                .collect(Collectors.toMap(
                        IntegraAcademicProgram::getProgramCode,
                        program -> program,
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));

        Map<AcademicProgramType, List<IntegraAcademicProgram>> result = new EnumMap<>(AcademicProgramType.class);
        result.put(AcademicProgramType.PREGRADO, new ArrayList<>(uniqueUndergrad.values()));
        result.put(AcademicProgramType.POSGRADO, new ArrayList<>(uniquePostgrad.values()));

        return result;
    }

    @Override
    public List<IntegraAcademicProgram> getIntegraAcademicProgramsByProgramCodes(Set<String> programCodes) {
        return getAllAcademicPrograms().stream()
                .filter(academicProgram -> programCodes.contains(academicProgram.getProgramCode()))
                .toList();
    }

    @Override
    public IntegraDependency getIntegraDependencyByDependencyName(String dependencyName) {
        return getAllDependencies().stream()
                .filter(dependency -> dependency.getDepName().equals(dependencyName))
                .findFirst()
                .orElseThrow(() -> {
                    String message = String.format("Integra dependency with name %s not found", dependencyName);
                    return new IntegraDependencyNotFoundException(message);
                });
    }

    @Override
    public Optional<IntegraFunctionary> getIntegraFunctionaryByEmail(String email) {
        return getAllFunctionaries()
                .stream()
                .filter(functionary -> functionary.getEmail().equals(email))
                .findFirst();
    }

    @Override
    public Optional<IntegraStudent> getIntegraStudentByEmail(String email) {
        return getAllStudents()
                .stream()
                .filter(student -> student.getEmail().equals(email))
                .findFirst();
    }

    private ResponseEntity<List<IntegraStudent>> getListResponseEntity(String url) {
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<IntegraStudent>>() {}
        );
    }

}
