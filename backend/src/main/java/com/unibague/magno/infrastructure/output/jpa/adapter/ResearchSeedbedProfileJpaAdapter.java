package com.unibague.magno.infrastructure.output.jpa.adapter;

import com.unibague.magno.domain.exception.researchseedbedprofile.ResearchSeedbedProfileNotFoundException;
import com.unibague.magno.domain.model.ResearchSeedbedProfile;
import com.unibague.magno.domain.model.excel.ExcelReport;
import com.unibague.magno.domain.model.excel.projections.SeedbedReportProjection;
import com.unibague.magno.domain.model.excel.metadata.SeedbedReportMetadata;
import com.unibague.magno.domain.spi.IResearchSeedbedProfilePersistencePort;
import com.unibague.magno.infrastructure.output.jpa.entity.ResearchSeedbedProfileEntity;
import com.unibague.magno.infrastructure.output.jpa.mapper.ResearchSeedbedProfileEntityMapper;
import com.unibague.magno.infrastructure.output.jpa.repository.IResearchSeedbedProfileRepository;
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
public class ResearchSeedbedProfileJpaAdapter implements IResearchSeedbedProfilePersistencePort {

    private final IResearchSeedbedProfileRepository researchSeedbedProfileRepository;
    private final ResearchSeedbedProfileEntityMapper researchSeedbedProfileEntityMapper;

    @Override
    public Optional<ResearchSeedbedProfile> findById(Long id) {
        Optional<ResearchSeedbedProfileEntity> researchSeedbedProfile
                = researchSeedbedProfileRepository.findById(id);
        return researchSeedbedProfile.map(researchSeedbedProfileEntityMapper::toResearchSeedbedProfile);
    }

    @Override
    public ResearchSeedbedProfile save(ResearchSeedbedProfile researchSeedbedProfile) {
        ResearchSeedbedProfileEntity researchSeedbedProfileEntity = researchSeedbedProfileEntityMapper
                .toResearchSeedbedProfileEntity(researchSeedbedProfile);
        ResearchSeedbedProfileEntity savedResearchSeedbedProfileEntity = researchSeedbedProfileRepository
                .save(researchSeedbedProfileEntity);
        return researchSeedbedProfileEntityMapper.toResearchSeedbedProfile(savedResearchSeedbedProfileEntity);
    }

    @Override
    public ResearchSeedbedProfile update(Long id, ResearchSeedbedProfile researchSeedbedProfile) {

        ResearchSeedbedProfileEntity existingEntity = researchSeedbedProfileRepository.findById(id)
                .orElseThrow(() -> new ResearchSeedbedProfileNotFoundException("ResearchSeedbedProfile not found with id: " + id));

        ResearchSeedbedProfileEntity updatedEntity = researchSeedbedProfileEntityMapper
                .toResearchSeedbedProfileEntity(id, researchSeedbedProfile);

        existingEntity.setResearchSeedbed(updatedEntity.getResearchSeedbed());
        existingEntity.setCoordinator(updatedEntity.getCoordinator());
        existingEntity.setTutor(updatedEntity.getTutor());
        existingEntity.setInvestigationGroupProfile(updatedEntity.getInvestigationGroupProfile());
        existingEntity.setAcademicPeriod(updatedEntity.getAcademicPeriod());
        existingEntity.setWasActive(updatedEntity.getWasActive());

        ResearchSeedbedProfileEntity savedEntity = researchSeedbedProfileRepository.save(existingEntity);
        return researchSeedbedProfileEntityMapper.toResearchSeedbedProfile(savedEntity);
    }

    @Override
    public void deleteById(Long id) {
        researchSeedbedProfileRepository.deleteById(id);
    }

    @Override
    public List<ResearchSeedbedProfile> findAll() {
        return researchSeedbedProfileEntityMapper
                .toResearchSeedbedProfileList(researchSeedbedProfileRepository.findAll());
    }

    @Override
    public List<ResearchSeedbedProfile> findAllByInvestigationGroupProfileId(Long id) {
        return researchSeedbedProfileEntityMapper
                .toResearchSeedbedProfileList(researchSeedbedProfileRepository.findAllByInvestigationGroupProfileId(id));
    }

    @Override
    public ExcelReport<SeedbedReportMetadata> getExcelBytesReportById(Long researchSeedbedProfileId, Long academicPeriodId) {
        List<SeedbedReportProjection> records = researchSeedbedProfileRepository
                .getSeedbedReportById(researchSeedbedProfileId, academicPeriodId);
        try{
            return generateExcelReport(records);
        }
        catch (IOException e) {
            throw new RuntimeException("Error generating Excel report", e);
        }
    }

    private ExcelReport<SeedbedReportMetadata> generateExcelReport(List<SeedbedReportProjection> records) throws IOException {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Reporte");

            String[] headers = {"Periodo académico", "Grupo de investigación", "Semillero", "Coordinador", "Estudiante",
                    "Código", "Programa académico", "Semestre", "Sexo"};
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                headerRow.createCell(i).setCellValue(headers[i]);
            }

            String researchSeedbedName = records.isEmpty() ? "" :
                    records.getFirst().getResearchSeedbedName().replaceAll("[\\\\/:*?\"<>|]", "");
            String academicPeriodName = records.isEmpty() ? "" :
                    records.getFirst().getAcademicPeriodName().replaceAll("[\\\\/:*?\"<>|]", "");

            int rowNum = 1;
            for (SeedbedReportProjection record : records) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(record.getAcademicPeriodName());
                row.createCell(1).setCellValue(record.getInvestigationGroupName());
                row.createCell(2).setCellValue(record.getResearchSeedbedName());
                row.createCell(3).setCellValue(record.getCoordinatorName());
                row.createCell(4).setCellValue(record.getStudentName());
                row.createCell(5).setCellValue(record.getCode());
                row.createCell(6).setCellValue(record.getAcademicProgramName());
                row.createCell(7).setCellValue(record.getSemester() != null ? record.getSemester() : 0);
                row.createCell(8).setCellValue(record.getSex());
            }

            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(bos);
            SeedbedReportMetadata metadata = new SeedbedReportMetadata(researchSeedbedName, academicPeriodName);
            return new ExcelReport<>(bos.toByteArray(), metadata);
        }
    }


}
