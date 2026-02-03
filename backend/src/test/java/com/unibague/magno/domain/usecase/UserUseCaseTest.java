package com.unibague.magno.domain.usecase;

import com.unibague.magno.application.dto.request.integra.IntegraUserRequest;
import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.exception.integra.IntegraInvalidTypeException;
import com.unibague.magno.domain.exception.user.UserNotFoundException;
import com.unibague.magno.domain.model.User;
import com.unibague.magno.domain.model.enums.JSONIntegraType;
import com.unibague.magno.domain.model.enums.Sex;
import com.unibague.magno.domain.model.enums.TypeOfInternalUser;
import com.unibague.magno.domain.model.integra.IntegraFunctionary;
import com.unibague.magno.domain.model.integra.IntegraStudent;
import com.unibague.magno.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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

    @InjectMocks
    private UserUseCase userUseCase;

    private User user;
    private IntegraFunctionary functionary;
    private IntegraStudent student;

    @BeforeEach
    void setUp() {
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
        when(userPersistencePort.save(any(User.class))).thenReturn(user);

        // Act
        User result = userUseCase.save(user);

        // Assert
        assertThat(result).isNotNull()
                .extracting(User::getId, User::getFullName, User::getIdentificationNumber, User::getEmail,
                        User::getUserCode, User::isExternalUser, User::getSex)
                .containsExactly(1L, "Juan Perez", "123456789", "juan@gmail.com",
                        "123456", false, Sex.MASCULINO);

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

        when(userPersistencePort.findByUserIdentification("123456")).thenReturn(Optional.of(user1));
        when(userPersistencePort.findByUserIdentification("654321")).thenReturn(Optional.empty());
        when(integraServicePort.getFirstIntegraStudentFound("654321")).thenReturn(integraStudent2);
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

}