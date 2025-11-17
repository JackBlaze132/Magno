package com.unibague.magno.domain.usecase;

import com.unibague.magno.application.dto.request.StudentSeedbedCertificateRequest;
import com.unibague.magno.application.dto.request.integra.IntegraUserRequest;
import com.unibague.magno.domain.api.IResearchSeedbedServicePort;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.exception.integra.IntegraInvalidTypeException;
import com.unibague.magno.domain.exception.user.FunctionaryNotAllowedToGenerateCertificateException;
import com.unibague.magno.domain.exception.user.NoDataAvailableToGenerateCertificateException;
import com.unibague.magno.domain.exception.user.UserAlreadyExistsException;
import com.unibague.magno.domain.exception.user.UserNotFoundException;
import com.unibague.magno.domain.model.ResearchSeedbed;
import com.unibague.magno.domain.model.User;
import com.unibague.magno.domain.model.certificates.projections.StudentSeedbedCertificateProjection;
import com.unibague.magno.domain.model.certificates.studentcertificates.StudentSeedbedCertificate;
import com.unibague.magno.domain.model.certificates.studentcertificates.StudentSeedbedParticipation;
import com.unibague.magno.domain.model.enums.JSONIntegraType;
import com.unibague.magno.domain.model.enums.Sex;
import com.unibague.magno.domain.model.enums.TypeOfInternalUser;
import com.unibague.magno.domain.model.integra.IntegraFunctionary;
import com.unibague.magno.domain.model.integra.IntegraStudent;
import com.unibague.magno.domain.spi.IUserPersistencePort;

import java.time.LocalDate;
import java.util.*;

import static com.unibague.magno.domain.usecase.ResearchSeedbedStudentProfileUseCase.IDENTIFICATION;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IIntegraServicePort integraServicePort;
    private final IResearchSeedbedServicePort researchSeedbedServicePort;

    public UserUseCase(IUserPersistencePort userPersistencePort,
                       IIntegraServicePort integraServicePort,
                       IResearchSeedbedServicePort researchSeedbedServicePort) {
        this.userPersistencePort = userPersistencePort;
        this.integraServicePort = integraServicePort;
        this.researchSeedbedServicePort = researchSeedbedServicePort;
    }

    @Override
    public User findById(Long id) {
        return userPersistencePort.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User with id %s not found", id)));
    }

    @Override
    public User save(User user) {
        if (findByEmailOptional(user.getEmail()).isPresent()){
            throw new UserAlreadyExistsException(String.format(
                    "User with email %s already exists", user.getEmail()));
        }
        return userPersistencePort.save(user);
    }

    @Override
    public User update(Long id, User user) {
        if(userPersistencePort.findById(id).isEmpty()) {
            throw new UserNotFoundException(
                    String.format("User with id %s could not be updated because it does not exist", user.getId()));
        }
        return userPersistencePort.update(id, user);
    }

    @Override
    public Optional<User> findByUserIdentification(String identification) {
        return userPersistencePort.findByUserIdentification(identification);
    }

    @Override
    public void deleteById(Long id) {
        if (userPersistencePort.findById(id).isEmpty()) {
            throw new UserNotFoundException(
                    String.format("User with id %s could not be deleted because it does not exist", id));
        }
        userPersistencePort.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return userPersistencePort.findAll();
    }

    @Override
    // Notice that this method suppose that the field with the identifications is called "identification"
    public List<User> getUserListByListOfStudentMaps(List<Map<String, String>> cleanedStudentListOfMaps) {
        return cleanedStudentListOfMaps.stream()
                .map(studentProfile -> {
                    String identification = studentProfile.get(IDENTIFICATION); // Getting the identification from the Map
                    return findByUserIdentification(identification)
                            .orElseGet(() -> {
                                // If the user doesn't exist, we create it
                                IntegraStudent integraStudent = integraServicePort.
                                        getFirstIntegraStudentFound(identification);
                                User user = getUserByIntegraStudent(integraStudent);
                                return save(user);
                            });
                })
                .toList();
    }

    @Override
    public List<String> findAllCountries() {
        String[] countryCodes = Locale.getISOCountries();
        Locale spanishLocale = Locale.of("es");
        return Arrays.stream(countryCodes)
                .map(countryCode -> Locale.of("", countryCode))
                .map(countryLocale -> countryLocale.getDisplayCountry(spanishLocale))
                .sorted()
                .toList();
    }

    @Override
    public List<User> findAllFunctionariesRegistered() {
        return userPersistencePort.findAllFunctionaries();
    }

    @Override
    public List<User> findAllStudentsRegistered() {
        return userPersistencePort.findAllStudents();
    }

    @Override
    public List<User> findAllExternalUsersRegistered() {
        return userPersistencePort.findAllExternalUsers();
    }

    @Override
    public User getUserByIntegraStudent(IntegraStudent integraStudent) {
        User user = new User();
        user.setIdentificationNumber(integraStudent.getIdentification());
        user.setFullName(integraStudent.getName());
        user.setEmail(integraStudent.getEmail());
        user.setUserCode(integraStudent.getCodeStudent());
        user.setExternalUser(false);
        user.setSex(integraStudent.getSexo().equalsIgnoreCase("M") ? Sex.MASCULINO : Sex.FEMENINO);
        user.setTypeOfInternalUser(TypeOfInternalUser.ESTUDIANTE);
        return user;
    }

    @Override
    public User getUserByIntegraFunctionary(IntegraFunctionary integraFunctionary) {
        User user = new User();
        user.setFullName(integraFunctionary.getFullName());
        user.setIdentificationNumber(integraFunctionary.getIdentification());
        user.setEmail(integraFunctionary.getEmail());
        user.setUserCode(integraFunctionary.getCodeUser());
        user.setExternalUser(false);
        user.setSex(integraFunctionary.getSex().equalsIgnoreCase("M") ? Sex.MASCULINO : Sex.FEMENINO);
        user.setTypeOfInternalUser(TypeOfInternalUser.FUNCIONARIO);
        return user;
    }

    @Override
    public User findUserByIdentification(List<User> users, String identification) {
        return users.stream()
                .filter(u -> u.getIdentificationNumber().equals(identification))
                .findFirst()
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User with identification %s not found", identification)
                ));
    }

    @Override
    public User mapFromIntegraFunctionary(IntegraUserRequest userRequest) {

        if (userRequest.getType().equals(JSONIntegraType.ESTUDIANTE)) {
            throw new IntegraInvalidTypeException("The type of the integra user is not valid for this method");
        }

        IntegraFunctionary integraFunctionary = integraServicePort.
                getIntegraFunctionaryByIdentification(userRequest.getIdentification());

        User user = new User();
        user.setFullName(integraFunctionary.getFullName());
        user.setIdentificationNumber(integraFunctionary.getIdentification());
        user.setEmail(integraFunctionary.getEmail());
        user.setUserCode(integraFunctionary.getCodeUser());
        user.setExternalUser(false);

        Sex sex = integraFunctionary.getSex().equalsIgnoreCase("M") ? Sex.MASCULINO : Sex.FEMENINO;
        user.setSex(sex);
        user.setTypeOfInternalUser(TypeOfInternalUser.FUNCIONARIO);
        return user;
    }

    @Override
    public User mapFromIntegraStudent(IntegraUserRequest userRequest) {

        if (userRequest.getType().equals(JSONIntegraType.FUNCIONARIO)) {
            throw new IntegraInvalidTypeException("The type of the integra user is not valid for this method");
        }

        IntegraStudent integraStudent = integraServicePort.
                getFirstIntegraStudentFound(userRequest.getIdentification());

        return getUserByIntegraStudent(integraStudent);
    }

    @Override
    public List<User> findAllInternalUsersRegistered() {
        return userPersistencePort.findAllInternalUsers();
    }

    @Override
    public User findByEmail(String email) {
        return userPersistencePort.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("User with email %s not found", email)));
    }

    private Optional<User> findByEmailOptional(String email) {
        return userPersistencePort.findByEmail(email);
    }

    @Override
    public List<StudentSeedbedCertificateProjection> getStudentParticipationsInSeedbedCertificates(Long userId, Long researchseedbedId) {
        return userPersistencePort.getStudentParticipationsInSeedbedCertificates(userId, researchseedbedId);
    }

    @Override
    public StudentSeedbedCertificate generateStudentSeedbedCertificate(Long userId, Long researchSeedbedId) {

        User studentUser = findById(userId);
        boolean isFunctionaryOrExternal = studentUser.getTypeOfInternalUser().equals(TypeOfInternalUser.FUNCIONARIO)
                || studentUser.getTypeOfInternalUser() == null;

        if (isFunctionaryOrExternal) {
            throw new FunctionaryNotAllowedToGenerateCertificateException
                    ("Functionaries or external users are not allowed to generate student seedbed certificates.");
        }

        ResearchSeedbed researchSeedbed = researchSeedbedServicePort.findById(researchSeedbedId);
        List<StudentSeedbedCertificateProjection> certificateData =
                getStudentParticipationsInSeedbedCertificates(userId, researchSeedbed.getId());

        if (certificateData.isEmpty()) {
            throw new NoDataAvailableToGenerateCertificateException(
                    "No data available to generate the student seedbed certificate.");
        }

        StudentSeedbedCertificate certificate = new StudentSeedbedCertificate();
        certificate.setStudentName(certificateData.getFirst().getStudentName());
        certificate.setIdentificationNumber(certificateData.getFirst().getIdentificationNumber());
        certificate.setSeedbedName(certificateData.getFirst().getSeedbedName());
        certificate.setInvestigationGroupName(certificateData.getFirst().getInvestigationGroupName());
        List<StudentSeedbedParticipation> participations = new ArrayList<>();
        for (StudentSeedbedCertificateProjection data : certificateData) {
            StudentSeedbedParticipation participation = new StudentSeedbedParticipation();
            participation.setStartDate(LocalDate.parse(data.getStartDate()));
            participation.setEndDate(LocalDate.parse(data.getEndDate()));
            participation.setSeedbedCoordinatorName(data.getSeedbedCoordinatorName());
            participation.setInvestigationGroupCoordinatorName(data.getInvestigationGroupCoordinatorName());
            participations.add(participation);
        }
        certificate.setSeedbedParticipations(participations);
        return certificate;
    }

    @Override
    public byte[] generateByteStudentSeedbedCertificate(StudentSeedbedCertificateRequest studentSeedbedCertificateRequest)
            throws Exception {
        Long userId = studentSeedbedCertificateRequest.getUserId();
        Long researchSeedbedId = studentSeedbedCertificateRequest.getResearchSeedbedId();
        StudentSeedbedCertificate certificate =
                generateStudentSeedbedCertificate(userId, researchSeedbedId);
        return userPersistencePort.generateStudentSeedbedCertificate(certificate);
    }
}
