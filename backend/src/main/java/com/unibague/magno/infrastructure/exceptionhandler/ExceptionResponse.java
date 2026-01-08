package com.unibague.magno.infrastructure.exceptionhandler;

import lombok.Getter;

@Getter
public enum ExceptionResponse {

    ACADEMIC_PERIOD_NOT_FOUND("ERR_ACADEMIC_PERIOD_001", "Período académico no encontrado."),
    END_DATE_BEFORE_START_DATE("ERR_ACADEMIC_PERIOD_002", "La fecha de finalización no puede ser anterior a la fecha de inicio."),
    ACADEMIC_PERIOD_NOT_CURRENT("ERR_ACADEMIC_PERIOD_003", "El período académico no está marcado como activo."),
    ACADEMIC_PERIOD_HAS_INVESTIGATION_GROUP_PROFILES("ERR_ACADEMIC_PERIOD_004",
            "No se puede eliminar el período académico porque tiene perfiles de grupo de investigación asociados."),
    ROLE_NOT_FOUND("ERR_ROLE_001", "Rol no encontrado."),
    DEPENDENCY_NOT_FOUND("ERR_DEPENDENCY_001", "Dependencia no encontrada."),
    ACADEMIC_PROGRAM_NOT_FOUND("ERR_ACADEMIC_PROGRAM_001", "Programa académico no encontrado."),
    USER_NOT_FOUND("ERR_USER_001", "Usuario no encontrado."),
    USER_ALREADY_EXISTS("ERR_USER_002", "El usuario ya existe."),
    USER_IS_NOT_EXTERNAL("ERR_USER_003", "El usuario no es externo."),
    FUNCTIONARY_OR_EXTERNAL_USER_NOT_ALLOWED_TO_GENERATE_CERTIFICATE("ERR_USER_004",
            "Solo los estudiantes pueden generar certificados de participación en semilleros."),
    NO_DATA_AVAILABLE_TO_GENERATE_SEEDBED_CERTIFICATE("ERR_USER_005",
            "No hay información disponible para generar el certificado de participación en semillero."),
    DIRI_USER_ALREADY_EXISTS("ERR_USER_006",
            "El usuario DIRI ya está registrado en el sistema."),
    DIRI_USER_NOT_FOUND("ERR_USER_007",
            "El usuario DIRI no está registrado en el sistema."),
    INVESTIGATION_GROUP_NOT_FOUND("ERR_INVESTIGATION_GROUP_001", "Grupo de investigación no encontrado."),
    INVESTIGATION_GROUP_PROFILE_NOT_FOUND("ERR_INVESTIGATION_GROUP_PROFILE_001",
            "Perfil de grupo de investigación no encontrado."),
    INVESTIGATION_GROUP_PROFILE_DUPLICATED_IN_SAME_ACADEMIC_PERIOD("ERR_INVESTIGATION_GROUP_PROFILE_002",
            "Ya existe un perfil de grupo de investigación para el período académico especificado."),
    INVESTIGATION_GROUP_PROFILE_FUNCTIONARY_IS_ALREADY_A_COORDINATOR_EXCEPTION("ERR_INVESTIGATION_GROUP_PROFILE_003",
            "El usuario ya es coordinador de un grupo de investigación en el período académico especificado."),
    INVESTIGATION_GROUP_PROFILE_HAS_RESEARCH_SEEDBED_PROFILES_EXCEPTION("ERR_INVESTIGATION_GROUP_PROFILE_004",
            "El perfil de grupo de investigación tiene perfiles de semilleros de investigación asociados y no puede ser eliminado."),
    RESEARCH_SEEDBED_NOT_FOUND("ERR_RESEARCH_SEEDBED_001", "Semillero de investigación no encontrado."),
    RESEARCH_SEEDBED_PROFILE_NOT_FOUND("ERR_RESEARCH_SEEDBED_PROFILE_001",
            "Perfil de semillero de investigación no encontrado."),
    RESEARCH_SEEDBED_PROFILE_SAME_COORDINATOR_AND_TUTOR("ERR_RESEARCH_SEEDBED_PROFILE_002",
            "El coordinador y el tutor no pueden ser la misma persona."),
    RESEARCH_SEEDBED_PROFILE_ALREADY_EXISTS_IN_ACADEMIC_PERIOD("ERR_RESEARCH_SEEDBED_PROFILE_003",
            "Ya existe un perfil de semillero de investigación en el periodo académico especificado."),
    RESEARCH_SEEDBED_PROFILE_HAS_STUDENTS_ASSOCIATED("ERR_RESEARCH_SEEDBED_PROFILE_004",
            "El perfil de semillero de investigación tiene estudiantes asociados."),
    RESEARCH_SEEDBED_STUDENT_PROFILE_NOT_FOUND("ERR_RESEARCH_SEEDBED_STUDENT_PROFILE_001",
            "Perfil de estudiante de semillero no encontrado."),
    RESEARCH_SEEDBED_STUDENT_PROFILE_ALREADY_EXISTS("ERR_RESEARCH_SEEDBED_STUDENT_PROFILE_002",
            "El perfil del estudiante ya está asociado al semillero de investigación."),
    RESEARCH_SEEDBED_STUDENT_PROFILE_LEADER_ALREADY_EXISTS("ERR_RESEARCH_SEEDBED_STUDENT_PROFILE_003",
            "El semillero ya tiene un líder asignado, remueva el líder actual antes de asignar uno nuevo."),
    FUNCTIONARY_PROFILE_NOT_FOUND("ERR_FUNCTIONARY_PROFILE_001", "Perfil de funcionario no encontrado."),
    FUNCTIONARY_PROFILE_ALREADY_EXISTS("ERR_FUNCTIONARY_PROFILE_002", "El perfil de funcionario ya existe."),
    STUDENT_PROFILE_NOT_FOUND("ERR_STUDENT_PROFILE_001", "Perfil de estudiante no encontrado."),
    STUDENT_PROFILE_ALREADY_EXISTS("ERR_STUDENT_PROFILE_002", "El perfil de estudiante ya existe."),
    EXTERNAL_USER_PROFILE_NOT_FOUND("ERR_EXTERNAL_USER_PROFILE_001", "Perfil de usuario externo no encontrado."),
    ENUM_BAD_REQUEST("ERR_ENUM_001", "Valor de 'enum' inválido."),
    INVALID_SEEDBED_ROLE("ERR_ENUM_002", "Rol de semillero inválido."),
    INTEGRA_API_ERROR("ERR_INTEGRA_API_001", "Error al conectar con la API de Integra."),
    INTEGRA_VPN_ACCESS_ERROR("ERR_INTEGRA_API_002", "Error de acceso VPN a la API de Integra."),
    UPLOAD_EXCEL_ERROR("ERR_UPLOAD_EXCEL_001", "Error al cargar el archivo Excel."),
    NULL_EMAIL("ERR_NULL_EMAIL_001", "El correo electrónico no puede ser nulo."),
    INVALID_EMAIL("ERR_INVALID_EMAIL_001", "El correo proporcionado no es válido, debe ser un correo institucional de Unibagué."),
    NULL_INTEGRA_RESPONSE("ERR_NULL_INTEGRA_RESPONSE_001", "La respuesta de Integra fue nula."),
    UNSUPPORTED_PRINCIPAL("ERR_UNSUPPORTED_PRINCIPAL_001",  "Tipo de autenticación no soportado."),
    NOT_ALLOWED_TO_DO_THIS_ACTION("ERR_SECURITY_001", "No tiene permisos para realizar esta acción."),
    FORBIDDEN_REQUEST("ERR_FORBIDDEN_001", "No tiene autorización para acceder a este recurso."),
    SQL_EXCEPTION("ERR_SQL_001", "Error en la base de datos."),
    VALIDATION_ERROR("ERR_VALIDATION_001", "Error de validación de datos."),
    GENERIC_ERROR("ERR_GENERIC_001", "Error inesperado del sistema.");

    private final String code;
    private final String message;

    ExceptionResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}