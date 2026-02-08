package com.unibague.magno.domain.usecase.helper;

import com.unibague.magno.domain.api.*;
import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.exception.academicperiod.AcademicPeriodNotCurrentException;
import com.unibague.magno.domain.model.*;
import com.unibague.magno.domain.model.enums.SeedbedRole;
import com.unibague.magno.domain.model.integra.IntegraFunctionary;
import com.unibague.magno.domain.spi.IInvestigationGroupProfilePersistencePort;
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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResearchSeedbedProfileHelperTest {

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
    private IInvestigationGroupProfilePersistencePort investigationGroupProfilePersistencePort;

    private ResearchSeedbedProfileHelper helper;

    private Role coordinatorRole;
    private Role tutorRole;
    private Role investigationGroupCoordinatorRole;
    private Dependency dependency;
    private User coordinatorUser;
    private User tutorUser;
    private AcademicPeriod currentAcademicPeriod;
    private AcademicPeriod pastAcademicPeriod;

    @BeforeEach
    void setUp() {
        helper = new ResearchSeedbedProfileHelper(
                integraServicePort,
                userServicePort,
                functionaryProfileServicePort,
                dependencyServicePort,
                roleServicePort,
                academicPeriodServicePort,
                investigationGroupProfilePersistencePort
        );

        coordinatorRole = new Role();
        coordinatorRole.setId(1L);
        coordinatorRole.setName(SeedbedRole.COORDINADOR_DE_SEMILLERO);

        tutorRole = new Role();
        tutorRole.setId(2L);
        tutorRole.setName(SeedbedRole.TUTOR_DE_SEMILLERO);

        investigationGroupCoordinatorRole = new Role();
        investigationGroupCoordinatorRole.setId(3L);
        investigationGroupCoordinatorRole.setName(SeedbedRole.COORDINADOR_DE_GRUPO_DE_INVESTIGACION);

        dependency = new Dependency();
        dependency.setId(10L);
        dependency.setName("Ingeniería de Sistemas");

        coordinatorUser = new User();
        coordinatorUser.setId(100L);
        coordinatorUser.setIdentificationNumber("111111111");

        tutorUser = new User();
        tutorUser.setId(200L);
        tutorUser.setIdentificationNumber("222222222");

        currentAcademicPeriod = new AcademicPeriod(
                1L,
                "2024-1",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 6, 30),
                true,
                true
        );

        pastAcademicPeriod = new AcademicPeriod(
                2L,
                "2023-2",
                LocalDate.of(2023, 7, 1),
                LocalDate.of(2023, 12, 31),
                false,
                true
        );
    }

    // ==================== verifyUsersHasFunctionaryProfiles Tests ====================

    @Test
    void verifyUsersHasFunctionaryProfiles_BothHaveProfiles_UpdatesIdsAndReturns() {
        // Arrange
        Long academicPeriodId = 1L;
        Long coordinatorUserId = 100L;
        Long tutorUserId = 200L;

        ResearchSeedbedProfile rsp = new ResearchSeedbedProfile(
                null, 1L, coordinatorUserId, tutorUserId, 1L, academicPeriodId, true
        );

        FunctionaryProfile coordinatorProfile = new FunctionaryProfile(50L, coordinatorUserId, academicPeriodId, 10L, 1L);
        FunctionaryProfile tutorProfile = new FunctionaryProfile(60L, tutorUserId, academicPeriodId, 10L, 2L);

        when(functionaryProfileServicePort.findAllProfilesByUserId(coordinatorUserId))
                .thenReturn(List.of(coordinatorProfile));
        when(functionaryProfileServicePort.findAllProfilesByUserId(tutorUserId))
                .thenReturn(List.of(tutorProfile));

        // Act
        ResearchSeedbedProfile result = helper.verifyUsersHasFunctionaryProfiles(rsp);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getCoordinatorId()).isEqualTo(50L);
        assertThat(result.getTutorId()).isEqualTo(60L);
        verify(functionaryProfileServicePort, never()).save(any());
    }

    @Test
    void verifyUsersHasFunctionaryProfiles_CoordinatorNoProfile_CreatesProfile() {
        // Arrange
        Long academicPeriodId = 1L;
        Long coordinatorUserId = 100L;
        Long tutorUserId = 200L;

        ResearchSeedbedProfile rsp = new ResearchSeedbedProfile(
                null, 1L, coordinatorUserId, tutorUserId, 1L, academicPeriodId, true
        );

        FunctionaryProfile tutorProfile = new FunctionaryProfile(60L, tutorUserId, academicPeriodId, 10L, 2L);
        FunctionaryProfile savedCoordinatorProfile = new FunctionaryProfile(70L, coordinatorUserId, academicPeriodId, 10L, 1L);

        IntegraFunctionary integraFunctionary = new IntegraFunctionary();
        integraFunctionary.setProgram("Ingeniería de Sistemas");

        when(functionaryProfileServicePort.findAllProfilesByUserId(coordinatorUserId))
                .thenReturn(Collections.emptyList());
        when(functionaryProfileServicePort.findAllProfilesByUserId(tutorUserId))
                .thenReturn(List.of(tutorProfile));
        when(userServicePort.findById(coordinatorUserId)).thenReturn(coordinatorUser);
        when(integraServicePort.getIntegraFunctionaryByIdentification("111111111"))
                .thenReturn(integraFunctionary);
        when(dependencyServicePort.findByName("Ingeniería de Sistemas")).thenReturn(dependency);
        when(roleServicePort.findByName(SeedbedRole.COORDINADOR_DE_SEMILLERO)).thenReturn(coordinatorRole);
        when(functionaryProfileServicePort.save(any(FunctionaryProfile.class))).thenReturn(savedCoordinatorProfile);

        // Act
        ResearchSeedbedProfile result = helper.verifyUsersHasFunctionaryProfiles(rsp);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getCoordinatorId()).isEqualTo(70L);
        verify(functionaryProfileServicePort, times(1)).save(any(FunctionaryProfile.class));
    }

    @Test
    void verifyUsersHasFunctionaryProfiles_TutorNoProfile_CreatesProfile() {
        // Arrange
        Long academicPeriodId = 1L;
        Long coordinatorUserId = 100L;
        Long tutorUserId = 200L;

        ResearchSeedbedProfile rsp = new ResearchSeedbedProfile(
                null, 1L, coordinatorUserId, tutorUserId, 1L, academicPeriodId, true
        );

        FunctionaryProfile coordinatorProfile = new FunctionaryProfile(50L, coordinatorUserId, academicPeriodId, 10L, 1L);
        coordinatorProfile.setRoleId(coordinatorRole.getId());
        FunctionaryProfile savedTutorProfile = new FunctionaryProfile(80L, tutorUserId, academicPeriodId, 10L, 2L);

        IntegraFunctionary integraFunctionary = new IntegraFunctionary();
        integraFunctionary.setProgram("Ingeniería de Sistemas");

        when(functionaryProfileServicePort.findAllProfilesByUserId(coordinatorUserId))
                .thenReturn(List.of(coordinatorProfile));
        when(functionaryProfileServicePort.findAllProfilesByUserId(tutorUserId))
                .thenReturn(Collections.emptyList());
        when(roleServicePort.findById(coordinatorRole.getId())).thenReturn(coordinatorRole);
        when(roleServicePort.findByName(SeedbedRole.COORDINADOR_DE_SEMILLERO)).thenReturn(coordinatorRole);
        when(userServicePort.findById(tutorUserId)).thenReturn(tutorUser);
        when(integraServicePort.getIntegraFunctionaryByIdentification("222222222"))
                .thenReturn(integraFunctionary);
        when(dependencyServicePort.findByName("Ingeniería de Sistemas")).thenReturn(dependency);
        when(roleServicePort.findByName(SeedbedRole.TUTOR_DE_SEMILLERO)).thenReturn(tutorRole);
        when(functionaryProfileServicePort.save(any(FunctionaryProfile.class))).thenReturn(savedTutorProfile);

        // Act
        ResearchSeedbedProfile result = helper.verifyUsersHasFunctionaryProfiles(rsp);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getCoordinatorId()).isEqualTo(50L);
        assertThat(result.getTutorId()).isEqualTo(80L);
        verify(functionaryProfileServicePort, times(1)).save(any(FunctionaryProfile.class));
    }

    @Test
    void verifyUsersHasFunctionaryProfiles_TutorIsNull_SkipsTutorProcessing() {
        // Arrange
        Long academicPeriodId = 1L;
        Long coordinatorUserId = 100L;

        ResearchSeedbedProfile rsp = new ResearchSeedbedProfile(
                null, 1L, coordinatorUserId, null, 1L, academicPeriodId, true
        );

        FunctionaryProfile coordinatorProfile = new FunctionaryProfile(50L, coordinatorUserId, academicPeriodId, 10L, coordinatorRole.getId());

        when(functionaryProfileServicePort.findAllProfilesByUserId(coordinatorUserId))
                .thenReturn(List.of(coordinatorProfile));
        // When coordinator already has profile and tutor is null, it goes to checkRoleForExistingProfile
        when(roleServicePort.findById(coordinatorRole.getId())).thenReturn(coordinatorRole);
        when(roleServicePort.findByName(SeedbedRole.COORDINADOR_DE_SEMILLERO)).thenReturn(coordinatorRole);

        // Act
        ResearchSeedbedProfile result = helper.verifyUsersHasFunctionaryProfiles(rsp);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getCoordinatorId()).isEqualTo(50L);
        assertThat(result.getTutorId()).isNull();
        verify(functionaryProfileServicePort, atLeastOnce()).findAllProfilesByUserId(coordinatorUserId);
    }

    @Test
    void verifyUsersHasFunctionaryProfiles_CoordinatorHasTutorRole_UpdatesToCoordinatorRole() {
        // Arrange
        Long academicPeriodId = 1L;
        Long coordinatorUserId = 100L;
        Long tutorUserId = 200L;

        ResearchSeedbedProfile rsp = new ResearchSeedbedProfile(
                null, 1L, coordinatorUserId, tutorUserId, 1L, academicPeriodId, true
        );

        // Coordinator has TUTOR role, should be updated to COORDINATOR
        // Tutor doesn't have a profile so the else branch is executed for coordinator
        FunctionaryProfile coordinatorProfile = new FunctionaryProfile(50L, coordinatorUserId, academicPeriodId, 10L, tutorRole.getId());
        FunctionaryProfile savedTutorProfile = new FunctionaryProfile(80L, tutorUserId, academicPeriodId, 10L, tutorRole.getId());

        IntegraFunctionary integraFunctionary = new IntegraFunctionary();
        integraFunctionary.setProgram("Ingeniería de Sistemas");

        when(functionaryProfileServicePort.findAllProfilesByUserId(coordinatorUserId))
                .thenReturn(List.of(coordinatorProfile));
        when(functionaryProfileServicePort.findAllProfilesByUserId(tutorUserId))
                .thenReturn(Collections.emptyList()); // Tutor has no profile
        when(roleServicePort.findById(tutorRole.getId())).thenReturn(tutorRole);
        when(roleServicePort.findByName(SeedbedRole.TUTOR_DE_SEMILLERO)).thenReturn(tutorRole);
        when(roleServicePort.findByName(SeedbedRole.COORDINADOR_DE_SEMILLERO)).thenReturn(coordinatorRole);
        when(userServicePort.findById(tutorUserId)).thenReturn(tutorUser);
        when(integraServicePort.getIntegraFunctionaryByIdentification("222222222"))
                .thenReturn(integraFunctionary);
        when(dependencyServicePort.findByName("Ingeniería de Sistemas")).thenReturn(dependency);
        when(functionaryProfileServicePort.save(any(FunctionaryProfile.class))).thenReturn(savedTutorProfile);

        // Act
        ResearchSeedbedProfile result = helper.verifyUsersHasFunctionaryProfiles(rsp);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getCoordinatorId()).isEqualTo(50L);
        verify(functionaryProfileServicePort, times(1)).update(eq(50L), any(FunctionaryProfile.class));
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

    // ==================== handleFunctionaryProfileChangesOnUpdate Tests ====================

    @Test
    void handleFunctionaryProfileChangesOnUpdate_OldCoordinatorInInvestigationGroups_NoChanges() {
        // Arrange
        Long academicPeriodId = 1L;
        Long oldCoordinatorId = 50L;
        Long oldTutorId = 60L;

        InvestigationGroupProfile igp = new InvestigationGroupProfile(1L, 1L, oldCoordinatorId, academicPeriodId);
        List<InvestigationGroupProfile> investigationGroupProfiles = List.of(igp);
        List<ResearchSeedbedProfile> researchSeedbedProfiles = Collections.emptyList();

        when(investigationGroupProfilePersistencePort.findAllByAcademicPeriodId(academicPeriodId))
                .thenReturn(investigationGroupProfiles);

        // Act
        helper.handleFunctionaryProfileChangesOnUpdate(researchSeedbedProfiles, academicPeriodId, oldCoordinatorId, oldTutorId);

        // Assert - No changes to coordinator because it's in investigation groups
        verify(functionaryProfileServicePort, never()).update(eq(oldCoordinatorId), any());
        verify(functionaryProfileServicePort, times(1)).deleteById(oldTutorId);
    }

    @Test
    void handleFunctionaryProfileChangesOnUpdate_OldCoordinatorInOtherSeedbedAsCoordinator_KeepsRole() {
        // Arrange
        Long academicPeriodId = 1L;
        Long oldCoordinatorId = 50L;
        Long oldTutorId = null;

        ResearchSeedbedProfile otherRsp = new ResearchSeedbedProfile(2L, 2L, oldCoordinatorId, 99L, 2L, academicPeriodId, true);
        List<ResearchSeedbedProfile> researchSeedbedProfiles = List.of(otherRsp);

        FunctionaryProfile coordinatorProfile = new FunctionaryProfile(oldCoordinatorId, 100L, academicPeriodId, 10L, 1L);

        when(investigationGroupProfilePersistencePort.findAllByAcademicPeriodId(academicPeriodId))
                .thenReturn(Collections.emptyList());
        when(functionaryProfileServicePort.findById(oldCoordinatorId)).thenReturn(coordinatorProfile);
        when(roleServicePort.findByName(SeedbedRole.COORDINADOR_DE_SEMILLERO)).thenReturn(coordinatorRole);

        // Act
        helper.handleFunctionaryProfileChangesOnUpdate(researchSeedbedProfiles, academicPeriodId, oldCoordinatorId, oldTutorId);

        // Assert
        verify(functionaryProfileServicePort, times(1)).update(eq(oldCoordinatorId), any(FunctionaryProfile.class));
        verify(functionaryProfileServicePort, never()).deleteById(oldCoordinatorId);
    }

    @Test
    void handleFunctionaryProfileChangesOnUpdate_OldCoordinatorInOtherSeedbedAsTutor_DowngradesToTutor() {
        // Arrange
        Long academicPeriodId = 1L;
        Long oldCoordinatorId = 50L;
        Long oldTutorId = null;

        ResearchSeedbedProfile otherRsp = new ResearchSeedbedProfile(2L, 2L, 99L, oldCoordinatorId, 2L, academicPeriodId, true);
        List<ResearchSeedbedProfile> researchSeedbedProfiles = List.of(otherRsp);

        FunctionaryProfile coordinatorProfile = new FunctionaryProfile(oldCoordinatorId, 100L, academicPeriodId, 10L, 1L);

        when(investigationGroupProfilePersistencePort.findAllByAcademicPeriodId(academicPeriodId))
                .thenReturn(Collections.emptyList());
        when(functionaryProfileServicePort.findById(oldCoordinatorId)).thenReturn(coordinatorProfile);
        when(roleServicePort.findByName(SeedbedRole.TUTOR_DE_SEMILLERO)).thenReturn(tutorRole);

        // Act
        helper.handleFunctionaryProfileChangesOnUpdate(researchSeedbedProfiles, academicPeriodId, oldCoordinatorId, oldTutorId);

        // Assert
        verify(functionaryProfileServicePort, times(1)).update(eq(oldCoordinatorId), any(FunctionaryProfile.class));
        verify(functionaryProfileServicePort, never()).deleteById(oldCoordinatorId);
    }

    @Test
    void handleFunctionaryProfileChangesOnUpdate_OldCoordinatorNotInAnyContext_Deleted() {
        // Arrange
        Long academicPeriodId = 1L;
        Long oldCoordinatorId = 50L;
        Long oldTutorId = null;

        List<ResearchSeedbedProfile> researchSeedbedProfiles = Collections.emptyList();

        when(investigationGroupProfilePersistencePort.findAllByAcademicPeriodId(academicPeriodId))
                .thenReturn(Collections.emptyList());

        // Act
        helper.handleFunctionaryProfileChangesOnUpdate(researchSeedbedProfiles, academicPeriodId, oldCoordinatorId, oldTutorId);

        // Assert
        verify(functionaryProfileServicePort, times(1)).deleteById(oldCoordinatorId);
    }

    @Test
    void handleFunctionaryProfileChangesOnUpdate_OldTutorInInvestigationGroups_UpdatesToInvestigationGroupCoordinator() {
        // Arrange
        Long academicPeriodId = 1L;
        Long oldCoordinatorId = 50L;
        Long oldTutorId = 60L;

        InvestigationGroupProfile igp = new InvestigationGroupProfile(1L, 1L, oldTutorId, academicPeriodId);
        List<InvestigationGroupProfile> investigationGroupProfiles = List.of(igp);
        List<ResearchSeedbedProfile> researchSeedbedProfiles = Collections.emptyList();

        FunctionaryProfile tutorProfile = new FunctionaryProfile(oldTutorId, 200L, academicPeriodId, 10L, 2L);

        when(investigationGroupProfilePersistencePort.findAllByAcademicPeriodId(academicPeriodId))
                .thenReturn(investigationGroupProfiles);
        when(functionaryProfileServicePort.findById(oldTutorId)).thenReturn(tutorProfile);
        when(roleServicePort.findByName(SeedbedRole.COORDINADOR_DE_GRUPO_DE_INVESTIGACION))
                .thenReturn(investigationGroupCoordinatorRole);

        // Act
        helper.handleFunctionaryProfileChangesOnUpdate(researchSeedbedProfiles, academicPeriodId, oldCoordinatorId, oldTutorId);

        // Assert
        verify(functionaryProfileServicePort, times(1)).update(eq(oldTutorId), any(FunctionaryProfile.class));
        verify(functionaryProfileServicePort, never()).deleteById(oldTutorId);
    }

    @Test
    void handleFunctionaryProfileChangesOnUpdate_OldTutorInOtherSeedbedAsCoordinator_UpdatesToCoordinator() {
        // Arrange
        Long academicPeriodId = 1L;
        Long oldCoordinatorId = 50L;
        Long oldTutorId = 60L;

        ResearchSeedbedProfile otherRsp = new ResearchSeedbedProfile(2L, 2L, oldTutorId, 99L, 2L, academicPeriodId, true);
        List<ResearchSeedbedProfile> researchSeedbedProfiles = List.of(otherRsp);

        FunctionaryProfile tutorProfile = new FunctionaryProfile(oldTutorId, 200L, academicPeriodId, 10L, 2L);

        when(investigationGroupProfilePersistencePort.findAllByAcademicPeriodId(academicPeriodId))
                .thenReturn(Collections.emptyList());
        when(functionaryProfileServicePort.findById(oldTutorId)).thenReturn(tutorProfile);
        when(roleServicePort.findByName(SeedbedRole.COORDINADOR_DE_SEMILLERO)).thenReturn(coordinatorRole);

        // Act
        helper.handleFunctionaryProfileChangesOnUpdate(researchSeedbedProfiles, academicPeriodId, oldCoordinatorId, oldTutorId);

        // Assert
        verify(functionaryProfileServicePort, times(1)).update(eq(oldTutorId), any(FunctionaryProfile.class));
        verify(functionaryProfileServicePort, times(1)).deleteById(oldCoordinatorId);
    }

    @Test
    void handleFunctionaryProfileChangesOnUpdate_OldTutorInOtherSeedbedAsTutor_NoChanges() {
        // Arrange
        Long academicPeriodId = 1L;
        Long oldCoordinatorId = 50L;
        Long oldTutorId = 60L;

        ResearchSeedbedProfile otherRsp = new ResearchSeedbedProfile(2L, 2L, 99L, oldTutorId, 2L, academicPeriodId, true);
        List<ResearchSeedbedProfile> researchSeedbedProfiles = List.of(otherRsp);

        when(investigationGroupProfilePersistencePort.findAllByAcademicPeriodId(academicPeriodId))
                .thenReturn(Collections.emptyList());

        // Act
        helper.handleFunctionaryProfileChangesOnUpdate(researchSeedbedProfiles, academicPeriodId, oldCoordinatorId, oldTutorId);

        // Assert - Tutor not deleted because it's still a tutor in another seedbed
        verify(functionaryProfileServicePort, never()).deleteById(oldTutorId);
        verify(functionaryProfileServicePort, times(1)).deleteById(oldCoordinatorId);
    }

    @Test
    void handleFunctionaryProfileChangesOnUpdate_OldTutorNotInAnyContext_Deleted() {
        // Arrange
        Long academicPeriodId = 1L;
        Long oldCoordinatorId = 50L;
        Long oldTutorId = 60L;

        List<ResearchSeedbedProfile> researchSeedbedProfiles = Collections.emptyList();

        when(investigationGroupProfilePersistencePort.findAllByAcademicPeriodId(academicPeriodId))
                .thenReturn(Collections.emptyList());

        // Act
        helper.handleFunctionaryProfileChangesOnUpdate(researchSeedbedProfiles, academicPeriodId, oldCoordinatorId, oldTutorId);

        // Assert
        verify(functionaryProfileServicePort, times(1)).deleteById(oldCoordinatorId);
        verify(functionaryProfileServicePort, times(1)).deleteById(oldTutorId);
    }

    @Test
    void handleFunctionaryProfileChangesOnUpdate_OldTutorIsNull_SkipsTutorHandling() {
        // Arrange
        Long academicPeriodId = 1L;
        Long oldCoordinatorId = 50L;
        Long oldTutorId = null;

        List<ResearchSeedbedProfile> researchSeedbedProfiles = Collections.emptyList();

        when(investigationGroupProfilePersistencePort.findAllByAcademicPeriodId(academicPeriodId))
                .thenReturn(Collections.emptyList());

        // Act
        helper.handleFunctionaryProfileChangesOnUpdate(researchSeedbedProfiles, academicPeriodId, oldCoordinatorId, oldTutorId);

        // Assert - Only coordinator is deleted, tutor handling is skipped
        verify(functionaryProfileServicePort, times(1)).deleteById(oldCoordinatorId);
        verify(functionaryProfileServicePort, times(1)).deleteById(anyLong()); // Only once for coordinator
    }
}
