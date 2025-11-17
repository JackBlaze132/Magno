package com.unibague.magno.application.handler.impl;

import com.unibague.magno.application.dto.request.StudentSeedbedCertificateRequest;
import com.unibague.magno.application.dto.request.integra.IntegraUserRequest;
import com.unibague.magno.application.dto.request.UserRequest;
import com.unibague.magno.application.dto.response.UserResponse;
import com.unibague.magno.application.handler.interfaces.IUserHandler;
import com.unibague.magno.application.mapper.request.UserRequestMapper;
import com.unibague.magno.application.mapper.response.UserResponseMapper;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.model.User;
import com.unibague.magno.domain.model.certificates.studentcertificates.StudentSeedbedCertificate;
import com.unibague.magno.infrastructure.util.certificates.HtmlRenderService;
import com.unibague.magno.infrastructure.util.certificates.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserHandler implements IUserHandler {

    private final IUserServicePort userServicePort;
    private final UserRequestMapper userRequestMapper;
    private final UserResponseMapper userResponseMapper;

    private final HtmlRenderService htmlRenderService;
    private final PdfService pdfService;
    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public UserResponse findById(Long id) {
        User user = userServicePort.findById(id);
        return userResponseMapper.toResponse(user);
    }

    @Override
    public UserResponse save(UserRequest user) {
        return userResponseMapper.toResponse(userServicePort.save(userRequestMapper.toUser(user)));
    }

    @Override
    public UserResponse save(IntegraUserRequest user) {
        return userResponseMapper.toResponse(userServicePort.save(userRequestMapper.toUser(user)));
    }

    @Override
    public UserResponse updateById(Long id, UserRequest user) {
        return userResponseMapper.toResponse(userServicePort.update(id, userRequestMapper.toUser(user)));
    }

    @Override
    public void deleteById(Long id) {
        userServicePort.deleteById(id);
    }

    @Override
    public List<UserResponse> findAll() {
        return userResponseMapper.toResponseList(userServicePort.findAll());
    }

    @Override
    public List<UserResponse> findAllFunctionariesRegistered() {
        return userServicePort.findAllFunctionariesRegistered()
                .stream()
                .map(userResponseMapper::toResponse)
                .toList();
    }

    @Override
    public List<UserResponse> findAllStudentsRegistered() {
        return userServicePort.findAllStudentsRegistered()
                .stream()
                .map(userResponseMapper::toResponse)
                .toList();
    }

    @Override
    public List<UserResponse> findAllExternalUsersRegistered() {
        return userServicePort.findAllExternalUsersRegistered()
                .stream()
                .map(userResponseMapper::toResponse)
                .toList();
    }

    @Override
    public List<String> findAllCountries() {
        return userServicePort.findAllCountries();
    }

    @Override
    public List<UserResponse> findAllInternalUsersRegistered() {
        return userServicePort.findAllInternalUsersRegistered()
                .stream()
                .map(userResponseMapper::toResponse)
                .toList();
    }

    @Override
    public StudentSeedbedCertificate generateStudentSeedbedCertificate(Long userId, Long researchSeedbedId) {
        return userServicePort.generateStudentSeedbedCertificate(userId, researchSeedbedId);
    }

    public byte[] generateStudentSeedbedCertificate(StudentSeedbedCertificateRequest studentSeedbedCertificateRequest)
            throws IOException {

        Long userId = studentSeedbedCertificateRequest.getUserId();
        Long researchSeedbedId = studentSeedbedCertificateRequest.getResearchSeedbedId();

        StudentSeedbedCertificate certificate = generateStudentSeedbedCertificate(userId, researchSeedbedId);

        Map<String, Object> data = new HashMap<>();

        data.put("nombre", certificate.getStudentName());
        data.put("cedula", certificate.getIdentificationNumber());
        data.put("semillero", certificate.getSeedbedName());
        data.put("grupo", certificate.getInvestigationGroupName());

        String coordinator = certificate.getSeedbedParticipations().isEmpty()
                ? ""
                : certificate.getSeedbedParticipations().getFirst().getSeedbedCoordinatorName();
        data.put("coordinador", coordinator);

        List<String> periodos = certificate.getSeedbedParticipations().stream()
                .map(p -> formatoPeriodo(p.getStartDate(), p.getEndDate()))
                .toList();

        data.put("periodos", periodos);

        LocalDate hoy = LocalDate.now();
        data.put("dia", hoy.getDayOfMonth());
        data.put("mes", mesEnEspanol(hoy.getMonthValue()));
        data.put("anio", hoy.getYear());

        String logo1Path = resourceLoader.getResource("classpath:static/images/logo1.png").getURI().toString();
        String logo2Path = resourceLoader.getResource("classpath:static/images/logo2.png").getURI().toString();
        String firmaPath = resourceLoader.getResource("classpath:static/firma.png").getURI().toString();

        data.put("logo1Path", logo1Path);
        data.put("logo2Path", logo2Path);
        data.put("firmaPath", firmaPath);

        // Director fijo por ahora
        data.put("director", "Jorge Enrique García Melo");
        data.put("cargo", "Director");

        // ----- Render HTML y generar PDF -----
        String html = htmlRenderService.renderCertificado(data);
        return pdfService.htmlToPdf(html);
    }

    private String formatoPeriodo(LocalDate inicio, LocalDate fin) {
        return mesEnEspanol(inicio.getMonthValue()) +
                " a " +
                mesEnEspanol(fin.getMonthValue()) +
                " de " +
                fin.getYear();
    }

    private String mesEnEspanol(int mes) {
        return switch (mes) {
            case 1 -> "enero";
            case 2 -> "febrero";
            case 3 -> "marzo";
            case 4 -> "abril";
            case 5 -> "mayo";
            case 6 -> "junio";
            case 7 -> "julio";
            case 8 -> "agosto";
            case 9 -> "septiembre";
            case 10 -> "octubre";
            case 11 -> "noviembre";
            case 12 -> "diciembre";
            default -> "";
        };
    }
}
