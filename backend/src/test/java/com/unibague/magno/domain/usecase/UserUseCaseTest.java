package com.unibague.magno.domain.usecase;

import com.unibague.magno.application.dto.request.StudentSeedbedCertificateRequest;
import com.unibague.magno.application.dto.request.integra.IntegraUserRequest;
import com.unibague.magno.domain.api.IResearchSeedbedServicePort;
import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.exception.integra.IntegraInvalidTypeException;
import com.unibague.magno.domain.exception.user.*;
import com.unibague.magno.domain.model.ResearchSeedbed;
import com.unibague.magno.domain.model.User;
import com.unibague.magno.domain.model.certificates.projections.StudentSeedbedCertificateProjection;
import com.unibague.magno.domain.model.certificates.studentcertificates.StudentSeedbedCertificate;
import com.unibague.magno.domain.model.enums.JSONIntegraType;
import com.unibague.magno.domain.model.enums.SeedbedRole;
import com.unibague.magno.domain.model.enums.Sex;
import com.unibague.magno.domain.model.enums.TypeOfInternalUser;
import com.unibague.magno.domain.model.integra.IntegraFunctionary;
import com.unibague.magno.domain.model.integra.IntegraStudent;
import com.unibague.magno.domain.spi.IUserPersistencePort;
import com.unibague.magno.domain.usecase.helper.IUserHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IIntegraServicePort integraServicePort;

    @Mock
    private IResearchSeedbedServicePort researchSeedbedServicePort;

    @Mock
    private IUserHelper userHelper;

    private UserUseCase userUseCase;

    private User user;
    private IntegraFunctionary functionary;
    private IntegraStudent student;

    @BeforeEach
    void setUp() {
        userUseCase = new UserUseCase(userPersistencePort, integraServicePort,
                researchSeedbedServicePort, userHelper);

        user = new User(1L, "Juan Perez", "123456789", "juan@gmail.com",
                "123456", false, Sex.MASCULINO, TypeOfInternalUser.ESTUDIANTE);

        functionary = new IntegraFunctionary(
                "Maria", "Gomez", "Maria Gomez", "123456",
                "maria.gomez@gmail.com", "mgomez123", "Docente Titular",
                "Facultad de Ingeniería", "Ingeniería de Sistemas", "DEP-ING-001",
                "Sede Central", "foto123.jpg", "/photos/funcionarios/mgomez.jpg",
                "IMG-98765", "15/05/1985", "10/01/2010", "4567",
                "2-9-10", "Profesor Asociado", "F");

        student = new IntegraStudent(
                "Daniel Lozano", "2420211010", "654321", "22",
                "INGENIERIA DE SISTEMAS", "4", "daniel@gmail.com", "Estudiante",
                "Activo", "9", "3154444", "M");
    }

    @Test
    void findById_UserExists_ReturnsUser() {
        // Arrange
        when(userPersistencePort.findById(1L)).thenReturn(Optional.of(user));

        // Act
        User result = userUseCase.findById(1L);

        // Assert
        assertThat(result).isNotNull()
                .extracting(User::getId, User::getFullName, User::getIdentificationNumber, User::getEmail,
                        User::getUserCode, User::isExternalUser, User::getSex)
                .containsExactly(1L, "Juan Perez", "123456789", "juan@gmail.com",
                        "123456", false, Sex.MASCULINO);

        verify(userPersistencePort, times(1)).findById(1L);
    }

    @Test
    void findById_UserDoesNotExist_ThrowsUserNotFoundException() {
        // Arrange
        when(userPersistencePort.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userUseCase.findById(99L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("Usuario con ID 99 no encontrado");

        verify(userPersistencePort, times(1)).findById(99L);
    }


    @Test
    void save_ValidUser_ReturnsSavedUser() {
        // Arrange
        when(userPersistencePort.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userPersistencePort.save(any(User.class))).thenReturn(user);

        // Act
        User result = userUseCase.save(user);

        // Assert
        assertThat(result).isNotNull()
                .extracting(User::getId, User::getFullName, User::getIdentificationNumber, User::getEmail,
                        User::getUserCode, User::isExternalUser, User::getSex)
                .containsExactly(1L, "Juan Perez", "123456789", "juan@gmail.com",
                        "123456", false, Sex.MASCULINO);

        verify(userPersistencePort, times(1)).findByEmail(user.getEmail());
        verify(userPersistencePort, times(1)).save(user);
    }


    @Test
    void update_ExistingUser_ReturnsUpdatedUser() {
        // Arrange
        when(userPersistencePort.findById(1L)).thenReturn(Optional.of(user));
        when(userPersistencePort.update(eq(1L), any(User.class))).thenReturn(user);

        // Act
        User result = userUseCase.update(1L, user);

        // Assert
        assertThat(result).isNotNull()
                .extracting(User::getId, User::getFullName)
                .containsExactly(1L, "Juan Perez");

        verify(userPersistencePort, times(1)).findById(1L);
        verify(userPersistencePort, times(1)).update(1L, user);
    }

    @Test
    void update_NonExistingUser_ThrowsUserNotFoundException() {
        // Arrange
        when(userPersistencePort.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userUseCase.update(1L, user))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("No se pudo actualizar el usuario con ID 1 porque no existe");

        verify(userPersistencePort, times(1)).findById(1L);
        verify(userPersistencePort, never()).update(anyLong(), any(User.class));
    }



    @Test
    void findByUserIdentification_UserExists_ReturnsUser() {
        // Arrange
        when(userPersistencePort.findByUserIdentification("123456789")).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userUseCase.findByUserIdentification("123456789");

        // Assert
        assertThat(result).isPresent()
                .contains(user);

        verify(userPersistencePort, times(1)).findByUserIdentification("123456789");
    }

    @Test
    void findByUserIdentification_UserDoesNotExist_ReturnsEmpty() {
        // Arrange
        when(userPersistencePort.findByUserIdentification("000000000")).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userUseCase.findByUserIdentification("000000000");

        // Assert
        assertThat(result).isEmpty();

        verify(userPersistencePort, times(1)).findByUserIdentification("000000000");
    }


    @Test
    void deleteById_UserExists_DeletesUser() {
        // Arrange
        when(userPersistencePort.findById(1L)).thenReturn(Optional.of(user));
        doNothing().when(userPersistencePort).deleteById(1L);

        // Act
        userUseCase.deleteById(1L);

        // Assert
        verify(userPersistencePort, times(1)).findById(1L);
        verify(userPersistencePort, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_UserDoesNotExist_ThrowsException() {
        // Arrange
        when(userPersistencePort.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userUseCase.deleteById(1L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("No se pudo eliminar el usuario con ID 1 porque no existe");

        verify(userPersistencePort, times(1)).findById(1L);
        verify(userPersistencePort, never()).deleteById(anyLong());
    }


    @Test
    void findAll_ReturnsListOfUsers() {
        // Arrange
        List<User> users = List.of(user, new User(2L, "Maria Lopez", "987654321", "maria@gmail.com",
                "654321", false, Sex.FEMENINO, TypeOfInternalUser.FUNCIONARIO));
        when(userPersistencePort.findAll()).thenReturn(users);

        // Act
        List<User> result = userUseCase.findAll();

        // Assert
        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .extracting(User::getFullName)
                .containsExactly("Juan Perez", "Maria Lopez");

        verify(userPersistencePort, times(1)).findAll();
    }

    @Test
    void findAll_NoUsers_ReturnsEmptyList() {
        // Arrange
        when(userPersistencePort.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<User> result = userUseCase.findAll();

        // Assert
        assertThat(result).isEmpty();

        verify(userPersistencePort, times(1)).findAll();
    }


    @Test
    void getUserListByListOfStudentMaps_ShouldReturnUsersList() {
        // Arrange
        Map<String, String> student1 = Map.of("identification", "123456");
        Map<String, String> student2 = Map.of("identification", "654321");
        List<Map<String, String>> studentMaps = List.of(student1, student2);

        User user1 = new User();
        user1.setIdentificationNumber("123456");

        IntegraStudent integraStudent2 = new IntegraStudent("Daniel Lozano", "2420211010", "654321", "22",
                "INGENIERIA DE SISTEMAS","4", "daniel@gmail.com", "Estudiante", "Activo", "9", "3154444", "M");
        User user2 = new User();
        user2.setIdentificationNumber("654321");
        user2.setEmail("daniel@gmail.com");

        when(userPersistencePort.findByUserIdentification("123456")).thenReturn(Optional.of(user1));
        when(userPersistencePort.findByUserIdentification("654321")).thenReturn(Optional.empty());
        when(integraServicePort.getFirstIntegraStudentFound("654321")).thenReturn(integraStudent2);
        when(userPersistencePort.findByEmail("daniel@gmail.com")).thenReturn(Optional.empty());
        when(userPersistencePort.save(any(User.class))).thenReturn(user2);

        // Act
        List<User> result = userUseCase.getUserListByListOfStudentMaps(studentMaps);

        // Assert
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getIdentificationNumber()).isEqualTo("123456");
        assertThat(result.get(1).getIdentificationNumber()).isEqualTo("654321");
    }


    @Test
    void findAllCountries_ReturnsListOfCountryNamesInSpanish() {
        //Arrange is not needed

        // Act
        List<String> result = userUseCase.findAllCountries();

        // Assert
        assertThat(result)
                .isNotEmpty() // Verify that the list is not null and has some elements
                .contains("México", "España", "Islas Vírgenes Británicas") // Some examples of countries in Spanish
                .doesNotContain("Mexico", "Spain", "British Virgin Islands") // Should not contain the names in English
                .doesNotHaveDuplicates()
                .isSorted();
    }


    @Test
    void getUserByIntegraStudent_ShouldReturnMappedUser() {
        // Act
        User userToCompare = userUseCase.getUserByIntegraStudent(student);

        // Assert
        assertThat(userToCompare)
                .isNotNull()
                .extracting(User::getIdentificationNumber, User::getFullName, User::getEmail, User::getUserCode, User::getSex)
                .containsExactly("654321", "Daniel Lozano", "daniel@gmail.com", "2420211010", Sex.MASCULINO);
    }


    @Test
    void findUserByIdentification_ShouldReturnUser_WhenUserExists() {
        // Arrange
        User user1 = new User();
        user1.setIdentificationNumber("123456");
        User user2 = new User();
        user2.setIdentificationNumber("654321");

        List<User> users = List.of(user1, user2);

        // Act
        User result = userUseCase.findUserByIdentification(users, "654321");

        // Assert
        assertThat(result.getIdentificationNumber()).isEqualTo("654321");
    }

    @Test
    void findUserByIdentification_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        List<User> users = List.of();

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userUseCase.findUserByIdentification(users, "999999"));
    }


    @Test
    void mapFromIntegraFunctionary_ShouldReturnMappedUser() {
        // Arrange
        IntegraUserRequest request = IntegraUserRequest.builder()
                .identification("123456")
                .type(JSONIntegraType.FUNCIONARIO)
                .build();

        when(integraServicePort.getIntegraFunctionaryByIdentification("123456")).thenReturn(functionary);

        // Act
        User userToCompare = userUseCase.mapFromIntegraFunctionary(request);

        // Assert
        assertThat(userToCompare).isNotNull()
                .extracting(User::getIdentificationNumber, User::getFullName, User::getEmail, User::getUserCode, User::getSex)
                .containsExactly("123456", "Maria Gomez", "maria.gomez@gmail.com", "mgomez123", Sex.FEMENINO);
    }

    @Test
    void mapFromIntegraFunctionary_ShouldThrowException_WhenTypeIsStudent() {
        // Arrange
        IntegraUserRequest request = IntegraUserRequest.builder()
                .identification("123456")
                .type(JSONIntegraType.ESTUDIANTE)
                .build();

        // Act & Assert
        assertThatThrownBy(() -> userUseCase.mapFromIntegraFunctionary(request))
                .isInstanceOf(IntegraInvalidTypeException.class);
    }

    @Test
    void mapFromIntegraStudent_ShouldReturnMappedUser() {
        // Arrange
        IntegraUserRequest request = IntegraUserRequest.builder()
                .identification("654321")
                .type(JSONIntegraType.ESTUDIANTE)
                .build();

        when(integraServicePort.getFirstIntegraStudentFound("654321")).thenReturn(student);

        // Act
        User userToCompare = userUseCase.mapFromIntegraStudent(request);

        // Assert
        assertThat(userToCompare).isNotNull()
                .extracting(User::getIdentificationNumber, User::getFullName, User::getEmail, User::getUserCode, User::getSex)
                .containsExactly("654321", "Daniel Lozano", "daniel@gmail.com", "2420211010", Sex.MASCULINO);
    }

    @Test
    void mapFromIntegraStudent_ShouldThrowException_WhenTypeIsFunctionary() {
        // Arrange
        IntegraUserRequest request = IntegraUserRequest.builder()
                .identification("654321")
                .type(JSONIntegraType.FUNCIONARIO)
                .build();

        // Act & Assert
        assertThatThrownBy(() -> userUseCase.mapFromIntegraStudent(request))
                .isInstanceOf(IntegraInvalidTypeException.class);
    }

    // ==================== TESTS ADICIONALES ====================

    @Test
    void save_UserAlreadyExists_ThrowsUserAlreadyExistsException() {
        // Arrange
        when(userPersistencePort.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // Act & Assert
        assertThatThrownBy(() -> userUseCase.save(user))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessage("El usuario con correo electrónico juan@gmail.com ya existe");

        verify(userPersistencePort, times(1)).findByEmail(user.getEmail());
        verify(userPersistencePort, never()).save(any(User.class));
    }

    @Test
    void findAllFunctionariesRegistered_ReturnsFunctionaryList() {
        // Arrange
        User functionary1 = new User(2L, "Carlos Rodriguez", "111111111", "carlos@gmail.com",
                "111111", false, Sex.MASCULINO, TypeOfInternalUser.FUNCIONARIO);
        User functionary2 = new User(3L, "Ana Martinez", "222222222", "ana@gmail.com",
                "222222", false, Sex.FEMENINO, TypeOfInternalUser.FUNCIONARIO);
        List<User> functionaries = List.of(functionary1, functionary2);

        when(userPersistencePort.findAllFunctionaries()).thenReturn(functionaries);

        // Act
        List<User> result = userUseCase.findAllFunctionariesRegistered();

        // Assert
        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .extracting(User::getTypeOfInternalUser)
                .containsOnly(TypeOfInternalUser.FUNCIONARIO);

        verify(userPersistencePort, times(1)).findAllFunctionaries();
    }

    @Test
    void findAllStudentsRegistered_ReturnsStudentList() {
        // Arrange
        User student1 = new User(2L, "Pedro Gomez", "333333333", "pedro@gmail.com",
                "333333", false, Sex.MASCULINO, TypeOfInternalUser.ESTUDIANTE);
        List<User> students = List.of(user, student1);

        when(userPersistencePort.findAllStudents()).thenReturn(students);

        // Act
        List<User> result = userUseCase.findAllStudentsRegistered();

        // Assert
        assertThat(result)
                .isNotNull()
                .hasSize(2)
                .extracting(User::getTypeOfInternalUser)
                .containsOnly(TypeOfInternalUser.ESTUDIANTE);

        verify(userPersistencePort, times(1)).findAllStudents();
    }

    @Test
    void findAllExternalUsersRegistered_ReturnsExternalUserList() {
        // Arrange
        User externalUser = new User(2L, "External User", "444444444", "external@gmail.com",
                null, true, Sex.MASCULINO, null);
        List<User> externalUsers = List.of(externalUser);

        when(userPersistencePort.findAllExternalUsers()).thenReturn(externalUsers);

        // Act
        List<User> result = userUseCase.findAllExternalUsersRegistered();

        // Assert
        assertThat(result)
                .isNotNull()
                .hasSize(1)
                .extracting(User::isExternalUser)
                .containsOnly(true);

        verify(userPersistencePort, times(1)).findAllExternalUsers();
    }

    @Test
    void getUserByIntegraFunctionary_ShouldReturnMappedUser() {
        // Arrange - functionary is already set up in setUp()

        // Act
        User userToCompare = userUseCase.getUserByIntegraFunctionary(functionary);

        // Assert
        assertThat(userToCompare)
                .isNotNull()
                .extracting(User::getIdentificationNumber, User::getFullName, User::getEmail,
                        User::getUserCode, User::getSex, User::getTypeOfInternalUser)
                .containsExactly("123456", "Maria Gomez", "maria.gomez@gmail.com",
                        "mgomez123", Sex.FEMENINO, TypeOfInternalUser.FUNCIONARIO);
        assertThat(userToCompare.isExternalUser()).isFalse();
    }

    @Test
    void getUserByIntegraFunctionary_MaleSex_ShouldReturnMasculino() {
        // Arrange
        IntegraFunctionary maleFunctionary = new IntegraFunctionary(
                "Pedro", "Lopez", "Pedro Lopez", "999999",
                "pedro@gmail.com", "plopez123", "Docente",
                "Facultad", "Programa", "DEP-001",
                "Sede", "foto.jpg", "/photo.jpg",
                "IMG-123", "01/01/1980", "01/01/2015", "1234",
                "1-1-1", "Profesor", "M");

        // Act
        User userToCompare = userUseCase.getUserByIntegraFunctionary(maleFunctionary);

        // Assert
        assertThat(userToCompare.getSex()).isEqualTo(Sex.MASCULINO);
    }

    @Test
    void findAllInternalUsersRegistered_ReturnsInternalUserList() {
        // Arrange
        List<User> internalUsers = List.of(user);
        when(userPersistencePort.findAllInternalUsers()).thenReturn(internalUsers);

        // Act
        List<User> result = userUseCase.findAllInternalUsersRegistered();

        // Assert
        assertThat(result)
                .isNotNull()
                .hasSize(1)
                .extracting(User::isExternalUser)
                .containsOnly(false);

        verify(userPersistencePort, times(1)).findAllInternalUsers();
    }

    @Test
    void findByEmail_UserExists_ReturnsUser() {
        // Arrange
        when(userPersistencePort.findByEmail("juan@gmail.com")).thenReturn(Optional.of(user));

        // Act
        User result = userUseCase.findByEmail("juan@gmail.com");

        // Assert
        assertThat(result)
                .isNotNull()
                .extracting(User::getEmail)
                .isEqualTo("juan@gmail.com");

        verify(userPersistencePort, times(1)).findByEmail("juan@gmail.com");
    }

    @Test
    void findByEmail_UserDoesNotExist_ThrowsUserNotFoundException() {
        // Arrange
        when(userPersistencePort.findByEmail("notfound@gmail.com")).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userUseCase.findByEmail("notfound@gmail.com"))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("Usuario con correo electrónico notfound@gmail.com no encontrado");

        verify(userPersistencePort, times(1)).findByEmail("notfound@gmail.com");
    }

    @Test
    void getStudentParticipationsInSeedbedCertificates_ReturnsProjectionList() {
        // Arrange
        StudentSeedbedCertificateProjection projection = mock(StudentSeedbedCertificateProjection.class);
        List<StudentSeedbedCertificateProjection> projections = List.of(projection);

        when(userPersistencePort.getStudentParticipationsInSeedbedCertificates(1L, 1L))
                .thenReturn(projections);

        // Act
        List<StudentSeedbedCertificateProjection> result =
                userUseCase.getStudentParticipationsInSeedbedCertificates(1L, 1L);

        // Assert
        assertThat(result)
                .isNotNull()
                .hasSize(1);

        verify(userPersistencePort, times(1))
                .getStudentParticipationsInSeedbedCertificates(1L, 1L);
    }

    @Test
    void generateStudentSeedbedCertificate_FunctionaryUser_ThrowsException() {
        // Arrange
        User functionaryUser = new User(2L, "Functionary", "111111111", "func@gmail.com",
                "111111", false, Sex.MASCULINO, TypeOfInternalUser.FUNCIONARIO);

        when(userPersistencePort.findById(2L)).thenReturn(Optional.of(functionaryUser));

        // Act & Assert
        assertThatThrownBy(() -> userUseCase.generateStudentSeedbedCertificate(2L, 1L))
                .isInstanceOf(FunctionaryNotAllowedToGenerateCertificateException.class)
                .hasMessage("Los funcionarios o usuarios externos no pueden generar certificados de participación en semilleros de investigación.");

        verify(userPersistencePort, times(1)).findById(2L);
    }

    @Test
    void generateStudentSeedbedCertificate_ExternalUser_ThrowsException() {
        // Arrange
        User externalUser = new User(3L, "External", "222222222", "external@gmail.com",
                null, true, Sex.MASCULINO, TypeOfInternalUser.FUNCIONARIO);

        when(userPersistencePort.findById(3L)).thenReturn(Optional.of(externalUser));

        // Act & Assert
        assertThatThrownBy(() -> userUseCase.generateStudentSeedbedCertificate(3L, 1L))
                .isInstanceOf(FunctionaryNotAllowedToGenerateCertificateException.class);

        verify(userPersistencePort, times(1)).findById(3L);
    }

    @Test
    void generateStudentSeedbedCertificate_NoDataAvailable_ThrowsException() {
        // Arrange
        ResearchSeedbed seedbed = new ResearchSeedbed();
        seedbed.setId(1L);

        when(userPersistencePort.findById(1L)).thenReturn(Optional.of(user));
        when(researchSeedbedServicePort.findById(1L)).thenReturn(seedbed);
        when(userPersistencePort.getStudentParticipationsInSeedbedCertificates(1L, 1L))
                .thenReturn(Collections.emptyList());

        // Act & Assert
        assertThatThrownBy(() -> userUseCase.generateStudentSeedbedCertificate(1L, 1L))
                .isInstanceOf(NoDataAvailableToGenerateCertificateException.class);

        verify(userPersistencePort, times(1)).findById(1L);
        verify(researchSeedbedServicePort, times(1)).findById(1L);
    }

    @Test
    void generateStudentSeedbedCertificate_ValidStudent_ReturnsCertificate() {
        // Arrange
        ResearchSeedbed seedbed = new ResearchSeedbed();
        seedbed.setId(1L);

        StudentSeedbedCertificateProjection projection = mock(StudentSeedbedCertificateProjection.class);
        when(projection.getStudentName()).thenReturn("Juan Perez");
        when(projection.getIdentificationNumber()).thenReturn("123456789");
        when(projection.getSeedbedName()).thenReturn("Semillero Test");
        when(projection.getInvestigationGroupName()).thenReturn("Grupo Test");
        when(projection.getStartDate()).thenReturn("2024-01-01");
        when(projection.getEndDate()).thenReturn("2024-06-30");
        when(projection.getSeedbedCoordinatorName()).thenReturn("Coordinator");
        when(projection.getInvestigationGroupCoordinatorName()).thenReturn("IG Coordinator");

        when(userPersistencePort.findById(1L)).thenReturn(Optional.of(user));
        when(researchSeedbedServicePort.findById(1L)).thenReturn(seedbed);
        when(userPersistencePort.getStudentParticipationsInSeedbedCertificates(1L, 1L))
                .thenReturn(List.of(projection));

        // Act
        StudentSeedbedCertificate result = userUseCase.generateStudentSeedbedCertificate(1L, 1L);

        // Assert
        assertThat(result)
                .isNotNull()
                .extracting(StudentSeedbedCertificate::getStudentName,
                        StudentSeedbedCertificate::getIdentificationNumber,
                        StudentSeedbedCertificate::getSeedbedName,
                        StudentSeedbedCertificate::getInvestigationGroupName)
                .containsExactly("Juan Perez", "123456789", "Semillero Test", "Grupo Test");

        assertThat(result.getSeedbedParticipations()).hasSize(1);
    }

    @Test
    void generateByteStudentSeedbedCertificate_ValidRequest_ReturnsBytes() throws Exception {
        // Arrange
        StudentSeedbedCertificateRequest request = StudentSeedbedCertificateRequest.builder()
                .userId(1L)
                .researchSeedbedId(1L)
                .build();

        ResearchSeedbed seedbed = new ResearchSeedbed();
        seedbed.setId(1L);

        StudentSeedbedCertificateProjection projection = mock(StudentSeedbedCertificateProjection.class);
        when(projection.getStudentName()).thenReturn("Juan Perez");
        when(projection.getIdentificationNumber()).thenReturn("123456789");
        when(projection.getSeedbedName()).thenReturn("Semillero Test");
        when(projection.getInvestigationGroupName()).thenReturn("Grupo Test");
        when(projection.getStartDate()).thenReturn("2024-01-01");
        when(projection.getEndDate()).thenReturn("2024-06-30");
        when(projection.getSeedbedCoordinatorName()).thenReturn("Coordinator");
        when(projection.getInvestigationGroupCoordinatorName()).thenReturn("IG Coordinator");

        byte[] expectedBytes = new byte[]{1, 2, 3};

        when(userPersistencePort.findById(1L)).thenReturn(Optional.of(user));
        when(researchSeedbedServicePort.findById(1L)).thenReturn(seedbed);
        when(userPersistencePort.getStudentParticipationsInSeedbedCertificates(1L, 1L))
                .thenReturn(List.of(projection));
        when(userPersistencePort.generateStudentSeedbedCertificate(any(StudentSeedbedCertificate.class)))
                .thenReturn(expectedBytes);

        // Act
        byte[] result = userUseCase.generateByteStudentSeedbedCertificate(request);

        // Assert
        assertThat(result).isEqualTo(expectedBytes);
        verify(userPersistencePort, times(1)).generateStudentSeedbedCertificate(any(StudentSeedbedCertificate.class));
    }

    @Test
    void findAllDiriUsers_ReturnsDiriUserList() {
        // Arrange
        List<User> diriUsers = List.of(user);
        when(userPersistencePort.findAllDistinctUsersByRole(SeedbedRole.DIRI)).thenReturn(diriUsers);

        // Act
        List<User> result = userUseCase.findAllDiriUsers();

        // Assert
        assertThat(result)
                .isNotNull()
                .hasSize(1);

        verify(userPersistencePort, times(1)).findAllDistinctUsersByRole(SeedbedRole.DIRI);
    }

    @Test
    void addDiriUser_ValidUser_ReturnsUser() {
        // Arrange
        when(userPersistencePort.findAllDistinctUsersByRole(SeedbedRole.DIRI))
                .thenReturn(Collections.emptyList());
        when(userPersistencePort.findByUserIdentification("123456789"))
                .thenReturn(Optional.of(user));
        doNothing().when(userHelper).addDiriUser("123456789", 1L);

        // Act
        User result = userUseCase.addDiriUser("123456789");

        // Assert
        assertThat(result)
                .isNotNull()
                .extracting(User::getIdentificationNumber)
                .isEqualTo("123456789");

        verify(userHelper, times(1)).addDiriUser("123456789", 1L);
    }

    @Test
    void addDiriUser_UserAlreadyDiri_ThrowsDiriUserAlreadyExistsException() {
        // Arrange
        when(userPersistencePort.findAllDistinctUsersByRole(SeedbedRole.DIRI))
                .thenReturn(List.of(user));

        // Act & Assert
        assertThatThrownBy(() -> userUseCase.addDiriUser("123456789"))
                .isInstanceOf(DiriUserAlreadyExistsException.class)
                .hasMessage("El usuario que intenta agregar ya es un usuario DIRI.");

        verify(userHelper, never()).addDiriUser(anyString(), anyLong());
    }

    @Test
    void addDiriUser_UserNotFound_ThrowsUserNotFoundException() {
        // Arrange
        when(userPersistencePort.findAllDistinctUsersByRole(SeedbedRole.DIRI))
                .thenReturn(Collections.emptyList());
        when(userPersistencePort.findByUserIdentification("999999999"))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userUseCase.addDiriUser("999999999"))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("Usuario con identificación 999999999 no encontrado");

        verify(userHelper, never()).addDiriUser(anyString(), anyLong());
    }

    @Test
    void deleteDiriUser_ValidDiriUser_DeletesSuccessfully() {
        // Arrange
        when(userPersistencePort.findAllDistinctUsersByRole(SeedbedRole.DIRI))
                .thenReturn(List.of(user));
        when(userPersistencePort.findByUserIdentification("123456789"))
                .thenReturn(Optional.of(user));
        doNothing().when(userHelper).deleteDiriUser("123456789", 1L);

        // Act
        userUseCase.deleteDiriUser("123456789");

        // Assert
        verify(userHelper, times(1)).deleteDiriUser("123456789", 1L);
    }

    @Test
    void deleteDiriUser_UserNotDiri_ThrowsDiriUserNotFoundException() {
        // Arrange
        when(userPersistencePort.findAllDistinctUsersByRole(SeedbedRole.DIRI))
                .thenReturn(Collections.emptyList());

        // Act & Assert
        assertThatThrownBy(() -> userUseCase.deleteDiriUser("123456789"))
                .isInstanceOf(DiriUserNotFoundException.class)
                .hasMessage("El usuario que intenta eliminar no es un usuario DIRI.");

        verify(userHelper, never()).deleteDiriUser(anyString(), anyLong());
    }

    @Test
    void deleteDiriUser_UserNotFound_ThrowsUserNotFoundException() {
        // Arrange
        when(userPersistencePort.findAllDistinctUsersByRole(SeedbedRole.DIRI))
                .thenReturn(List.of(user));
        when(userPersistencePort.findByUserIdentification("123456789"))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userUseCase.deleteDiriUser("123456789"))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("Usuario con identificación 123456789 no encontrado");

        verify(userHelper, never()).deleteDiriUser(anyString(), anyLong());
    }

    @Test
    void findInvestigationGroupCoordinatorsByAcademicPeriodId_ReturnsCoordinatorList() {
        // Arrange
        User coordinator = new User(2L, "Coordinator", "555555555", "coord@gmail.com",
                "555555", false, Sex.MASCULINO, TypeOfInternalUser.FUNCIONARIO);
        List<User> coordinators = List.of(coordinator);

        when(userPersistencePort.findInvestigationGroupCoordinatorsByAcademicPeriodId(1L))
                .thenReturn(coordinators);

        // Act
        List<User> result = userUseCase.findInvestigationGroupCoordinatorsByAcademicPeriodId(1L);

        // Assert
        assertThat(result)
                .isNotNull()
                .hasSize(1)
                .extracting(User::getFullName)
                .containsExactly("Coordinator");

        verify(userPersistencePort, times(1))
                .findInvestigationGroupCoordinatorsByAcademicPeriodId(1L);
    }

    @Test
    void getUserByIntegraStudent_FemaleSex_ShouldReturnFemenino() {
        // Arrange
        IntegraStudent femaleStudent = new IntegraStudent(
                "Maria Lopez", "2420211011", "777777", "21",
                "INGENIERIA DE SISTEMAS", "5", "maria@gmail.com", "Estudiante",
                "Activo", "8", "3155555", "F");

        // Act
        User userToCompare = userUseCase.getUserByIntegraStudent(femaleStudent);

        // Assert
        assertThat(userToCompare.getSex()).isEqualTo(Sex.FEMENINO);
        assertThat(userToCompare.getTypeOfInternalUser()).isEqualTo(TypeOfInternalUser.ESTUDIANTE);
    }

}