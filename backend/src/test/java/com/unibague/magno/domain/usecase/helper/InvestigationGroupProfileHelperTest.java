package com.unibague.magno.domain.usecase.helper;

import com.unibague.magno.domain.api.*;
import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.exception.academicperiod.AcademicPeriodNotCurrentException;
import com.unibague.magno.domain.exception.academicperiod.AcademicPeriodNotVisibleException;
import com.unibague.magno.domain.exception.investigationgroupprofile.InvestigationGroupProfileFunctionaryIsAlreadyACoordinatorException;
import com.unibague.magno.domain.exception.investigationgroupprofile.InvestigationGroupProfileHasResearchSeedbedProfilesException;
import com.unibague.magno.domain.model.*;
import com.unibague.magno.domain.model.enums.SeedbedRole;
import com.unibague.magno.domain.model.integra.IntegraFunctionary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InvestigationGroupProfileHelperTest {

    @Mock
    private IIntegraServicePort integraServicePort;
    @Mock
    private IUserServicePort userServicePort;
    @Mock
    private IFunctionaryProfileServicePort functionaryProfileServicePort;
    @Mock
    private IDependencyServicePort dependencyServicePort;
    @Mock
    private IRoleServicePort roleServicePort;
    @Mock
    private IAcademicPeriodServicePort academicPeriodServicePort;
    @Mock
    private IResearchSeedbedProfileServicePort researchSeedbedProfileServicePort;

    private InvestigationGroupProfileHelper helper;

    private Role investigationGroupCoordinatorRole;
    private Role seedbedCoordinatorRole;
    private Role seedbedTutorRole;
    private Dependency dependency;
    private User coordinatorUser;
    private AcademicPeriod currentAcademicPeriod;
    private AcademicPeriod pastAcademicPeriod;
    private AcademicPeriod visibleAcademicPeriod;
    private AcademicPeriod notVisibleAcademicPeriod;

    @BeforeEach
    void setUp() {
        helper = new InvestigationGroupProfileHelper(
                integraServicePort,
                userServicePort,
                functionaryProfileServicePort,
                dependencyServicePort,
                roleServicePort,
                academicPeriodServicePort,
                researchSeedbedProfileServicePort
        );

        investigationGroupCoordinatorRole = new Role();
        investigationGroupCoordinatorRole.setId(1L);
        investigationGroupCoordinatorRole.setName(SeedbedRole.COORDINADOR_DE_GRUPO_DE_INVESTIGACION);

        seedbedCoordinatorRole = new Role();
        seedbedCoordinatorRole.setId(2L);
        seedbedCoordinatorRole.setName(SeedbedRole.COORDINADOR_DE_SEMILLERO);

        seedbedTutorRole = new Role();
        seedbedTutorRole.setId(3L);
        seedbedTutorRole.setName(SeedbedRole.TUTOR_DE_SEMILLERO);

        dependency = new Dependency();
        dependency.setId(10L);
        dependency.setName("Ingeniería de Sistemas");

        coordinatorUser = new User();
        coordinatorUser.setId(100L);
        coordinatorUser.setIdentificationNumber("111111111");

        currentAcademicPeriod = new AcademicPeriod(
                1L, "2024-1",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 6, 30),
                true, true
        );

        pastAcademicPeriod = new AcademicPeriod(
                2L, "2023-2",
                LocalDate.of(2023, 7, 1),
                LocalDate.of(2023, 12, 31),
                false, true
        );

        visibleAcademicPeriod = new AcademicPeriod(
                3L, "2024-2",
                LocalDate.of(2024, 7, 1),
                LocalDate.of(2024, 12, 31),
                true, true
        );

        notVisibleAcademicPeriod = new AcademicPeriod(
                4L, "2025-1",
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 6, 30),
                true, false
        );
    }

    // ==================== verifyUserHasFunctionaryProfile Tests ====================

    @Test
    void verifyUserHasFunctionaryProfile_UserHasProfileWithCorrectRole_UpdatesCoordinatorId() {
        // Arrange
        Long userId = 100L;
        Long academicPeriodId = 1L;

        InvestigationGroupProfile igp = new InvestigationGroupProfile(null, 1L, userId, academicPeriodId);

        FunctionaryProfile existingProfile = new FunctionaryProfile(
                50L, userId, academicPeriodId, 10L, investigationGroupCoordinatorRole.getId()
        );

        when(functionaryProfileServicePort.findAllProfilesByUserId(userId))
                .thenReturn(List.of(existingProfile));
        when(roleServicePort.findById(investigationGroupCoordinatorRole.getId()))
                .thenReturn(investigationGroupCoordinatorRole);

        // Act
        InvestigationGroupProfile result = helper.verifyUserHasFunctionaryProfile(igp);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getCoordinatorId()).isEqualTo(50L);
        verify(functionaryProfileServicePort, never()).save(any());
        verify(functionaryProfileServicePort, never()).update(anyLong(), any());
    }

    @Test
    void verifyUserHasFunctionaryProfile_UserHasProfileWithWrongRole_UpdatesRoleAndCoordinatorId() {
        // Arrange
        Long userId = 100L;
        Long academicPeriodId = 1L;

        InvestigationGroupProfile igp = new InvestigationGroupProfile(null, 1L, userId, academicPeriodId);

        FunctionaryProfile existingProfile = new FunctionaryProfile(
                50L, userId, academicPeriodId, 10L, seedbedTutorRole.getId()
        );

        when(functionaryProfileServicePort.findAllProfilesByUserId(userId))
                .thenReturn(List.of(existingProfile));
        when(roleServicePort.findById(seedbedTutorRole.getId()))
                .thenReturn(seedbedTutorRole);
        when(roleServicePort.findByName(SeedbedRole.COORDINADOR_DE_GRUPO_DE_INVESTIGACION))
                .thenReturn(investigationGroupCoordinatorRole);

        // Act
        InvestigationGroupProfile result = helper.verifyUserHasFunctionaryProfile(igp);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getCoordinatorId()).isEqualTo(50L);
        verify(functionaryProfileServicePort, times(1)).update(eq(50L), any(FunctionaryProfile.class));
    }

    @Test
    void verifyUserHasFunctionaryProfile_UserHasNoProfile_CreatesNewProfile() {
        // Arrange
        Long userId = 100L;
        Long academicPeriodId = 1L;

        InvestigationGroupProfile igp = new InvestigationGroupProfile(null, 1L, userId, academicPeriodId);

        IntegraFunctionary integraFunctionary = new IntegraFunctionary();
        integraFunctionary.setProgram("Ingeniería de Sistemas");

        FunctionaryProfile savedProfile = new FunctionaryProfile(
                70L, userId, academicPeriodId, 10L, investigationGroupCoordinatorRole.getId()
        );

        when(functionaryProfileServicePort.findAllProfilesByUserId(userId))
                .thenReturn(Collections.emptyList());
        when(userServicePort.findById(userId)).thenReturn(coordinatorUser);
        when(integraServicePort.getIntegraFunctionaryByIdentification("111111111"))
                .thenReturn(integraFunctionary);
        when(dependencyServicePort.findByName("Ingeniería de Sistemas")).thenReturn(dependency);
        when(roleServicePort.findByName(SeedbedRole.COORDINADOR_DE_GRUPO_DE_INVESTIGACION))
                .thenReturn(investigationGroupCoordinatorRole);
        when(functionaryProfileServicePort.save(any(FunctionaryProfile.class)))
                .thenReturn(savedProfile);

        // Act
        InvestigationGroupProfile result = helper.verifyUserHasFunctionaryProfile(igp);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getCoordinatorId()).isEqualTo(70L);
        verify(functionaryProfileServicePort, times(1)).save(any(FunctionaryProfile.class));
    }

    @Test
    void verifyUserHasFunctionaryProfile_UserHasProfileInDifferentPeriod_CreatesNewProfile() {
        // Arrange
        Long userId = 100L;
        Long academicPeriodId = 1L;
        Long differentPeriodId = 2L;

        InvestigationGroupProfile igp = new InvestigationGroupProfile(null, 1L, userId, academicPeriodId);

        FunctionaryProfile existingProfileDifferentPeriod = new FunctionaryProfile(
                50L, userId, differentPeriodId, 10L, investigationGroupCoordinatorRole.getId()
        );

        IntegraFunctionary integraFunctionary = new IntegraFunctionary();
        integraFunctionary.setProgram("Ingeniería de Sistemas");

        FunctionaryProfile savedProfile = new FunctionaryProfile(
                70L, userId, academicPeriodId, 10L, investigationGroupCoordinatorRole.getId()
        );

        when(functionaryProfileServicePort.findAllProfilesByUserId(userId))
                .thenReturn(List.of(existingProfileDifferentPeriod));
        when(userServicePort.findById(userId)).thenReturn(coordinatorUser);
        when(integraServicePort.getIntegraFunctionaryByIdentification("111111111"))
                .thenReturn(integraFunctionary);
        when(dependencyServicePort.findByName("Ingeniería de Sistemas")).thenReturn(dependency);
        when(roleServicePort.findByName(SeedbedRole.COORDINADOR_DE_GRUPO_DE_INVESTIGACION))
                .thenReturn(investigationGroupCoordinatorRole);
        when(functionaryProfileServicePort.save(any(FunctionaryProfile.class)))
                .thenReturn(savedProfile);

        // Act
        InvestigationGroupProfile result = helper.verifyUserHasFunctionaryProfile(igp);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getCoordinatorId()).isEqualTo(70L);
        verify(functionaryProfileServicePort, times(1)).save(any(FunctionaryProfile.class));
    }

    // ==================== verifyAcademicPeriodIsCurrent Tests ====================

    @Test
    void verifyAcademicPeriodIsCurrent_PeriodIsCurrent_NoException() {
        // Arrange
        Long academicPeriodId = 1L;
        when(academicPeriodServicePort.findById(academicPeriodId)).thenReturn(currentAcademicPeriod);

        // Act & Assert - No exception should be thrown
        helper.verifyAcademicPeriodIsCurrent(academicPeriodId, "Error message");

        verify(academicPeriodServicePort, times(1)).findById(academicPeriodId);
    }

    @Test
    void verifyAcademicPeriodIsCurrent_PeriodIsNotCurrent_ThrowsException() {
        // Arrange
        Long academicPeriodId = 2L;
        String errorMessage = "El período académico no está vigente";
        when(academicPeriodServicePort.findById(academicPeriodId)).thenReturn(pastAcademicPeriod);

        // Act & Assert
        assertThatThrownBy(() -> helper.verifyAcademicPeriodIsCurrent(academicPeriodId, errorMessage))
                .isInstanceOf(AcademicPeriodNotCurrentException.class)
                .hasMessage(errorMessage);

        verify(academicPeriodServicePort, times(1)).findById(academicPeriodId);
    }

    // ==================== verifyAcademicPeriodIsVisible Tests ====================

    @Test
    void verifyAcademicPeriodIsVisible_PeriodIsVisible_NoException() {
        // Arrange
        Long academicPeriodId = 3L;
        when(academicPeriodServicePort.findById(academicPeriodId)).thenReturn(visibleAcademicPeriod);

        // Act & Assert - No exception should be thrown
        helper.verifyAcademicPeriodIsVisible(academicPeriodId, "Error message");

        verify(academicPeriodServicePort, times(1)).findById(academicPeriodId);
    }

    @Test
    void verifyAcademicPeriodIsVisible_PeriodIsNotVisible_ThrowsException() {
        // Arrange
        Long academicPeriodId = 4L;
        String errorMessage = "El período académico no es visible";
        when(academicPeriodServicePort.findById(academicPeriodId)).thenReturn(notVisibleAcademicPeriod);

        // Act & Assert
        assertThatThrownBy(() -> helper.verifyAcademicPeriodIsVisible(academicPeriodId, errorMessage))
                .isInstanceOf(AcademicPeriodNotVisibleException.class)
                .hasMessage(errorMessage);

        verify(academicPeriodServicePort, times(1)).findById(academicPeriodId);
    }

    // ==================== verifyThatUserIsNotAlreadyAInvestigationGroupCoordinator Tests ====================

    @Test
    void verifyThatUserIsNotAlreadyAInvestigationGroupCoordinator_UserIsNotCoordinator_NoException() {
        // Arrange
        Long userId = 100L;
        Long academicPeriodId = 1L;

        User otherUser = new User();
        otherUser.setId(200L);

        when(userServicePort.findInvestigationGroupCoordinatorsByAcademicPeriodId(academicPeriodId))
                .thenReturn(List.of(otherUser));

        // Act & Assert - No exception should be thrown
        helper.verifyThatUserIsNotAlreadyAInvestigationGroupCoordinator(userId, academicPeriodId);

        verify(userServicePort, times(1)).findInvestigationGroupCoordinatorsByAcademicPeriodId(academicPeriodId);
    }

    @Test
    void verifyThatUserIsNotAlreadyAInvestigationGroupCoordinator_UserIsAlreadyCoordinator_ThrowsException() {
        // Arrange
        Long userId = 100L;
        Long academicPeriodId = 1L;

        when(userServicePort.findInvestigationGroupCoordinatorsByAcademicPeriodId(academicPeriodId))
                .thenReturn(List.of(coordinatorUser));

        // Act & Assert
        assertThatThrownBy(() -> helper.verifyThatUserIsNotAlreadyAInvestigationGroupCoordinator(userId, academicPeriodId))
                .isInstanceOf(InvestigationGroupProfileFunctionaryIsAlreadyACoordinatorException.class)
                .hasMessageContaining("ya es");

        verify(userServicePort, times(1)).findInvestigationGroupCoordinatorsByAcademicPeriodId(academicPeriodId);
    }

    @Test
    void verifyThatUserIsNotAlreadyAInvestigationGroupCoordinator_NoCoordinatorsExist_NoException() {
        // Arrange
        Long userId = 100L;
        Long academicPeriodId = 1L;

        when(userServicePort.findInvestigationGroupCoordinatorsByAcademicPeriodId(academicPeriodId))
                .thenReturn(Collections.emptyList());

        // Act & Assert - No exception should be thrown
        helper.verifyThatUserIsNotAlreadyAInvestigationGroupCoordinator(userId, academicPeriodId);

        verify(userServicePort, times(1)).findInvestigationGroupCoordinatorsByAcademicPeriodId(academicPeriodId);
    }

    // ==================== handleFunctionaryProfileChangeOnUpdate Tests ====================

    @Test
    void handleFunctionaryProfileChangeOnUpdate_OldCoordinatorIsSeedbedCoordinator_UpdatesToSeedbedCoordinatorRole() {
        // Arrange
        Long oldCoordinatorId = 50L;
        Long academicPeriodId = 1L;
        Long investigationGroupProfileId = 1L;

        ResearchSeedbedProfile rspAsCoordinator = new ResearchSeedbedProfile(
                1L, 1L, oldCoordinatorId, 60L, 1L, academicPeriodId, true
        );

        FunctionaryProfile functionaryProfile = new FunctionaryProfile(
                oldCoordinatorId, 100L, academicPeriodId, 10L, investigationGroupCoordinatorRole.getId()
        );

        when(researchSeedbedProfileServicePort.findAllByAcademicPeriodId(academicPeriodId))
                .thenReturn(List.of(rspAsCoordinator));
        when(roleServicePort.findByName(SeedbedRole.COORDINADOR_DE_SEMILLERO))
                .thenReturn(seedbedCoordinatorRole);
        when(functionaryProfileServicePort.findById(oldCoordinatorId))
                .thenReturn(functionaryProfile);

        // Act
        helper.handleFunctionaryProfileChangeOnUpdate(oldCoordinatorId, academicPeriodId, investigationGroupProfileId);

        // Assert
        verify(functionaryProfileServicePort, times(1)).update(eq(oldCoordinatorId), any(FunctionaryProfile.class));
        verify(functionaryProfileServicePort, never()).deleteById(anyLong());
    }

    @Test
    void handleFunctionaryProfileChangeOnUpdate_OldCoordinatorIsSeedbedTutor_UpdatesToTutorRole() {
        // Arrange
        Long oldCoordinatorId = 50L;
        Long academicPeriodId = 1L;
        Long investigationGroupProfileId = 1L;

        ResearchSeedbedProfile rspAsTutor = new ResearchSeedbedProfile(
                1L, 1L, 60L, oldCoordinatorId, 1L, academicPeriodId, true
        );

        FunctionaryProfile functionaryProfile = new FunctionaryProfile(
                oldCoordinatorId, 100L, academicPeriodId, 10L, investigationGroupCoordinatorRole.getId()
        );

        when(researchSeedbedProfileServicePort.findAllByAcademicPeriodId(academicPeriodId))
                .thenReturn(List.of(rspAsTutor));
        when(roleServicePort.findByName(SeedbedRole.TUTOR_DE_SEMILLERO))
                .thenReturn(seedbedTutorRole);
        when(functionaryProfileServicePort.findById(oldCoordinatorId))
                .thenReturn(functionaryProfile);

        // Act
        helper.handleFunctionaryProfileChangeOnUpdate(oldCoordinatorId, academicPeriodId, investigationGroupProfileId);

        // Assert
        verify(functionaryProfileServicePort, times(1)).update(eq(oldCoordinatorId), any(FunctionaryProfile.class));
        verify(functionaryProfileServicePort, never()).deleteById(anyLong());
    }

    @Test
    void handleFunctionaryProfileChangeOnUpdate_OldCoordinatorNotInAnySeedbed_DeletesProfile() {
        // Arrange
        Long oldCoordinatorId = 50L;
        Long academicPeriodId = 1L;
        Long investigationGroupProfileId = 1L;

        when(researchSeedbedProfileServicePort.findAllByAcademicPeriodId(academicPeriodId))
                .thenReturn(Collections.emptyList());

        // Act
        helper.handleFunctionaryProfileChangeOnUpdate(oldCoordinatorId, academicPeriodId, investigationGroupProfileId);

        // Assert
        verify(functionaryProfileServicePort, times(1)).deleteById(oldCoordinatorId);
        verify(functionaryProfileServicePort, never()).update(anyLong(), any());
    }

    @Test
    void handleFunctionaryProfileChangeOnUpdate_OldCoordinatorInOtherSeedbedAsNeitherCoordinatorNorTutor_DeletesProfile() {
        // Arrange
        Long oldCoordinatorId = 50L;
        Long academicPeriodId = 1L;
        Long investigationGroupProfileId = 1L;

        // A seedbed where oldCoordinatorId is neither coordinator nor tutor
        ResearchSeedbedProfile rsp = new ResearchSeedbedProfile(
                1L, 1L, 99L, 88L, 1L, academicPeriodId, true
        );

        when(researchSeedbedProfileServicePort.findAllByAcademicPeriodId(academicPeriodId))
                .thenReturn(List.of(rsp));

        // Act
        helper.handleFunctionaryProfileChangeOnUpdate(oldCoordinatorId, academicPeriodId, investigationGroupProfileId);

        // Assert
        verify(functionaryProfileServicePort, times(1)).deleteById(oldCoordinatorId);
    }

    // ==================== verifyThatInvestigationGroupProfileHasNoResearchSeedbedProfiles Tests ====================

    @Test
    void verifyThatInvestigationGroupProfileHasNoResearchSeedbedProfiles_HasNoProfiles_NoException() {
        // Arrange
        Long investigationGroupProfileId = 1L;

        when(researchSeedbedProfileServicePort.findAllByInvestigationGroupProfileId(investigationGroupProfileId))
                .thenReturn(Collections.emptyList());

        // Act & Assert - No exception should be thrown
        helper.verifyThatInvestigationGroupProfileHasNoResearchSeedbedProfiles(investigationGroupProfileId);

        verify(researchSeedbedProfileServicePort, times(1))
                .findAllByInvestigationGroupProfileId(investigationGroupProfileId);
    }

    @Test
    void verifyThatInvestigationGroupProfileHasNoResearchSeedbedProfiles_HasProfiles_ThrowsException() {
        // Arrange
        Long investigationGroupProfileId = 1L;

        ResearchSeedbedProfile rsp1 = new ResearchSeedbedProfile(1L, 1L, 50L, 60L, investigationGroupProfileId, 1L, true);
        ResearchSeedbedProfile rsp2 = new ResearchSeedbedProfile(2L, 2L, 51L, 61L, investigationGroupProfileId, 1L, true);

        when(researchSeedbedProfileServicePort.findAllByInvestigationGroupProfileId(investigationGroupProfileId))
                .thenReturn(List.of(rsp1, rsp2));

        // Act & Assert
        assertThatThrownBy(() -> helper.verifyThatInvestigationGroupProfileHasNoResearchSeedbedProfiles(investigationGroupProfileId))
                .isInstanceOf(InvestigationGroupProfileHasResearchSeedbedProfilesException.class)
                .hasMessageContaining("2 perfil(es)");

        verify(researchSeedbedProfileServicePort, times(1))
                .findAllByInvestigationGroupProfileId(investigationGroupProfileId);
    }

    @Test
    void verifyThatInvestigationGroupProfileHasNoResearchSeedbedProfiles_HasOneProfile_ThrowsExceptionWithCorrectCount() {
        // Arrange
        Long investigationGroupProfileId = 1L;

        ResearchSeedbedProfile rsp = new ResearchSeedbedProfile(1L, 1L, 50L, 60L, investigationGroupProfileId, 1L, true);

        when(researchSeedbedProfileServicePort.findAllByInvestigationGroupProfileId(investigationGroupProfileId))
                .thenReturn(List.of(rsp));

        // Act & Assert
        assertThatThrownBy(() -> helper.verifyThatInvestigationGroupProfileHasNoResearchSeedbedProfiles(investigationGroupProfileId))
                .isInstanceOf(InvestigationGroupProfileHasResearchSeedbedProfilesException.class)
                .hasMessageContaining("1 perfil(es)");
    }
}
