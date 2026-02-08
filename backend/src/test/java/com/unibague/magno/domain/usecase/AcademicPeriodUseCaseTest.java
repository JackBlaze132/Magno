package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.exception.academicperiod.AcademicPeriodAlreadyExistsException;
import com.unibague.magno.domain.exception.academicperiod.AcademicPeriodNotCurrentException;
import com.unibague.magno.domain.exception.academicperiod.AcademicPeriodNotFoundException;
import com.unibague.magno.domain.exception.academicperiod.EndDateBeforeStartDateException;
import com.unibague.magno.domain.exception.academicperiod.MultipleActiveAcademicPeriodsException;
import com.unibague.magno.domain.model.AcademicPeriod;
import com.unibague.magno.domain.spi.IAcademicPeriodPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AcademicPeriodUseCaseTest {

    @Mock
    private IAcademicPeriodPersistencePort academicPeriodPersistencePort;

    private AcademicPeriodUseCase academicPeriodUseCase;
    private AcademicPeriod academicPeriod;

    @BeforeEach
    void setUp() throws Exception {
        academicPeriodUseCase = new AcademicPeriodUseCase(academicPeriodPersistencePort);
        academicPeriod = createAcademicPeriod(1L, "2025-1",
                LocalDate.of(2025, 1, 15),
                LocalDate.of(2025, 6, 15),
                true, true);
    }

    private AcademicPeriod createAcademicPeriod(Long id, String name, LocalDate startDate,
                                                LocalDate endDate, boolean isCurrent, boolean isVisible) throws Exception {
        Constructor<AcademicPeriod> constructor = AcademicPeriod.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        AcademicPeriod period = constructor.newInstance();
        period.setId(id);
        period.setName(name);
        period.setStartDate(startDate);
        period.setEndDate(endDate);
        period.setCurrent(isCurrent);
        period.setVisible(isVisible);
        return period;
    }

    @Test
    void findById_AcademicPeriodExists_ReturnsAcademicPeriod() {
        // Arrange
        when(academicPeriodPersistencePort.findById(1L)).thenReturn(Optional.of(academicPeriod));

        // Act
        AcademicPeriod result = academicPeriodUseCase.findById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("2025-1");
        assertThat(result.getStartDate()).isEqualTo(LocalDate.of(2025, 1, 15));
        assertThat(result.getEndDate()).isEqualTo(LocalDate.of(2025, 6, 15));
        assertThat(result.isCurrent()).isTrue();
        assertThat(result.isVisible()).isTrue();
        verify(academicPeriodPersistencePort, times(1)).findById(1L);
    }

    @Test
    void findById_AcademicPeriodDoesNotExist_ThrowsAcademicPeriodNotFoundException() {
        // Arrange
        when(academicPeriodPersistencePort.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> academicPeriodUseCase.findById(99L))
                .isInstanceOf(AcademicPeriodNotFoundException.class)
                .hasMessage("Período académico con ID 99 no encontrado");
        verify(academicPeriodPersistencePort, times(1)).findById(99L);
    }

    @Test
    void findByName_AcademicPeriodExists_ReturnsAcademicPeriod() {
        // Arrange
        when(academicPeriodPersistencePort.findByName("2025-1")).thenReturn(Optional.of(academicPeriod));

        // Act
        AcademicPeriod result = academicPeriodUseCase.findByName("2025-1");

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("2025-1");
        verify(academicPeriodPersistencePort, times(1)).findByName("2025-1");
    }

    @Test
    void findByName_AcademicPeriodDoesNotExist_ThrowsAcademicPeriodNotFoundException() {
        // Arrange
        when(academicPeriodPersistencePort.findByName("2030-1")).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> academicPeriodUseCase.findByName("2030-1"))
                .isInstanceOf(AcademicPeriodNotFoundException.class)
                .hasMessage("Período académico con nombre 2030-1 no encontrado");
        verify(academicPeriodPersistencePort, times(1)).findByName("2030-1");
    }

    @Test
    void save_ValidAcademicPeriod_SavesSuccessfully() {
        // Arrange
        when(academicPeriodPersistencePort.findAll()).thenReturn(Collections.emptyList());
        when(academicPeriodPersistencePort.findAllActiveAndVisible()).thenReturn(Collections.emptyList());
        when(academicPeriodPersistencePort.save(academicPeriod)).thenReturn(academicPeriod);

        // Act
        AcademicPeriod result = academicPeriodUseCase.save(academicPeriod);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("2025-1");
        verify(academicPeriodPersistencePort, times(1)).save(academicPeriod);
    }

    @Test
    void save_EndDateBeforeStartDate_ThrowsEndDateBeforeStartDateException() throws Exception {
        // Arrange
        AcademicPeriod invalidPeriod = createAcademicPeriod(1L, "2025-1",
                LocalDate.of(2025, 6, 15),
                LocalDate.of(2025, 1, 15),
                true, true);

        // Act & Assert
        assertThatThrownBy(() -> academicPeriodUseCase.save(invalidPeriod))
                .isInstanceOf(EndDateBeforeStartDateException.class)
                .hasMessage("La fecha de finalización no puede ser anterior a la fecha de inicio");
        verify(academicPeriodPersistencePort, never()).save(any());
    }

    @Test
    void save_InactiveAcademicPeriod_ThrowsAcademicPeriodNotCurrentException() throws Exception {
        // Arrange
        AcademicPeriod inactivePeriod = createAcademicPeriod(1L, "2025-1",
                LocalDate.of(2025, 1, 15),
                LocalDate.of(2025, 6, 15),
                false, true);

        // Act & Assert
        assertThatThrownBy(() -> academicPeriodUseCase.save(inactivePeriod))
                .isInstanceOf(AcademicPeriodNotCurrentException.class)
                .hasMessage("No se puede guardar o actualizar el período académico porque se marca como inactivo");
        verify(academicPeriodPersistencePort, never()).save(any());
    }

    @Test
    void save_DuplicateName_ThrowsAcademicPeriodAlreadyExistsException() throws Exception {
        // Arrange
        AcademicPeriod existingPeriod = createAcademicPeriod(2L, "2025-1",
                LocalDate.of(2024, 8, 1),
                LocalDate.of(2024, 12, 15),
                false, true);

        when(academicPeriodPersistencePort.findAll()).thenReturn(List.of(existingPeriod));

        // Act & Assert
        assertThatThrownBy(() -> academicPeriodUseCase.save(academicPeriod))
                .isInstanceOf(AcademicPeriodAlreadyExistsException.class)
                .hasMessage("Ya existe un período académico con el nombre '2025-1'");
        verify(academicPeriodPersistencePort, never()).save(any());
    }

    @Test
    void save_DuplicateNameCaseInsensitive_ThrowsAcademicPeriodAlreadyExistsException() throws Exception {
        // Arrange
        AcademicPeriod existingPeriod = createAcademicPeriod(2L, "2025-1",
                LocalDate.of(2024, 8, 1),
                LocalDate.of(2024, 12, 15),
                false, true);

        AcademicPeriod newPeriod = createAcademicPeriod(3L, "  2025-1  ",
                LocalDate.of(2025, 1, 15),
                LocalDate.of(2025, 6, 15),
                true, true);

        when(academicPeriodPersistencePort.findAll()).thenReturn(List.of(existingPeriod));

        // Act & Assert
        assertThatThrownBy(() -> academicPeriodUseCase.save(newPeriod))
                .isInstanceOf(AcademicPeriodAlreadyExistsException.class)
                .hasMessage("Ya existe un período académico con el nombre '2025-1'");
        verify(academicPeriodPersistencePort, never()).save(any());
    }

    @Test
    void save_AnotherActiveAndVisiblePeriodExists_ThrowsMultipleActiveAcademicPeriodsException() throws Exception {
        // Arrange
        AcademicPeriod existingActivePeriod = createAcademicPeriod(2L, "2024-2",
                LocalDate.of(2024, 8, 1),
                LocalDate.of(2024, 12, 15),
                true, true);

        when(academicPeriodPersistencePort.findAll()).thenReturn(Collections.emptyList());
        when(academicPeriodPersistencePort.findAllActiveAndVisible())
                .thenReturn(List.of(existingActivePeriod));

        // Act & Assert
        assertThatThrownBy(() -> academicPeriodUseCase.save(academicPeriod))
                .isInstanceOf(MultipleActiveAcademicPeriodsException.class)
                .hasMessage("Ya existe otro período académico activo. Espere a que el período actual finalice antes de activar uno nuevo.");
        verify(academicPeriodPersistencePort, never()).save(any());
    }

    @Test
    void save_NotVisiblePeriodWhenActiveExists_SavesSuccessfully() throws Exception {
        // Arrange
        AcademicPeriod notVisiblePeriod = createAcademicPeriod(1L, "2025-1",
                LocalDate.of(2025, 1, 15),
                LocalDate.of(2025, 6, 15),
                true, false);

        when(academicPeriodPersistencePort.findAll()).thenReturn(Collections.emptyList());
        when(academicPeriodPersistencePort.save(notVisiblePeriod)).thenReturn(notVisiblePeriod);

        // Act
        AcademicPeriod result = academicPeriodUseCase.save(notVisiblePeriod);

        // Assert
        assertThat(result).isNotNull();
        verify(academicPeriodPersistencePort, times(1)).save(notVisiblePeriod);
        verify(academicPeriodPersistencePort, never()).findAllActiveAndVisible();
    }

    @Test
    void update_ValidAcademicPeriod_UpdatesSuccessfully() {
        // Arrange
        when(academicPeriodPersistencePort.findById(1L)).thenReturn(Optional.of(academicPeriod));
        when(academicPeriodPersistencePort.findAll()).thenReturn(List.of(academicPeriod));
        when(academicPeriodPersistencePort.findAllActiveAndVisible()).thenReturn(List.of(academicPeriod));
        when(academicPeriodPersistencePort.update(1L, academicPeriod)).thenReturn(academicPeriod);

        // Act
        AcademicPeriod result = academicPeriodUseCase.update(1L, academicPeriod);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(academicPeriodPersistencePort, times(1)).findById(1L);
        verify(academicPeriodPersistencePort, times(1)).update(1L, academicPeriod);
    }

    @Test
    void update_AcademicPeriodDoesNotExist_ThrowsAcademicPeriodNotFoundException() {
        // Arrange
        when(academicPeriodPersistencePort.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> academicPeriodUseCase.update(99L, academicPeriod))
                .isInstanceOf(AcademicPeriodNotFoundException.class)
                .hasMessage("No se pudo actualizar el período académico con ID 99 porque no existe");
        verify(academicPeriodPersistencePort, times(1)).findById(99L);
        verify(academicPeriodPersistencePort, never()).update(anyLong(), any());
    }

    @Test
    void update_EndDateBeforeStartDate_ThrowsEndDateBeforeStartDateException() throws Exception {
        // Arrange
        AcademicPeriod invalidPeriod = createAcademicPeriod(1L, "2025-1",
                LocalDate.of(2025, 6, 15),
                LocalDate.of(2025, 1, 15),
                true, true);

        // Act & Assert
        assertThatThrownBy(() -> academicPeriodUseCase.update(1L, invalidPeriod))
                .isInstanceOf(EndDateBeforeStartDateException.class)
                .hasMessage("La fecha de finalización no puede ser anterior a la fecha de inicio");
        verify(academicPeriodPersistencePort, never()).update(anyLong(), any());
    }

    @Test
    void update_InactiveAcademicPeriod_ThrowsAcademicPeriodNotCurrentException() throws Exception {
        // Arrange
        AcademicPeriod inactivePeriod = createAcademicPeriod(1L, "2025-1",
                LocalDate.of(2025, 1, 15),
                LocalDate.of(2025, 6, 15),
                false, true);

        // Act & Assert
        assertThatThrownBy(() -> academicPeriodUseCase.update(1L, inactivePeriod))
                .isInstanceOf(AcademicPeriodNotCurrentException.class)
                .hasMessage("No se puede guardar o actualizar el período académico porque se marca como inactivo");
        verify(academicPeriodPersistencePort, never()).update(anyLong(), any());
    }

    @Test
    void update_DuplicateNameWithDifferentId_ThrowsAcademicPeriodAlreadyExistsException() throws Exception {
        // Arrange
        AcademicPeriod existingPeriod = createAcademicPeriod(2L, "2025-1",
                LocalDate.of(2024, 8, 1),
                LocalDate.of(2024, 12, 15),
                false, true);

        when(academicPeriodPersistencePort.findById(1L)).thenReturn(Optional.of(academicPeriod));
        when(academicPeriodPersistencePort.findAll()).thenReturn(List.of(academicPeriod, existingPeriod));

        // Act & Assert
        assertThatThrownBy(() -> academicPeriodUseCase.update(1L, academicPeriod))
                .isInstanceOf(AcademicPeriodAlreadyExistsException.class)
                .hasMessage("Ya existe un período académico con el nombre '2025-1'");
        verify(academicPeriodPersistencePort, never()).update(anyLong(), any());
    }

    @Test
    void update_SameNameSameId_UpdatesSuccessfully() {
        // Arrange
        when(academicPeriodPersistencePort.findById(1L)).thenReturn(Optional.of(academicPeriod));
        when(academicPeriodPersistencePort.findAll()).thenReturn(List.of(academicPeriod));
        when(academicPeriodPersistencePort.findAllActiveAndVisible()).thenReturn(List.of(academicPeriod));
        when(academicPeriodPersistencePort.update(1L, academicPeriod)).thenReturn(academicPeriod);

        // Act
        AcademicPeriod result = academicPeriodUseCase.update(1L, academicPeriod);

        // Assert
        assertThat(result).isNotNull();
        verify(academicPeriodPersistencePort, times(1)).update(1L, academicPeriod);
    }

    @Test
    void update_ActivatingPeriodWhenAnotherIsActive_ThrowsMultipleActiveAcademicPeriodsException() throws Exception {
        // Arrange
        AcademicPeriod existingActivePeriod = createAcademicPeriod(2L, "2024-2",
                LocalDate.of(2024, 8, 1),
                LocalDate.of(2024, 12, 15),
                true, true);

        when(academicPeriodPersistencePort.findById(1L)).thenReturn(Optional.of(academicPeriod));
        when(academicPeriodPersistencePort.findAll()).thenReturn(List.of(academicPeriod, existingActivePeriod));
        when(academicPeriodPersistencePort.findAllActiveAndVisible())
                .thenReturn(List.of(existingActivePeriod));

        // Act & Assert
        assertThatThrownBy(() -> academicPeriodUseCase.update(1L, academicPeriod))
                .isInstanceOf(MultipleActiveAcademicPeriodsException.class)
                .hasMessage("Ya existe otro período académico activo. Espere a que el período actual finalice antes de activar uno nuevo.");
        verify(academicPeriodPersistencePort, never()).update(anyLong(), any());
    }

    @Test
    void deleteById_ActiveAcademicPeriod_DeletesSuccessfully() {
        // Arrange
        when(academicPeriodPersistencePort.findById(1L)).thenReturn(Optional.of(academicPeriod));

        // Act
        academicPeriodUseCase.deleteById(1L);

        // Assert
        verify(academicPeriodPersistencePort, times(2)).findById(1L);
        verify(academicPeriodPersistencePort, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_AcademicPeriodDoesNotExist_ThrowsAcademicPeriodNotFoundException() {
        // Arrange
        when(academicPeriodPersistencePort.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> academicPeriodUseCase.deleteById(99L))
                .isInstanceOf(AcademicPeriodNotFoundException.class)
                .hasMessage("No se pudo eliminar el período académico con ID 99 porque no existe");
        verify(academicPeriodPersistencePort, times(1)).findById(99L);
        verify(academicPeriodPersistencePort, never()).deleteById(anyLong());
    }

    @Test
    void deleteById_InactiveAcademicPeriod_ThrowsAcademicPeriodNotCurrentException() throws Exception {
        // Arrange
        AcademicPeriod inactivePeriod = createAcademicPeriod(1L, "2025-1",
                LocalDate.of(2025, 1, 15),
                LocalDate.of(2025, 6, 15),
                false, true);

        when(academicPeriodPersistencePort.findById(1L)).thenReturn(Optional.of(inactivePeriod));

        // Act & Assert
        assertThatThrownBy(() -> academicPeriodUseCase.deleteById(1L))
                .isInstanceOf(AcademicPeriodNotCurrentException.class)
                .hasMessage("No se puede eliminar el período académico porque no está activo");
        verify(academicPeriodPersistencePort, times(2)).findById(1L);
        verify(academicPeriodPersistencePort, never()).deleteById(anyLong());
    }

    @Test
    void findAll_ReturnsAllAcademicPeriods() throws Exception {
        // Arrange
        AcademicPeriod period2 = createAcademicPeriod(2L, "2024-2",
                LocalDate.of(2024, 8, 1),
                LocalDate.of(2024, 12, 15),
                false, true);

        List<AcademicPeriod> periods = Arrays.asList(academicPeriod, period2);
        when(academicPeriodPersistencePort.findAll()).thenReturn(periods);

        // Act
        List<AcademicPeriod> result = academicPeriodUseCase.findAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("2025-1");
        assertThat(result.get(1).getName()).isEqualTo("2024-2");
        verify(academicPeriodPersistencePort, times(1)).findAll();
    }

    @Test
    void findAll_NoAcademicPeriods_ReturnsEmptyList() {
        // Arrange
        when(academicPeriodPersistencePort.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<AcademicPeriod> result = academicPeriodUseCase.findAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(academicPeriodPersistencePort, times(1)).findAll();
    }

    @Test
    void findAllVisible_ReturnsVisibleAcademicPeriods() throws Exception {
        // Arrange
        AcademicPeriod visiblePeriod = createAcademicPeriod(2L, "2024-2",
                LocalDate.of(2024, 8, 1),
                LocalDate.of(2024, 12, 15),
                false, true);

        List<AcademicPeriod> visiblePeriods = Arrays.asList(academicPeriod, visiblePeriod);
        when(academicPeriodPersistencePort.findAllVisible()).thenReturn(visiblePeriods);

        // Act
        List<AcademicPeriod> result = academicPeriodUseCase.findAllVisible();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        verify(academicPeriodPersistencePort, times(1)).findAllVisible();
    }

    @Test
    void findAllNotVisible_ReturnsNotVisibleAcademicPeriods() throws Exception {
        // Arrange
        AcademicPeriod hiddenPeriod = createAcademicPeriod(3L, "2023-1",
                LocalDate.of(2023, 1, 15),
                LocalDate.of(2023, 6, 15),
                false, false);

        List<AcademicPeriod> hiddenPeriods = List.of(hiddenPeriod);
        when(academicPeriodPersistencePort.findAllNotVisible()).thenReturn(hiddenPeriods);

        // Act
        List<AcademicPeriod> result = academicPeriodUseCase.findAllNotVisible();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).isVisible()).isFalse();
        verify(academicPeriodPersistencePort, times(1)).findAllNotVisible();
    }

    @Test
    void findActiveAcademicPeriod_OneActivePeriod_ReturnsActivePeriod() {
        // Arrange
        when(academicPeriodPersistencePort.findAllActiveAndVisible())
                .thenReturn(List.of(academicPeriod));

        // Act
        AcademicPeriod result = academicPeriodUseCase.findActiveAcademicPeriod();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("2025-1");
        assertThat(result.isCurrent()).isTrue();
        assertThat(result.isVisible()).isTrue();
        verify(academicPeriodPersistencePort, times(1)).findAllActiveAndVisible();
    }

    @Test
    void findActiveAcademicPeriod_NoActivePeriod_ThrowsAcademicPeriodNotFoundException() {
        // Arrange
        when(academicPeriodPersistencePort.findAllActiveAndVisible())
                .thenReturn(Collections.emptyList());

        // Act & Assert
        assertThatThrownBy(() -> academicPeriodUseCase.findActiveAcademicPeriod())
                .isInstanceOf(AcademicPeriodNotFoundException.class)
                .hasMessage("No hay ningún período académico activo en el sistema");
        verify(academicPeriodPersistencePort, times(1)).findAllActiveAndVisible();
    }

    @Test
    void findActiveAcademicPeriod_MultipleActivePeriods_ThrowsMultipleActiveAcademicPeriodsException() throws Exception {
        // Arrange
        AcademicPeriod period2 = createAcademicPeriod(2L, "2024-2",
                LocalDate.of(2024, 8, 1),
                LocalDate.of(2024, 12, 15),
                true, true);

        when(academicPeriodPersistencePort.findAllActiveAndVisible())
                .thenReturn(Arrays.asList(academicPeriod, period2));

        // Act & Assert
        assertThatThrownBy(() -> academicPeriodUseCase.findActiveAcademicPeriod())
                .isInstanceOf(MultipleActiveAcademicPeriodsException.class)
                .hasMessage("Se encontraron 2 períodos académicos activos. Solo debe haber uno.");
        verify(academicPeriodPersistencePort, times(1)).findAllActiveAndVisible();
    }
}