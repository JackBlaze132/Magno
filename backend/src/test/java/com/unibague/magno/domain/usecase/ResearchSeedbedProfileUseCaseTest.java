package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.exception.researchseedbed.ResearchSeedbedNotFoundException;
import com.unibague.magno.domain.exception.researchseedbedprofile.ResearchSeedbedProfileAlreadyExistsInAcademicPeriod;
import com.unibague.magno.domain.exception.researchseedbedprofile.SameCoordinatorAndTutorException;
import com.unibague.magno.domain.model.ResearchSeedbedProfile;
import com.unibague.magno.domain.model.excel.ExcelReport;
import com.unibague.magno.domain.model.excel.metadata.SeedbedReportMetadata;
import com.unibague.magno.domain.spi.IResearchSeedbedProfilePersistencePort;
import com.unibague.magno.domain.usecase.helper.IResearchSeedbedProfileHelper;
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
class ResearchSeedbedProfileUseCaseTest {

    @Mock
    private IResearchSeedbedProfilePersistencePort researchSeedbedProfilePersistencePort;
    @Mock
    private IResearchSeedbedProfileHelper researchSeedbedProfileHelper;

    private ResearchSeedbedProfileUseCase researchSeedbedProfileUseCase;
    private ResearchSeedbedProfile researchSeedbedProfile;

    @BeforeEach
    void setUp() {
        researchSeedbedProfileUseCase = new ResearchSeedbedProfileUseCase(
                researchSeedbedProfilePersistencePort,
                researchSeedbedProfileHelper
        );

        researchSeedbedProfile = new ResearchSeedbedProfile();
        researchSeedbedProfile.setId(1L);
        researchSeedbedProfile.setResearchSeedbedId(1L);
        researchSeedbedProfile.setAcademicPeriodId(1L);
        researchSeedbedProfile.setInvestigationGroupProfileId(1L);
        researchSeedbedProfile.setCoordinatorId(1L);
        researchSeedbedProfile.setTutorId(2L);
    }

    @Test
    void findById_ResearchSeedbedProfileExists_ReturnsProfile() {
        // Arrange
        when(researchSeedbedProfilePersistencePort.findById(1L))
                .thenReturn(Optional.of(researchSeedbedProfile));

        // Act
        ResearchSeedbedProfile result = researchSeedbedProfileUseCase.findById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getResearchSeedbedId()).isEqualTo(1L);
        assertThat(result.getCoordinatorId()).isEqualTo(1L);
        assertThat(result.getTutorId()).isEqualTo(2L);
        verify(researchSeedbedProfilePersistencePort, times(1)).findById(1L);
    }

    @Test
    void findById_ResearchSeedbedProfileDoesNotExist_ThrowsResearchSeedbedNotFoundException() {
        // Arrange
        when(researchSeedbedProfilePersistencePort.findById(99L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> researchSeedbedProfileUseCase.findById(99L))
                .isInstanceOf(ResearchSeedbedNotFoundException.class)
                .hasMessage("Perfil de semillero de investigación con ID 99 no encontrado");
        verify(researchSeedbedProfilePersistencePort, times(1)).findById(99L);
    }

    @Test
    void save_ValidResearchSeedbedProfile_SavesSuccessfully() {
        // Arrange
        when(researchSeedbedProfilePersistencePort.findAllByAcademicPeriodId(1L))
                .thenReturn(Collections.emptyList());
        doNothing().when(researchSeedbedProfileHelper)
                .verifyAcademicPeriodIsCurrent(anyLong(), anyString());
        when(researchSeedbedProfileHelper.verifyUsersHasFunctionaryProfiles(researchSeedbedProfile))
                .thenReturn(researchSeedbedProfile);
        when(researchSeedbedProfilePersistencePort.save(researchSeedbedProfile))
                .thenReturn(researchSeedbedProfile);

        // Act
        ResearchSeedbedProfile result = researchSeedbedProfileUseCase.save(researchSeedbedProfile);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(researchSeedbedProfilePersistencePort, times(1)).findAllByAcademicPeriodId(1L);
        verify(researchSeedbedProfileHelper, times(1))
                .verifyAcademicPeriodIsCurrent(1L, "El período académico debe estar activo para crear o actualizar un perfil de semillero de investigación.");
        verify(researchSeedbedProfilePersistencePort, times(1)).save(researchSeedbedProfile);
    }

    @Test
    void save_ResearchSeedbedProfileAlreadyExistsInPeriod_ThrowsResearchSeedbedProfileAlreadyExistsInAcademicPeriod() {
        // Arrange
        ResearchSeedbedProfile existingProfile = new ResearchSeedbedProfile();
        existingProfile.setId(2L);
        existingProfile.setResearchSeedbedId(1L);
        existingProfile.setAcademicPeriodId(1L);

        when(researchSeedbedProfilePersistencePort.findAllByAcademicPeriodId(1L))
                .thenReturn(Arrays.asList(existingProfile));

        // Act & Assert
        assertThatThrownBy(() -> researchSeedbedProfileUseCase.save(researchSeedbedProfile))
                .isInstanceOf(ResearchSeedbedProfileAlreadyExistsInAcademicPeriod.class)
                .hasMessage("Este semillero ya existe en el periodo academico actual");
        verify(researchSeedbedProfilePersistencePort, times(1)).findAllByAcademicPeriodId(1L);
        verify(researchSeedbedProfilePersistencePort, never()).save(any());
    }

    @Test
    void save_CoordinatorAndTutorAreSamePerson_ThrowsSameCoordinatorAndTutorException() {
        // Arrange
        researchSeedbedProfile.setTutorId(1L); // Same as coordinator

        when(researchSeedbedProfilePersistencePort.findAllByAcademicPeriodId(1L))
                .thenReturn(Collections.emptyList());

        // Act & Assert
        assertThatThrownBy(() -> researchSeedbedProfileUseCase.save(researchSeedbedProfile))
                .isInstanceOf(SameCoordinatorAndTutorException.class)
                .hasMessage("El coordinador y el tutor no pueden ser la misma persona.");
        verify(researchSeedbedProfilePersistencePort, never()).save(any());
    }

    @Test
    void update_ValidUpdate_UpdatesSuccessfully() {
        // Arrange
        ResearchSeedbedProfile existingProfile = new ResearchSeedbedProfile();
        existingProfile.setId(1L);
        existingProfile.setCoordinatorId(1L);
        existingProfile.setTutorId(2L);
        existingProfile.setAcademicPeriodId(1L);

        when(researchSeedbedProfilePersistencePort.findById(1L))
                .thenReturn(Optional.of(existingProfile));
        doNothing().when(researchSeedbedProfileHelper)
                .verifyAcademicPeriodIsCurrent(anyLong(), anyString());
        when(researchSeedbedProfileHelper.verifyUsersHasFunctionaryProfiles(researchSeedbedProfile))
                .thenReturn(researchSeedbedProfile);
        when(researchSeedbedProfilePersistencePort.update(1L, researchSeedbedProfile))
                .thenReturn(researchSeedbedProfile);
        // Removed the unnecessary stubbing for findAllByAcademicPeriodId

        // Act
        ResearchSeedbedProfile result = researchSeedbedProfileUseCase.update(1L, researchSeedbedProfile);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(researchSeedbedProfilePersistencePort, times(1)).findById(1L);
        verify(researchSeedbedProfilePersistencePort, times(1)).update(1L, researchSeedbedProfile);
        verify(researchSeedbedProfileHelper, never())
                .handleFunctionaryProfileChangesOnUpdate(any(), anyLong(), anyLong(), anyLong());
    }

    @Test
    void update_CoordinatorChanged_HandlesProfileChanges() {
        // Arrange
        ResearchSeedbedProfile existingProfile = new ResearchSeedbedProfile();
        existingProfile.setId(1L);
        existingProfile.setCoordinatorId(1L);
        existingProfile.setTutorId(2L);
        existingProfile.setAcademicPeriodId(1L);

        ResearchSeedbedProfile updatedProfile = new ResearchSeedbedProfile();
        updatedProfile.setId(1L);
        updatedProfile.setCoordinatorId(3L); // Changed coordinator
        updatedProfile.setTutorId(2L);
        updatedProfile.setAcademicPeriodId(1L);

        when(researchSeedbedProfilePersistencePort.findById(1L))
                .thenReturn(Optional.of(existingProfile));
        doNothing().when(researchSeedbedProfileHelper)
                .verifyAcademicPeriodIsCurrent(anyLong(), anyString());
        when(researchSeedbedProfileHelper.verifyUsersHasFunctionaryProfiles(updatedProfile))
                .thenReturn(updatedProfile);
        when(researchSeedbedProfilePersistencePort.update(1L, updatedProfile))
                .thenReturn(updatedProfile);
        when(researchSeedbedProfilePersistencePort.findAllByAcademicPeriodId(1L))
                .thenReturn(List.of(updatedProfile));
        doNothing().when(researchSeedbedProfileHelper)
                .handleFunctionaryProfileChangesOnUpdate(any(), anyLong(), anyLong(), anyLong());

        // Act
        ResearchSeedbedProfile result = researchSeedbedProfileUseCase.update(1L, updatedProfile);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getCoordinatorId()).isEqualTo(3L);
        verify(researchSeedbedProfilePersistencePort, times(1)).update(1L, updatedProfile);
        verify(researchSeedbedProfileHelper, times(1))
                .handleFunctionaryProfileChangesOnUpdate(any(), eq(1L), eq(1L), eq(2L));
    }

    @Test
    void update_TutorChanged_HandlesProfileChanges() {
        // Arrange
        ResearchSeedbedProfile existingProfile = new ResearchSeedbedProfile();
        existingProfile.setId(1L);
        existingProfile.setCoordinatorId(1L);
        existingProfile.setTutorId(2L);
        existingProfile.setAcademicPeriodId(1L);

        ResearchSeedbedProfile updatedProfile = new ResearchSeedbedProfile();
        updatedProfile.setId(1L);
        updatedProfile.setCoordinatorId(1L);
        updatedProfile.setTutorId(3L); // Changed tutor
        updatedProfile.setAcademicPeriodId(1L);

        when(researchSeedbedProfilePersistencePort.findById(1L))
                .thenReturn(Optional.of(existingProfile));
        doNothing().when(researchSeedbedProfileHelper)
                .verifyAcademicPeriodIsCurrent(anyLong(), anyString());
        when(researchSeedbedProfileHelper.verifyUsersHasFunctionaryProfiles(updatedProfile))
                .thenReturn(updatedProfile);
        when(researchSeedbedProfilePersistencePort.update(1L, updatedProfile))
                .thenReturn(updatedProfile);
        when(researchSeedbedProfilePersistencePort.findAllByAcademicPeriodId(1L))
                .thenReturn(List.of(updatedProfile));
        doNothing().when(researchSeedbedProfileHelper)
                .handleFunctionaryProfileChangesOnUpdate(any(), anyLong(), anyLong(), anyLong());

        // Act
        ResearchSeedbedProfile result = researchSeedbedProfileUseCase.update(1L, updatedProfile);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTutorId()).isEqualTo(3L);
        verify(researchSeedbedProfilePersistencePort, times(1)).update(1L, updatedProfile);
        verify(researchSeedbedProfileHelper, times(1))
                .handleFunctionaryProfileChangesOnUpdate(any(), eq(1L), eq(1L), eq(2L));
    }

    @Test
    void update_TutorChangedFromNullToValue_HandlesProfileChanges() {
        // Arrange
        ResearchSeedbedProfile existingProfile = new ResearchSeedbedProfile();
        existingProfile.setId(1L);
        existingProfile.setCoordinatorId(1L);
        existingProfile.setTutorId(null); // No tutor initially
        existingProfile.setAcademicPeriodId(1L);

        ResearchSeedbedProfile updatedProfile = new ResearchSeedbedProfile();
        updatedProfile.setId(1L);
        updatedProfile.setCoordinatorId(1L);
        updatedProfile.setTutorId(2L); // Added tutor
        updatedProfile.setAcademicPeriodId(1L);

        when(researchSeedbedProfilePersistencePort.findById(1L))
                .thenReturn(Optional.of(existingProfile));
        doNothing().when(researchSeedbedProfileHelper)
                .verifyAcademicPeriodIsCurrent(anyLong(), anyString());
        when(researchSeedbedProfileHelper.verifyUsersHasFunctionaryProfiles(updatedProfile))
                .thenReturn(updatedProfile);
        when(researchSeedbedProfilePersistencePort.update(1L, updatedProfile))
                .thenReturn(updatedProfile);
        when(researchSeedbedProfilePersistencePort.findAllByAcademicPeriodId(1L))
                .thenReturn(List.of(updatedProfile));
        doNothing().when(researchSeedbedProfileHelper)
                .handleFunctionaryProfileChangesOnUpdate(any(), anyLong(), anyLong(), any());

        // Act
        ResearchSeedbedProfile result = researchSeedbedProfileUseCase.update(1L, updatedProfile);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTutorId()).isEqualTo(2L);
        verify(researchSeedbedProfilePersistencePort, times(1)).update(1L, updatedProfile);
        verify(researchSeedbedProfileHelper, times(1))
                .handleFunctionaryProfileChangesOnUpdate(any(), eq(1L), eq(1L), isNull());
    }

    @Test
    void update_TutorChangedFromValueToNull_HandlesProfileChanges() {
        // Arrange
        ResearchSeedbedProfile existingProfile = new ResearchSeedbedProfile();
        existingProfile.setId(1L);
        existingProfile.setCoordinatorId(1L);
        existingProfile.setTutorId(2L);
        existingProfile.setAcademicPeriodId(1L);

        ResearchSeedbedProfile updatedProfile = new ResearchSeedbedProfile();
        updatedProfile.setId(1L);
        updatedProfile.setCoordinatorId(1L);
        updatedProfile.setTutorId(null); // Removed tutor
        updatedProfile.setAcademicPeriodId(1L);

        when(researchSeedbedProfilePersistencePort.findById(1L))
                .thenReturn(Optional.of(existingProfile));
        doNothing().when(researchSeedbedProfileHelper)
                .verifyAcademicPeriodIsCurrent(anyLong(), anyString());
        when(researchSeedbedProfileHelper.verifyUsersHasFunctionaryProfiles(updatedProfile))
                .thenReturn(updatedProfile);
        when(researchSeedbedProfilePersistencePort.update(1L, updatedProfile))
                .thenReturn(updatedProfile);
        when(researchSeedbedProfilePersistencePort.findAllByAcademicPeriodId(1L))
                .thenReturn(List.of(updatedProfile));
        doNothing().when(researchSeedbedProfileHelper)
                .handleFunctionaryProfileChangesOnUpdate(any(), anyLong(), anyLong(), anyLong());

        // Act
        ResearchSeedbedProfile result = researchSeedbedProfileUseCase.update(1L, updatedProfile);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getTutorId()).isNull();
        verify(researchSeedbedProfilePersistencePort, times(1)).update(1L, updatedProfile);
        verify(researchSeedbedProfileHelper, times(1))
                .handleFunctionaryProfileChangesOnUpdate(any(), eq(1L), eq(1L), eq(2L));
    }

    @Test
    void update_BothTutorsNull_DoesNotHandleProfileChanges() {
        // Arrange
        ResearchSeedbedProfile existingProfile = new ResearchSeedbedProfile();
        existingProfile.setId(1L);
        existingProfile.setCoordinatorId(1L);
        existingProfile.setTutorId(null);
        existingProfile.setAcademicPeriodId(1L);

        ResearchSeedbedProfile updatedProfile = new ResearchSeedbedProfile();
        updatedProfile.setId(1L);
        updatedProfile.setCoordinatorId(1L);
        updatedProfile.setTutorId(null); // Still null
        updatedProfile.setAcademicPeriodId(1L);

        when(researchSeedbedProfilePersistencePort.findById(1L))
                .thenReturn(Optional.of(existingProfile));
        doNothing().when(researchSeedbedProfileHelper)
                .verifyAcademicPeriodIsCurrent(anyLong(), anyString());
        when(researchSeedbedProfileHelper.verifyUsersHasFunctionaryProfiles(updatedProfile))
                .thenReturn(updatedProfile);
        when(researchSeedbedProfilePersistencePort.update(1L, updatedProfile))
                .thenReturn(updatedProfile);

        // Act
        ResearchSeedbedProfile result = researchSeedbedProfileUseCase.update(1L, updatedProfile);

        // Assert
        assertThat(result).isNotNull();
        verify(researchSeedbedProfilePersistencePort, times(1)).update(1L, updatedProfile);
        verify(researchSeedbedProfileHelper, never())
                .handleFunctionaryProfileChangesOnUpdate(any(), anyLong(), anyLong(), any());
    }

    @Test
    void update_CoordinatorAndTutorAreSamePerson_ThrowsSameCoordinatorAndTutorException() {
        // Arrange
        ResearchSeedbedProfile existingProfile = new ResearchSeedbedProfile();
        existingProfile.setId(1L);
        existingProfile.setCoordinatorId(1L);
        existingProfile.setTutorId(2L);

        researchSeedbedProfile.setTutorId(1L); // Same as coordinator

        when(researchSeedbedProfilePersistencePort.findById(1L))
                .thenReturn(Optional.of(existingProfile));

        // Act & Assert
        assertThatThrownBy(() -> researchSeedbedProfileUseCase.update(1L, researchSeedbedProfile))
                .isInstanceOf(SameCoordinatorAndTutorException.class)
                .hasMessage("El coordinador y el tutor no pueden ser la misma persona.");
        verify(researchSeedbedProfilePersistencePort, never()).update(anyLong(), any());
    }

    @Test
    void deleteById_ValidDelete_DeletesAndHandlesProfileChanges() {
        // Arrange
        when(researchSeedbedProfilePersistencePort.findById(1L))
                .thenReturn(Optional.of(researchSeedbedProfile));
        doNothing().when(researchSeedbedProfileHelper)
                .verifyAcademicPeriodIsCurrent(anyLong(), anyString());
        doNothing().when(researchSeedbedProfilePersistencePort).deleteById(1L);
        when(researchSeedbedProfilePersistencePort.findAllByAcademicPeriodId(1L))
                .thenReturn(Collections.emptyList());
        doNothing().when(researchSeedbedProfileHelper)
                .handleFunctionaryProfileChangesOnUpdate(any(), anyLong(), anyLong(), anyLong());

        // Act
        researchSeedbedProfileUseCase.deleteById(1L);

        // Assert
        verify(researchSeedbedProfilePersistencePort, times(1)).findById(1L);
        verify(researchSeedbedProfileHelper, times(1))
                .verifyAcademicPeriodIsCurrent(1L, "El período académico debe estar activo para eliminar un perfil de semillero de investigación.");
        verify(researchSeedbedProfilePersistencePort, times(1)).deleteById(1L);
        verify(researchSeedbedProfileHelper, times(1))
                .handleFunctionaryProfileChangesOnUpdate(any(), eq(1L), eq(1L), eq(2L));
    }

    @Test
    void deleteById_ProfileWithNullTutor_DeletesAndHandlesProfileChanges() {
        // Arrange
        researchSeedbedProfile.setTutorId(null);

        when(researchSeedbedProfilePersistencePort.findById(1L))
                .thenReturn(Optional.of(researchSeedbedProfile));
        doNothing().when(researchSeedbedProfileHelper)
                .verifyAcademicPeriodIsCurrent(anyLong(), anyString());
        doNothing().when(researchSeedbedProfilePersistencePort).deleteById(1L);
        when(researchSeedbedProfilePersistencePort.findAllByAcademicPeriodId(1L))
                .thenReturn(Collections.emptyList());
        doNothing().when(researchSeedbedProfileHelper)
                .handleFunctionaryProfileChangesOnUpdate(any(), anyLong(), anyLong(), any());

        // Act
        researchSeedbedProfileUseCase.deleteById(1L);

        // Assert
        verify(researchSeedbedProfilePersistencePort, times(1)).deleteById(1L);
        verify(researchSeedbedProfileHelper, times(1))
                .handleFunctionaryProfileChangesOnUpdate(any(), eq(1L), eq(1L), isNull());
    }

    @Test
    void findAll_ReturnsAllProfiles() {
        // Arrange
        ResearchSeedbedProfile profile2 = new ResearchSeedbedProfile();
        profile2.setId(2L);

        List<ResearchSeedbedProfile> profiles = Arrays.asList(researchSeedbedProfile, profile2);
        when(researchSeedbedProfilePersistencePort.findAll()).thenReturn(profiles);

        // Act
        List<ResearchSeedbedProfile> result = researchSeedbedProfileUseCase.findAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        verify(researchSeedbedProfilePersistencePort, times(1)).findAll();
    }

    @Test
    void findAllByInvestigationGroupProfileId_ReturnsProfilesForGroup() {
        // Arrange
        List<ResearchSeedbedProfile> profiles = Arrays.asList(researchSeedbedProfile);
        when(researchSeedbedProfilePersistencePort.findAllByInvestigationGroupProfileId(1L))
                .thenReturn(profiles);

        // Act
        List<ResearchSeedbedProfile> result = researchSeedbedProfileUseCase
                .findAllByInvestigationGroupProfileId(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getInvestigationGroupProfileId()).isEqualTo(1L);
        verify(researchSeedbedProfilePersistencePort, times(1))
                .findAllByInvestigationGroupProfileId(1L);
    }

    @Test
    void findAllByAcademicPeriodId_ReturnsProfilesForPeriod() {
        // Arrange
        List<ResearchSeedbedProfile> profiles = Collections.singletonList(researchSeedbedProfile);
        when(researchSeedbedProfilePersistencePort.findAllByAcademicPeriodId(1L))
                .thenReturn(profiles);

        // Act
        List<ResearchSeedbedProfile> result = researchSeedbedProfileUseCase
                .findAllByAcademicPeriodId(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getAcademicPeriodId()).isEqualTo(1L);
        verify(researchSeedbedProfilePersistencePort, times(1))
                .findAllByAcademicPeriodId(1L);
    }

    @Test
    void getExcelBytesReportById_ReturnsExcelReport() {
        // Arrange
        byte[] expectedBytes = new byte[]{1, 2, 3};
        SeedbedReportMetadata metadata = new SeedbedReportMetadata("SeedbedName", "academic period");
        ExcelReport<SeedbedReportMetadata> excelReport = new ExcelReport<>(expectedBytes, metadata);

        when(researchSeedbedProfilePersistencePort.getExcelBytesReportById(1L, 1L))
                .thenReturn(excelReport);

        // Act
        ExcelReport<SeedbedReportMetadata> result = researchSeedbedProfileUseCase
                .getExcelBytesReportById(1L, 1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo(expectedBytes);
        assertThat(result.getMetadata()).isEqualTo(metadata);
        verify(researchSeedbedProfilePersistencePort, times(1))
                .getExcelBytesReportById(1L, 1L);
    }
}