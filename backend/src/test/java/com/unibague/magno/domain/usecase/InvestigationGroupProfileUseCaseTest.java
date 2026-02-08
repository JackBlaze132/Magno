package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IFunctionaryProfileServicePort;
import com.unibague.magno.domain.api.IRoleServicePort;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.exception.investigationgroupprofile.InvestigationGroupProfileDuplicatedInSameAcademicPeriodException;
import com.unibague.magno.domain.exception.investigationgroupprofile.InvestigationGroupProfileNotFoundException;
import com.unibague.magno.domain.model.InvestigationGroupProfile;
import com.unibague.magno.domain.model.excel.ExcelReport;
import com.unibague.magno.domain.model.excel.metadata.ActiveSeedbedsMetadata;
import com.unibague.magno.domain.model.excel.metadata.InvestigationGroupHYRMetadata;
import com.unibague.magno.domain.model.excel.metadata.InvestigationGroupYRMetadata;
import com.unibague.magno.domain.spi.IInvestigationGroupProfilePersistencePort;
import com.unibague.magno.domain.usecase.helper.IInvestigationGroupProfileHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvestigationGroupProfileUseCaseTest {

    @Mock
    private IInvestigationGroupProfilePersistencePort investigationGroupProfilePersistencePort;
    @Mock
    private IUserServicePort userServicePort;
    @Mock
    private IFunctionaryProfileServicePort functionaryProfileServicePort;
    @Mock
    private IInvestigationGroupProfileHelper investigationGroupProfileHelper;
    @Mock
    private IRoleServicePort roleServicePort;

    private InvestigationGroupProfileUseCase investigationGroupProfileUseCase;
    private InvestigationGroupProfile investigationGroupProfile;

    @BeforeEach
    void setUp() {
        investigationGroupProfileUseCase = new InvestigationGroupProfileUseCase(
                investigationGroupProfilePersistencePort,
                userServicePort,
                functionaryProfileServicePort,
                investigationGroupProfileHelper,
                roleServicePort
        );

        investigationGroupProfile = new InvestigationGroupProfile();
        investigationGroupProfile.setId(1L);
        investigationGroupProfile.setInvestigationGroupId(1L);
        investigationGroupProfile.setAcademicPeriodId(1L);
        investigationGroupProfile.setCoordinatorId(1L);
    }

    @Test
    void findById_InvestigationGroupProfileExists_ReturnsProfile() {
        // Arrange
        when(investigationGroupProfilePersistencePort.findById(1L))
                .thenReturn(Optional.of(investigationGroupProfile));

        // Act
        InvestigationGroupProfile result = investigationGroupProfileUseCase.findById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getInvestigationGroupId()).isEqualTo(1L);
        assertThat(result.getAcademicPeriodId()).isEqualTo(1L);
        assertThat(result.getCoordinatorId()).isEqualTo(1L);
        verify(investigationGroupProfilePersistencePort, times(1)).findById(1L);
    }

    @Test
    void findById_InvestigationGroupProfileDoesNotExist_ThrowsInvestigationGroupProfileNotFoundException() {
        // Arrange
        when(investigationGroupProfilePersistencePort.findById(99L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> investigationGroupProfileUseCase.findById(99L))
                .isInstanceOf(InvestigationGroupProfileNotFoundException.class)
                .hasMessage("Perfil de grupo de investigación con ID 99 no encontrado");
        verify(investigationGroupProfilePersistencePort, times(1)).findById(99L);
    }

    @Test
    void save_ValidInvestigationGroupProfile_SavesSuccessfully() {
        // Arrange
        doNothing().when(investigationGroupProfileHelper)
                .verifyAcademicPeriodIsCurrent(anyLong(), anyString());
        doNothing().when(investigationGroupProfileHelper)
                .verifyAcademicPeriodIsVisible(anyLong(), anyString());
        when(investigationGroupProfilePersistencePort.findAllByAcademicPeriodId(1L))
                .thenReturn(Collections.emptyList());
        doNothing().when(investigationGroupProfileHelper)
                .verifyThatUserIsNotAlreadyAInvestigationGroupCoordinator(anyLong(), anyLong());
        when(investigationGroupProfileHelper.verifyUserHasFunctionaryProfile(investigationGroupProfile))
                .thenReturn(investigationGroupProfile);
        when(investigationGroupProfilePersistencePort.save(investigationGroupProfile))
                .thenReturn(investigationGroupProfile);

        // Act
        InvestigationGroupProfile result = investigationGroupProfileUseCase.save(investigationGroupProfile);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(investigationGroupProfileHelper, times(1))
                .verifyAcademicPeriodIsCurrent(1L, "El período académico debe estar activo para crear un nuevo perfil de grupo de investigación");
        verify(investigationGroupProfileHelper, times(1))
                .verifyAcademicPeriodIsVisible(1L, "No se permite crear perfiles de grupo de investigación en períodos académicos que no son visibles");
        verify(investigationGroupProfilePersistencePort, times(1)).findAllByAcademicPeriodId(1L);
        verify(investigationGroupProfileHelper, times(1))
                .verifyThatUserIsNotAlreadyAInvestigationGroupCoordinator(1L, 1L);
        verify(investigationGroupProfilePersistencePort, times(1)).save(investigationGroupProfile);
    }

    @Test
    void save_InvestigationGroupProfileAlreadyExistsInPeriod_ThrowsInvestigationGroupProfileDuplicatedInSameAcademicPeriodException() {
        // Arrange
        InvestigationGroupProfile existingProfile = new InvestigationGroupProfile();
        existingProfile.setId(2L);
        existingProfile.setInvestigationGroupId(1L);
        existingProfile.setAcademicPeriodId(1L);

        doNothing().when(investigationGroupProfileHelper)
                .verifyAcademicPeriodIsCurrent(anyLong(), anyString());
        doNothing().when(investigationGroupProfileHelper)
                .verifyAcademicPeriodIsVisible(anyLong(), anyString());
        when(investigationGroupProfilePersistencePort.findAllByAcademicPeriodId(1L))
                .thenReturn(Arrays.asList(existingProfile));

        // Act & Assert
        assertThatThrownBy(() -> investigationGroupProfileUseCase.save(investigationGroupProfile))
                .isInstanceOf(InvestigationGroupProfileDuplicatedInSameAcademicPeriodException.class)
                .hasMessage("Ya existe un perfil de grupo de investigación con ID 1 para el período académico con ID 1");
        verify(investigationGroupProfilePersistencePort, times(1)).findAllByAcademicPeriodId(1L);
        verify(investigationGroupProfilePersistencePort, never()).save(any());
    }

    @Test
    void update_ValidUpdate_UpdatesSuccessfully() {
        // Arrange
        InvestigationGroupProfile existingProfile = new InvestigationGroupProfile();
        existingProfile.setId(1L);
        existingProfile.setCoordinatorId(1L);
        existingProfile.setAcademicPeriodId(1L);

        when(investigationGroupProfilePersistencePort.findById(1L))
                .thenReturn(Optional.of(existingProfile));
        doNothing().when(investigationGroupProfileHelper)
                .verifyAcademicPeriodIsCurrent(anyLong(), anyString());
        when(investigationGroupProfileHelper.verifyUserHasFunctionaryProfile(investigationGroupProfile))
                .thenReturn(investigationGroupProfile);
        when(investigationGroupProfilePersistencePort.update(1L, investigationGroupProfile))
                .thenReturn(investigationGroupProfile);

        // Act
        InvestigationGroupProfile result = investigationGroupProfileUseCase.update(1L, investigationGroupProfile);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(investigationGroupProfilePersistencePort, times(1)).findById(1L);
        verify(investigationGroupProfileHelper, times(1))
                .verifyAcademicPeriodIsCurrent(1L, "El período académico debe estar activo para actualizar un perfil de grupo de investigación");
        verify(investigationGroupProfilePersistencePort, times(1)).update(1L, investigationGroupProfile);
        verify(investigationGroupProfileHelper, never())
                .handleFunctionaryProfileChangeOnUpdate(anyLong(), anyLong(), anyLong());
    }

    @Test
    void update_CoordinatorChanged_HandlesProfileChanges() {
        // Arrange
        InvestigationGroupProfile existingProfile = new InvestigationGroupProfile();
        existingProfile.setId(1L);
        existingProfile.setCoordinatorId(1L);
        existingProfile.setAcademicPeriodId(1L);

        InvestigationGroupProfile updatedProfile = new InvestigationGroupProfile();
        updatedProfile.setId(1L);
        updatedProfile.setCoordinatorId(2L); // Changed coordinator
        updatedProfile.setAcademicPeriodId(1L);

        when(investigationGroupProfilePersistencePort.findById(1L))
                .thenReturn(Optional.of(existingProfile));
        doNothing().when(investigationGroupProfileHelper)
                .verifyAcademicPeriodIsCurrent(anyLong(), anyString());
        when(investigationGroupProfileHelper.verifyUserHasFunctionaryProfile(updatedProfile))
                .thenReturn(updatedProfile);
        when(investigationGroupProfilePersistencePort.update(1L, updatedProfile))
                .thenReturn(updatedProfile);
        doNothing().when(investigationGroupProfileHelper)
                .handleFunctionaryProfileChangeOnUpdate(anyLong(), anyLong(), anyLong());

        // Act
        InvestigationGroupProfile result = investigationGroupProfileUseCase.update(1L, updatedProfile);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getCoordinatorId()).isEqualTo(2L);
        verify(investigationGroupProfilePersistencePort, times(1)).update(1L, updatedProfile);
        verify(investigationGroupProfileHelper, times(1))
                .handleFunctionaryProfileChangeOnUpdate(1L, 1L, 1L);
    }

    @Test
    void update_InvestigationGroupProfileDoesNotExist_ThrowsInvestigationGroupProfileNotFoundException() {
        // Arrange
        when(investigationGroupProfilePersistencePort.findById(99L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> investigationGroupProfileUseCase.update(99L, investigationGroupProfile))
                .isInstanceOf(InvestigationGroupProfileNotFoundException.class)
                .hasMessage("Perfil de grupo de investigación con ID 99 no encontrado");
        verify(investigationGroupProfilePersistencePort, times(1)).findById(99L);
        verify(investigationGroupProfilePersistencePort, never()).update(anyLong(), any());
    }

    @Test
    void deleteById_ValidDelete_DeletesSuccessfullyAndDeletesFunctionaryProfile() {
        // Arrange
        when(investigationGroupProfilePersistencePort.findById(1L))
                .thenReturn(Optional.of(investigationGroupProfile));
        doNothing().when(investigationGroupProfileHelper)
                .verifyAcademicPeriodIsCurrent(anyLong(), anyString());
        doNothing().when(investigationGroupProfileHelper)
                .verifyThatInvestigationGroupProfileHasNoResearchSeedbedProfiles(anyLong());
        doNothing().when(investigationGroupProfilePersistencePort).deleteById(1L);
        doNothing().when(functionaryProfileServicePort).deleteById(1L);

        // Act
        investigationGroupProfileUseCase.deleteById(1L);

        // Assert
        verify(investigationGroupProfilePersistencePort, times(2)).findById(1L);
        verify(investigationGroupProfileHelper, times(1))
                .verifyAcademicPeriodIsCurrent(1L, "El período académico debe estar activo para eliminar un perfil de grupo de investigación");
        verify(investigationGroupProfileHelper, times(1))
                .verifyThatInvestigationGroupProfileHasNoResearchSeedbedProfiles(1L);
        verify(investigationGroupProfilePersistencePort, times(1)).deleteById(1L);
        verify(functionaryProfileServicePort, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_InvestigationGroupProfileDoesNotExist_ThrowsInvestigationGroupProfileNotFoundException() {
        // Arrange
        when(investigationGroupProfilePersistencePort.findById(99L))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> investigationGroupProfileUseCase.deleteById(99L))
                .isInstanceOf(InvestigationGroupProfileNotFoundException.class)
                .hasMessage("No se pudo eliminar el perfil de grupo de investigación con ID 99 porque no existe");
        verify(investigationGroupProfilePersistencePort, times(1)).findById(99L);
        verify(investigationGroupProfilePersistencePort, never()).deleteById(anyLong());
    }

    @Test
    void findAll_ReturnsAllProfiles() {
        // Arrange
        InvestigationGroupProfile profile2 = new InvestigationGroupProfile();
        profile2.setId(2L);

        List<InvestigationGroupProfile> profiles = Arrays.asList(investigationGroupProfile, profile2);
        when(investigationGroupProfilePersistencePort.findAll()).thenReturn(profiles);

        // Act
        List<InvestigationGroupProfile> result = investigationGroupProfileUseCase.findAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        verify(investigationGroupProfilePersistencePort, times(1)).findAll();
    }

    @Test
    void findAllByAcademicPeriodId_ReturnsProfilesForPeriod() {
        // Arrange
        List<InvestigationGroupProfile> profiles = Collections.singletonList(investigationGroupProfile);
        when(investigationGroupProfilePersistencePort.findAllByAcademicPeriodId(1L))
                .thenReturn(profiles);

        // Act
        List<InvestigationGroupProfile> result = investigationGroupProfileUseCase.findAllByAcademicPeriodId(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getAcademicPeriodId()).isEqualTo(1L);
        verify(investigationGroupProfilePersistencePort, times(1)).findAllByAcademicPeriodId(1L);
    }

    @Test
    void getExcelBytesForHalfYearInvestigationGroupReport_ReturnsExcelReport() {
        // Arrange
        byte[] expectedBytes = new byte[]{1, 2, 3};
        InvestigationGroupHYRMetadata metadata = new InvestigationGroupHYRMetadata("2023-B");
        ExcelReport<InvestigationGroupHYRMetadata> excelReport = new ExcelReport<>(expectedBytes, metadata);

        when(investigationGroupProfilePersistencePort.getExcelBytesForHalfYearInvestigationGroupReport(1L))
                .thenReturn(excelReport);

        // Act
        ExcelReport<InvestigationGroupHYRMetadata> result = investigationGroupProfileUseCase
                .getExcelBytesForHalfYearInvestigationGroupReport(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo(expectedBytes);
        assertThat(result.getMetadata()).isNotNull();
        verify(investigationGroupProfilePersistencePort, times(1))
                .getExcelBytesForHalfYearInvestigationGroupReport(1L);
    }

    @Test
    void getExcelBytesForAnnualYearInvestigationGroupReport_ReturnsExcelReport() {
        // Arrange
        byte[] expectedBytes = new byte[]{1, 2, 3};
        InvestigationGroupYRMetadata metadata = new InvestigationGroupYRMetadata("2023-A", "2023-B");
        ExcelReport<InvestigationGroupYRMetadata> excelReport = new ExcelReport<>(expectedBytes, metadata);

        when(investigationGroupProfilePersistencePort.getExcelBytesForAnnualYearInvestigationGroupReport(1L, 2L))
                .thenReturn(excelReport);

        // Act
        ExcelReport<InvestigationGroupYRMetadata> result = investigationGroupProfileUseCase
                .getExcelBytesForAnnualYearInvestigationGroupReport(1L, 2L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo(expectedBytes);
        assertThat(result.getMetadata()).isNotNull();
        verify(investigationGroupProfilePersistencePort, times(1))
                .getExcelBytesForAnnualYearInvestigationGroupReport(1L, 2L);
    }

    @Test
    void getExcelBytesForHalfYearActiveSeedbedsReport_ReturnsExcelReport() {
        // Arrange
        byte[] expectedBytes = new byte[]{1, 2, 3};
        ActiveSeedbedsMetadata metadata = new ActiveSeedbedsMetadata("2023-B");
        ExcelReport<ActiveSeedbedsMetadata> excelReport = new ExcelReport<>(expectedBytes, metadata);

        when(investigationGroupProfilePersistencePort.getExcelBytesForHalfYearActiveSeedbedsReport(1L))
                .thenReturn(excelReport);

        // Act
        ExcelReport<ActiveSeedbedsMetadata> result = investigationGroupProfileUseCase
                .getExcelBytesForHalfYearActiveSeedbedsReport(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo(expectedBytes);
        assertThat(result.getMetadata()).isNotNull();
        verify(investigationGroupProfilePersistencePort, times(1))
                .getExcelBytesForHalfYearActiveSeedbedsReport(1L);
    }

    @Test
    void getExcelBytesForAnnualActiveSeedbedsReport_ReturnsExcelReport() {
        // Arrange
        byte[] expectedBytes = new byte[]{1, 2, 3};
        ActiveSeedbedsMetadata metadata = new ActiveSeedbedsMetadata("2023-A");
        ExcelReport<ActiveSeedbedsMetadata> excelReport = new ExcelReport<>(expectedBytes, metadata);

        when(investigationGroupProfilePersistencePort.getExcelBytesForAnnualActiveSeedbedsReport(1L, 2L))
                .thenReturn(excelReport);

        // Act
        ExcelReport<ActiveSeedbedsMetadata> result = investigationGroupProfileUseCase
                .getExcelBytesForAnnualActiveSeedbedsReport(1L, 2L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).isEqualTo(expectedBytes);
        assertThat(result.getMetadata()).isNotNull();
        verify(investigationGroupProfilePersistencePort, times(1))
                .getExcelBytesForAnnualActiveSeedbedsReport(1L, 2L);
    }
}