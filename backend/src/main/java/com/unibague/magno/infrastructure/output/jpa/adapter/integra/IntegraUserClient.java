package com.unibague.magno.infrastructure.output.jpa.adapter.integra;

import com.unibague.magno.domain.exception.integra.IntegraDependencyNotFoundException;
import com.unibague.magno.domain.model.integra.IntegraAcademicProgram;
import com.unibague.magno.domain.model.integra.IntegraDependency;
import com.unibague.magno.domain.model.integra.IntegraFunctionary;
import com.unibague.magno.domain.model.integra.IntegraStudent;
import com.unibague.magno.domain.spi.integra.IIntegraPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;

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

    @Value("${integra.academic.programs.url}")
    private String academicProgramsUrl;

    @Value("${integra.all.dependencies.url}")
    private String dependenciesUrl;

    @Override
    public List<IntegraFunctionary> getAllFunctionaries() {
        final String url = baseUrl + allFunctionariesUrl;

        ResponseEntity<List<IntegraFunctionary>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<IntegraFunctionary>>() {}
        );
        return response.getBody();
    }

    @Override
    public List<IntegraStudent> getIntegraStudentRecordsByIdentification(String identification) {
        final String url = baseUrl + studentsUrl1 + identification + studentsUrl2;

        ResponseEntity<List<IntegraStudent>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<IntegraStudent>>() {}
        );
        return response.getBody();
    }

    private List<IntegraAcademicProgram> getAllAcademicPrograms() {
        final String url = baseUrl + academicProgramsUrl;

        ResponseEntity<List<IntegraAcademicProgram>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<IntegraAcademicProgram>>() {}
        );
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
}
