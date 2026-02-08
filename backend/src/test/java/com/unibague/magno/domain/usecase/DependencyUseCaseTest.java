package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.exception.dependency.DependencyNotFoundException;
import com.unibague.magno.domain.model.Dependency;
import com.unibague.magno.domain.model.integra.IntegraDependency;
import com.unibague.magno.domain.spi.IDependencyPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DependencyUseCaseTest {

    @Mock
    private IDependencyPersistencePort dependencyPersistencePort;
    @Mock
    private IIntegraServicePort integraServicePort;

    private DependencyUseCase dependencyUseCase;
    private Dependency dependency;
    private IntegraDependency integraDependency;

    @BeforeEach
    void setUp() {
        dependencyUseCase = new DependencyUseCase(
                dependencyPersistencePort,
                integraServicePort
        );

        dependency = new Dependency();
        dependency.setId(1L);
        dependency.setName("Facultad de Ingeniería");

        integraDependency = new IntegraDependency();
        integraDependency.setDepName("Facultad de Ingeniería");
    }

    @Test
    void findById_DependencyExists_ReturnsDependency() {
        // Arrange
        when(dependencyPersistencePort.findById(1L)).thenReturn(Optional.of(dependency));

        // Act
        Dependency result = dependencyUseCase.findById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Facultad de Ingeniería");
        verify(dependencyPersistencePort, times(1)).findById(1L);
    }

    @Test
    void findById_DependencyDoesNotExist_ThrowsDependencyNotFoundException() {
        // Arrange
        when(dependencyPersistencePort.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> dependencyUseCase.findById(99L))
                .isInstanceOf(DependencyNotFoundException.class)
                .hasMessage("Dependencia con ID 99 no encontrada");
        verify(dependencyPersistencePort, times(1)).findById(99L);
    }

    @Test
    void save_ValidDependency_SavesSuccessfully() {
        // Arrange
        when(dependencyPersistencePort.save(dependency)).thenReturn(dependency);

        // Act
        Dependency result = dependencyUseCase.save(dependency);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Facultad de Ingeniería");
        verify(dependencyPersistencePort, times(1)).save(dependency);
    }

    @Test
    void update_DependencyExists_UpdatesSuccessfully() {
        // Arrange
        when(dependencyPersistencePort.findById(1L)).thenReturn(Optional.of(dependency));
        when(dependencyPersistencePort.update(1L, dependency)).thenReturn(dependency);

        // Act
        Dependency result = dependencyUseCase.update(1L, dependency);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(dependencyPersistencePort, times(1)).findById(1L);
        verify(dependencyPersistencePort, times(1)).update(1L, dependency);
    }

    @Test
    void update_DependencyDoesNotExist_ThrowsDependencyNotFoundException() {
        // Arrange
        when(dependencyPersistencePort.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> dependencyUseCase.update(99L, dependency))
                .isInstanceOf(DependencyNotFoundException.class)
                .hasMessage("No se pudo actualizar la dependencia con ID 99 porque no existe");
        verify(dependencyPersistencePort, times(1)).findById(99L);
        verify(dependencyPersistencePort, never()).update(anyLong(), any());
    }

    @Test
    void deleteById_DependencyExists_DeletesSuccessfully() {
        // Arrange
        when(dependencyPersistencePort.findById(1L)).thenReturn(Optional.of(dependency));

        // Act
        dependencyUseCase.deleteById(1L);

        // Assert
        verify(dependencyPersistencePort, times(1)).findById(1L);
        verify(dependencyPersistencePort, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_DependencyDoesNotExist_ThrowsDependencyNotFoundException() {
        // Arrange
        when(dependencyPersistencePort.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> dependencyUseCase.deleteById(99L))
                .isInstanceOf(DependencyNotFoundException.class)
                .hasMessage("No se pudo eliminar la dependencia con ID 99 porque no existe");
        verify(dependencyPersistencePort, times(1)).findById(99L);
        verify(dependencyPersistencePort, never()).deleteById(anyLong());
    }

    @Test
    void findAll_ReturnsAllDependencies() {
        // Arrange
        Dependency dependency2 = new Dependency();
        dependency2.setId(2L);
        dependency2.setName("Facultad de Ciencias");

        List<Dependency> dependencies = Arrays.asList(dependency, dependency2);
        when(dependencyPersistencePort.findAll()).thenReturn(dependencies);

        // Act
        List<Dependency> result = dependencyUseCase.findAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Facultad de Ingeniería");
        assertThat(result.get(1).getName()).isEqualTo("Facultad de Ciencias");
        verify(dependencyPersistencePort, times(1)).findAll();
    }

    @Test
    void findAll_NoDependencies_ReturnsEmptyList() {
        // Arrange
        when(dependencyPersistencePort.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<Dependency> result = dependencyUseCase.findAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(dependencyPersistencePort, times(1)).findAll();
    }

    @Test
    void findByName_DependencyExistsLocally_ReturnsDependency() {
        // Arrange
        when(dependencyPersistencePort.findByName("Facultad de Ingeniería"))
                .thenReturn(Optional.of(dependency));

        // Act
        Dependency result = dependencyUseCase.findByName("Facultad de Ingeniería");

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Facultad de Ingeniería");
        verify(dependencyPersistencePort, times(1)).findByName("Facultad de Ingeniería");
        verify(integraServicePort, never()).getIntegraDependencyByDependencyName(anyString());
        verify(dependencyPersistencePort, never()).save(any());
    }

    @Test
    void findByName_DependencyNotFoundLocallyButExistsInIntegra_CreatesAndReturnsDependency() {
        // Arrange
        when(dependencyPersistencePort.findByName("Facultad de Ciencias"))
                .thenReturn(Optional.empty());
        when(integraServicePort.getIntegraDependencyByDependencyName("Facultad de Ciencias"))
                .thenReturn(integraDependency);

        Dependency newDependency = new Dependency();
        newDependency.setId(2L);
        newDependency.setName("Facultad de Ingeniería");

        when(dependencyPersistencePort.save(any(Dependency.class)))
                .thenReturn(newDependency);

        // Act
        Dependency result = dependencyUseCase.findByName("Facultad de Ciencias");

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Facultad de Ingeniería");
        verify(dependencyPersistencePort, times(1)).findByName("Facultad de Ciencias");
        verify(integraServicePort, times(1)).getIntegraDependencyByDependencyName("Facultad de Ciencias");
        verify(dependencyPersistencePort, times(1)).save(any(Dependency.class));
    }

    @Test
    void findByName_NullName_ThrowsIllegalArgumentException() {
        // Act & Assert
        assertThatThrownBy(() -> dependencyUseCase.findByName(null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El nombre no puede ser nulo o vacío");
        verify(dependencyPersistencePort, never()).findByName(anyString());
    }

    @Test
    void findByName_EmptyName_ThrowsIllegalArgumentException() {
        // Act & Assert
        assertThatThrownBy(() -> dependencyUseCase.findByName(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El nombre no puede ser nulo o vacío");
        verify(dependencyPersistencePort, never()).findByName(anyString());
    }

    @Test
    void findByName_BlankName_ThrowsIllegalArgumentException() {
        // Act & Assert
        assertThatThrownBy(() -> dependencyUseCase.findByName("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El nombre no puede ser nulo o vacío");
        verify(dependencyPersistencePort, never()).findByName(anyString());
    }

    @Test
    void findByNameOptional_DependencyExists_ReturnsOptionalWithDependency() {
        // Arrange
        when(dependencyPersistencePort.findByName("Facultad de Ingeniería"))
                .thenReturn(Optional.of(dependency));

        // Act
        Optional<Dependency> result = dependencyUseCase.findByNameOptional("Facultad de Ingeniería");

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(1L);
        assertThat(result.get().getName()).isEqualTo("Facultad de Ingeniería");
        verify(dependencyPersistencePort, times(1)).findByName("Facultad de Ingeniería");
    }

    @Test
    void findByNameOptional_DependencyDoesNotExist_ReturnsEmptyOptional() {
        // Arrange
        when(dependencyPersistencePort.findByName("Facultad Inexistente"))
                .thenReturn(Optional.empty());

        // Act
        Optional<Dependency> result = dependencyUseCase.findByNameOptional("Facultad Inexistente");

        // Assert
        assertThat(result).isEmpty();
        verify(dependencyPersistencePort, times(1)).findByName("Facultad Inexistente");
    }
}