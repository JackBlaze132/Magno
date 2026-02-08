package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.*;
import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.exception.academicperiod.AcademicPeriodNotCurrentException;
import com.unibague.magno.domain.exception.researchseedbedstudentprofile.ALeaderAlreadyExistsInSeedbedException;
import com.unibague.magno.domain.exception.researchseedbedstudentprofile.ResearchSeedbedStudentProfileNotFoundException;
import com.unibague.magno.domain.exception.researchseedbedstudentprofile.StudentProfileAlreadyExistsInSeedbedException;
import com.unibague.magno.domain.model.*;
import com.unibague.magno.domain.model.enums.SeedbedRole;
import com.unibague.magno.domain.spi.IResearchSeedbedStudentProfilePersistencePort;
import com.unibague.magno.domain.usecase.helper.IResearchSeedbedStudentProfileHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResearchSeedbedStudentProfileUseCaseTest {

    @Mock
    private IResearchSeedbedStudentProfilePersistencePort researchSeedbedStudentProfilePersistencePort;
    @Mock
    private IUserServicePort userServicePort;
    @Mock
    private IIntegraServicePort integraServicePort;
    @Mock
    private IStudentProfileServicePort studentProfileServicePort;
    @Mock
    private IResearchSeedbedProfileServicePort researchSeedbedProfileServicePort;
    @Mock
    private IResearchSeedbedStudentProfileHelper researchSeedbedStudentProfileHelper;
    @Mock
    private IRoleServicePort roleServicePort;

    private ResearchSeedbedStudentProfileUseCase researchSeedbedStudentProfileUseCase;
    private ResearchSeedbedStudentProfile researchSeedbedStudentProfile;
    private ResearchSeedbedProfile researchSeedbedProfile;
    private StudentProfile studentProfile;
    private Role estudianteRole;
    private Role estudianteLiderRole;
    private User user;

    @BeforeEach
    void setUp() {
        researchSeedbedStudentProfileUseCase = new ResearchSeedbedStudentProfileUseCase(
                researchSeedbedStudentProfilePersistencePort,
                userServicePort,
                integraServicePort,
                studentProfileServicePort,
                researchSeedbedProfileServicePort,
                researchSeedbedStudentProfileHelper,
                roleServicePort
        );

        user = new User();
        user.setId(1L);
        user.setFullName("Juan Perez");
        user.setIdentificationNumber("123456789");

        estudianteRole = new Role();
        estudianteRole.setId(1L);
        estudianteRole.setName(SeedbedRole.ESTUDIANTE);

        estudianteLiderRole = new Role();
        estudianteLiderRole.setId(2L);
        estudianteLiderRole.setName(SeedbedRole.ESTUDIANTE_LIDER);

        studentProfile = new StudentProfile();
        studentProfile.setId(1L);
        studentProfile.setUserId(1L);
        studentProfile.setAcademicPeriodId(1L);
        studentProfile.setRoleId(1L);

        researchSeedbedProfile = new ResearchSeedbedProfile();
        researchSeedbedProfile.setId(1L);
        researchSeedbedProfile.setAcademicPeriodId(1L);
        researchSeedbedProfile.setResearchSeedbedId(1L);

        researchSeedbedStudentProfile = new ResearchSeedbedStudentProfile();
        researchSeedbedStudentProfile.setId(1L);
        researchSeedbedStudentProfile.setStudentProfileId(1L);
        researchSeedbedStudentProfile.setResearchSeedbedProfileId(1L);
        researchSeedbedStudentProfile.setIsLeader(false);
        researchSeedbedStudentProfile.setWasActive(false);
    }

    @Test
    void findById_ResearchSeedbedStudentProfileExists_ReturnsProfile() {
        // Arrange
        when(researchSeedbedStudentProfilePersistencePort.findById(1L))
                .thenReturn(Optional.of(researchSeedbedStudentProfile));

        // Act
        ResearchSeedbedStudentProfile result = researchSeedbedStudentProfileUseCase.findById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getStudentProfileId()).isEqualTo(1L);
        assertThat(result.getResearchSeedbedProfileId()).isEqualTo(1L);
        verify(researchSeedbedStudentProfilePersistencePort, times(1)).findById(1L);
    }

    @Test
    void findById_ResearchSeedbedStudentProfileDoesNotExist_ThrowsResearchSeedbedStudentProfileNotFoundException() {
        // Arrange
        when(researchSeedbedStudentProfilePersistencePort.findById(99L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> researchSeedbedStudentProfileUseCase.findById(99L))
                .isInstanceOf(ResearchSeedbedStudentProfileNotFoundException.class)
                .hasMessage("Perfil de estudiante de semillero de investigación con ID 99 no encontrado");
        verify(researchSeedbedStudentProfilePersistencePort, times(1)).findById(99L);
    }

    @Test
    void save_ValidResearchSeedbedStudentProfile_SavesSuccessfully() {
        // Arrange
        when(researchSeedbedProfileServicePort.findById(1L)).thenReturn(researchSeedbedProfile);
        when(researchSeedbedStudentProfileHelper.verifyAcademicPeriodIsCurrentStatus(1L)).thenReturn(false);
        when(researchSeedbedStudentProfileHelper.verifyStudentHasAProfile(researchSeedbedStudentProfile))
                .thenReturn(researchSeedbedStudentProfile);
        when(researchSeedbedStudentProfilePersistencePort.existsByStudentProfileIdAndResearchSeedbedProfileId(1L, 1L))
                .thenReturn(false);
        when(researchSeedbedStudentProfilePersistencePort.save(researchSeedbedStudentProfile))
                .thenReturn(researchSeedbedStudentProfile);
        when(researchSeedbedStudentProfilePersistencePort.findAllByStudentProfileIdAndAcademicPeriodId(1L, 1L))
                .thenReturn(Arrays.asList(researchSeedbedStudentProfile));
        when(roleServicePort.findByName(SeedbedRole.ESTUDIANTE)).thenReturn(estudianteRole);

        // Act
        ResearchSeedbedStudentProfile result = researchSeedbedStudentProfileUseCase.save(researchSeedbedStudentProfile);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(researchSeedbedStudentProfilePersistencePort, times(1)).save(researchSeedbedStudentProfile);
        verify(studentProfileServicePort, times(1)).updateRoleId(1L, 1L);
    }

    @Test
    void save_AcademicPeriodNotCurrent_ThrowsAcademicPeriodNotCurrentException() {
        // Arrange
        when(researchSeedbedProfileServicePort.findById(1L)).thenReturn(researchSeedbedProfile);
        when(researchSeedbedStudentProfileHelper.verifyAcademicPeriodIsCurrentStatus(1L)).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> researchSeedbedStudentProfileUseCase.save(researchSeedbedStudentProfile))
                .isInstanceOf(AcademicPeriodNotCurrentException.class)
                .hasMessage("No se puede agregar un estudiante a un perfil de semillero de investigación asociado a un período académico inactivo.");
        verify(researchSeedbedStudentProfilePersistencePort, never()).save(any());
    }

    @Test
    void save_StudentProfileAlreadyExistsInSeedbed_ThrowsStudentProfileAlreadyExistsInSeedbedException() {
        // Arrange
        when(researchSeedbedProfileServicePort.findById(1L)).thenReturn(researchSeedbedProfile);
        when(researchSeedbedStudentProfileHelper.verifyAcademicPeriodIsCurrentStatus(1L)).thenReturn(false);
        when(researchSeedbedStudentProfileHelper.verifyStudentHasAProfile(researchSeedbedStudentProfile))
                .thenReturn(researchSeedbedStudentProfile);
        when(researchSeedbedStudentProfilePersistencePort.existsByStudentProfileIdAndResearchSeedbedProfileId(1L, 1L))
                .thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> researchSeedbedStudentProfileUseCase.save(researchSeedbedStudentProfile))
                .isInstanceOf(StudentProfileAlreadyExistsInSeedbedException.class)
                .hasMessage("El perfil de estudiante con ID 1 ya está asociado al perfil de semillero de investigación con ID 1");
        verify(researchSeedbedStudentProfilePersistencePort, never()).save(any());
    }

    @Test
    void save_IsLeaderButLeaderAlreadyExists_ThrowsALeaderAlreadyExistsInSeedbedException() {
        // Arrange
        researchSeedbedStudentProfile.setIsLeader(true);
        ResearchSeedbedStudentProfile existingLeader = new ResearchSeedbedStudentProfile();
        existingLeader.setId(2L);
        existingLeader.setIsLeader(true);

        when(researchSeedbedProfileServicePort.findById(1L)).thenReturn(researchSeedbedProfile);
        when(researchSeedbedStudentProfileHelper.verifyAcademicPeriodIsCurrentStatus(1L)).thenReturn(false);
        when(researchSeedbedStudentProfileHelper.verifyStudentHasAProfile(researchSeedbedStudentProfile))
                .thenReturn(researchSeedbedStudentProfile);
        when(researchSeedbedStudentProfilePersistencePort.existsByStudentProfileIdAndResearchSeedbedProfileId(1L, 1L))
                .thenReturn(false);
        when(researchSeedbedStudentProfilePersistencePort.findAllByResearchSeedbedProfileId(1L))
                .thenReturn(Arrays.asList(existingLeader));

        // Act & Assert
        assertThatThrownBy(() -> researchSeedbedStudentProfileUseCase.save(researchSeedbedStudentProfile))
                .isInstanceOf(ALeaderAlreadyExistsInSeedbedException.class)
                .hasMessage("El perfil de semillero de investigación con ID 1 ya tiene un líder asignado");
        verify(researchSeedbedStudentProfilePersistencePort, never()).save(any());
    }

    @Test
    void save_IsLeaderAndNoLeaderExists_SavesAndUpdatesRoleToEstudianteLider() {
        // Arrange
        researchSeedbedStudentProfile.setIsLeader(true);
        when(researchSeedbedProfileServicePort.findById(1L)).thenReturn(researchSeedbedProfile);
        when(researchSeedbedStudentProfileHelper.verifyAcademicPeriodIsCurrentStatus(1L)).thenReturn(false);
        when(researchSeedbedStudentProfileHelper.verifyStudentHasAProfile(researchSeedbedStudentProfile))
                .thenReturn(researchSeedbedStudentProfile);
        when(researchSeedbedStudentProfilePersistencePort.existsByStudentProfileIdAndResearchSeedbedProfileId(1L, 1L))
                .thenReturn(false);
        when(researchSeedbedStudentProfilePersistencePort.findAllByResearchSeedbedProfileId(1L))
                .thenReturn(Collections.emptyList());
        when(researchSeedbedStudentProfilePersistencePort.save(researchSeedbedStudentProfile))
                .thenReturn(researchSeedbedStudentProfile);
        when(researchSeedbedStudentProfilePersistencePort.findAllByStudentProfileIdAndAcademicPeriodId(1L, 1L))
                .thenReturn(Arrays.asList(researchSeedbedStudentProfile));
        when(roleServicePort.findByName(SeedbedRole.ESTUDIANTE_LIDER)).thenReturn(estudianteLiderRole);

        // Act
        ResearchSeedbedStudentProfile result = researchSeedbedStudentProfileUseCase.save(researchSeedbedStudentProfile);

        // Assert
        assertThat(result).isNotNull();
        verify(researchSeedbedStudentProfilePersistencePort, times(1)).save(researchSeedbedStudentProfile);
        verify(studentProfileServicePort, times(1)).updateRoleId(1L, 2L);
    }

    @Test
    void update_ValidUpdate_UpdatesSuccessfully() {
        // Arrange
        when(researchSeedbedStudentProfilePersistencePort.findById(1L))
                .thenReturn(Optional.of(researchSeedbedStudentProfile));
        when(researchSeedbedProfileServicePort.findById(1L)).thenReturn(researchSeedbedProfile);
        when(researchSeedbedStudentProfileHelper.verifyAcademicPeriodIsCurrentStatus(1L)).thenReturn(false);
        when(researchSeedbedStudentProfilePersistencePort.update(1L, researchSeedbedStudentProfile))
                .thenReturn(researchSeedbedStudentProfile);

        // Act
        ResearchSeedbedStudentProfile result = researchSeedbedStudentProfileUseCase.update(1L, researchSeedbedStudentProfile);

        // Assert
        assertThat(result).isNotNull();
        verify(researchSeedbedStudentProfilePersistencePort, times(1)).findById(1L);
        verify(researchSeedbedStudentProfilePersistencePort, times(1)).update(1L, researchSeedbedStudentProfile);
    }

    @Test
    void update_ResearchSeedbedStudentProfileDoesNotExist_ThrowsResearchSeedbedStudentProfileNotFoundException() {
        // Arrange
        when(researchSeedbedStudentProfilePersistencePort.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> researchSeedbedStudentProfileUseCase.update(99L, researchSeedbedStudentProfile))
                .isInstanceOf(ResearchSeedbedStudentProfileNotFoundException.class)
                .hasMessage("No se pudo actualizar el perfil de estudiante de semillero de investigación con ID 99 porque no fue encontrado");
        verify(researchSeedbedStudentProfilePersistencePort, never()).update(anyLong(), any());
    }

    @Test
    void update_AcademicPeriodNotCurrent_ThrowsAcademicPeriodNotCurrentException() {
        // Arrange
        when(researchSeedbedStudentProfilePersistencePort.findById(1L))
                .thenReturn(Optional.of(researchSeedbedStudentProfile));
        when(researchSeedbedProfileServicePort.findById(1L)).thenReturn(researchSeedbedProfile);
        when(researchSeedbedStudentProfileHelper.verifyAcademicPeriodIsCurrentStatus(1L)).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> researchSeedbedStudentProfileUseCase.update(1L, researchSeedbedStudentProfile))
                .isInstanceOf(AcademicPeriodNotCurrentException.class)
                .hasMessage("No se puede actualizar un estudiante en un perfil de semillero de investigación asociado a un período académico inactivo.");
        verify(researchSeedbedStudentProfilePersistencePort, never()).update(anyLong(), any());
    }

    @Test
    void update_ChangingToLeaderWhenLeaderExists_ThrowsALeaderAlreadyExistsInSeedbedException() {
        // Arrange
        ResearchSeedbedStudentProfile existingProfile = new ResearchSeedbedStudentProfile();
        existingProfile.setId(1L);
        existingProfile.setIsLeader(false);

        ResearchSeedbedStudentProfile existingLeader = new ResearchSeedbedStudentProfile();
        existingLeader.setId(2L);
        existingLeader.setIsLeader(true);

        researchSeedbedStudentProfile.setIsLeader(true);

        when(researchSeedbedStudentProfilePersistencePort.findById(1L))
                .thenReturn(Optional.of(existingProfile));
        when(researchSeedbedProfileServicePort.findById(1L)).thenReturn(researchSeedbedProfile);
        when(researchSeedbedStudentProfileHelper.verifyAcademicPeriodIsCurrentStatus(1L)).thenReturn(false);
        when(researchSeedbedStudentProfilePersistencePort.findAllByResearchSeedbedProfileId(1L))
                .thenReturn(Arrays.asList(existingProfile, existingLeader));

        // Act & Assert
        assertThatThrownBy(() -> researchSeedbedStudentProfileUseCase.update(1L, researchSeedbedStudentProfile))
                .isInstanceOf(ALeaderAlreadyExistsInSeedbedException.class)
                .hasMessage("El perfil de semillero de investigación con ID 1 ya tiene un líder asignado");
        verify(researchSeedbedStudentProfilePersistencePort, never()).update(anyLong(), any());
    }

    @Test
    void update_IsLeaderStatusChanged_UpdatesRole() {
        // Arrange
        ResearchSeedbedStudentProfile existingProfile = new ResearchSeedbedStudentProfile();
        existingProfile.setId(1L);
        existingProfile.setStudentProfileId(1L);
        existingProfile.setResearchSeedbedProfileId(1L);
        existingProfile.setIsLeader(false);

        researchSeedbedStudentProfile.setIsLeader(true);

        when(researchSeedbedStudentProfilePersistencePort.findById(1L))
                .thenReturn(Optional.of(existingProfile));
        when(researchSeedbedProfileServicePort.findById(1L)).thenReturn(researchSeedbedProfile);
        when(researchSeedbedStudentProfileHelper.verifyAcademicPeriodIsCurrentStatus(1L)).thenReturn(false);
        when(researchSeedbedStudentProfilePersistencePort.findAllByResearchSeedbedProfileId(1L))
                .thenReturn(Arrays.asList(existingProfile));
        when(researchSeedbedStudentProfilePersistencePort.update(1L, researchSeedbedStudentProfile))
                .thenReturn(researchSeedbedStudentProfile);
        when(researchSeedbedStudentProfilePersistencePort.findAllByStudentProfileIdAndAcademicPeriodId(1L, 1L))
                .thenReturn(Arrays.asList(researchSeedbedStudentProfile));
        when(roleServicePort.findByName(SeedbedRole.ESTUDIANTE_LIDER)).thenReturn(estudianteLiderRole);

        // Act
        ResearchSeedbedStudentProfile result = researchSeedbedStudentProfileUseCase.update(1L, researchSeedbedStudentProfile);

        // Assert
        assertThat(result).isNotNull();
        verify(researchSeedbedStudentProfilePersistencePort, times(1)).update(1L, researchSeedbedStudentProfile);
        verify(studentProfileServicePort, times(1)).updateRoleId(1L, 2L);
    }

    @Test
    void deleteById_ValidDelete_DeletesSuccessfullyAndUpdatesRole() {
        // Arrange
        when(researchSeedbedStudentProfilePersistencePort.findById(1L))
                .thenReturn(Optional.of(researchSeedbedStudentProfile));
        when(researchSeedbedProfileServicePort.findById(1L)).thenReturn(researchSeedbedProfile);
        when(researchSeedbedStudentProfileHelper.verifyAcademicPeriodIsCurrentStatus(1L)).thenReturn(false);
        when(researchSeedbedStudentProfilePersistencePort.findAllByStudentProfileIdAndAcademicPeriodId(1L, 1L))
                .thenReturn(Collections.emptyList());

        // Act
        researchSeedbedStudentProfileUseCase.deleteById(1L);

        // Assert
        verify(researchSeedbedStudentProfilePersistencePort, times(2)).findById(1L);
        verify(researchSeedbedStudentProfilePersistencePort, times(1)).deleteById(1L);
        verify(studentProfileServicePort, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_ResearchSeedbedStudentProfileDoesNotExist_ThrowsResearchSeedbedStudentProfileNotFoundException() {
        // Arrange
        when(researchSeedbedStudentProfilePersistencePort.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> researchSeedbedStudentProfileUseCase.deleteById(99L))
                .isInstanceOf(ResearchSeedbedStudentProfileNotFoundException.class)
                .hasMessage("No se pudo eliminar el perfil de estudiante de semillero de investigación con ID 99 porque no fue encontrado");
        verify(researchSeedbedStudentProfilePersistencePort, never()).deleteById(anyLong());
    }

    @Test
    void deleteById_AcademicPeriodNotCurrent_ThrowsAcademicPeriodNotCurrentException() {
        // Arrange
        when(researchSeedbedStudentProfilePersistencePort.findById(1L))
                .thenReturn(Optional.of(researchSeedbedStudentProfile));
        when(researchSeedbedProfileServicePort.findById(1L)).thenReturn(researchSeedbedProfile);
        when(researchSeedbedStudentProfileHelper.verifyAcademicPeriodIsCurrentStatus(1L)).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> researchSeedbedStudentProfileUseCase.deleteById(1L))
                .isInstanceOf(AcademicPeriodNotCurrentException.class)
                .hasMessage("No se puede eliminar un estudiante de un perfil de semillero de investigación asociado a un período académico inactivo.");
        verify(researchSeedbedStudentProfilePersistencePort, never()).deleteById(anyLong());
    }

    @Test
    void deleteById_StudentHasRemainingProfiles_UpdatesRoleInsteadOfDeletingStudentProfile() {
        // Arrange
        ResearchSeedbedStudentProfile remainingProfile = new ResearchSeedbedStudentProfile();
        remainingProfile.setId(2L);
        remainingProfile.setStudentProfileId(1L);
        remainingProfile.setIsLeader(false);

        when(researchSeedbedStudentProfilePersistencePort.findById(1L))
                .thenReturn(Optional.of(researchSeedbedStudentProfile));
        when(researchSeedbedProfileServicePort.findById(1L)).thenReturn(researchSeedbedProfile);
        when(researchSeedbedStudentProfileHelper.verifyAcademicPeriodIsCurrentStatus(1L)).thenReturn(false);
        when(researchSeedbedStudentProfilePersistencePort.findAllByStudentProfileIdAndAcademicPeriodId(1L, 1L))
                .thenReturn(Arrays.asList(remainingProfile));
        when(roleServicePort.findByName(SeedbedRole.ESTUDIANTE)).thenReturn(estudianteRole);

        // Act
        researchSeedbedStudentProfileUseCase.deleteById(1L);

        // Assert
        verify(researchSeedbedStudentProfilePersistencePort, times(1)).deleteById(1L);
        verify(studentProfileServicePort, times(1)).updateRoleId(1L, 1L);
        verify(studentProfileServicePort, never()).deleteById(anyLong());
    }

    @Test
    void findAll_ReturnsAllProfiles() {
        // Arrange
        ResearchSeedbedStudentProfile profile2 = new ResearchSeedbedStudentProfile();
        profile2.setId(2L);

        List<ResearchSeedbedStudentProfile> profiles = Arrays.asList(researchSeedbedStudentProfile, profile2);
        when(researchSeedbedStudentProfilePersistencePort.findAll()).thenReturn(profiles);

        // Act
        List<ResearchSeedbedStudentProfile> result = researchSeedbedStudentProfileUseCase.findAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        verify(researchSeedbedStudentProfilePersistencePort, times(1)).findAll();
    }

    @Test
    void existsByStudentProfileIdAndResearchSeedbedProfileId_ProfileExists_ReturnsTrue() {
        // Arrange
        when(researchSeedbedStudentProfilePersistencePort
                .existsByStudentProfileIdAndResearchSeedbedProfileId(1L, 1L))
                .thenReturn(true);

        // Act
        boolean result = researchSeedbedStudentProfileUseCase
                .existsByStudentProfileIdAndResearchSeedbedProfileId(1L, 1L);

        // Assert
        assertThat(result).isTrue();
        verify(researchSeedbedStudentProfilePersistencePort, times(1))
                .existsByStudentProfileIdAndResearchSeedbedProfileId(1L, 1L);
    }

    @Test
    void findAllByResearchSeedbedProfileId_ReturnsProfilesForSeedbed() {
        // Arrange
        List<ResearchSeedbedStudentProfile> profiles = Arrays.asList(researchSeedbedStudentProfile);
        when(researchSeedbedStudentProfilePersistencePort.findAllByResearchSeedbedProfileId(1L))
                .thenReturn(profiles);

        // Act
        List<ResearchSeedbedStudentProfile> result = researchSeedbedStudentProfileUseCase
                .findAllByResearchSeedbedProfileId(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        verify(researchSeedbedStudentProfilePersistencePort, times(1))
                .findAllByResearchSeedbedProfileId(1L);
    }

    @Test
    void findAllByStudentProfileIdAndAcademicPeriodId_ReturnsProfilesForStudentInPeriod() {
        // Arrange
        List<ResearchSeedbedStudentProfile> profiles = Arrays.asList(researchSeedbedStudentProfile);
        when(researchSeedbedStudentProfilePersistencePort
                .findAllByStudentProfileIdAndAcademicPeriodId(1L, 1L))
                .thenReturn(profiles);

        // Act
        List<ResearchSeedbedStudentProfile> result = researchSeedbedStudentProfileUseCase
                .findAllByStudentProfileIdAndAcademicPeriodId(1L, 1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        verify(researchSeedbedStudentProfilePersistencePort, times(1))
                .findAllByStudentProfileIdAndAcademicPeriodId(1L, 1L);
    }

    @Test
    void saveAllByExcel_ValidData_SavesAllProfiles() {
        // Arrange
        Map<String, String> studentMap = new HashMap<>();
        studentMap.put("identification", "123456789");
        List<Map<String, String>> studentMaps = Arrays.asList(studentMap);

        when(researchSeedbedProfileServicePort.findById(1L)).thenReturn(researchSeedbedProfile);
        when(researchSeedbedStudentProfileHelper.verifyAcademicPeriodIsCurrentStatus(1L)).thenReturn(false);
        when(integraServicePort.getCleanedStudentListOfMaps(studentMaps)).thenReturn(studentMaps);
        when(userServicePort.getUserListByListOfStudentMaps(studentMaps)).thenReturn(Arrays.asList(user));
        when(studentProfileServicePort.getOrCreateStudentProfiles(studentMaps, Arrays.asList(user), 1L))
                .thenReturn(Arrays.asList(studentProfile));
        when(researchSeedbedStudentProfilePersistencePort
                .existsByStudentProfileIdAndResearchSeedbedProfileId(1L, 1L))
                .thenReturn(false);
        when(researchSeedbedStudentProfilePersistencePort.save(any(ResearchSeedbedStudentProfile.class)))
                .thenReturn(researchSeedbedStudentProfile);

        // Act
        List<ResearchSeedbedStudentProfile> result = researchSeedbedStudentProfileUseCase
                .saveAllByExcel(1L, studentMaps);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        verify(researchSeedbedStudentProfilePersistencePort, times(1)).save(any(ResearchSeedbedStudentProfile.class));
    }

    @Test
    void saveAllByExcel_AcademicPeriodNotCurrent_ThrowsAcademicPeriodNotCurrentException() {
        // Arrange
        Map<String, String> studentMap = new HashMap<>();
        studentMap.put("identification", "123456789");
        List<Map<String, String>> studentMaps = Arrays.asList(studentMap);

        when(researchSeedbedProfileServicePort.findById(1L)).thenReturn(researchSeedbedProfile);
        when(researchSeedbedStudentProfileHelper.verifyAcademicPeriodIsCurrentStatus(1L)).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> researchSeedbedStudentProfileUseCase.saveAllByExcel(1L, studentMaps))
                .isInstanceOf(AcademicPeriodNotCurrentException.class)
                .hasMessage("No se pueden agregar estudiantes a un perfil de semillero de investigación asociado a un período académico inactivo.");
        verify(researchSeedbedStudentProfilePersistencePort, never()).save(any());
    }
}