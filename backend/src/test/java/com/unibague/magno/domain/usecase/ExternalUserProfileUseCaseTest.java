package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IAcademicPeriodServicePort;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.exception.academicperiod.AcademicPeriodNotCurrentException;
import com.unibague.magno.domain.exception.externaluser.ExternalUserProfileNotFoundException;
import com.unibague.magno.domain.exception.externaluser.UserIsNotExternalException;
import com.unibague.magno.domain.model.AcademicPeriod;
import com.unibague.magno.domain.model.ExternalUserProfile;
import com.unibague.magno.domain.model.User;
import com.unibague.magno.domain.spi.IExternalUserProfilePersistencePort;
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
class ExternalUserProfileUseCaseTest {

    @Mock
    private IExternalUserProfilePersistencePort externalUserProfilePersistencePort;
    @Mock
    private IUserServicePort userServicePort;
    @Mock
    private IAcademicPeriodServicePort academicPeriodServicePort;

    private ExternalUserProfileUseCase externalUserProfileUseCase;
    private ExternalUserProfile externalUserProfile;
    private User externalUser;
    private User internalUser;
    private AcademicPeriod academicPeriod;

    @BeforeEach
    void setUp() {
        externalUserProfileUseCase = new ExternalUserProfileUseCase(
                externalUserProfilePersistencePort,
                userServicePort,
                academicPeriodServicePort
        );

        externalUser = new User();
        externalUser.setId(1L);
        externalUser.setFullName("Juan Perez");
        externalUser.setExternalUser(true);

        internalUser = new User();
        internalUser.setId(2L);
        internalUser.setFullName("Maria Garcia");
        internalUser.setExternalUser(false);

        academicPeriod = new AcademicPeriod(1L, "2024-1", LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 6, 30), true, true);

        externalUserProfile = new ExternalUserProfile();
        externalUserProfile.setId(1L);
        externalUserProfile.setUserId(1L);
        externalUserProfile.setAcademicPeriodId(1L);
        externalUserProfile.setResearchSeedbedProfileId(1L);
    }

    @Test
    void findById_ExternalUserProfileExists_ReturnsProfile() {
        // Arrange
        when(externalUserProfilePersistencePort.findById(1L))
                .thenReturn(Optional.of(externalUserProfile));

        // Act
        ExternalUserProfile result = externalUserProfileUseCase.findById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getUserId()).isEqualTo(1L);
        assertThat(result.getAcademicPeriodId()).isEqualTo(1L);
        assertThat(result.getResearchSeedbedProfileId()).isEqualTo(1L);
        verify(externalUserProfilePersistencePort, times(1)).findById(1L);
    }

    @Test
    void findById_ExternalUserProfileDoesNotExist_ThrowsExternalUserProfileNotFoundException() {
        // Arrange
        when(externalUserProfilePersistencePort.findById(99L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> externalUserProfileUseCase.findById(99L))
                .isInstanceOf(ExternalUserProfileNotFoundException.class)
                .hasMessage("Perfil de usuario externo con ID 99 no encontrado");
        verify(externalUserProfilePersistencePort, times(1)).findById(99L);
    }

    @Test
    void save_ValidExternalUserProfile_SavesSuccessfully() {
        // Arrange
        when(userServicePort.findById(1L)).thenReturn(externalUser);
        when(academicPeriodServicePort.findById(1L)).thenReturn(academicPeriod);
        when(externalUserProfilePersistencePort.save(externalUserProfile))
                .thenReturn(externalUserProfile);

        // Act
        ExternalUserProfile result = externalUserProfileUseCase.save(externalUserProfile);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(userServicePort, times(1)).findById(1L);
        verify(academicPeriodServicePort, times(1)).findById(1L);
        verify(externalUserProfilePersistencePort, times(1)).save(externalUserProfile);
    }

    @Test
    void save_UserIsNotExternal_ThrowsUserIsNotExternalException() {
        // Arrange
        externalUserProfile.setUserId(2L);
        when(userServicePort.findById(2L)).thenReturn(internalUser);

        // Act & Assert
        assertThatThrownBy(() -> externalUserProfileUseCase.save(externalUserProfile))
                .isInstanceOf(UserIsNotExternalException.class)
                .hasMessage("El usuario con ID 2 no es un usuario externo y no puede tener un perfil de usuario externo");
        verify(userServicePort, times(1)).findById(2L);
        verify(externalUserProfilePersistencePort, never()).save(any());
    }

    @Test
    void save_AcademicPeriodNotCurrent_ThrowsAcademicPeriodNotCurrentException() {
        // Arrange
        academicPeriod.setCurrent(false);
        when(userServicePort.findById(1L)).thenReturn(externalUser);
        when(academicPeriodServicePort.findById(1L)).thenReturn(academicPeriod);

        // Act & Assert
        assertThatThrownBy(() -> externalUserProfileUseCase.save(externalUserProfile))
                .isInstanceOf(AcademicPeriodNotCurrentException.class)
                .hasMessage("No se puede guardar el perfil de usuario externo porque el período académico no está activo");
        verify(userServicePort, times(1)).findById(1L);
        verify(academicPeriodServicePort, times(1)).findById(1L);
        verify(externalUserProfilePersistencePort, never()).save(any());
    }

    @Test
    void update_ValidUpdate_UpdatesSuccessfully() {
        // Arrange
        when(externalUserProfilePersistencePort.findById(1L))
                .thenReturn(Optional.of(externalUserProfile));
        when(academicPeriodServicePort.findById(1L)).thenReturn(academicPeriod);
        when(externalUserProfilePersistencePort.update(1L, externalUserProfile))
                .thenReturn(externalUserProfile);

        // Act
        ExternalUserProfile result = externalUserProfileUseCase.update(1L, externalUserProfile);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(externalUserProfilePersistencePort, times(1)).findById(1L);
        verify(academicPeriodServicePort, times(1)).findById(1L);
        verify(externalUserProfilePersistencePort, times(1)).update(1L, externalUserProfile);
    }

    @Test
    void update_ExternalUserProfileDoesNotExist_ThrowsExternalUserProfileNotFoundException() {
        // Arrange
        when(externalUserProfilePersistencePort.findById(99L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> externalUserProfileUseCase.update(99L, externalUserProfile))
                .isInstanceOf(ExternalUserProfileNotFoundException.class)
                .hasMessage("No se pudo actualizar el perfil de usuario externo con ID 99 porque no existe");
        verify(externalUserProfilePersistencePort, times(1)).findById(99L);
        verify(externalUserProfilePersistencePort, never()).update(anyLong(), any());
    }

    @Test
    void update_AcademicPeriodNotCurrent_ThrowsAcademicPeriodNotCurrentException() {
        // Arrange
        academicPeriod.setCurrent(false);
        when(externalUserProfilePersistencePort.findById(1L))
                .thenReturn(Optional.of(externalUserProfile));
        when(academicPeriodServicePort.findById(1L)).thenReturn(academicPeriod);

        // Act & Assert
        assertThatThrownBy(() -> externalUserProfileUseCase.update(1L, externalUserProfile))
                .isInstanceOf(AcademicPeriodNotCurrentException.class)
                .hasMessage("No se puede actualizar el perfil de usuario externo porque el período académico no está activo");
        verify(externalUserProfilePersistencePort, times(1)).findById(1L);
        verify(academicPeriodServicePort, times(1)).findById(1L);
        verify(externalUserProfilePersistencePort, never()).update(anyLong(), any());
    }

    @Test
    void deleteById_ValidDelete_DeletesSuccessfully() {
        // Arrange
        when(externalUserProfilePersistencePort.findById(1L))
                .thenReturn(Optional.of(externalUserProfile));
        when(academicPeriodServicePort.findById(1L)).thenReturn(academicPeriod);

        // Act
        externalUserProfileUseCase.deleteById(1L);

        // Assert
        verify(externalUserProfilePersistencePort, times(2)).findById(1L);
        verify(academicPeriodServicePort, times(1)).findById(1L);
        verify(externalUserProfilePersistencePort, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_ExternalUserProfileDoesNotExist_ThrowsExternalUserProfileNotFoundException() {
        // Arrange
        when(externalUserProfilePersistencePort.findById(99L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> externalUserProfileUseCase.deleteById(99L))
                .isInstanceOf(ExternalUserProfileNotFoundException.class)
                .hasMessage("No se pudo eliminar el perfil de usuario externo con ID 99 porque no existe");
        verify(externalUserProfilePersistencePort, times(1)).findById(99L);
        verify(externalUserProfilePersistencePort, never()).deleteById(anyLong());
    }

    @Test
    void deleteById_AcademicPeriodNotCurrent_ThrowsAcademicPeriodNotCurrentException() {
        // Arrange
        academicPeriod.setCurrent(false);
        when(externalUserProfilePersistencePort.findById(1L))
                .thenReturn(Optional.of(externalUserProfile));
        when(academicPeriodServicePort.findById(1L)).thenReturn(academicPeriod);

        // Act & Assert
        assertThatThrownBy(() -> externalUserProfileUseCase.deleteById(1L))
                .isInstanceOf(AcademicPeriodNotCurrentException.class)
                .hasMessage("No se puede eliminar el perfil de usuario externo porque el período académico no está activo");
        verify(externalUserProfilePersistencePort, times(2)).findById(1L);
        verify(academicPeriodServicePort, times(1)).findById(1L);
        verify(externalUserProfilePersistencePort, never()).deleteById(anyLong());
    }

    @Test
    void findAll_ReturnsAllProfiles() {
        // Arrange
        ExternalUserProfile profile2 = new ExternalUserProfile();
        profile2.setId(2L);

        List<ExternalUserProfile> profiles = Arrays.asList(externalUserProfile, profile2);
        when(externalUserProfilePersistencePort.findAll()).thenReturn(profiles);

        // Act
        List<ExternalUserProfile> result = externalUserProfileUseCase.findAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        verify(externalUserProfilePersistencePort, times(1)).findAll();
    }

    @Test
    void findAllProfilesByUserId_ReturnsUserProfiles() {
        // Arrange
        List<ExternalUserProfile> profiles = Collections.singletonList(externalUserProfile);
        when(externalUserProfilePersistencePort.findAllProfilesByUserId(1L))
                .thenReturn(profiles);

        // Act
        List<ExternalUserProfile> result = externalUserProfileUseCase.findAllProfilesByUserId(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getUserId()).isEqualTo(1L);
        verify(externalUserProfilePersistencePort, times(1)).findAllProfilesByUserId(1L);
    }

    @Test
    void findAllProfilesByUserId_UserHasNoProfiles_ReturnsEmptyList() {
        // Arrange
        when(externalUserProfilePersistencePort.findAllProfilesByUserId(99L))
                .thenReturn(Collections.emptyList());

        // Act
        List<ExternalUserProfile> result = externalUserProfileUseCase.findAllProfilesByUserId(99L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(externalUserProfilePersistencePort, times(1)).findAllProfilesByUserId(99L);
    }

    @Test
    void findAllByResearchSeedbedProfileId_ReturnsProfilesForSeedbed() {
        // Arrange
        List<ExternalUserProfile> profiles = Collections.singletonList(externalUserProfile);
        when(externalUserProfilePersistencePort.findAllByResearchSeedbedProfileId(1L))
                .thenReturn(profiles);

        // Act
        List<ExternalUserProfile> result = externalUserProfileUseCase.findAllByResearchSeedbedProfileId(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getResearchSeedbedProfileId()).isEqualTo(1L);
        verify(externalUserProfilePersistencePort, times(1)).findAllByResearchSeedbedProfileId(1L);
    }

    @Test
    void findAllByResearchSeedbedProfileId_NoProfilesForSeedbed_ReturnsEmptyList() {
        // Arrange
        when(externalUserProfilePersistencePort.findAllByResearchSeedbedProfileId(99L))
                .thenReturn(Collections.emptyList());

        // Act
        List<ExternalUserProfile> result = externalUserProfileUseCase.findAllByResearchSeedbedProfileId(99L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(externalUserProfilePersistencePort, times(1)).findAllByResearchSeedbedProfileId(99L);
    }
}