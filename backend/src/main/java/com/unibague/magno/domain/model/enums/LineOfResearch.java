package com.unibague.magno.domain.model.enums;

/**
 * Research lines associated with investigation groups and seedbeds.
 * <p>
 * Information extracted from https://minciencias.gov.co/la-ciencia-en-cifras/grupos# on 01/18/25 (43 in total).
 * </p>
 */
public enum LineOfResearch {
    AGRICULTURA_SILVICULTURA_Y_PESCA("Agricultura, Silvicultura y Pesca"),
    ARTE("Arte"),
    BIOTECNOLOGIA_AGRICOLA("Biotecnologia Agricola"),
    BIOTECNOLOGIA_AMBIENTAL("Biotecnologia Ambiental"),
    BIOTECNOLOGIA_EN_SALUD("Biotecnologia en Salud"),
    BIOTECNOLIGIA_INDUSTRIAL("Biotecnologia Industrial"),
    CIENCIAS_ANIMALES_Y_LECHERIA("Ciencias Animales y Lecheria"),
    CIENCIAS_BIOLOGICAS("Ciencias Biologicas"),
    CIENCIAS_DE_LA_EDUCACION("Ciencias de la Educacion"),
    CIENCIAS_DE_LA_SALUD("Ciencias de la Salud"),
    CIENCIAS_DE_LA_TIERRA_Y_MEDIOAMBIENTALES("Ciencias de la Tierra y Medioambientales"),
    CIENCIAS_FISICAS("Ciencias Fisicas"),
    CIENCIAS_POLITICAS("Ciencias Politicas"),
    CIENCIAS_QUIMICAS("Ciencias Quimicas"),
    CIENCIAS_VETERINARIAS("Ciencias Veterinarias"),
    COMPUTACION_Y_CIENCIAS_DE_LA_INFORMACION("Computacion y Ciencias de la Informacion"),
    DERECHO("Derecho"),
    ECONOMIA_Y_NEGOCIOS("Economia y Negocios"),
    GEOGRAFIA_SOCIAL_Y_ECONOMIA("Geografia Social y Economia"),
    HISTORIA_Y_ARQUEOLOGIA("Historia y Arqueologia"),
    IDIOMAS_Y_LITERATURA("Idiomas y Literatura"),
    INGENIERIA_AMBIENTAL("Ingenieria Ambiental"),
    INGENIERIA_CIVIL("Ingenieria Civil"),
    INGENIERIA_DE_LOS_MATERIALES("Ingenieria de los Materiales"),
    INGENIERIA_MECANICA("Ingenieria Mecanica"),
    INGENIERIA_MEDICA("Ingenieria Medica"),
    INGENIERIA_QUIMICA("Ingenieria Quimica"),
    INGENIERIAS_ELECTRICA_ELECTRONICA_E_INFORMATICA("Ingenierias Electrica, Electronica e Informatica"),
    MATEMATICA("Matematica"),
    MEDICINA_BASICA("Medicina Basica"),
    MEDICINA_CLINICA("Medicina Clinica"),
    NANOTECNOLOGIA("Nanotecnologia"),
    NO_REGISTRA("No Registra"),
    OTRAS_CIENCIAS_AGRICOLAS("Otras Ciencias Agricolas"),
    OTRAS_CIENCIAS_MEDICAS("Otras Ciencias Medicas"),
    OTRAS_CIENCIAS_NATURALES("Otras Ciencias Naturales"),
    OTRAS_CIENCIAS_SOCIALES("Otras Ciencias Sociales"),
    OTRAS_HISTORIAS("Otras Historias"),
    OTRAS_HUMANIDADES("Otras Humanidades"),
    OTRAS_INGENIERIAS_Y_TECNOLOGIAS("Otras Ingenierias y Tecnologias"),
    PERIODISMO_Y_COMUNICACIONES("Periodismo y Comunicaciones"),
    PSICOLOGIA("Psicologia"),
    SOCIOLOGIA("Sociologia");

    private final String formattedName;

    LineOfResearch(String formattedName) {
        this.formattedName = formattedName;
    }

    /**
     * Returns the display-friendly name for this enum value.
     *
     * @return the formatted name
     */
    public String getFormattedName() {
        return formattedName;
    }
}

