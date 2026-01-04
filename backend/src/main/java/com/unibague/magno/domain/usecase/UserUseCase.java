package com.unibague.magno.domain.usecase;

import com.unibague.magno.application.dto.request.StudentSeedbedCertificateRequest;
import com.unibague.magno.application.dto.request.integra.IntegraUserRequest;
import com.unibague.magno.domain.api.IResearchSeedbedServicePort;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.exception.integra.IntegraInvalidTypeException;
import com.unibague.magno.domain.exception.user.*;
import com.unibague.magno.domain.model.ResearchSeedbed;
import com.unibague.magno.domain.model.User;
import com.unibague.magno.domain.model.certificates.projections.StudentSeedbedCertificateProjection;
import com.unibague.magno.domain.model.certificates.studentcertificates.StudentSeedbedCertificate;
import com.unibague.magno.domain.model.certificates.studentcertificates.StudentSeedbedParticipation;
import com.unibague.magno.domain.model.enums.JSONIntegraType;
import com.unibague.magno.domain.model.enums.SeedbedRole;
import com.unibague.magno.domain.model.enums.Sex;
import com.unibague.magno.domain.model.enums.TypeOfInternalUser;
import com.unibague.magno.domain.model.integra.IntegraFunctionary;
import com.unibague.magno.domain.model.integra.IntegraStudent;
import com.unibague.magno.domain.spi.IUserPersistencePort;
import com.unibague.magno.domain.usecase.helper.IUserHelper;

import java.time.LocalDate;
import java.util.*;

import static com.unibague.magno.domain.usecase.ResearchSeedbedStudentProfileUseCase.IDENTIFICATION;

public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IIntegraServicePort integraServicePort;
    private final IResearchSeedbedServicePort researchSeedbedServicePort;
    private final IUserHelper userHelper;

    public UserUseCase(IUserPersistencePort userPersistencePort,
                       IIntegraServicePort integraServicePort,
                       IResearchSeedbedServicePort researchSeedbedServicePort,
                       IUserHelper userHelper) {
        this.userPersistencePort = userPersistencePort;
        this.integraServicePort = integraServicePort;
        this.researchSeedbedServicePort = researchSeedbedServicePort;
        this.userHelper = userHelper;
    }

    @Override
    public User findById(Long id) {
        return userPersistencePort.findById(id)
                .orElseThrow(() -> new UserNotFoundException(
                        String.format("Usuario con ID %s no encontrado", id)));
    }

    @Override
    public User save(User user) {
        if (findByEmailOptional(user.getEmail()).isPresent()){
            throw new UserAlreadyExistsException(String.format(
                    "El usuario con correo electrónico %s ya existe", user.getEmail()));
        }
        return userPersistencePort.save(user);
    }

    @Override
    public User update(Long id, User user) {
        if(userPersistencePort.findById(id).isEmpty()) {
            throw new UserNotFoundException(
                    String.format("No se pudo actualizar el usuario con ID %s porque no existe", user.getId()));
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
                    String.format("No se pudo eliminar el usuario con ID %s porque no existe", id));
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
                        String.format("Usuario con identificación %s no encontrado", identification)
                ));
    }

    @Override
    public User mapFromIntegraFunctionary(IntegraUserRequest userRequest) {

        if (userRequest.getType().equals(JSONIntegraType.ESTUDIANTE)) {
            throw new IntegraInvalidTypeException("El tipo de usuario de Integra no es válido para este método");
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
            throw new IntegraInvalidTypeException("El tipo de usuario de Integra no es válido para este método");
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
                        String.format("Usuario con correo electrónico %s no encontrado", email)));
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
                    ("Los funcionarios o usuarios externos no pueden generar certificados de participación en semilleros de investigación.");
        }

        ResearchSeedbed researchSeedbed = researchSeedbedServicePort.findById(researchSeedbedId);
        List<StudentSeedbedCertificateProjection> certificateData =
                getStudentParticipationsInSeedbedCertificates(userId, researchSeedbed.getId());

        if (certificateData.isEmpty()) {
            throw new NoDataAvailableToGenerateCertificateException(
                    "No hay información disponible para generar el certificado de participación en semillero de investigación." +
                            "Esto puede suceder debido a que la participación del estudiante se registró como inactiva o el semillero" +
                            "está marcado como inactivo en los diferentes periodos académicos.");
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

    @Override
    public List<User> findAllDiriUsers() {
        return userPersistencePort.findAllDistinctUsersByRole(SeedbedRole.DIRI);
    }

    @Override
    public User addDiriUser(String diriIdentification) {
        List<User> diriUsers = findAllDiriUsers();
        boolean alreadyExists = diriUsers.stream()
                .anyMatch(user -> user.getIdentificationNumber().equals(diriIdentification));
        if (alreadyExists) {
            throw new DiriUserAlreadyExistsException("El usuario que intenta agregar ya es un usuario DIRI.");
        }
        Optional<User> userOptional = findByUserIdentification(diriIdentification);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(
                    String.format("Usuario con identificación %s no encontrado", diriIdentification));
        }
        User user = userOptional.get();
        userHelper.addDiriUser(diriIdentification, user.getId());
        return user;
    }

    @Override
    public void deleteDiriUser(String diriIdentification) {
        List<User> diriUsers = findAllDiriUsers();
        boolean exists = diriUsers.stream()
                .anyMatch(user -> user.getIdentificationNumber().equals(diriIdentification));
        if (!exists) {
            throw new DiriUserNotFoundException("El usuario que intenta eliminar no es un usuario DIRI.");
        }
        Optional<User> userOptional = findByUserIdentification(diriIdentification);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(
                    String.format("Usuario con identificación %s no encontrado", diriIdentification));
        }
        User user = userOptional.get();
        userHelper.deleteDiriUser(diriIdentification, user.getId());
    }

    @Override
    public List<User> findInvestigationGroupCoordinatorsByAcademicPeriodId(Long academicPeriodId) {
        return userPersistencePort.findInvestigationGroupCoordinatorsByAcademicPeriodId(academicPeriodId);
    }
}
