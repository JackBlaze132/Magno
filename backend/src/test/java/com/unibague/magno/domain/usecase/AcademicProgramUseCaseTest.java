package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.integra.IIntegraServicePort;
import com.unibague.magno.domain.exception.academicprogram.AcademicProgramNotFoundException;
import com.unibague.magno.domain.model.AcademicProgram;
import com.unibague.magno.domain.model.enums.AcademicProgramType;
import com.unibague.magno.domain.model.integra.IntegraAcademicProgram;
import com.unibague.magno.domain.spi.IAcademicProgramPersistencePort;
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
class AcademicProgramUseCaseTest {

    @Mock
    private IAcademicProgramPersistencePort academicProgramPersistencePort;
    @Mock
    private IIntegraServicePort integraServicePort;

    private AcademicProgramUseCase academicProgramUseCase;
    private AcademicProgram academicProgram;
    private IntegraAcademicProgram integraAcademicProgram;

    @BeforeEach
    void setUp() {
        academicProgramUseCase = new AcademicProgramUseCase(
                academicProgramPersistencePort,
                integraServicePort
        );

        academicProgram = new AcademicProgram();
        academicProgram.setId(1L);
        academicProgram.setName("Ingeniería de Sistemas");
        academicProgram.setProgramCode("22");
        academicProgram.setType(AcademicProgramType.PREGRADO);

        integraAcademicProgram = new IntegraAcademicProgram();
        integraAcademicProgram.setProgramName("Ingeniería de Sistemas");
        integraAcademicProgram.setProgramCode("22");
    }

    @Test
    void findById_AcademicProgramExists_ReturnsAcademicProgram() {
        // Arrange
        when(academicProgramPersistencePort.findById(1L)).thenReturn(Optional.of(academicProgram));

        // Act
        AcademicProgram result = academicProgramUseCase.findById(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Ingeniería de Sistemas");
        assertThat(result.getProgramCode()).isEqualTo("22");
        assertThat(result.getType()).isEqualTo(AcademicProgramType.PREGRADO);
        verify(academicProgramPersistencePort, times(1)).findById(1L);
    }

    @Test
    void findById_AcademicProgramDoesNotExist_ThrowsAcademicProgramNotFoundException() {
        // Arrange
        when(academicProgramPersistencePort.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> academicProgramUseCase.findById(99L))
                .isInstanceOf(AcademicProgramNotFoundException.class)
                .hasMessage("Programa académico con ID 99 no encontrado");
        verify(academicProgramPersistencePort, times(1)).findById(99L);
    }

    @Test
    void save_NewAcademicProgram_SavesSuccessfully() {
        // Arrange
        when(academicProgramPersistencePort.existsByProgramCodeAndProgramName("22", "Ingeniería de Sistemas"))
                .thenReturn(false);
        when(academicProgramPersistencePort.save(academicProgram)).thenReturn(academicProgram);

        // Act
        AcademicProgram result = academicProgramUseCase.save(academicProgram);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("Ingeniería de Sistemas");
        assertThat(result.getProgramCode()).isEqualTo("22");
        verify(academicProgramPersistencePort, times(1))
                .existsByProgramCodeAndProgramName("22", "Ingeniería de Sistemas");
        verify(academicProgramPersistencePort, times(1)).save(academicProgram);
    }

    @Test
    void save_AcademicProgramAlreadyExists_ReturnsExistingProgram() {
        // Arrange
        when(academicProgramPersistencePort.existsByProgramCodeAndProgramName("22", "Ingeniería de Sistemas"))
                .thenReturn(true);
        when(academicProgramPersistencePort.findByAcademicProgramCode("22"))
                .thenReturn(academicProgram);

        // Act
        AcademicProgram result = academicProgramUseCase.save(academicProgram);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getProgramCode()).isEqualTo("22");
        verify(academicProgramPersistencePort, times(1))
                .existsByProgramCodeAndProgramName("22", "Ingeniería de Sistemas");
        verify(academicProgramPersistencePort, times(1)).findByAcademicProgramCode("22");
        verify(academicProgramPersistencePort, never()).save(any());
    }

    @Test
    void update_AcademicProgramExists_UpdatesSuccessfully() {
        // Arrange
        when(academicProgramPersistencePort.findById(1L)).thenReturn(Optional.of(academicProgram));
        when(academicProgramPersistencePort.update(1L, academicProgram)).thenReturn(academicProgram);

        // Act
        AcademicProgram result = academicProgramUseCase.update(1L, academicProgram);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        verify(academicProgramPersistencePort, times(1)).findById(1L);
        verify(academicProgramPersistencePort, times(1)).update(1L, academicProgram);
    }

    @Test
    void update_AcademicProgramDoesNotExist_ThrowsAcademicProgramNotFoundException() {
        // Arrange
        when(academicProgramPersistencePort.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> academicProgramUseCase.update(99L, academicProgram))
                .isInstanceOf(AcademicProgramNotFoundException.class)
                .hasMessage("No se pudo actualizar el programa académico con ID 99 porque no existe");
        verify(academicProgramPersistencePort, times(1)).findById(99L);
        verify(academicProgramPersistencePort, never()).update(anyLong(), any());
    }

    @Test
    void deleteById_AcademicProgramExists_DeletesSuccessfully() {
        // Arrange
        when(academicProgramPersistencePort.findById(1L)).thenReturn(Optional.of(academicProgram));

        // Act
        academicProgramUseCase.deleteById(1L);

        // Assert
        verify(academicProgramPersistencePort, times(1)).findById(1L);
        verify(academicProgramPersistencePort, times(1)).deleteById(1L);
    }

    @Test
    void deleteById_AcademicProgramDoesNotExist_ThrowsAcademicProgramNotFoundException() {
        // Arrange
        when(academicProgramPersistencePort.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> academicProgramUseCase.deleteById(99L))
                .isInstanceOf(AcademicProgramNotFoundException.class)
                .hasMessage("No se pudo eliminar el programa académico con ID 99 porque no existe");
        verify(academicProgramPersistencePort, times(1)).findById(99L);
        verify(academicProgramPersistencePort, never()).deleteById(anyLong());
    }

    @Test
    void findAll_ReturnsAllAcademicPrograms() {
        // Arrange
        AcademicProgram program2 = new AcademicProgram();
        program2.setId(2L);
        program2.setName("Ingeniería Civil");
        program2.setProgramCode("25");
        program2.setType(AcademicProgramType.PREGRADO);

        List<AcademicProgram> programs = Arrays.asList(academicProgram, program2);
        when(academicProgramPersistencePort.findAll()).thenReturn(programs);

        // Act
        List<AcademicProgram> result = academicProgramUseCase.findAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getName()).isEqualTo("Ingeniería de Sistemas");
        assertThat(result.get(1).getName()).isEqualTo("Ingeniería Civil");
        verify(academicProgramPersistencePort, times(1)).findAll();
    }

    @Test
    void findAll_NoAcademicPrograms_ReturnsEmptyList() {
        // Arrange
        when(academicProgramPersistencePort.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<AcademicProgram> result = academicProgramUseCase.findAll();

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(academicProgramPersistencePort, times(1)).findAll();
    }

    @Test
    void findAcademicProgramsByIds_ValidIds_ReturnsPrograms() {
        // Arrange
        AcademicProgram program2 = new AcademicProgram();
        program2.setId(2L);
        program2.setName("Ingeniería Industrial");
        program2.setProgramCode("24");
        program2.setType(AcademicProgramType.PREGRADO);

        Set<Long> ids = Set.of(1L, 2L);
        Set<AcademicProgram> programs = Set.of(academicProgram, program2);
        when(academicProgramPersistencePort.findAcademicProgramsByIds(ids)).thenReturn(programs);

        // Act
        Set<AcademicProgram> result = academicProgramUseCase.findAcademicProgramsByIds(ids);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        verify(academicProgramPersistencePort, times(1)).findAcademicProgramsByIds(ids);
    }

    @Test
    void findAcademicProgramsByIds_EmptyIds_ReturnsEmptySet() {
        // Arrange
        Set<Long> ids = Collections.emptySet();
        when(academicProgramPersistencePort.findAcademicProgramsByIds(ids))
                .thenReturn(Collections.emptySet());

        // Act
        Set<AcademicProgram> result = academicProgramUseCase.findAcademicProgramsByIds(ids);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(academicProgramPersistencePort, times(1)).findAcademicProgramsByIds(ids);
    }

    @Test
    void findAcademicProgramsByAcademicProgramCodes_EmptyCodes_ThrowsIllegalArgumentException() {
        // Arrange
        Set<String> emptyCodes = Collections.emptySet();

        // Act & Assert
        assertThatThrownBy(() -> academicProgramUseCase.findAcademicProgramsByAcademicProgramCodes(emptyCodes))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Los códigos de programa académico no pueden estar vacíos");
        verify(academicProgramPersistencePort, never()).findAcademicProgramsByAcademicProgramCodes(any());
    }

    @Test
    void findAcademicProgramsByAcademicProgramCodes_AllCodesExistLocally_ReturnsPrograms() {
        // Arrange
        Set<String> codes = Set.of("22", "25");

        AcademicProgram program2 = new AcademicProgram();
        program2.setId(2L);
        program2.setName("Ingeniería Civil");
        program2.setProgramCode("25");
        program2.setType(AcademicProgramType.PREGRADO);

        Set<AcademicProgram> programs = Set.of(academicProgram, program2);
        when(academicProgramPersistencePort.findAcademicProgramsByAcademicProgramCodes(codes))
                .thenReturn(programs);

        // Act
        Set<AcademicProgram> result = academicProgramUseCase.findAcademicProgramsByAcademicProgramCodes(codes);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        verify(academicProgramPersistencePort, times(1)).findAcademicProgramsByAcademicProgramCodes(codes);
        verify(integraServicePort, never()).getIntegraAcademicProgramsByProgramCodes(any());
    }

    @Test
    void findAcademicProgramsByAcademicProgramCodes_NoCodesExistLocally_FetchesFromIntegraAndSaves() {
        // Arrange
        Set<String> codes = Set.of("22");

        IntegraAcademicProgram integraProgram = new IntegraAcademicProgram();
        integraProgram.setProgramName("Ingeniería de Sistemas");
        integraProgram.setProgramCode("22");

        when(academicProgramPersistencePort.findAcademicProgramsByAcademicProgramCodes(codes))
                .thenReturn(Collections.emptySet());
        when(integraServicePort.getIntegraAcademicProgramsByProgramCodes(codes))
                .thenReturn(List.of(integraProgram));
        when(academicProgramPersistencePort.existsByProgramCodeAndProgramName("22", "Ingeniería de Sistemas"))
                .thenReturn(false);
        when(academicProgramPersistencePort.save(any(AcademicProgram.class)))
                .thenReturn(academicProgram);

        // Act
        Set<AcademicProgram> result = academicProgramUseCase.findAcademicProgramsByAcademicProgramCodes(codes);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        verify(academicProgramPersistencePort, times(1)).findAcademicProgramsByAcademicProgramCodes(codes);
        verify(integraServicePort, times(1)).getIntegraAcademicProgramsByProgramCodes(codes);
        verify(academicProgramPersistencePort, times(1)).save(any(AcademicProgram.class));
    }

    @Test
    void findAcademicProgramsByAcademicProgramCodes_SomeCodesMissing_FetchesMissingFromIntegra() {
        // Arrange
        Set<String> codes = Set.of("22", "25");

        Set<AcademicProgram> localPrograms = new HashSet<>(Set.of(academicProgram));

        IntegraAcademicProgram integraProgram = new IntegraAcademicProgram();
        integraProgram.setProgramName("Ingeniería Civil");
        integraProgram.setProgramCode("25");

        AcademicProgram civilProgram = new AcademicProgram();
        civilProgram.setId(2L);
        civilProgram.setName("Ingeniería Civil");
        civilProgram.setProgramCode("25");
        civilProgram.setType(AcademicProgramType.PREGRADO);

        when(academicProgramPersistencePort.findAcademicProgramsByAcademicProgramCodes(codes))
                .thenReturn(localPrograms);
        when(integraServicePort.getIntegraAcademicProgramsByProgramCodes(Set.of("25")))
                .thenReturn(List.of(integraProgram));
        when(academicProgramPersistencePort.existsByProgramCodeAndProgramName("25", "Ingeniería Civil"))
                .thenReturn(false);
        when(academicProgramPersistencePort.save(any(AcademicProgram.class)))
                .thenReturn(civilProgram);

        // Act
        Set<AcademicProgram> result = academicProgramUseCase.findAcademicProgramsByAcademicProgramCodes(codes);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        verify(academicProgramPersistencePort, times(1)).findAcademicProgramsByAcademicProgramCodes(codes);
        verify(integraServicePort, times(1)).getIntegraAcademicProgramsByProgramCodes(Set.of("25"));
        verify(academicProgramPersistencePort, times(1)).save(any(AcademicProgram.class));
    }

    @Test
    void findAcademicProgramsByAcademicProgramCodes_MultipleCodesFromIntegra_SavesAll() {
        // Arrange
        Set<String> codes = Set.of("22", "25", "24");

        IntegraAcademicProgram integraProgram1 = new IntegraAcademicProgram();
        integraProgram1.setProgramName("Ingeniería de Sistemas");
        integraProgram1.setProgramCode("22");

        IntegraAcademicProgram integraProgram2 = new IntegraAcademicProgram();
        integraProgram2.setProgramName("Ingeniería Civil");
        integraProgram2.setProgramCode("25");

        IntegraAcademicProgram integraProgram3 = new IntegraAcademicProgram();
        integraProgram3.setProgramName("Ingeniería Industrial");
        integraProgram3.setProgramCode("24");

        AcademicProgram program1 = new AcademicProgram();
        program1.setId(1L);
        program1.setProgramCode("22");

        AcademicProgram program2 = new AcademicProgram();
        program2.setId(2L);
        program2.setProgramCode("25");

        AcademicProgram program3 = new AcademicProgram();
        program3.setId(3L);
        program3.setProgramCode("24");

        when(academicProgramPersistencePort.findAcademicProgramsByAcademicProgramCodes(codes))
                .thenReturn(Collections.emptySet());
        when(integraServicePort.getIntegraAcademicProgramsByProgramCodes(codes))
                .thenReturn(List.of(integraProgram1, integraProgram2, integraProgram3));
        when(academicProgramPersistencePort.existsByProgramCodeAndProgramName(anyString(), anyString()))
                .thenReturn(false);
        when(academicProgramPersistencePort.save(any(AcademicProgram.class)))
                .thenReturn(program1, program2, program3);

        // Act
        Set<AcademicProgram> result = academicProgramUseCase.findAcademicProgramsByAcademicProgramCodes(codes);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(3);
        verify(academicProgramPersistencePort, times(1)).findAcademicProgramsByAcademicProgramCodes(codes);
        verify(integraServicePort, times(1)).getIntegraAcademicProgramsByProgramCodes(codes);
        verify(academicProgramPersistencePort, times(3)).save(any(AcademicProgram.class));
    }

    @Test
    void existsByProgramCodeAndProgramName_ProgramExists_ReturnsTrue() {
        // Arrange
        when(academicProgramPersistencePort.existsByProgramCodeAndProgramName("22", "Ingeniería de Sistemas"))
                .thenReturn(true);

        // Act
        boolean result = academicProgramUseCase.existsByProgramCodeAndProgramName("22", "Ingeniería de Sistemas");

        // Assert
        assertThat(result).isTrue();
        verify(academicProgramPersistencePort, times(1))
                .existsByProgramCodeAndProgramName("22", "Ingeniería de Sistemas");
    }

    @Test
    void existsByProgramCodeAndProgramName_ProgramDoesNotExist_ReturnsFalse() {
        // Arrange
        when(academicProgramPersistencePort.existsByProgramCodeAndProgramName("99", "Non-Existent Program"))
                .thenReturn(false);

        // Act
        boolean result = academicProgramUseCase.existsByProgramCodeAndProgramName("99", "Non-Existent Program");

        // Assert
        assertThat(result).isFalse();
        verify(academicProgramPersistencePort, times(1))
                .existsByProgramCodeAndProgramName("99", "Non-Existent Program");
    }

    @Test
    void findByAcademicProgramCode_ValidCode_ReturnsProgram() {
        // Arrange
        when(academicProgramPersistencePort.findByAcademicProgramCode("22"))
                .thenReturn(academicProgram);

        // Act
        AcademicProgram result = academicProgramUseCase.findByAcademicProgramCode("22");

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getProgramCode()).isEqualTo("22");
        assertThat(result.getName()).isEqualTo("Ingeniería de Sistemas");
        verify(academicProgramPersistencePort, times(1)).findByAcademicProgramCode("22");
    }
}