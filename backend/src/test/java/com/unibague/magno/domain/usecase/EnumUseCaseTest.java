package com.unibague.magno.domain.usecase;

import com.unibague.magno.domain.api.IInvestigationGroupServicePort;
import com.unibague.magno.domain.api.IResearchSeedbedServicePort;
import com.unibague.magno.domain.model.InvestigationGroup;
import com.unibague.magno.domain.model.ResearchSeedbed;
import com.unibague.magno.domain.model.enums.LineOfResearch;
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
class EnumUseCaseTest {

    @Mock
    private IInvestigationGroupServicePort investigationGroupServicePort;
    @Mock
    private IResearchSeedbedServicePort researchSeedbedServicePort;

    private EnumUseCase enumUseCase;

    // Test enum with getFormattedName method
    public enum TestEnum {
        VALUE_ONE("Value One"),
        VALUE_TWO("Value Two"),
        VALUE_THREE("Value Three");

        private final String formattedName;

        TestEnum(String formattedName) {
            this.formattedName = formattedName;
        }

        public String getFormattedName() {
            return formattedName;
        }
    }

    // Test enum without getFormattedName method
    public enum InvalidEnum {
        INVALID_VALUE_ONE,
        INVALID_VALUE_TWO
    }

    @BeforeEach
    void setUp() {
        enumUseCase = new EnumUseCase(
                investigationGroupServicePort,
                researchSeedbedServicePort
        );
    }

    @Test
    void getAllEnumValues_ValidEnum_ReturnsFormattedNames() {
        // Act
        List<String> result = enumUseCase.getAllEnumValues(TestEnum.class);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(3);
        assertThat(result).containsExactly("Value One", "Value Two", "Value Three");
    }

    @Test
    void getAllEnumValues_LineOfResearchEnum_ReturnsFormattedNames() {
        // Act
        List<String> result = enumUseCase.getAllEnumValues(LineOfResearch.class);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(43);
        assertThat(result).contains(
                "Agricultura, Silvicultura y Pesca",
                "Computacion y Ciencias de la Informacion",
                "Ingenieria Mecanica"
        );
    }

    @Test
    void getAllEnumValues_EnumWithoutGetFormattedName_ThrowsIllegalArgumentException() {
        // Act & Assert
        assertThatThrownBy(() -> enumUseCase.getAllEnumValues(InvalidEnum.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El enum no tiene un metodo getFormattedName.");
    }

    @Test
    void existsInEnum_ValueExists_ReturnsTrue() {
        // Act
        boolean result = enumUseCase.existsInEnum("Value_One", TestEnum.class);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    void existsInEnum_ValueExistsCaseInsensitive_ReturnsTrue() {
        // Act
        boolean result = enumUseCase.existsInEnum("value_one", TestEnum.class);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    void existsInEnum_LineOfResearchExists_ReturnsTrue() {
        // Act
        boolean result = enumUseCase.existsInEnum("Ingenieria_Mecanica", LineOfResearch.class);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    void existsInEnum_ValueDoesNotExist_ReturnsFalse() {
        // Act
        boolean result = enumUseCase.existsInEnum("NON_EXISTENT_VALUE", TestEnum.class);

        // Assert
        assertThat(result).isFalse();
    }

    @Test
    void existsInEnum_EnumWithoutGetFormattedName_ThrowsIllegalArgumentException() {
        // Act & Assert
        assertThatThrownBy(() -> enumUseCase.existsInEnum("INVALID_VALUE_ONE", InvalidEnum.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El enum no tiene un metodo getFormattedName.");
    }

    @Test
    void getLinesOfResearchByInvestigationGroupId_ReturnsFormattedLines() {
        // Arrange
        InvestigationGroup investigationGroup = new InvestigationGroup();
        investigationGroup.setId(1L);
        investigationGroup.setLinesOfResearch(Set.of(
                LineOfResearch.COMPUTACION_Y_CIENCIAS_DE_LA_INFORMACION,
                LineOfResearch.INGENIERIA_MECANICA
        ));

        when(investigationGroupServicePort.findById(1L)).thenReturn(investigationGroup);

        // Act
        List<String> result = enumUseCase.getLinesOfResearchByInvestigationGroupId(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactlyInAnyOrder(
                "Computacion y Ciencias de la Informacion",
                "Ingenieria Mecanica"
        );
        verify(investigationGroupServicePort, times(1)).findById(1L);
    }

    @Test
    void getLinesOfResearchByInvestigationGroupId_SingleLine_ReturnsOneFormattedLine() {
        // Arrange
        InvestigationGroup investigationGroup = new InvestigationGroup();
        investigationGroup.setId(1L);
        investigationGroup.setLinesOfResearch(Set.of(LineOfResearch.CIENCIAS_DE_LA_SALUD));

        when(investigationGroupServicePort.findById(1L)).thenReturn(investigationGroup);

        // Act
        List<String> result = enumUseCase.getLinesOfResearchByInvestigationGroupId(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result).containsExactly("Ciencias de la Salud");
        verify(investigationGroupServicePort, times(1)).findById(1L);
    }

    @Test
    void getLinesOfResearchByInvestigationGroupId_EmptySet_ReturnsEmptyList() {
        // Arrange
        InvestigationGroup investigationGroup = new InvestigationGroup();
        investigationGroup.setId(1L);
        investigationGroup.setLinesOfResearch(Collections.emptySet());

        when(investigationGroupServicePort.findById(1L)).thenReturn(investigationGroup);

        // Act
        List<String> result = enumUseCase.getLinesOfResearchByInvestigationGroupId(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        verify(investigationGroupServicePort, times(1)).findById(1L);
    }

    @Test
    void getLineOfResearchByResearchSeedbedId_ReturnsFormattedLine() {
        // Arrange
        ResearchSeedbed researchSeedbed = new ResearchSeedbed();
        researchSeedbed.setId(1L);
        researchSeedbed.setLineOfResearch(LineOfResearch.BIOTECNOLOGIA_AMBIENTAL);

        when(researchSeedbedServicePort.findById(1L)).thenReturn(researchSeedbed);

        // Act
        String result = enumUseCase.getLineOfResearchByResearchSeedbedId(1L);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEqualTo("Biotecnologia Ambiental");
        verify(researchSeedbedServicePort, times(1)).findById(1L);
    }

    @Test
    void getLineOfResearchByResearchSeedbedId_NullLineOfResearch_ThrowsNullPointerException() {
        // Arrange
        ResearchSeedbed researchSeedbed = new ResearchSeedbed();
        researchSeedbed.setId(1L);
        researchSeedbed.setLineOfResearch(null);

        when(researchSeedbedServicePort.findById(1L)).thenReturn(researchSeedbed);

        // Act & Assert
        assertThatThrownBy(() -> enumUseCase.getLineOfResearchByResearchSeedbedId(1L))
                .isInstanceOf(NullPointerException.class);
        verify(researchSeedbedServicePort, times(1)).findById(1L);
    }

    @Test
    void getAllEnumValuesAsMap_ValidEnum_ReturnsMap() {
        // Act
        Map<String, String> result = enumUseCase.getAllEnumValuesAsMap(TestEnum.class);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(3);
        assertThat(result).containsEntry("VALUE_ONE", "Value One");
        assertThat(result).containsEntry("VALUE_TWO", "Value Two");
        assertThat(result).containsEntry("VALUE_THREE", "Value Three");
    }

    @Test
    void getAllEnumValuesAsMap_LineOfResearchEnum_ReturnsMapWithAllValues() {
        // Act
        Map<String, String> result = enumUseCase.getAllEnumValuesAsMap(LineOfResearch.class);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(43);
        assertThat(result).containsEntry("COMPUTACION_Y_CIENCIAS_DE_LA_INFORMACION",
                "Computacion y Ciencias de la Informacion");
        assertThat(result).containsEntry("INGENIERIA_MECANICA", "Ingenieria Mecanica");
        assertThat(result).containsEntry("CIENCIAS_DE_LA_SALUD", "Ciencias de la Salud");
    }

    @Test
    void getAllEnumValuesAsMap_EnumWithoutGetFormattedName_ThrowsIllegalArgumentException() {
        // Act & Assert
        assertThatThrownBy(() -> enumUseCase.getAllEnumValuesAsMap(InvalidEnum.class))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("El enum no tiene un metodo getFormattedName.");
    }

    @Test
    void getAllEnumValuesAsMap_SingleValueEnum_ReturnsMapWithOneEntry() {
        // Arrange
        enum SingleValueEnum {
            ONLY_VALUE("Only Value");

            private final String formattedName;

            SingleValueEnum(String formattedName) {
                this.formattedName = formattedName;
            }

            public String getFormattedName() {
                return formattedName;
            }
        }

        // Act
        Map<String, String> result = enumUseCase.getAllEnumValuesAsMap(SingleValueEnum.class);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result).containsEntry("ONLY_VALUE", "Only Value");
    }
}