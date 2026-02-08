package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.exception.investigationgroup.InvestigationGroupAlreadyExistsException;
import com.unibague.magno.domain.exception.investigationgroup.InvestigationGroupHasAssociatedProfilesException;
import com.unibague.magno.domain.exception.investigationgroup.InvestigationGroupNotFoundException;
import com.unibague.magno.domain.model.InvestigationGroup;
import com.unibague.magno.domain.spi.IInvestigationGroupPersistencePort;
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
class InvestigationGroupUseCaseTest {

    @Mock
    private IInvestigationGroupPersistencePort investigationGroupPersistencePort;

    private InvestigationGroupUseCase investigationGroupUseCase;
    private InvestigationGroup investigationGroup;

    @BeforeEach
    void setUp() {
        investigationGroupUseCase = new InvestigationGroupUseCase(investigationGroupPersistencePort);

        investigationGroup = new InvestigationGroup();
        investigationGroup.setId(1L);
        investigationGroup.setName("Grupo de IA");
    }

    @Test
    void findById_InvestigationGroupExists_ReturnsInvestigationGroup() {
        // Arrange
        when(investigationGroupPersistencePort.findById(1L)).thenReturn(Optional.of(investigationGroup));

        // Act
        InvestigationGroup result = investigationGroupUseCase.findById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Grupo de IA");
        verify(investigationGroupPersistencePort, times(1)).findById(1L);
    }

    @Test
    void findById_InvestigationGroupDoesNotExist_ThrowsInvestigationGroupNotFoundException() {
        // Arrange
        when(investigationGroupPersistencePort.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> investigationGroupUseCase.findById(99L))
                .isInstanceOf(InvestigationGroupNotFoundException.class)
                .hasMessage("InvestigationGroup with ID 99 not found");
        verify(investigationGroupPersistencePort, times(1)).findById(99L);
    }

    @Test
    void save_ValidInvestigationGroup_SavesSuccessfully() {
        // Arrange
        when(investigationGroupPersistencePort.findAll()).thenReturn(Collections.emptyList());
        when(investigationGroupPersistencePort.save(investigationGroup)).thenReturn(investigationGroup);

        // Act
        InvestigationGroup result = investigationGroupUseCase.save(investigationGroup);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Grupo de IA");
        verify(investigationGroupPersistencePort, times(1)).findAll();
        verify(investigationGroupPersistencePort, times(1)).save(investigationGroup);
    }

    @Test
    void save_InvestigationGroupWithDuplicateName_ThrowsInvestigationGroupAlreadyExistsException() {
        // Arrange
        InvestigationGroup existingGroup = new InvestigationGroup();
        existingGroup.setId(2L);
        existingGroup.setName("Grupo de IA");

        when(investigationGroupPersistencePort.findAll()).thenReturn(Arrays.asList(existingGroup));

        // Act & Assert
        assertThatThrownBy(() -> investigationGroupUseCase.save(investigationGroup))
                .isInstanceOf(InvestigationGroupAlreadyExistsException.class)
                .hasMessage("Ya existe un grupo de investigación con el nombre 'Grupo de IA'");
        verify(investigationGroupPersistencePort, times(1)).findAll();
        verify(investigationGroupPersistencePort, never()).save(any());
    }

    @Test
    void save_InvestigationGroupWithDuplicateNameDifferentCase_ThrowsInvestigationGroupAlreadyExistsException() {
        // Arrange
        InvestigationGroup existingGroup = new InvestigationGroup();
        existingGroup.setId(2L);
        existingGroup.setName("GRUPO DE IA");

        InvestigationGroup newGroup = new InvestigationGroup();
        newGroup.setName("grupo de ia");

        when(investigationGroupPersistencePort.findAll()).thenReturn(List.of(existingGroup));

        // Act & Assert
        assertThatThrownBy(() -> investigationGroupUseCase.save(newGroup))
                .isInstanceOf(InvestigationGroupAlreadyExistsException.class)
                .hasMessage("Ya existe un grupo de investigación con el nombre 'grupo de ia'");
        verify(investigationGroupPersistencePort, times(1)).findAll();
        verify(investigationGroupPersistencePort, never()).save(any());
    }

    @Test
    void save_InvestigationGroupWithDuplicateNameWithSpaces_ThrowsInvestigationGroupAlreadyExistsException() {
        // Arrange
        InvestigationGroup existingGroup = new InvestigationGroup();
        existingGroup.setId(2L);
        existingGroup.setName("Grupo de IA");

        InvestigationGroup newGroup = new InvestigationGroup();
        newGroup.setName("  Grupo de IA  ");

        when(investigationGroupPersistencePort.findAll()).thenReturn(Arrays.asList(existingGroup));

        // Act & Assert
        assertThatThrownBy(() -> investigationGroupUseCase.save(newGroup))
                .isInstanceOf(InvestigationGroupAlreadyExistsException.class)
                .hasMessage("Ya existe un grupo de investigación con el nombre 'Grupo de IA'");
        verify(investigationGroupPersistencePort, times(1)).findAll();
        verify(investigationGroupPersistencePort, never()).save(any());
    }

    @Test
    void update_InvestigationGroupExists_UpdatesSuccessfully() {
        // Arrange
        when(investigationGroupPersistencePort.findById(1L)).thenReturn(Optional.of(investigationGroup));
        when(investigationGroupPersistencePort.findAll()).thenReturn(Collections.emptyList());
        when(investigationGroupPersistencePort.update(1L, investigationGroup)).thenReturn(investigationGroup);

        // Act
        InvestigationGroup result = investigationGroupUseCase.update(1L, investigationGroup);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(investigationGroupPersistencePort, times(1)).findById(1L);
        verify(investigationGroupPersistencePort, times(1)).findAll();
        verify(investigationGroupPersistencePort, times(1)).update(1L, investigationGroup);
    }

    @Test
    void update_InvestigationGroupDoesNotExist_ThrowsInvestigationGroupNotFoundException() {
        // Arrange
        when(investigationGroupPersistencePort.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> investigationGroupUseCase.update(99L, investigationGroup))
                .isInstanceOf(InvestigationGroupNotFoundException.class)
                .hasMessage("InvestigationGroup with ID 99 could not be updated because it does not exist");
        verify(investigationGroupPersistencePort, times(1)).findById(99L);
        verify(investigationGroupPersistencePort, never()).update(anyLong(), any());
    }

    @Test
    void update_InvestigationGroupWithDuplicateName_ThrowsInvestigationGroupAlreadyExistsException() {
        // Arrange
        InvestigationGroup existingGroup = new InvestigationGroup();
        existingGroup.setId(2L);
        existingGroup.setName("Grupo de IA");

        when(investigationGroupPersistencePort.findById(1L)).thenReturn(Optional.of(investigationGroup));
        when(investigationGroupPersistencePort.findAll()).thenReturn(Arrays.asList(existingGroup));

        // Act & Assert
        assertThatThrownBy(() -> investigationGroupUseCase.update(1L, investigationGroup))
                .isInstanceOf(InvestigationGroupAlreadyExistsException.class)
                .hasMessage("Ya existe un grupo de investigación con el nombre 'Grupo de IA'");
        verify(investigationGroupPersistencePort, times(1)).findById(1L);
        verify(investigationGroupPersistencePort, times(1)).findAll();
        verify(investigationGroupPersistencePort, never()).update(anyLong(), any());
    }

    @Test
    void deleteById_InvestigationGroupExistsWithoutProfiles_DeletesSuccessfully() {
        // Arrange
        when(investigationGroupPersistencePort.findById(1L)).thenReturn(Optional.of(investigationGroup));
        when(investigationGroupPersistencePort.findInvestigationGroupsWithAssociatedProfiles())
                .thenReturn(Collections.emptyList());

        // Act
        investigationGroupUseCase.deleteById(1L);

        // Assert
        verify(investigationGroupPersistencePort, times(2)).findById(1L);
        verify(investigationGroupPersistencePort, times(1)).findInvestigationGroupsWithAssociatedProfiles();
        verify(investigationGroupPersistencePort, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_InvestigationGroupDoesNotExist_ThrowsInvestigationGroupNotFoundException() {
        // Arrange
        when(investigationGroupPersistencePort.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> investigationGroupUseCase.deleteById(99L))
                .isInstanceOf(InvestigationGroupNotFoundException.class)
                .hasMessage("InvestigationGroup with ID 99 could not be deleted because it does not exist");
        verify(investigationGroupPersistencePort, times(1)).findById(99L);
        verify(investigationGroupPersistencePort, never()).deleteById(anyLong());
    }

    @Test
    void deleteById_InvestigationGroupHasAssociatedProfiles_ThrowsInvestigationGroupHasAssociatedProfilesException() {
        // Arrange
        when(investigationGroupPersistencePort.findById(1L)).thenReturn(Optional.of(investigationGroup));
        when(investigationGroupPersistencePort.findInvestigationGroupsWithAssociatedProfiles())
                .thenReturn(Collections.singletonList(investigationGroup));

        // Act & Assert
        assertThatThrownBy(() -> investigationGroupUseCase.deleteById(1L))
                .isInstanceOf(InvestigationGroupHasAssociatedProfilesException.class)
                .hasMessage("El grupo de investigacion Grupo de IA no puede ser eliminado porque tiene perfiles asociados");
        verify(investigationGroupPersistencePort, times(2)).findById(1L);
        verify(investigationGroupPersistencePort, times(1)).findInvestigationGroupsWithAssociatedProfiles();
        verify(investigationGroupPersistencePort, never()).deleteById(anyLong());
    }

    @Test
    void findAll_ReturnsAllInvestigationGroups() {
        // Arrange
        InvestigationGroup group2 = new InvestigationGroup();
        group2.setId(2L);
        group2.setName("Grupo de Robótica");

        List<InvestigationGroup> groups = Arrays.asList(investigationGroup, group2);
        when(investigationGroupPersistencePort.findAll()).thenReturn(groups);

        // Act
        List<InvestigationGroup> result = investigationGroupUseCase.findAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Grupo de IA");
        assertThat(result.get(1).getName()).isEqualTo("Grupo de Robótica");
        verify(investigationGroupPersistencePort, times(1)).findAll();
    }

    @Test
    void findInvestigationGroupsWithAssociatedProfiles_ReturnsGroupsWithProfiles() {
        // Arrange
        List<InvestigationGroup> groupsWithProfiles = Arrays.asList(investigationGroup);
        when(investigationGroupPersistencePort.findInvestigationGroupsWithAssociatedProfiles())
                .thenReturn(groupsWithProfiles);

        // Act
        List<InvestigationGroup> result = investigationGroupUseCase.findInvestigationGroupsWithAssociatedProfiles();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        verify(investigationGroupPersistencePort, times(1)).findInvestigationGroupsWithAssociatedProfiles();
    }

    @Test
    void findInvestigationGroupsWithAssociatedProfiles_NoGroupsWithProfiles_ReturnsEmptyList() {
        // Arrange
        when(investigationGroupPersistencePort.findInvestigationGroupsWithAssociatedProfiles())
                .thenReturn(Collections.emptyList());

        // Act
        List<InvestigationGroup> result = investigationGroupUseCase.findInvestigationGroupsWithAssociatedProfiles();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(investigationGroupPersistencePort, times(1)).findInvestigationGroupsWithAssociatedProfiles();
    }
}