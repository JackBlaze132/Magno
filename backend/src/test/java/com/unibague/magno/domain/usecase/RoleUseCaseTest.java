package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.exception.role.RoleNotFoundException;
import com.unibague.magno.domain.model.Role;
import com.unibague.magno.domain.model.enums.SeedbedRole;
import com.unibague.magno.domain.spi.IRolePersistencePort;
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
class RoleUseCaseTest {

    @Mock
    private IRolePersistencePort rolePersistencePort;

    private RoleUseCase roleUseCase;
    private Role role;

    @BeforeEach
    void setUp() {
        roleUseCase = new RoleUseCase(rolePersistencePort);

        role = new Role();
        role.setId(1L);
        role.setName(SeedbedRole.ESTUDIANTE);
    }

    @Test
    void findById_RoleExists_ReturnsRole() {
        // Arrange
        when(rolePersistencePort.findById(1L)).thenReturn(Optional.of(role));

        // Act
        Role result = roleUseCase.findById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo(SeedbedRole.ESTUDIANTE);
        verify(rolePersistencePort, times(1)).findById(1L);
    }

    @Test
    void findById_RoleDoesNotExist_ThrowsRoleNotFoundException() {
        // Arrange
        when(rolePersistencePort.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> roleUseCase.findById(99L))
                .isInstanceOf(RoleNotFoundException.class)
                .hasMessage("Rol con ID 99 no encontrado");
        verify(rolePersistencePort, times(1)).findById(99L);
    }

    @Test
    void findByName_RoleExists_ReturnsRole() {
        // Arrange
        when(rolePersistencePort.findByName(SeedbedRole.ESTUDIANTE))
                .thenReturn(Optional.of(role));

        // Act
        Role result = roleUseCase.findByName(SeedbedRole.ESTUDIANTE);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo(SeedbedRole.ESTUDIANTE);
        verify(rolePersistencePort, times(1)).findByName(SeedbedRole.ESTUDIANTE);
    }

    @Test
    void findByName_RoleDoesNotExist_ThrowsRoleNotFoundException() {
        // Arrange
        when(rolePersistencePort.findByName(SeedbedRole.DIRI))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> roleUseCase.findByName(SeedbedRole.DIRI))
                .isInstanceOf(RoleNotFoundException.class)
                .hasMessage("Rol con nombre DIRI no encontrado");
        verify(rolePersistencePort, times(1)).findByName(SeedbedRole.DIRI);
    }

    @Test
    void save_ValidRole_SavesSuccessfully() {
        // Arrange
        when(rolePersistencePort.save(role)).thenReturn(role);

        // Act
        Role result = roleUseCase.save(role);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo(SeedbedRole.ESTUDIANTE);
        verify(rolePersistencePort, times(1)).save(role);
    }

    @Test
    void update_RoleExists_UpdatesSuccessfully() {
        // Arrange
        when(rolePersistencePort.findById(1L)).thenReturn(Optional.of(role));
        when(rolePersistencePort.update(1L, role)).thenReturn(role);

        // Act
        Role result = roleUseCase.update(1L, role);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(rolePersistencePort, times(1)).findById(1L);
        verify(rolePersistencePort, times(1)).update(1L, role);
    }

    @Test
    void update_RoleDoesNotExist_ThrowsRoleNotFoundException() {
        // Arrange
        when(rolePersistencePort.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> roleUseCase.update(99L, role))
                .isInstanceOf(RoleNotFoundException.class)
                .hasMessage("No se pudo actualizar el rol con ID 99 porque no existe");
        verify(rolePersistencePort, times(1)).findById(99L);
        verify(rolePersistencePort, never()).update(anyLong(), any());
    }

    @Test
    void deleteById_RoleExists_DeletesSuccessfully() {
        // Arrange
        when(rolePersistencePort.findById(1L)).thenReturn(Optional.of(role));

        // Act
        roleUseCase.deleteById(1L);

        // Assert
        verify(rolePersistencePort, times(1)).findById(1L);
        verify(rolePersistencePort, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_RoleDoesNotExist_ThrowsRoleNotFoundException() {
        // Arrange
        when(rolePersistencePort.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> roleUseCase.deleteById(99L))
                .isInstanceOf(RoleNotFoundException.class)
                .hasMessage("No se pudo eliminar el rol con ID 99 porque no existe");
        verify(rolePersistencePort, times(1)).findById(99L);
        verify(rolePersistencePort, never()).deleteById(anyLong());
    }

    @Test
    void findAll_ReturnsAllRoles() {
        // Arrange
        Role role2 = new Role();
        role2.setId(2L);
        role2.setName(SeedbedRole.DIRI);

        List<Role> roles = Arrays.asList(role, role2);
        when(rolePersistencePort.findAll()).thenReturn(roles);

        // Act
        List<Role> result = roleUseCase.findAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo(SeedbedRole.ESTUDIANTE);
        assertThat(result.get(1).getName()).isEqualTo(SeedbedRole.DIRI);
        verify(rolePersistencePort, times(1)).findAll();
    }

    @Test
    void findRolesByIds_ReturnsMatchingRoles() {
        // Arrange
        Role role2 = new Role();
        role2.setId(2L);
        role2.setName(SeedbedRole.DIRI);

        Set<Long> ids = Set.of(1L, 2L);
        Set<Role> roles = Set.of(role, role2);
        when(rolePersistencePort.findRolesByIds(ids)).thenReturn(roles);

        // Act
        Set<Role> result = roleUseCase.findRolesByIds(ids);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).contains(role, role2);
        verify(rolePersistencePort, times(1)).findRolesByIds(ids);
    }

    @Test
    void findRolesByIds_EmptySet_ReturnsEmptySet() {
        // Arrange
        Set<Long> ids = Set.of();
        Set<Role> emptySet = Set.of();
        when(rolePersistencePort.findRolesByIds(ids)).thenReturn(emptySet);

        // Act
        Set<Role> result = roleUseCase.findRolesByIds(ids);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(rolePersistencePort, times(1)).findRolesByIds(ids);
    }

    @Test
    void findAllRolesByUserId_ReturnsUserRoles() {
        // Arrange
        Role role2 = new Role();
        role2.setId(2L);
        role2.setName(SeedbedRole.ESTUDIANTE_LIDER);

        List<Role> userRoles = Arrays.asList(role, role2);
        when(rolePersistencePort.findAllRolesByUserId(1L)).thenReturn(userRoles);

        // Act
        List<Role> result = roleUseCase.findAllRolesByUserId(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo(SeedbedRole.ESTUDIANTE);
        assertThat(result.get(1).getName()).isEqualTo(SeedbedRole.ESTUDIANTE_LIDER);
        verify(rolePersistencePort, times(1)).findAllRolesByUserId(1L);
    }

    @Test
    void findAllRolesByUserId_UserHasNoRoles_ReturnsEmptyList() {
        // Arrange
        List<Role> emptyList = Collections.emptyList();
        when(rolePersistencePort.findAllRolesByUserId(99L)).thenReturn(emptyList);

        // Act
        List<Role> result = roleUseCase.findAllRolesByUserId(99L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(rolePersistencePort, times(1)).findAllRolesByUserId(99L);
    }
}