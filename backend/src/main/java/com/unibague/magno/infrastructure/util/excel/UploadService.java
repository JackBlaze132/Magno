package com.unibague.magno.infrastructure.util.excel;

import com.unibague.magno.domain.exception.excel.UploadExcelException;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UploadService {

    private final UploadUtil uploadUtil;

    public List<Map<String, String>> uploadExcel(MultipartFile file) throws IOException {

        Path tempDir = Files.createTempDirectory("");
        File tempFile = tempDir.resolve(Objects.requireNonNull(file.getOriginalFilename())).toFile();
        file.transferTo(tempFile);

        try (Workbook workbook = WorkbookFactory.create(tempFile)) {
            Sheet sheet = workbook.getSheetAt(0);
            Supplier<Stream<Row>> rowStreamSupplier = uploadUtil.getRowStreamSupplier(sheet);

            Optional<Row> optionalHeaderRow = rowStreamSupplier.get().findFirst();
            if (optionalHeaderRow.isEmpty()) {
                throw new UploadExcelException("El archivo Excel no tiene una fila de encabezado");
            }

            Row headerRow = optionalHeaderRow.get();

            List<String> headerCells = uploadUtil.getStream(headerRow)
                    .map(this::getCellValueAsString)
                    .toList();

            int colCount = headerCells.size();

            return rowStreamSupplier.get()
                    .skip(1)
                    .map(row -> {
                        List<String> cellList = uploadUtil.getStream(row)
                                .map(this::getCellValueAsString)
                                .toList();

                        return uploadUtil.cellIteratorSupplier(colCount)
                                .get()
                                .collect(Collectors.toMap(headerCells::get, cellList::get));
                    })
                    .toList();
        }
    }

    private String getCellValueAsString(Cell cell){
        switch (cell.getCellType()){
            case STRING:
                return cell.getStringCellValue();
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)){
                    return String.valueOf(cell.getDateCellValue());
                }
                else{
                    double numericValue = cell.getNumericCellValue();
                    if (numericValue == (long) numericValue) {
                        return String.format("%d", (long) numericValue);
                    } else {
                        return BigDecimal.valueOf(numericValue).toPlainString();
                    }
                }
            default:
                return "";
        }
    }
}
