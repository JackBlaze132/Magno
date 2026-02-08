package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IAcademicPeriodServicePort;
import com.unibague.magno.domain.api.IAcademicProgramServicePort;
import com.unibague.magno.domain.api.IRoleServicePort;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.exception.academicperiod.AcademicPeriodNotVisibleException;
import com.unibague.magno.domain.exception.role.DiriRoleNotAllowedException;
import com.unibague.magno.domain.exception.studentprofile.StudentProfileAlreadyExistsException;
import com.unibague.magno.domain.exception.studentprofile.StudentProfileNotFoundException;
import com.unibague.magno.domain.model.AcademicPeriod;
import com.unibague.magno.domain.model.AcademicProgram;
import com.unibague.magno.domain.model.Role;
import com.unibague.magno.domain.model.StudentProfile;
import com.unibague.magno.domain.model.User;
import com.unibague.magno.domain.model.enums.SeedbedRole;
import com.unibague.magno.domain.model.integra.IntegraStudent;
import com.unibague.magno.domain.spi.IStudentProfilePersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentProfileUseCaseTest {

    @Mock
    private IStudentProfilePersistencePort studentProfilePersistencePort;
    @Mock
    private IUserServicePort userServicePort;
    @Mock
    private IIntegraServicePort integraServicePort;
    @Mock
    private IAcademicProgramServicePort academicProgramServicePort;
    @Mock
    private IRoleServicePort roleServicePort;
    @Mock
    private IAcademicPeriodServicePort academicPeriodServicePort;

    private StudentProfileUseCase studentProfileUseCase;
    private StudentProfile studentProfile;
    private AcademicPeriod academicPeriod;
    private Role estudianteRole;
    private Role diriRole;
    private User user;
    private IntegraStudent integraStudent;
    private AcademicProgram academicProgram;

    @BeforeEach
    void setUp() {
        studentProfileUseCase = new StudentProfileUseCase(
                studentProfilePersistencePort,
                userServicePort,
                integraServicePort,
                academicProgramServicePort,
                roleServicePort,
                academicPeriodServicePort
        );

        user = new User();
        user.setId(1L);
        user.setFullName("Juan Perez");
        user.setIdentificationNumber("123456789");

        academicPeriod = new AcademicPeriod(1L, "2024-I", null, null, true, true);

        estudianteRole = new Role();
        estudianteRole.setId(1L);
        estudianteRole.setName(SeedbedRole.ESTUDIANTE);

        diriRole = new Role();
        diriRole.setId(2L);
        diriRole.setName(SeedbedRole.DIRI);

        studentProfile = new StudentProfile();
        studentProfile.setId(1L);
        studentProfile.setUserId(1L);
        studentProfile.setAcademicPeriodId(1L);
        studentProfile.setRoleId(1L);
        studentProfile.setSemester((byte) 5);
        studentProfile.setAcademicProgramsIds(Set.of(1L));

        integraStudent = new IntegraStudent(
                "Juan Perez", "2420211010", "123456789", "22",
                "INGENIERIA DE SISTEMAS", "5", "juan@gmail.com", "Estudiante",
                "Activo", "9", "3154444", "M"
        );

        academicProgram = new AcademicProgram();
        academicProgram.setId(1L);
        academicProgram.setProgramCode("2420211010");
    }

    @Test
    void findById_StudentProfileExists_ReturnsStudentProfile() {
        // Arrange
        when(studentProfilePersistencePort.findById(1L)).thenReturn(Optional.of(studentProfile));

        // Act
        StudentProfile result = studentProfileUseCase.findById(1L);

        System.out.println(result.getId() + " " + result.getUserId() + " " + result.getAcademicPeriodId() + " " + result.getSemester());

        // Assert
        assertThat(result).isNotNull()
                .extracting(StudentProfile::getId, StudentProfile::getUserId,
                        StudentProfile::getAcademicPeriodId, StudentProfile::getSemester)
                .containsExactly(1L, 1L, 1L, (byte) 5);
        verify(studentProfilePersistencePort, times(1)).findById(1L);
    }

    @Test
    void findById_StudentProfileDoesNotExist_ThrowsStudentProfileNotFoundException() {
        // Arrange
        when(studentProfilePersistencePort.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> studentProfileUseCase.findById(99L))
                .isInstanceOf(StudentProfileNotFoundException.class)
                .hasMessage("Perfil de estudiante con ID 99 no encontrado");
        verify(studentProfilePersistencePort, times(1)).findById(99L);
    }

    @Test
    void save_ValidStudentProfile_SavesSuccessfully() {
        // Arrange
        when(academicPeriodServicePort.findById(1L)).thenReturn(academicPeriod);
        when(roleServicePort.findByName(SeedbedRole.DIRI)).thenReturn(diriRole);
        when(studentProfilePersistencePort.existsByUserIdAndAcademicPeriodId(1L, 1L))
                .thenReturn(false);
        when(studentProfilePersistencePort.save(studentProfile)).thenReturn(studentProfile);

        // Act
        StudentProfile result = studentProfileUseCase.save(studentProfile);

        // Assert
        assertThat(result).isNotNull();
        verify(academicPeriodServicePort, times(1)).findById(1L);
        verify(studentProfilePersistencePort, times(1)).save(studentProfile);
    }

    @Test
    void save_AcademicPeriodNotVisible_ThrowsAcademicPeriodNotVisibleException() {
        // Arrange
        academicPeriod.setVisible(false);
        when(academicPeriodServicePort.findById(1L)).thenReturn(academicPeriod);

        // Act & Assert
        assertThatThrownBy(() -> studentProfileUseCase.save(studentProfile))
                .isInstanceOf(AcademicPeriodNotVisibleException.class)
                .hasMessage("No se permite crear perfiles de estudiante en períodos académicos que no son visibles");
        verify(academicPeriodServicePort, times(1)).findById(1L);
        verify(studentProfilePersistencePort, never()).save(any());
    }

    @Test
    void save_RoleIsDiri_ThrowsDiriRoleNotAllowedException() {
        // Arrange
        studentProfile.setRoleId(2L);
        when(academicPeriodServicePort.findById(1L)).thenReturn(academicPeriod);
        when(roleServicePort.findByName(SeedbedRole.DIRI)).thenReturn(diriRole);

        // Act & Assert
        assertThatThrownBy(() -> studentProfileUseCase.save(studentProfile))
                .isInstanceOf(DiriRoleNotAllowedException.class)
                .hasMessage("No se permite crear perfiles de estudiante con rol DIRI a través de este método");
        verify(studentProfilePersistencePort, never()).save(any());
    }

    @Test
    void save_StudentProfileAlreadyExists_ThrowsStudentProfileAlreadyExistsException() {
        // Arrange
        when(academicPeriodServicePort.findById(1L)).thenReturn(academicPeriod);
        when(roleServicePort.findByName(SeedbedRole.DIRI)).thenReturn(diriRole);
        when(studentProfilePersistencePort.existsByUserIdAndAcademicPeriodId(1L, 1L))
                .thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> studentProfileUseCase.save(studentProfile))
                .isInstanceOf(StudentProfileAlreadyExistsException.class)
                .hasMessage("No se pudo guardar el perfil de estudiante con ID de usuario 1 porque ya existe en el período académico con ID 1");
        verify(studentProfilePersistencePort, never()).save(any());
    }

    @Test
    void saveIgnoringPeriodVisibility_ValidStudentProfile_SavesSuccessfully() {
        // Arrange
        when(studentProfilePersistencePort.existsByUserIdAndAcademicPeriodId(1L, 1L))
                .thenReturn(false);
        when(studentProfilePersistencePort.save(studentProfile)).thenReturn(studentProfile);

        // Act
        StudentProfile result = studentProfileUseCase.saveIgnoringPeriodVisibility(studentProfile);

        // Assert
        assertThat(result).isNotNull();
        verify(studentProfilePersistencePort, times(1)).save(studentProfile);
        verify(academicPeriodServicePort, never()).findById(anyLong());
    }

    @Test
    void update_StudentProfileExists_UpdatesSuccessfully() {
        // Arrange
        when(studentProfilePersistencePort.findById(1L)).thenReturn(Optional.of(studentProfile));
        when(studentProfilePersistencePort.update(1L, studentProfile)).thenReturn(studentProfile);

        // Act
        StudentProfile result = studentProfileUseCase.update(1L, studentProfile);

        // Assert
        assertThat(result).isNotNull();
        verify(studentProfilePersistencePort, times(1)).findById(1L);
        verify(studentProfilePersistencePort, times(1)).update(1L, studentProfile);
    }

    @Test
    void update_StudentProfileDoesNotExist_ThrowsStudentProfileNotFoundException() {
        // Arrange
        when(studentProfilePersistencePort.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> studentProfileUseCase.update(99L, studentProfile))
                .isInstanceOf(StudentProfileNotFoundException.class)
                .hasMessage("No se pudo actualizar el perfil de estudiante con ID 99 porque no existe");
        verify(studentProfilePersistencePort, never()).update(anyLong(), any());
    }

    @Test
    void deleteById_StudentProfileExists_DeletesSuccessfully() {
        // Arrange
        when(studentProfilePersistencePort.findById(1L)).thenReturn(Optional.of(studentProfile));

        // Act
        studentProfileUseCase.deleteById(1L);

        // Assert
        verify(studentProfilePersistencePort, times(1)).findById(1L);
        verify(studentProfilePersistencePort, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_StudentProfileDoesNotExist_ThrowsStudentProfileNotFoundException() {
        // Arrange
        when(studentProfilePersistencePort.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> studentProfileUseCase.deleteById(99L))
                .isInstanceOf(StudentProfileNotFoundException.class)
                .hasMessage("No se pudo eliminar el perfil de estudiante con ID 99 porque no existe");
        verify(studentProfilePersistencePort, never()).deleteById(anyLong());
    }

    @Test
    void findAll_ReturnsAllStudentProfiles() {
        // Arrange
        List<StudentProfile> profiles = Arrays.asList(studentProfile);
        when(studentProfilePersistencePort.findAll()).thenReturn(profiles);

        // Act
        List<StudentProfile> result = studentProfileUseCase.findAll();

        // Assert
        assertThat(result).hasSize(1);
        verify(studentProfilePersistencePort, times(1)).findAll();
    }

    @Test
    void findByUserIdAndAcademicPeriodId_ProfileExists_ReturnsOptionalWithProfile() {
        // Arrange
        when(studentProfilePersistencePort.findByUserIdAndAcademicPeriodId(1L, 1L))
                .thenReturn(Optional.of(studentProfile));

        // Act
        Optional<StudentProfile> result = studentProfileUseCase.findByUserIdAndAcademicPeriodId(1L, 1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        verify(studentProfilePersistencePort, times(1)).findByUserIdAndAcademicPeriodId(1L, 1L);
    }

    @Test
    void existsByUserIdAndAcademicPeriodId_ProfileExists_ReturnsTrue() {
        // Arrange
        when(studentProfilePersistencePort.existsByUserIdAndAcademicPeriodId(1L, 1L))
                .thenReturn(true);

        // Act
        boolean result = studentProfileUseCase.existsByUserIdAndAcademicPeriodId(1L, 1L);

        // Assert
        assertThat(result).isTrue();
        verify(studentProfilePersistencePort, times(1)).existsByUserIdAndAcademicPeriodId(1L, 1L);
    }

    @Test
    void createStudentProfileFromIntegraData_CreatesProfileSuccessfully() {
        // Arrange
        List<IntegraStudent> integraStudents = Arrays.asList(integraStudent);
        when(integraServicePort.getIntegraStudentRecordsByIdentification("123456789"))
                .thenReturn(integraStudents);
        when(integraServicePort.getMaxSemester(integraStudents)).thenReturn((byte) 5);
        when(academicProgramServicePort.findAcademicProgramsByAcademicProgramCodes(any()))
                .thenReturn(Set.of(academicProgram));
        when(roleServicePort.findByName(SeedbedRole.ESTUDIANTE)).thenReturn(estudianteRole);
        when(academicPeriodServicePort.findById(1L)).thenReturn(academicPeriod);
        when(roleServicePort.findByName(SeedbedRole.DIRI)).thenReturn(diriRole);
        when(studentProfilePersistencePort.existsByUserIdAndAcademicPeriodId(1L, 1L))
                .thenReturn(false);
        when(studentProfilePersistencePort.save(any(StudentProfile.class))).thenReturn(studentProfile);

        // Act
        StudentProfile result = studentProfileUseCase.createStudentProfileFromIntegraData(
                "123456789", 1L, user);

        // Assert
        assertThat(result).isNotNull();
        verify(integraServicePort, times(1)).getIntegraStudentRecordsByIdentification("123456789");
        verify(studentProfilePersistencePort, times(1)).save(any(StudentProfile.class));
    }

    @Test
    void updateRoleId_UpdatesRoleSuccessfully() {
        // Arrange
        doNothing().when(studentProfilePersistencePort).updateRoleId(1L, 2L);

        // Act
        studentProfileUseCase.updateRoleId(1L, 2L);

        // Assert
        verify(studentProfilePersistencePort, times(1)).updateRoleId(1L, 2L);
    }

    @Test
    void findAllByAcademicPeriodId_ReturnsProfilesForPeriod() {
        // Arrange
        List<StudentProfile> profiles = Arrays.asList(studentProfile);
        when(studentProfilePersistencePort.findAllByAcademicPeriodId(1L)).thenReturn(profiles);

        // Act
        List<StudentProfile> result = studentProfileUseCase.findAllByAcademicPeriodId(1L);

        // Assert
        assertThat(result).hasSize(1);
        verify(studentProfilePersistencePort, times(1)).findAllByAcademicPeriodId(1L);
    }

    @Test
    void findAllProfilesByUserId_ReturnsAllUserProfiles() {
        // Arrange
        List<StudentProfile> profiles = Arrays.asList(studentProfile);
        when(studentProfilePersistencePort.findAllProfilesByUserId(1L)).thenReturn(profiles);

        // Act
        List<StudentProfile> result = studentProfileUseCase.findAllProfilesByUserId(1L);

        // Assert
        assertThat(result).hasSize(1);
        verify(studentProfilePersistencePort, times(1)).findAllProfilesByUserId(1L);
    }
}