package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.exception.researchseedbed.ResearchSeedbedAlreadyExistsException;
import com.unibague.magno.domain.exception.researchseedbed.ResearchSeedbedHasAssociatedProfilesException;
import com.unibague.magno.domain.exception.researchseedbed.ResearchSeedbedNotFoundException;
import com.unibague.magno.domain.model.ResearchSeedbed;
import com.unibague.magno.domain.spi.IResearchSeedbedPersistencePort;
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
class ResearchSeedbedUseCaseTest {

    @Mock
    private IResearchSeedbedPersistencePort researchSeedbedPersistencePort;

    private ResearchSeedbedUseCase researchSeedbedUseCase;
    private ResearchSeedbed researchSeedbed;

    @BeforeEach
    void setUp() {
        researchSeedbedUseCase = new ResearchSeedbedUseCase(researchSeedbedPersistencePort);

        researchSeedbed = new ResearchSeedbed();
        researchSeedbed.setId(1L);
        researchSeedbed.setName("Semillero de IA");
    }

    @Test
    void findById_ResearchSeedbedExists_ReturnsResearchSeedbed() {
        // Arrange
        when(researchSeedbedPersistencePort.findById(1L)).thenReturn(Optional.of(researchSeedbed));

        // Act
        ResearchSeedbed result = researchSeedbedUseCase.findById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Semillero de IA");
        verify(researchSeedbedPersistencePort, times(1)).findById(1L);
    }

    @Test
    void findById_ResearchSeedbedDoesNotExist_ThrowsResearchSeedbedNotFoundException() {
        // Arrange
        when(researchSeedbedPersistencePort.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> researchSeedbedUseCase.findById(99L))
                .isInstanceOf(ResearchSeedbedNotFoundException.class)
                .hasMessage("Semillero de investigación con ID 99 no encontrado");
        verify(researchSeedbedPersistencePort, times(1)).findById(99L);
    }

    @Test
    void save_ValidResearchSeedbed_SavesSuccessfully() {
        // Arrange
        when(researchSeedbedPersistencePort.findAll()).thenReturn(Collections.emptyList());
        when(researchSeedbedPersistencePort.save(researchSeedbed)).thenReturn(researchSeedbed);

        // Act
        ResearchSeedbed result = researchSeedbedUseCase.save(researchSeedbed);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Semillero de IA");
        verify(researchSeedbedPersistencePort, times(1)).findAll();
        verify(researchSeedbedPersistencePort, times(1)).save(researchSeedbed);
    }

    @Test
    void save_ResearchSeedbedWithDuplicateName_ThrowsResearchSeedbedAlreadyExistsException() {
        // Arrange
        ResearchSeedbed existingSeedbed = new ResearchSeedbed();
        existingSeedbed.setId(2L);
        existingSeedbed.setName("Semillero de IA");

        when(researchSeedbedPersistencePort.findAll()).thenReturn(Arrays.asList(existingSeedbed));

        // Act & Assert
        assertThatThrownBy(() -> researchSeedbedUseCase.save(researchSeedbed))
                .isInstanceOf(ResearchSeedbedAlreadyExistsException.class)
                .hasMessage("Ya existe un semillero de investigación con el nombre 'Semillero de IA'");
        verify(researchSeedbedPersistencePort, times(1)).findAll();
        verify(researchSeedbedPersistencePort, never()).save(any());
    }

    @Test
    void save_ResearchSeedbedWithDuplicateNameDifferentCase_ThrowsResearchSeedbedAlreadyExistsException() {
        // Arrange
        ResearchSeedbed existingSeedbed = new ResearchSeedbed();
        existingSeedbed.setId(2L);
        existingSeedbed.setName("SEMILLERO DE IA");

        ResearchSeedbed newSeedbed = new ResearchSeedbed();
        newSeedbed.setName("semillero de ia");

        when(researchSeedbedPersistencePort.findAll()).thenReturn(Arrays.asList(existingSeedbed));

        // Act & Assert
        assertThatThrownBy(() -> researchSeedbedUseCase.save(newSeedbed))
                .isInstanceOf(ResearchSeedbedAlreadyExistsException.class)
                .hasMessage("Ya existe un semillero de investigación con el nombre 'semillero de ia'");
        verify(researchSeedbedPersistencePort, times(1)).findAll();
        verify(researchSeedbedPersistencePort, never()).save(any());
    }

    @Test
    void save_ResearchSeedbedWithDuplicateNameWithSpaces_ThrowsResearchSeedbedAlreadyExistsException() {
        // Arrange
        ResearchSeedbed existingSeedbed = new ResearchSeedbed();
        existingSeedbed.setId(2L);
        existingSeedbed.setName("Semillero de IA");

        ResearchSeedbed newSeedbed = new ResearchSeedbed();
        newSeedbed.setName("  Semillero de IA  ");

        when(researchSeedbedPersistencePort.findAll()).thenReturn(Arrays.asList(existingSeedbed));

        // Act & Assert
        assertThatThrownBy(() -> researchSeedbedUseCase.save(newSeedbed))
                .isInstanceOf(ResearchSeedbedAlreadyExistsException.class)
                .hasMessage("Ya existe un semillero de investigación con el nombre 'Semillero de IA'");
        verify(researchSeedbedPersistencePort, times(1)).findAll();
        verify(researchSeedbedPersistencePort, never()).save(any());
    }

    @Test
    void update_ResearchSeedbedExists_UpdatesSuccessfully() {
        // Arrange
        when(researchSeedbedPersistencePort.findById(1L)).thenReturn(Optional.of(researchSeedbed));
        when(researchSeedbedPersistencePort.findAll()).thenReturn(Collections.emptyList());
        when(researchSeedbedPersistencePort.update(1L, researchSeedbed)).thenReturn(researchSeedbed);

        // Act
        ResearchSeedbed result = researchSeedbedUseCase.update(1L, researchSeedbed);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(researchSeedbedPersistencePort, times(1)).findById(1L);
        verify(researchSeedbedPersistencePort, times(1)).findAll();
        verify(researchSeedbedPersistencePort, times(1)).update(1L, researchSeedbed);
    }

    @Test
    void update_ResearchSeedbedDoesNotExist_ThrowsResearchSeedbedNotFoundException() {
        // Arrange
        when(researchSeedbedPersistencePort.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> researchSeedbedUseCase.update(99L, researchSeedbed))
                .isInstanceOf(ResearchSeedbedNotFoundException.class)
                .hasMessage("No se pudo actualizar el semillero de investigación con ID 99 porque no existe");
        verify(researchSeedbedPersistencePort, times(1)).findById(99L);
        verify(researchSeedbedPersistencePort, never()).update(anyLong(), any());
    }

    @Test
    void update_ResearchSeedbedWithDuplicateName_ThrowsResearchSeedbedAlreadyExistsException() {
        // Arrange
        ResearchSeedbed existingSeedbed = new ResearchSeedbed();
        existingSeedbed.setId(2L);
        existingSeedbed.setName("Semillero de IA");

        when(researchSeedbedPersistencePort.findById(1L)).thenReturn(Optional.of(researchSeedbed));
        when(researchSeedbedPersistencePort.findAll()).thenReturn(Arrays.asList(existingSeedbed));

        // Act & Assert
        assertThatThrownBy(() -> researchSeedbedUseCase.update(1L, researchSeedbed))
                .isInstanceOf(ResearchSeedbedAlreadyExistsException.class)
                .hasMessage("Ya existe un semillero de investigación con el nombre 'Semillero de IA'");
        verify(researchSeedbedPersistencePort, times(1)).findById(1L);
        verify(researchSeedbedPersistencePort, times(1)).findAll();
        verify(researchSeedbedPersistencePort, never()).update(anyLong(), any());
    }

    @Test
    void deleteById_ResearchSeedbedExistsWithoutProfiles_DeletesSuccessfully() {
        // Arrange
        when(researchSeedbedPersistencePort.findById(1L)).thenReturn(Optional.of(researchSeedbed));
        when(researchSeedbedPersistencePort.findResearchSeedbedsWithAssociatedProfiles())
                .thenReturn(Collections.emptyList());

        // Act
        researchSeedbedUseCase.deleteById(1L);

        // Assert
        verify(researchSeedbedPersistencePort, times(2)).findById(1L);
        verify(researchSeedbedPersistencePort, times(1)).findResearchSeedbedsWithAssociatedProfiles();
        verify(researchSeedbedPersistencePort, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_ResearchSeedbedDoesNotExist_ThrowsResearchSeedbedNotFoundException() {
        // Arrange
        when(researchSeedbedPersistencePort.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> researchSeedbedUseCase.deleteById(99L))
                .isInstanceOf(ResearchSeedbedNotFoundException.class)
                .hasMessage("No se pudo eliminar el semillero de investigación con ID 99 porque no existe");
        verify(researchSeedbedPersistencePort, times(1)).findById(99L);
        verify(researchSeedbedPersistencePort, never()).deleteById(anyLong());
    }

    @Test
    void deleteById_ResearchSeedbedHasAssociatedProfiles_ThrowsResearchSeedbedHasAssociatedProfilesException() {
        // Arrange
        when(researchSeedbedPersistencePort.findById(1L)).thenReturn(Optional.of(researchSeedbed));
        when(researchSeedbedPersistencePort.findResearchSeedbedsWithAssociatedProfiles())
                .thenReturn(Arrays.asList(researchSeedbed));

        // Act & Assert
        assertThatThrownBy(() -> researchSeedbedUseCase.deleteById(1L))
                .isInstanceOf(ResearchSeedbedHasAssociatedProfilesException.class)
                .hasMessage("No se pudo eliminar el semillero de investigación Semillero de IA porque tiene perfiles asociados");
        verify(researchSeedbedPersistencePort, times(2)).findById(1L);
        verify(researchSeedbedPersistencePort, times(1)).findResearchSeedbedsWithAssociatedProfiles();
        verify(researchSeedbedPersistencePort, never()).deleteById(anyLong());
    }

    @Test
    void findAll_ReturnsAllResearchSeedbeds() {
        // Arrange
        ResearchSeedbed seedbed2 = new ResearchSeedbed();
        seedbed2.setId(2L);
        seedbed2.setName("Semillero de Robótica");

        List<ResearchSeedbed> seedbeds = Arrays.asList(researchSeedbed, seedbed2);
        when(researchSeedbedPersistencePort.findAll()).thenReturn(seedbeds);

        // Act
        List<ResearchSeedbed> result = researchSeedbedUseCase.findAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Semillero de IA");
        assertThat(result.get(1).getName()).isEqualTo("Semillero de Robótica");
        verify(researchSeedbedPersistencePort, times(1)).findAll();
    }

    @Test
    void findResearchSeedbedsByUserId_ReturnsUserSeedbeds() {
        // Arrange
        List<ResearchSeedbed> userSeedbeds = Arrays.asList(researchSeedbed);
        when(researchSeedbedPersistencePort.findResearchSeedbedsByUserId(1L))
                .thenReturn(userSeedbeds);

        // Act
        List<ResearchSeedbed> result = researchSeedbedUseCase.findResearchSeedbedsByUserId(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        verify(researchSeedbedPersistencePort, times(1)).findResearchSeedbedsByUserId(1L);
    }

    @Test
    void findResearchSeedbedsByUserId_UserHasNoSeedbeds_ReturnsEmptyList() {
        // Arrange
        when(researchSeedbedPersistencePort.findResearchSeedbedsByUserId(99L))
                .thenReturn(Collections.emptyList());

        // Act
        List<ResearchSeedbed> result = researchSeedbedUseCase.findResearchSeedbedsByUserId(99L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(researchSeedbedPersistencePort, times(1)).findResearchSeedbedsByUserId(99L);
    }

    @Test
    void findResearchSeedbedsWithAssociatedProfiles_ReturnsSeedbedsWithProfiles() {
        // Arrange
        List<ResearchSeedbed> seedbedsWithProfiles = Arrays.asList(researchSeedbed);
        when(researchSeedbedPersistencePort.findResearchSeedbedsWithAssociatedProfiles())
                .thenReturn(seedbedsWithProfiles);

        // Act
        List<ResearchSeedbed> result = researchSeedbedUseCase.findResearchSeedbedsWithAssociatedProfiles();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        verify(researchSeedbedPersistencePort, times(1)).findResearchSeedbedsWithAssociatedProfiles();
    }

    @Test
    void findResearchSeedbedsWithAssociatedProfiles_NoSeedbedsWithProfiles_ReturnsEmptyList() {
        // Arrange
        when(researchSeedbedPersistencePort.findResearchSeedbedsWithAssociatedProfiles())
                .thenReturn(Collections.emptyList());

        // Act
        List<ResearchSeedbed> result = researchSeedbedUseCase.findResearchSeedbedsWithAssociatedProfiles();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(researchSeedbedPersistencePort, times(1)).findResearchSeedbedsWithAssociatedProfiles();
    }
}