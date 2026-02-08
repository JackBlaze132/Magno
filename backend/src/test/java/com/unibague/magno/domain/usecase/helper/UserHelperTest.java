package com.unibague.magno.domain.usecase.helper;

import com.unibague.magno.domain.api.IAcademicPeriodServicePort;
import com.unibague.magno.domain.api.IDependencyServicePort;
import com.unibague.magno.domain.api.IFunctionaryProfileServicePort;
import com.unibague.magno.domain.api.IRoleServicePort;
import com.unibague.magno.domain.model.AcademicPeriod;
import com.unibague.magno.domain.model.Dependency;
import com.unibague.magno.domain.model.FunctionaryProfile;
import com.unibague.magno.domain.model.Role;
import com.unibague.magno.domain.model.enums.SeedbedRole;
import com.unibague.magno.domain.model.util.SystemConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserHelperTest {

    @Mock
    private IFunctionaryProfileServicePort functionaryProfileServicePort;
    @Mock
    private IAcademicPeriodServicePort academicPeriodServicePort;
    @Mock
    private IRoleServicePort roleServicePort;
    @Mock
    private IDependencyServicePort dependencyServicePort;

    private UserHelper userHelper;
    private AcademicPeriod academicPeriod;
    private Role diriRole;
    private Dependency dependency;

    @BeforeEach
    void setUp() {
        userHelper = new UserHelper(
                functionaryProfileServicePort,
                academicPeriodServicePort,
                roleServicePort,
                dependencyServicePort
        );

        academicPeriod = new AcademicPeriod(
                1L,
                SystemConstants.ADMIN_REGISTRATION_ACADEMIC_PERIOD_NAME,
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 12, 31),
                false,
                true
        );

        diriRole = new Role();
        diriRole.setId(10L);
        diriRole.setName(SeedbedRole.DIRI);

        dependency = new Dependency();
        dependency.setId(5L);
        dependency.setName(SystemConstants.DIRI_DEPENDENCY_NAME);
    }

    @Test
    void addDiriUser_Success_CreatesFunctionaryProfile() {
        // Arrange
        String diriIdentification = "123456789";
        Long diriUserId = 100L;

        when(academicPeriodServicePort.findByName(SystemConstants.ADMIN_REGISTRATION_ACADEMIC_PERIOD_NAME))
                .thenReturn(academicPeriod);
        when(roleServicePort.findByName(SeedbedRole.DIRI))
                .thenReturn(diriRole);
        when(dependencyServicePort.findByName(SystemConstants.DIRI_DEPENDENCY_NAME))
                .thenReturn(dependency);

        FunctionaryProfile savedProfile = new FunctionaryProfile(
                1L,
                diriUserId,
                academicPeriod.getId(),
                dependency.getId(),
                diriRole.getId()
        );
        when(functionaryProfileServicePort.saveIgnoringPeriodVisibility(any(FunctionaryProfile.class)))
                .thenReturn(savedProfile);

        // Act
        userHelper.addDiriUser(diriIdentification, diriUserId);

        // Assert
        ArgumentCaptor<FunctionaryProfile> profileCaptor = ArgumentCaptor.forClass(FunctionaryProfile.class);
        verify(functionaryProfileServicePort, times(1))
                .saveIgnoringPeriodVisibility(profileCaptor.capture());

        FunctionaryProfile capturedProfile = profileCaptor.getValue();
        assertThat(capturedProfile.getUserId()).isEqualTo(diriUserId);
        assertThat(capturedProfile.getAcademicPeriodId()).isEqualTo(academicPeriod.getId());
        assertThat(capturedProfile.getDependencyId()).isEqualTo(dependency.getId());
        assertThat(capturedProfile.getRoleId()).isEqualTo(diriRole.getId());
        assertThat(capturedProfile.getId()).isNull();

        verify(academicPeriodServicePort, times(1))
                .findByName(SystemConstants.ADMIN_REGISTRATION_ACADEMIC_PERIOD_NAME);
        verify(roleServicePort, times(1)).findByName(SeedbedRole.DIRI);
        verify(dependencyServicePort, times(1)).findByName(SystemConstants.DIRI_DEPENDENCY_NAME);
    }

    @Test
    void deleteDiriUser_WithDiriProfiles_DeletesAllDiriProfiles() {
        // Arrange
        String diriIdentification = "123456789";
        Long diriUserId = 100L;

        FunctionaryProfile diriProfile1 = new FunctionaryProfile(1L, diriUserId, 1L, 5L, diriRole.getId());
        FunctionaryProfile diriProfile2 = new FunctionaryProfile(2L, diriUserId, 2L, 5L, diriRole.getId());

        when(roleServicePort.findByName(SeedbedRole.DIRI)).thenReturn(diriRole);
        when(functionaryProfileServicePort.findAllProfilesByUserId(diriUserId))
                .thenReturn(List.of(diriProfile1, diriProfile2));

        // Act
        userHelper.deleteDiriUser(diriIdentification, diriUserId);

        // Assert
        verify(roleServicePort, times(1)).findByName(SeedbedRole.DIRI);
        verify(functionaryProfileServicePort, times(1)).findAllProfilesByUserId(diriUserId);
        verify(functionaryProfileServicePort, times(1)).deleteById(1L);
        verify(functionaryProfileServicePort, times(1)).deleteById(2L);
    }

    @Test
    void deleteDiriUser_WithMixedProfiles_DeletesOnlyDiriProfiles() {
        // Arrange
        String diriIdentification = "123456789";
        Long diriUserId = 100L;
        Long otherRoleId = 99L;

        FunctionaryProfile diriProfile = new FunctionaryProfile(1L, diriUserId, 1L, 5L, diriRole.getId());
        FunctionaryProfile otherProfile = new FunctionaryProfile(2L, diriUserId, 1L, 5L, otherRoleId);

        when(roleServicePort.findByName(SeedbedRole.DIRI)).thenReturn(diriRole);
        when(functionaryProfileServicePort.findAllProfilesByUserId(diriUserId))
                .thenReturn(List.of(diriProfile, otherProfile));

        // Act
        userHelper.deleteDiriUser(diriIdentification, diriUserId);

        // Assert
        verify(functionaryProfileServicePort, times(1)).deleteById(1L);
        verify(functionaryProfileServicePort, never()).deleteById(2L);
    }

    @Test
    void deleteDiriUser_NoDiriProfiles_DoesNotDeleteAnything() {
        // Arrange
        String diriIdentification = "123456789";
        Long diriUserId = 100L;

        when(roleServicePort.findByName(SeedbedRole.DIRI)).thenReturn(diriRole);
        when(functionaryProfileServicePort.findAllProfilesByUserId(diriUserId))
                .thenReturn(Collections.emptyList());

        // Act
        userHelper.deleteDiriUser(diriIdentification, diriUserId);

        // Assert
        verify(roleServicePort, times(1)).findByName(SeedbedRole.DIRI);
        verify(functionaryProfileServicePort, times(1)).findAllProfilesByUserId(diriUserId);
        verify(functionaryProfileServicePort, never()).deleteById(anyLong());
    }

    @Test
    void deleteDiriUser_OnlyNonDiriProfiles_DoesNotDeleteAnything() {
        // Arrange
        String diriIdentification = "123456789";
        Long diriUserId = 100L;
        Long otherRoleId = 99L;

        FunctionaryProfile otherProfile1 = new FunctionaryProfile(1L, diriUserId, 1L, 5L, otherRoleId);
        FunctionaryProfile otherProfile2 = new FunctionaryProfile(2L, diriUserId, 2L, 5L, otherRoleId);

        when(roleServicePort.findByName(SeedbedRole.DIRI)).thenReturn(diriRole);
        when(functionaryProfileServicePort.findAllProfilesByUserId(diriUserId))
                .thenReturn(List.of(otherProfile1, otherProfile2));

        // Act
        userHelper.deleteDiriUser(diriIdentification, diriUserId);

        // Assert
        verify(functionaryProfileServicePort, never()).deleteById(anyLong());
    }
}
