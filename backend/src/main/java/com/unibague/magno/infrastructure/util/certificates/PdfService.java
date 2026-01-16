package com.unibague.magno.infrastructure.util.certificates;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;

/**
 * Service for PDF generation and date formatting utilities.
 * Converts HTML content to PDF documents and provides Spanish-language
 * date formatting methods for certificates.
 */
@Service
public class PdfService {

    private static final String[] MONTHS = {
            "", "enero", "febrero", "marzo", "abril", "mayo", "junio",
            "julio", "agosto", "septiembre", "octubre", "noviembre", "diciembre"
    };

    public byte[] htmlToPdf(String html) throws IOException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.withHtmlContent(html, null);
        builder.toStream(outputStream);
        builder.run();

        return outputStream.toByteArray();
    }

    public String periodFormat(LocalDate startDate, LocalDate endDate) {
        return spanishMonth(startDate.getMonthValue(), true) +
                " a " +
                spanishMonth(endDate.getMonthValue(), false) +
                " de " +
                endDate.getYear();
    }

    public String spanishMonth(int monthNumber, boolean isCapitalized) {
        if (monthNumber < 1 || monthNumber > 12) return "";

        String month = MONTHS[monthNumber];
        return isCapitalized
                ? month.substring(0, 1).toUpperCase() + month.substring(1)
                : month;
    }
}

