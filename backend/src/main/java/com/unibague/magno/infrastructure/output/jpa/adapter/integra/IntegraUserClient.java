package com.unibague.magno.infrastructure.output.jpa.adapter.integra;

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
    public List<IntegraStudent> getStudentByIdentification(String identification) {
        final String url = baseUrl + studentsUrl1 + identification + studentsUrl2;

        ResponseEntity<List<IntegraStudent>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<IntegraStudent>>() {}
        );
        return response.getBody();
    }
}
