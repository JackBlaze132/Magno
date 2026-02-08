package com.unibague.magno.domain.usecase.helper;

import com.unibague.magno.domain.api.IAcademicPeriodServicePort;
import com.unibague.magno.domain.api.IResearchSeedbedProfileServicePort;
import com.unibague.magno.domain.api.IStudentProfileServicePort;
import com.unibague.magno.domain.api.IUserServicePort;
import com.unibague.magno.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ResearchSeedbedStudentProfileHelperTest {

    @Mock
    private IUserServicePort userServicePort;
    @Mock
    private IResearchSeedbedProfileServicePort researchSeedbedProfileServicePort;
    @Mock
    private IStudentProfileServicePort studentProfileServicePort;
    @Mock
    private IAcademicPeriodServicePort academicPeriodServicePort;

    private ResearchSeedbedStudentProfileHelper helper;
    private ResearchSeedbedProfile researchSeedbedProfile;
    private User studentUser;
    private StudentProfile existingStudentProfile;

    @BeforeEach
    void setUp() {
        helper = new ResearchSeedbedStudentProfileHelper(
                userServicePort,
                researchSeedbedProfileServicePort,
                studentProfileServicePort,
                academicPeriodServicePort
        );

        researchSeedbedProfile = new ResearchSeedbedProfile(
                10L,      // id
                1L,       // researchSeedbedId
                2L,       // coordinatorId
                3L,       // tutorId
                4L,       // investigationGroupProfileId
                1L,       // academicPeriodId
                true      // wasActive
        );

        studentUser = new User();
        studentUser.setId(100L);
        studentUser.setIdentificationNumber("123456789");
        studentUser.setEmail("student@example.com");

        existingStudentProfile = new StudentProfile();
        existingStudentProfile.setId(50L);
        existingStudentProfile.setUserId(100L);
        existingStudentProfile.setAcademicPeriodId(1L);
    }

    @Test
    void verifyStudentHasAProfile_StudentAlreadyHasProfile_ReturnsWithExistingProfileId() {
        // Arrange
        Long studentUserId = 100L;
        Long researchSeedbedProfileId = 10L;

        ResearchSeedbedStudentProfile input = new ResearchSeedbedStudentProfile(
                null,
                researchSeedbedProfileId,
                studentUserId,  // This is actually the userId initially
                true,
                false
        );

        when(researchSeedbedProfileServicePort.findById(researchSeedbedProfileId))
                .thenReturn(researchSeedbedProfile);
        when(studentProfileServicePort.existsByUserIdAndAcademicPeriodId(studentUserId, 1L))
                .thenReturn(true);
        when(studentProfileServicePort.findByUserIdAndAcademicPeriodId(studentUserId, 1L))
                .thenReturn(Optional.of(existingStudentProfile));

        // Act
        ResearchSeedbedStudentProfile result = helper.verifyStudentHasAProfile(input);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getStudentProfileId()).isEqualTo(50L);
        assertThat(result.getResearchSeedbedProfileId()).isEqualTo(researchSeedbedProfileId);

        verify(researchSeedbedProfileServicePort, times(1)).findById(researchSeedbedProfileId);
        verify(studentProfileServicePort, times(1)).existsByUserIdAndAcademicPeriodId(studentUserId, 1L);
        verify(studentProfileServicePort, times(1)).findByUserIdAndAcademicPeriodId(studentUserId, 1L);
        verify(userServicePort, never()).findById(anyLong());
        verify(studentProfileServicePort, never()).createStudentProfileFromIntegraData(anyString(), anyLong(), any());
    }

    @Test
    void verifyStudentHasAProfile_StudentDoesNotHaveProfile_CreatesNewProfile() {
        // Arrange
        Long studentUserId = 100L;
        Long researchSeedbedProfileId = 10L;

        ResearchSeedbedStudentProfile input = new ResearchSeedbedStudentProfile(
                null,
                researchSeedbedProfileId,
                studentUserId,
                true,
                false
        );

        StudentProfile newStudentProfile = new StudentProfile();
        newStudentProfile.setId(75L);
        newStudentProfile.setUserId(studentUserId);
        newStudentProfile.setAcademicPeriodId(1L);

        when(researchSeedbedProfileServicePort.findById(researchSeedbedProfileId))
                .thenReturn(researchSeedbedProfile);
        when(studentProfileServicePort.existsByUserIdAndAcademicPeriodId(studentUserId, 1L))
                .thenReturn(false);
        when(userServicePort.findById(studentUserId))
                .thenReturn(studentUser);
        when(studentProfileServicePort.createStudentProfileFromIntegraData(
                studentUser.getIdentificationNumber(), 1L, studentUser))
                .thenReturn(newStudentProfile);

        // Act
        ResearchSeedbedStudentProfile result = helper.verifyStudentHasAProfile(input);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getStudentProfileId()).isEqualTo(75L);
        assertThat(result.getResearchSeedbedProfileId()).isEqualTo(researchSeedbedProfileId);

        verify(researchSeedbedProfileServicePort, times(1)).findById(researchSeedbedProfileId);
        verify(studentProfileServicePort, times(1)).existsByUserIdAndAcademicPeriodId(studentUserId, 1L);
        verify(userServicePort, times(1)).findById(studentUserId);
        verify(studentProfileServicePort, times(1)).createStudentProfileFromIntegraData(
                studentUser.getIdentificationNumber(), 1L, studentUser);
    }

    @Test
    void verifyStudentHasAProfile_ProfileExistsButNotFound_ReturnsOriginalStudentProfileId() {
        // Arrange
        Long studentUserId = 100L;
        Long researchSeedbedProfileId = 10L;

        ResearchSeedbedStudentProfile input = new ResearchSeedbedStudentProfile(
                null,
                researchSeedbedProfileId,
                studentUserId,
                true,
                false
        );

        when(researchSeedbedProfileServicePort.findById(researchSeedbedProfileId))
                .thenReturn(researchSeedbedProfile);
        when(studentProfileServicePort.existsByUserIdAndAcademicPeriodId(studentUserId, 1L))
                .thenReturn(true);
        when(studentProfileServicePort.findByUserIdAndAcademicPeriodId(studentUserId, 1L))
                .thenReturn(Optional.empty());

        // Act
        ResearchSeedbedStudentProfile result = helper.verifyStudentHasAProfile(input);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getStudentProfileId()).isEqualTo(studentUserId); // Unchanged because Optional.empty()

        verify(studentProfileServicePort, times(1)).findByUserIdAndAcademicPeriodId(studentUserId, 1L);
        verify(userServicePort, never()).findById(anyLong());
    }

    @Test
    void verifyAcademicPeriodIsCurrentStatus_PeriodIsCurrent_ReturnsFalse() {
        // Arrange
        Long academicPeriodId = 1L;
        AcademicPeriod currentPeriod = new AcademicPeriod(
                academicPeriodId,
                "2024-1",
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 6, 30),
                true,   // isCurrent = true
                true
        );

        when(academicPeriodServicePort.findById(academicPeriodId))
                .thenReturn(currentPeriod);

        // Act
        boolean result = helper.verifyAcademicPeriodIsCurrentStatus(academicPeriodId);

        // Assert
        assertThat(result).isFalse(); // Returns !isCurrent(), so false when current
        verify(academicPeriodServicePort, times(1)).findById(academicPeriodId);
    }

    @Test
    void verifyAcademicPeriodIsCurrentStatus_PeriodIsNotCurrent_ReturnsTrue() {
        // Arrange
        Long academicPeriodId = 2L;
        AcademicPeriod pastPeriod = new AcademicPeriod(
                academicPeriodId,
                "2023-2",
                LocalDate.of(2023, 7, 1),
                LocalDate.of(2023, 12, 31),
                false,  // isCurrent = false
                true
        );

        when(academicPeriodServicePort.findById(academicPeriodId))
                .thenReturn(pastPeriod);

        // Act
        boolean result = helper.verifyAcademicPeriodIsCurrentStatus(academicPeriodId);

        // Assert
        assertThat(result).isTrue(); // Returns !isCurrent(), so true when not current
        verify(academicPeriodServicePort, times(1)).findById(academicPeriodId);
    }
}
