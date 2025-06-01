package com.unibague.magno.domain.model.projections;

// This interface is used to define the structure of the data returned by the seedbed report query.
// The name of the variables in this interface should match the aliases used in the SQL query defined in the repository.
// To avoid unnecessary complexity, we are not using a DTO class for this projection and just let the names in spanish.
public interface SeedbedReportProjection {
    String getPeriodoAcademico();
    String getGrupoDeInvestigacion();
    String getSemillero();
    String getCoordinador();
    String getNombreDelEstudiante();
    String getCodigo();
    String getNombreDelProgramaAcademico();
    Integer getSemestre();
    String getSexo();
}


