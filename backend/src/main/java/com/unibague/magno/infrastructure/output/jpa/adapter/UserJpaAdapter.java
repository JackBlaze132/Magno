package com.unibague.magno.infrastructure.output.jpa.adapter;

import com.ibm.icu.text.RuleBasedNumberFormat;
import com.unibague.magno.application.dto.util.certificate.StudentSeedbedCertificateBuilder;
import com.unibague.magno.application.dto.util.certificate.StudentSeedbedCertificateFields;
import com.unibague.magno.domain.model.User;
import com.unibague.magno.domain.model.certificates.projections.StudentSeedbedCertificateProjection;
import com.unibague.magno.domain.model.certificates.studentcertificates.StudentSeedbedCertificate;
import com.unibague.magno.domain.model.enums.SeedbedRole;
import com.unibague.magno.domain.spi.IUserPersistencePort;
import com.unibague.magno.infrastructure.output.jpa.entity.UserEntity;
import com.unibague.magno.infrastructure.output.jpa.mapper.UserEntityMapper;
import com.unibague.magno.infrastructure.output.jpa.repository.IUserRepository;
import com.unibague.magno.infrastructure.util.certificates.HtmlRenderService;
import com.unibague.magno.infrastructure.util.certificates.PdfService;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@Component
@Transactional
@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    private final HtmlRenderService htmlRenderService;
    private final PdfService pdfService;

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public Optional<User> findById(Long id) {
        Optional<UserEntity> user = userRepository.findById(id);
        return user.map(userEntityMapper::toUser);
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = userEntityMapper.toUserEntity(user);
        UserEntity savedUserEntity = userRepository.save(userEntity);
        return userEntityMapper.toUser(savedUserEntity);
    }

    @Override
    public User update(Long id, User user) {
        UserEntity userEntity = userEntityMapper.toUserEntity(id, user);
        UserEntity updatedUserEntity = userRepository.save(userEntity);
        return userEntityMapper.toUser(updatedUserEntity);
    }

    @Override
    public Optional<User> findByUserIdentification(String identification) {
        Optional<UserEntity> user = Optional.empty();
        try {
            user = userRepository.findByIdentificationNumber(identification);
        } catch (Exception e) {
            if (e instanceof IncorrectResultSizeDataAccessException || e instanceof NonUniqueResultException) {
                return userRepository.findAllByIdentificationNumber(identification)
                        .stream()
                        .map(userEntityMapper::toUser)
                        .findFirst();
            }
        }
        return user.map(userEntityMapper::toUser);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return userEntityMapper.toUserList(userRepository.findAll());
    }

    @Override
    public List<User> findAllExternalUsers() {
        return userRepository.findAllByIsExternalUserTrue()
                .stream()
                .map(userEntityMapper::toUser)
                .toList();
    }

    @Override
    public List<User> findAllInternalUsers() {
        return userRepository.findByIsExternalUserFalse()
                .stream()
                .map(userEntityMapper::toUser)
                .toList();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userEntityMapper::toUser);
    }

    @Override
    public List<User> findAllFunctionaries() {
        return userRepository.findAllFunctionaries()
                .stream()
                .map(userEntityMapper::toUser)
                .toList();
    }

    @Override
    public List<User> findAllStudents() {
        return userRepository.findAllStudents()
                .stream()
                .map(userEntityMapper::toUser)
                .toList();
    }

    @Override
    public List<StudentSeedbedCertificateProjection> getStudentParticipationsInSeedbedCertificates(Long userId, Long researchseedbedId) {
        return userRepository.getStudentParticipationsInSeedbedCertificates(userId, researchseedbedId);
    }

    @Override
    public byte[] generateStudentSeedbedCertificate(StudentSeedbedCertificate certificate)
            throws IOException {

        String coordinator = certificate.getSeedbedParticipations().isEmpty()
                ? ""
                : certificate.getSeedbedParticipations().getFirst().getSeedbedCoordinatorName();

        List<String> periods = certificate.getSeedbedParticipations().stream()
                .map(p -> pdfService.periodFormat(p.getStartDate(), p.getEndDate()))
                .toList();

        LocalDate today = LocalDate.now(ZoneId.of(StudentSeedbedCertificateFields.TIMEZONE_VALUE));

        String logo1Path = resourceLoader.getResource("classpath:static/images/logo1.png").getURI().toString();
        String firmaPath = resourceLoader.getResource("classpath:static/firma.png").getURI().toString();

        RuleBasedNumberFormat formatter =
                new RuleBasedNumberFormat(new Locale("es", "ES"), RuleBasedNumberFormat.SPELLOUT);

        Map<String, Object> data = new StudentSeedbedCertificateBuilder()
                .nombre(certificate.getStudentName())
                .cedula(certificate.getIdentificationNumber())
                .semillero(certificate.getSeedbedName().toUpperCase())
                .grupo(certificate.getInvestigationGroupName().toUpperCase())
                .coordinador(coordinator)
                .periodos(periods)
                .dia(today.getDayOfMonth())
                .diaString(formatter.format(today.getDayOfMonth()))
                .mes(pdfService.spanishMonth(today.getMonthValue(), false))
                .anio(today.getYear())
                .logo1(logo1Path)
                .firma(firmaPath)
                .director(StudentSeedbedCertificateFields.DIRECTOR_VALUE)
                .cargo(StudentSeedbedCertificateFields.CARGO_VALUE)
                .build();

        String html = htmlRenderService.renderCertificado(data);
        return pdfService.htmlToPdf(html);
    }

    @Override
    public List<User> findAllDistinctUsersByRole(SeedbedRole seedbedRole) {
        return userRepository.findAllDistinctUsersByRole(seedbedRole)
                .stream()
                .map(userEntityMapper::toUser)
                .toList();
    }

}
