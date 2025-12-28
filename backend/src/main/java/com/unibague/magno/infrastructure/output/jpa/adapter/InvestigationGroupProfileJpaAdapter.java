package com.unibague.magno.infrastructure.output.jpa.adapter;

import com.unibague.magno.domain.exception.investigationgroupprofile.InvestigationGroupProfileNotFoundException;
import com.unibague.magno.domain.model.InvestigationGroupProfile;
import com.unibague.magno.domain.model.excel.ExcelReport;
import com.unibague.magno.domain.model.excel.metadata.ActiveSeedbedsMetadata;
import com.unibague.magno.domain.model.excel.metadata.InvestigationGroupHYRMetadata;
import com.unibague.magno.domain.model.excel.metadata.InvestigationGroupYRMetadata;
import com.unibague.magno.domain.model.excel.projections.ActiveSeedbedsProjection;
import com.unibague.magno.domain.model.excel.projections.InvestigationGroupHYRProjection;
import com.unibague.magno.domain.model.excel.projections.InvestigationGroupYRProjection;
import com.unibague.magno.domain.spi.IInvestigationGroupProfilePersistencePort;
import com.unibague.magno.infrastructure.output.jpa.entity.FunctionaryProfileEntity;
import com.unibague.magno.infrastructure.output.jpa.entity.InvestigationGroupProfileEntity;
import com.unibague.magno.infrastructure.output.jpa.mapper.InvestigationGroupProfileEntityMapper;
import com.unibague.magno.infrastructure.output.jpa.repository.IFunctionaryProfileRepository;
import com.unibague.magno.infrastructure.output.jpa.repository.IInvestigationGroupProfileRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
public class InvestigationGroupProfileJpaAdapter implements IInvestigationGroupProfilePersistencePort {

    private final IInvestigationGroupProfileRepository investigationGroupProfileRepository;
    private final InvestigationGroupProfileEntityMapper investigationGroupProfileEntityMapper;

    private final IFunctionaryProfileRepository functionaryProfileRepository;

    @Override
    public Optional<InvestigationGroupProfile> findById(Long id) {
        Optional<InvestigationGroupProfileEntity> investigationGroupProfile
                = investigationGroupProfileRepository.findById(id);
        return investigationGroupProfile.map(investigationGroupProfileEntityMapper::toInvestigationGroupProfile);
    }

    @Override
    public InvestigationGroupProfile save(InvestigationGroupProfile investigationGroupProfile) {
        InvestigationGroupProfileEntity investigationGroupProfileEntity = investigationGroupProfileEntityMapper
                .toInvestigationGroupProfileEntity(investigationGroupProfile);
        InvestigationGroupProfileEntity savedInvestigationGroupProfileEntity = investigationGroupProfileRepository
                .save(investigationGroupProfileEntity);
        return investigationGroupProfileEntityMapper.toInvestigationGroupProfile(savedInvestigationGroupProfileEntity);
    }

    @Override
    public InvestigationGroupProfile update(Long id, InvestigationGroupProfile investigationGroupProfile) {

        InvestigationGroupProfileEntity existingEntity = investigationGroupProfileRepository.findById(id)
                .orElseThrow(() -> new InvestigationGroupProfileNotFoundException(
                        String.format("InvestigationGroupProfile with id %s not found", id)));

        InvestigationGroupProfileEntity updatedEntity = investigationGroupProfileEntityMapper
                .toInvestigationGroupProfileEntity(id, investigationGroupProfile);

        existingEntity.setInvestigationGroup(updatedEntity.getInvestigationGroup());
        existingEntity.setCoordinator(updatedEntity.getCoordinator());
        existingEntity.setAcademicPeriod(updatedEntity.getAcademicPeriod());

        InvestigationGroupProfileEntity savedEntity = investigationGroupProfileRepository.save(existingEntity);
        return investigationGroupProfileEntityMapper.toInvestigationGroupProfile(savedEntity);
    }

    @Override
    /**
     * This method uses the repository of FunctionaryProfile to delete the bidirectional relationship between
     * InvestigationGroupProfile and FunctionaryProfile, then deletes the InvestigationGroupProfile to avoid
     * unexpected jpa exceptions.
     */
    public void deleteById(Long id) {
        InvestigationGroupProfileEntity profile = investigationGroupProfileRepository.findById(id)
                .orElseThrow(() -> new InvestigationGroupProfileNotFoundException(
                        String.format("InvestigationGroupProfile with id %s not found", id)));

        if (profile.getCoordinator() != null) {
            FunctionaryProfileEntity coordinator = profile.getCoordinator();
            coordinator.setInvestigationGroup(null);
            functionaryProfileRepository.save(coordinator);
        }

        investigationGroupProfileRepository.delete(profile);
    }

    @Override
    public List<InvestigationGroupProfile> findAll() {
        return investigationGroupProfileEntityMapper
                .toInvestigationGroupProfileList(investigationGroupProfileRepository.findAll());
    }

    @Override
    public List<InvestigationGroupProfile> findAllByAcademicPeriodId(Long academicPeriodId) {
        return investigationGroupProfileEntityMapper.toInvestigationGroupProfileList(
                investigationGroupProfileRepository.findByAcademicPeriodId(academicPeriodId)
        );
    }

    @Override
    public ExcelReport<InvestigationGroupHYRMetadata> getExcelBytesForHalfYearInvestigationGroupReport(Long academicPeriodId) {
        List<InvestigationGroupHYRProjection> records = investigationGroupProfileRepository
                .getInvestigationGroupsReportByAcademicPeriodId(academicPeriodId);
        try {
            return createHYExcelReport(records);
        } catch (IOException e) {
            throw new RuntimeException("Error al generar el reporte de Excel", e);
        }
    }

    @Override
    public ExcelReport<InvestigationGroupYRMetadata> getExcelBytesForAnnualYearInvestigationGroupReport(Long academicPeriodId1, Long academicPeriodId2) {
        List<InvestigationGroupYRProjection> records = investigationGroupProfileRepository
                .getInvestigationGroupsReportByAcademicPeriodId1AndAcademicPeriodId2(academicPeriodId1, academicPeriodId2);
        try {
            return createYExcelReport(records);
        } catch (IOException e) {
            throw new RuntimeException("Error al generar el reporte de Excel", e);
        }
    }

    @Override
    public ExcelReport<ActiveSeedbedsMetadata> getExcelBytesForHalfYearActiveSeedbedsReport(Long academicPeriodId) {
        List<ActiveSeedbedsProjection> records = investigationGroupProfileRepository
                .getActiveSeedbedsReportByAcademicPeriod(academicPeriodId);

        try {
            return createActiveSeedbedsExcelReport(records);
        }
        catch (IOException e) {
            throw new RuntimeException("Error al generar el reporte de Excel", e);
        }
    }

    @Override
    public ExcelReport<ActiveSeedbedsMetadata> getExcelBytesForAnnualActiveSeedbedsReport(Long academicPeriodId1, Long academicPeriodId2) {
        List<ActiveSeedbedsProjection> records = investigationGroupProfileRepository
                .getActiveSeedbedsReportByAcademicPeriod1AndAcademicPeriodId2(academicPeriodId1, academicPeriodId2);

        try {
            return createActiveSeedbedsExcelReport(records);
        } catch (IOException e) {
            throw new RuntimeException("Error al generar el reporte de Excel", e);
        }
    }

    private ExcelReport<InvestigationGroupHYRMetadata> createHYExcelReport(List<InvestigationGroupHYRProjection> records)
            throws IOException {
        try(Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream bos = new ByteArrayOutputStream()){
            Sheet sheet = workbook.createSheet("Reporte");

            String[] headers = {"Periodo Académico", "Grupo de Investigación", "Semillero", "Coordinador",
                    "Número de estudiantes", "Activo"};

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            String academicPeriodName = records.isEmpty() ? "" : records.getFirst().getAcademicPeriodName()
                    .replaceAll("[\\\\/:*?\"<>|]", "");

            int rowIndex = 1;
            for (InvestigationGroupHYRProjection record : records) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(academicPeriodName);
                row.createCell(1).setCellValue(record.getInvestigationGroupName());
                row.createCell(2).setCellValue(record.getResearchSeedbedName());
                row.createCell(3).setCellValue(record.getCoordinatorName());
                row.createCell(4).setCellValue(record.getStudentCount());
                row.createCell(5).setCellValue(Boolean.TRUE.equals(record.getIsActive()) ? "Activo" : "Inactivo");
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(bos);
            InvestigationGroupHYRMetadata metadata = new InvestigationGroupHYRMetadata(academicPeriodName);
            return new ExcelReport<>(bos.toByteArray(), metadata);
        }
    }

    private ExcelReport<InvestigationGroupYRMetadata> createYExcelReport(List<InvestigationGroupYRProjection> records)
            throws IOException {
        try(Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream bos = new ByteArrayOutputStream()){

            Sheet sheet = workbook.createSheet("Reporte");
            String[] headers = {"Periodos Académico", "Grupo de Investigación", "Semillero",
                    "Número de estudiantes"};

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            String academicPeriodName = records.isEmpty() ? "" : records.getFirst().getAcademicPeriodName()
                    .replaceAll("[\\\\/:*?\"<>|]", "");

            int rowIndex = 1;
            for (InvestigationGroupYRProjection record : records) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(academicPeriodName);
                row.createCell(1).setCellValue(record.getInvestigationGroupName());
                row.createCell(2).setCellValue(record.getResearchSeedbedName());
                row.createCell(3).setCellValue(record.getStudentCount());
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            String[] parts = academicPeriodName.split("__");

            String part1 = parts[0];
            String part2 = parts[1];

            workbook.write(bos);
            InvestigationGroupYRMetadata metadata = new InvestigationGroupYRMetadata(part1, part2);
            return new ExcelReport<>(bos.toByteArray(), metadata);
        }
    }

    private ExcelReport<ActiveSeedbedsMetadata> createActiveSeedbedsExcelReport(
            List<ActiveSeedbedsProjection> records) throws IOException {

        try(Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Reporte");
            String[] headers = {"Periodos Académico", "Grupo de Investigación", "Semilleros activos",
                    "Estudiantes activos por grupo"};

            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            String academicPeriodName = records.isEmpty() ? "" : records.getFirst().getAcademicPeriodName()
                    .replaceAll("[\\\\/:*?\"<>|]", "");

            int rowIndex = 1;
            for (ActiveSeedbedsProjection record : records) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(academicPeriodName);
                row.createCell(1).setCellValue(record.getInvestigationGroupName());
                row.createCell(2).setCellValue(record.getActiveSeedbedsCount());
                row.createCell(3).setCellValue(record.getActiveStudentsCount());
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(bos);
            ActiveSeedbedsMetadata metadata = new ActiveSeedbedsMetadata(academicPeriodName);
            return new ExcelReport<>(bos.toByteArray(), metadata);
        }
    }
}
