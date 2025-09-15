package com.unibague.magno.infrastructure.output.jpa.adapter.integra;

import com.unibague.magno.domain.exception.integra.IntegraDependencyNotFoundException;
import com.unibague.magno.domain.exception.integra.NullIntegraResponseException;
import com.unibague.magno.domain.model.integra.*;
import com.unibague.magno.domain.spi.integra.IIntegraPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class IntegraUserClient implements IIntegraPersistencePort {

    private final RestTemplate restTemplate;

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

    @Value("${INTEGRA_ACADEMIC_PROGRAMS_ACADEMIA_URL}")
    private String academiaAcademicProgramsGeneralUrl;

    private final String undergraduate = "1";
    private final String postgraduate = "2";

    public List<AcademiaAcademicProgram> getAcademiaAcademicPrograms1(){
        String path = academiaAcademicProgramsGeneralUrl + undergraduate;
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
        String path = academiaAcademicProgramsGeneralUrl + postgraduate;
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
        final String url = baseUrl + allFunctionariesUrl;

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
        final String url = baseUrl + studentsUrl1 + identification + studentsUrl2;

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
        final String url = baseUrl + academicProgramsUrl;

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
        final String url = baseUrl + dependenciesUrl;

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

    private List<IntegraStudent> getAllStudents() {
        final String url = baseUrl + allStudentsUrl;

        ResponseEntity<List<IntegraStudent>> response = getListResponseEntity(url);
        if (response.getBody() == null) {
            throw new NullIntegraResponseException();
        }
        return response.getBody();
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

    public String getUndergraduate() {
        return undergraduate;
    }
}
