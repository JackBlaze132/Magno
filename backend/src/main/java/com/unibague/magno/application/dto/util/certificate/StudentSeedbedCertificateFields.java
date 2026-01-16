package com.unibague.magno.application.dto.util.certificate;

/**
 * Constants class defining field names used in student seedbed participation certificates.
 * These constants map to placeholders in the certificate template for PDF generation.
 */
public class StudentSeedbedCertificateFields {

    public static final String NOMBRE = "nombre";
    public static final String CEDULA = "cedula";
    public static final String SEMILLERO = "semillero";
    public static final String GRUPO = "grupo";
    public static final String COORDINADOR = "coordinador";
    public static final String PERIODOS = "periodos";

    public static final String DIA = "dia";
    public static final String DIA_STRING = "diaString";
    public static final String MES = "mes";
    public static final String ANIO = "anio";

    public static final String LOGO1 = "logo1Path";
    public static final String LOGO2 = "logo2Path";
    public static final String FIRMA = "firmaPath";

    public static final String DIRECTOR = "director";
    public static final String CARGO = "cargo";

    public static final String DIRECTOR_VALUE = "Jorge Enrique García Melo";
    public static final String CARGO_VALUE = "Director";
    public static final String LOGO1_VALUE = "classpath:static/images/logo1.png";
    public static final String FIRMA_VALUE = "classpath:static/firma.png";
    public static final String TIMEZONE_VALUE = "America/Bogota";

    private StudentSeedbedCertificateFields() {}
}
