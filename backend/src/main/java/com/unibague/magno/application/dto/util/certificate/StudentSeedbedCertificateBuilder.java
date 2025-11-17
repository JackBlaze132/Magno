package com.unibague.magno.application.dto.util.certificate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.unibague.magno.application.dto.util.certificate.StudentSeedbedCertificateFields.*;

public class StudentSeedbedCertificateBuilder {

    private final Map<String, Object> values = new HashMap<>();

    public StudentSeedbedCertificateBuilder nombre(String v) {
        values.put(NOMBRE, v);
        return this;
    }

    public StudentSeedbedCertificateBuilder cedula(String v) {
        values.put(CEDULA, v);
        return this;
    }

    public StudentSeedbedCertificateBuilder semillero(String v) {
        values.put(SEMILLERO, v);
        return this;
    }

    public StudentSeedbedCertificateBuilder grupo(String v) {
        values.put(GRUPO, v);
        return this;
    }

    public StudentSeedbedCertificateBuilder coordinador(String v) {
        values.put(COORDINADOR, v);
        return this;
    }

    public StudentSeedbedCertificateBuilder periodos(List<String> v) {
        values.put(PERIODOS, v);
        return this;
    }

    public StudentSeedbedCertificateBuilder dia(int v) {
        values.put(DIA, v);
        return this;
    }

    public StudentSeedbedCertificateBuilder diaString(String v) {
        values.put(DIA_STRING, v);
        return this;
    }

    public StudentSeedbedCertificateBuilder mes(String v) {
        values.put(MES, v);
        return this;
    }

    public StudentSeedbedCertificateBuilder anio(int v) {
        values.put(ANIO, v);
        return this;
    }

    public StudentSeedbedCertificateBuilder logo1(String v) {
        values.put(LOGO1, v);
        return this;
    }

    public StudentSeedbedCertificateBuilder logo2(String v) {
        values.put(LOGO2, v);
        return this;
    }

    public StudentSeedbedCertificateBuilder firma(String v) {
        values.put(FIRMA, v);
        return this;
    }

    public StudentSeedbedCertificateBuilder director(String v) {
        values.put(DIRECTOR, v);
        return this;
    }

    public StudentSeedbedCertificateBuilder cargo(String v) {
        values.put(CARGO, v);
        return this;
    }

    public Map<String, Object> build() {
        return values;
    }
}
