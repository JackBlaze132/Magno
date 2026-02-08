package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IAcademicPeriodServicePort;
import com.unibague.magno.domain.api.IRoleServicePort;
import com.unibague.magno.domain.exception.academicperiod.AcademicPeriodNotVisibleException;
import com.unibague.magno.domain.exception.functionaryprofile.FunctionaryProfileAlreadyExistsException;
import com.unibague.magno.domain.exception.functionaryprofile.FunctionaryProfileNotFoundException;
import com.unibague.magno.domain.exception.role.DiriRoleNotAllowedException;
import com.unibague.magno.domain.model.AcademicPeriod;
import com.unibague.magno.domain.model.FunctionaryProfile;
import com.unibague.magno.domain.model.Role;
import com.unibague.magno.domain.model.enums.SeedbedRole;
import com.unibague.magno.domain.spi.IFunctionaryProfilePersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FunctionaryProfileUseCaseTest {

    @Mock
    private IFunctionaryProfilePersistencePort functionaryProfilePersistencePort;
    @Mock
    private IAcademicPeriodServicePort academicPeriodServicePort;
    @Mock
    private IRoleServicePort roleServicePort;

    private FunctionaryProfileUseCase functionaryProfileUseCase;
    private FunctionaryProfile functionaryProfile;
    private AcademicPeriod academicPeriod;
    private Role coordinadorRole;
    private Role diriRole;

    @BeforeEach
    void setUp() {
        functionaryProfileUseCase = new FunctionaryProfileUseCase(
                functionaryProfilePersistencePort,
                academicPeriodServicePort,
                roleServicePort
        );

        academicPeriod = new AcademicPeriod(1L, "2024-1", LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 6, 30), true, true);

        coordinadorRole = new Role();
        coordinadorRole.setId(1L);
        coordinadorRole.setName(SeedbedRole.COORDINADOR_DE_SEMILLERO);

        diriRole = new Role();
        diriRole.setId(2L);
        diriRole.setName(SeedbedRole.DIRI);

        functionaryProfile = new FunctionaryProfile();
        functionaryProfile.setId(1L);
        functionaryProfile.setUserId(1L);
        functionaryProfile.setAcademicPeriodId(1L);
        functionaryProfile.setRoleId(1L);
    }

    @Test
    void findById_FunctionaryProfileExists_ReturnsProfile() {
        // Arrange
        when(functionaryProfilePersistencePort.findById(1L))
                .thenReturn(Optional.of(functionaryProfile));

        // Act
        FunctionaryProfile result = functionaryProfileUseCase.findById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getUserId()).isEqualTo(1L);
        assertThat(result.getAcademicPeriodId()).isEqualTo(1L);
        assertThat(result.getRoleId()).isEqualTo(1L);
        verify(functionaryProfilePersistencePort, times(1)).findById(1L);
    }

    @Test
    void findById_FunctionaryProfileDoesNotExist_ThrowsFunctionaryProfileNotFoundException() {
        // Arrange
        when(functionaryProfilePersistencePort.findById(99L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> functionaryProfileUseCase.findById(99L))
                .isInstanceOf(FunctionaryProfileNotFoundException.class)
                .hasMessage("FunctionaryProfile with ID 99 not found");
        verify(functionaryProfilePersistencePort, times(1)).findById(99L);
    }

    @Test
    void save_ValidFunctionaryProfile_SavesSuccessfully() {
        // Arrange
        when(academicPeriodServicePort.findById(1L)).thenReturn(academicPeriod);
        when(roleServicePort.findByName(SeedbedRole.DIRI)).thenReturn(diriRole);
        when(functionaryProfilePersistencePort.existsByUserIdAndAcademicPeriodId(1L, 1L))
                .thenReturn(false);
        when(functionaryProfilePersistencePort.save(functionaryProfile))
                .thenReturn(functionaryProfile);

        // Act
        FunctionaryProfile result = functionaryProfileUseCase.save(functionaryProfile);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(academicPeriodServicePort, times(1)).findById(1L);
        verify(roleServicePort, times(1)).findByName(SeedbedRole.DIRI);
        verify(functionaryProfilePersistencePort, times(1))
                .existsByUserIdAndAcademicPeriodId(1L, 1L);
        verify(functionaryProfilePersistencePort, times(1)).save(functionaryProfile);
    }

    @Test
    void save_AcademicPeriodNotVisible_ThrowsAcademicPeriodNotVisibleException() {
        // Arrange
        academicPeriod.setVisible(false);
        when(academicPeriodServicePort.findById(1L)).thenReturn(academicPeriod);

        // Act & Assert
        assertThatThrownBy(() -> functionaryProfileUseCase.save(functionaryProfile))
                .isInstanceOf(AcademicPeriodNotVisibleException.class)
                .hasMessage("No se permite crear perfiles de funcionario en períodos académicos que no son visibles");
        verify(academicPeriodServicePort, times(1)).findById(1L);
        verify(functionaryProfilePersistencePort, never()).save(any());
    }

    @Test
    void save_RoleIsDiri_ThrowsDiriRoleNotAllowedException() {
        // Arrange
        functionaryProfile.setRoleId(2L);
        when(academicPeriodServicePort.findById(1L)).thenReturn(academicPeriod);
        when(roleServicePort.findByName(SeedbedRole.DIRI)).thenReturn(diriRole);

        // Act & Assert
        assertThatThrownBy(() -> functionaryProfileUseCase.save(functionaryProfile))
                .isInstanceOf(DiriRoleNotAllowedException.class)
                .hasMessage("No se permite crear perfiles de funcionario con rol DIRI a través de este método");
        verify(academicPeriodServicePort, times(1)).findById(1L);
        verify(roleServicePort, times(1)).findByName(SeedbedRole.DIRI);
        verify(functionaryProfilePersistencePort, never()).save(any());
    }

    @Test
    void save_FunctionaryProfileAlreadyExists_ThrowsFunctionaryProfileAlreadyExistsException() {
        // Arrange
        when(academicPeriodServicePort.findById(1L)).thenReturn(academicPeriod);
        when(roleServicePort.findByName(SeedbedRole.DIRI)).thenReturn(diriRole);
        when(functionaryProfilePersistencePort.existsByUserIdAndAcademicPeriodId(1L, 1L))
                .thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> functionaryProfileUseCase.save(functionaryProfile))
                .isInstanceOf(FunctionaryProfileAlreadyExistsException.class)
                .hasMessage("FunctionaryProfile with user ID 1 and academic period ID 1 already exists");
        verify(functionaryProfilePersistencePort, times(1))
                .existsByUserIdAndAcademicPeriodId(1L, 1L);
        verify(functionaryProfilePersistencePort, never()).save(any());
    }

    @Test
    void saveIgnoringPeriodVisibility_ValidFunctionaryProfile_SavesSuccessfully() {
        // Arrange
        when(functionaryProfilePersistencePort.existsByUserIdAndAcademicPeriodId(1L, 1L))
                .thenReturn(false);
        when(functionaryProfilePersistencePort.save(functionaryProfile))
                .thenReturn(functionaryProfile);

        // Act
        FunctionaryProfile result = functionaryProfileUseCase.saveIgnoringPeriodVisibility(functionaryProfile);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(functionaryProfilePersistencePort, times(1))
                .existsByUserIdAndAcademicPeriodId(1L, 1L);
        verify(functionaryProfilePersistencePort, times(1)).save(functionaryProfile);
        verify(academicPeriodServicePort, never()).findById(anyLong());
        verify(roleServicePort, never()).findByName(any());
    }

    @Test
    void saveIgnoringPeriodVisibility_FunctionaryProfileAlreadyExists_ThrowsFunctionaryProfileAlreadyExistsException() {
        // Arrange
        when(functionaryProfilePersistencePort.existsByUserIdAndAcademicPeriodId(1L, 1L))
                .thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> functionaryProfileUseCase.saveIgnoringPeriodVisibility(functionaryProfile))
                .isInstanceOf(FunctionaryProfileAlreadyExistsException.class)
                .hasMessage("FunctionaryProfile with user ID 1 and academic period ID 1 already exists");
        verify(functionaryProfilePersistencePort, times(1))
                .existsByUserIdAndAcademicPeriodId(1L, 1L);
        verify(functionaryProfilePersistencePort, never()).save(any());
    }

    @Test
    void update_FunctionaryProfileExists_UpdatesSuccessfully() {
        // Arrange
        when(functionaryProfilePersistencePort.findById(1L))
                .thenReturn(Optional.of(functionaryProfile));
        when(functionaryProfilePersistencePort.update(1L, functionaryProfile))
                .thenReturn(functionaryProfile);

        // Act
        FunctionaryProfile result = functionaryProfileUseCase.update(1L, functionaryProfile);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(functionaryProfilePersistencePort, times(1)).findById(1L);
        verify(functionaryProfilePersistencePort, times(1)).update(1L, functionaryProfile);
    }

    @Test
    void update_FunctionaryProfileDoesNotExist_ThrowsFunctionaryProfileNotFoundException() {
        // Arrange
        when(functionaryProfilePersistencePort.findById(99L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> functionaryProfileUseCase.update(99L, functionaryProfile))
                .isInstanceOf(FunctionaryProfileNotFoundException.class)
                .hasMessage("FunctionaryProfile with ID 99 could not be updated because it does not exist");
        verify(functionaryProfilePersistencePort, times(1)).findById(99L);
        verify(functionaryProfilePersistencePort, never()).update(anyLong(), any());
    }

    @Test
    void deleteById_FunctionaryProfileExists_DeletesSuccessfully() {
        // Arrange
        when(functionaryProfilePersistencePort.findById(1L))
                .thenReturn(Optional.of(functionaryProfile));

        // Act
        functionaryProfileUseCase.deleteById(1L);

        // Assert
        verify(functionaryProfilePersistencePort, times(1)).findById(1L);
        verify(functionaryProfilePersistencePort, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_FunctionaryProfileDoesNotExist_ThrowsFunctionaryProfileNotFoundException() {
        // Arrange
        when(functionaryProfilePersistencePort.findById(99L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> functionaryProfileUseCase.deleteById(99L))
                .isInstanceOf(FunctionaryProfileNotFoundException.class)
                .hasMessage("FunctionaryProfile with ID 99 could not be deleted because it does not exist");
        verify(functionaryProfilePersistencePort, times(1)).findById(99L);
        verify(functionaryProfilePersistencePort, never()).deleteById(anyLong());
    }

    @Test
    void existsByUserIdAndAcademicPeriodId_ProfileExists_ReturnsTrue() {
        // Arrange
        when(functionaryProfilePersistencePort.existsByUserIdAndAcademicPeriodId(1L, 1L))
                .thenReturn(true);

        // Act
        boolean result = functionaryProfileUseCase.existsByUserIdAndAcademicPeriodId(1L, 1L);

        // Assert
        assertThat(result).isTrue();
        verify(functionaryProfilePersistencePort, times(1))
                .existsByUserIdAndAcademicPeriodId(1L, 1L);
    }

    @Test
    void existsByUserIdAndAcademicPeriodId_ProfileDoesNotExist_ReturnsFalse() {
        // Arrange
        when(functionaryProfilePersistencePort.existsByUserIdAndAcademicPeriodId(99L, 99L))
                .thenReturn(false);

        // Act
        boolean result = functionaryProfileUseCase.existsByUserIdAndAcademicPeriodId(99L, 99L);

        // Assert
        assertThat(result).isFalse();
        verify(functionaryProfilePersistencePort, times(1))
                .existsByUserIdAndAcademicPeriodId(99L, 99L);
    }

    @Test
    void findAll_ReturnsAllProfiles() {
        // Arrange
        FunctionaryProfile profile2 = new FunctionaryProfile();
        profile2.setId(2L);

        List<FunctionaryProfile> profiles = Arrays.asList(functionaryProfile, profile2);
        when(functionaryProfilePersistencePort.findAll()).thenReturn(profiles);

        // Act
        List<FunctionaryProfile> result = functionaryProfileUseCase.findAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        verify(functionaryProfilePersistencePort, times(1)).findAll();
    }

    @Test
    void findAllProfilesByUserId_ReturnsUserProfiles() {
        // Arrange
        List<FunctionaryProfile> profiles = Collections.singletonList(functionaryProfile);
        when(functionaryProfilePersistencePort.findAllProfilesByUserId(1L))
                .thenReturn(profiles);

        // Act
        List<FunctionaryProfile> result = functionaryProfileUseCase.findAllProfilesByUserId(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUserId()).isEqualTo(1L);
        verify(functionaryProfilePersistencePort, times(1)).findAllProfilesByUserId(1L);
    }

    @Test
    void findAllProfilesByUserId_UserHasNoProfiles_ReturnsEmptyList() {
        // Arrange
        when(functionaryProfilePersistencePort.findAllProfilesByUserId(99L))
                .thenReturn(Collections.emptyList());

        // Act
        List<FunctionaryProfile> result = functionaryProfileUseCase.findAllProfilesByUserId(99L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(functionaryProfilePersistencePort, times(1)).findAllProfilesByUserId(99L);
    }

    @Test
    void findAllProfilesByAcademicPeriodId_ReturnsProfilesForPeriod() {
        // Arrange
        List<FunctionaryProfile> profiles = Collections.singletonList(functionaryProfile);
        when(functionaryProfilePersistencePort.findAllProfilesByAcademicPeriodId(1L))
                .thenReturn(profiles);

        // Act
        List<FunctionaryProfile> result = functionaryProfileUseCase.findAllProfilesByAcademicPeriodId(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getAcademicPeriodId()).isEqualTo(1L);
        verify(functionaryProfilePersistencePort, times(1)).findAllProfilesByAcademicPeriodId(1L);
    }

    @Test
    void findAllProfilesByAcademicPeriodId_NoProfilesInPeriod_ReturnsEmptyList() {
        // Arrange
        when(functionaryProfilePersistencePort.findAllProfilesByAcademicPeriodId(99L))
                .thenReturn(Collections.emptyList());

        // Act
        List<FunctionaryProfile> result = functionaryProfileUseCase.findAllProfilesByAcademicPeriodId(99L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(functionaryProfilePersistencePort, times(1)).findAllProfilesByAcademicPeriodId(99L);
    }

    @Test
    void findAllProfilesByFunctionaryProfileIdAndAcademicPeriodId_ReturnsProfiles() {
        // Arrange
        List<FunctionaryProfile> profiles = Collections.singletonList(functionaryProfile);
        when(functionaryProfilePersistencePort
                .findAllProfilesByFunctionaryProfileIdAndAcademicPeriodId(1L, 1L))
                .thenReturn(profiles);

        // Act
        List<FunctionaryProfile> result = functionaryProfileUseCase
                .findAllProfilesByFunctionaryProfileIdAndAcademicPeriodId(1L, 1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        verify(functionaryProfilePersistencePort, times(1))
                .findAllProfilesByFunctionaryProfileIdAndAcademicPeriodId(1L, 1L);
    }

    @Test
    void findAllProfilesByFunctionaryProfileIdAndAcademicPeriodId_NoProfiles_ReturnsEmptyList() {
        // Arrange
        when(functionaryProfilePersistencePort
                .findAllProfilesByFunctionaryProfileIdAndAcademicPeriodId(99L, 99L))
                .thenReturn(Collections.emptyList());

        // Act
        List<FunctionaryProfile> result = functionaryProfileUseCase
                .findAllProfilesByFunctionaryProfileIdAndAcademicPeriodId(99L, 99L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(functionaryProfilePersistencePort, times(1))
                .findAllProfilesByFunctionaryProfileIdAndAcademicPeriodId(99L, 99L);
    }
}