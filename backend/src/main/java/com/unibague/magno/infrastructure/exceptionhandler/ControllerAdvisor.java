package com.unibague.magno.infrastructure.exceptionhandler;

import com.unibague.magno.domain.exception.academicperiod.AcademicPeriodNotFoundException;
import com.unibague.magno.domain.exception.academicprogram.AcademicProgramNotFoundException;
import com.unibague.magno.domain.exception.dependency.DependencyNotFoundException;
import com.unibague.magno.domain.exception.enums.EnumBadRequestException;
import com.unibague.magno.domain.exception.excel.UploadExcelException;
import com.unibague.magno.domain.exception.externaluser.ExternalUserProfileNotFoundException;
import com.unibague.magno.domain.exception.functionaryprofile.FunctionaryProfileNotFoundException;
import com.unibague.magno.domain.exception.integra.IntegraUserNotFoundException;
import com.unibague.magno.domain.exception.investigationgroup.InvestigationGroupNotFoundException;
import com.unibague.magno.domain.exception.investigationgroupprofile.InvestigationGroupProfileNotFoundException;
import com.unibague.magno.domain.exception.researchseedbed.ResearchSeedbedNotFoundException;
import com.unibague.magno.domain.exception.researchseedbedprofile.ResearchSeedbedProfileNotFoundException;
import com.unibague.magno.domain.exception.researchseedbedstudentprofile.ResearchSeedbedStudentProfileNotFoundException;
import com.unibague.magno.domain.exception.role.RoleNotFoundException;
import com.unibague.magno.domain.exception.studentprofile.StudentProfileNotFoundException;
import com.unibague.magno.domain.exception.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import com.unibague.magno.domain.model.ErrorResponse;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.unibague.magno.infrastructure.exceptionhandler.ExceptionResponse.ACADEMIC_PERIOD_NOT_FOUND;

@RestControllerAdvice
public class ControllerAdvisor {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AcademicPeriodNotFoundException.class)
    public ErrorResponse handleAcademicPeriodNotFoundException(AcademicPeriodNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ACADEMIC_PERIOD_NOT_FOUND.getCode());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setExceptionClassName(exception.getClass().getName());
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RoleNotFoundException.class)
    public ErrorResponse handleRoleNotFoundException(RoleNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ExceptionResponse.ROLE_NOT_FOUND.getCode());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setExceptionClassName(exception.getClass().getName());
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DependencyNotFoundException.class)
    public ErrorResponse handleDependencyNotFoundException(DependencyNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ExceptionResponse.DEPENDENCY_NOT_FOUND.getCode());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setExceptionClassName(exception.getClass().getName());
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AcademicProgramNotFoundException.class)
    public ErrorResponse handleDependencyNotFoundException(AcademicProgramNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ExceptionResponse.ACADEMIC_PROGRAM_NOT_FOUND.getCode());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setExceptionClassName(exception.getClass().getName());
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponse handleUserNotFoundException(UserNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ExceptionResponse.USER_NOT_FOUND.getCode());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setExceptionClassName(exception.getClass().getName());
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(InvestigationGroupNotFoundException.class)
    public ErrorResponse handleUserNotFoundException(InvestigationGroupNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ExceptionResponse.INVESTIGATION_GROUP_NOT_FOUND.getCode());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setExceptionClassName(exception.getClass().getName());
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(FunctionaryProfileNotFoundException.class)
    public ErrorResponse handleUserNotFoundException(FunctionaryProfileNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ExceptionResponse.FUNCTIONARY_PROFILE_NOT_FOUND.getCode());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setExceptionClassName(exception.getClass().getName());
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ErrorResponse handleUserNotFoundException(HttpClientErrorException.NotFound exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ExceptionResponse.INTEGRA_API_ERROR.getCode());
        errorResponse.setMessage(ExceptionResponse.INTEGRA_API_ERROR.getMessage());
        errorResponse.setDetails(
                Collections.singletonList
                        ("Possible reasons: Incorrect path in environment variables or VPN access required."));
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setExceptionClassName(exception.getClass().getName());
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IntegraUserNotFoundException.class)
    public ErrorResponse handleUserNotFoundException(IntegraUserNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ExceptionResponse.INTEGRA_API_ERROR.getCode());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setExceptionClassName(exception.getClass().getName());
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(InvestigationGroupProfileNotFoundException.class)
    public ErrorResponse handleUserNotFoundException(InvestigationGroupProfileNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ExceptionResponse.INVESTIGATION_GROUP_PROFILE_NOT_FOUND.getCode());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setExceptionClassName(exception.getClass().getName());
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResearchSeedbedNotFoundException.class)
    public ErrorResponse handleUserNotFoundException(ResearchSeedbedNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ExceptionResponse.RESEARCH_SEEDBED_NOT_FOUND.getCode());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setExceptionClassName(exception.getClass().getName());
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResearchSeedbedProfileNotFoundException.class)
    public ErrorResponse handleUserNotFoundException(ResearchSeedbedProfileNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ExceptionResponse.RESEARCH_SEEDBED_PROFILE_NOT_FOUND.getCode());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setExceptionClassName(exception.getClass().getName());
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(StudentProfileNotFoundException.class)
    public ErrorResponse handleUserNotFoundException(StudentProfileNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ExceptionResponse.STUDENT_PROFILE_NOT_FOUND.getCode());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setExceptionClassName(exception.getClass().getName());
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResearchSeedbedStudentProfileNotFoundException.class)
    public ErrorResponse handleUserNotFoundException(ResearchSeedbedStudentProfileNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ExceptionResponse.RESEARCH_SEEDBED_STUDENT_PROFILE_NOT_FOUND.getCode());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setExceptionClassName(exception.getClass().getName());
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ExternalUserProfileNotFoundException.class)
    public ErrorResponse handleUserNotFoundException(ExternalUserProfileNotFoundException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ExceptionResponse.EXTERNAL_USER_PROFILE_NOT_FOUND.getCode());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setExceptionClassName(exception.getClass().getName());
        return errorResponse;
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EnumBadRequestException.class)
    public ErrorResponse handleUserNotFoundException(EnumBadRequestException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ExceptionResponse.ENUM_BAD_REQUEST.getCode());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setExceptionClassName(exception.getClass().getName());
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UploadExcelException.class)
    public ErrorResponse handleUserNotFoundException(UploadExcelException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ExceptionResponse.UPLOAD_EXCEL_ERROR.getCode());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setExceptionClassName(exception.getClass().getName());
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        BindingResult result = exception.getBindingResult();
        
        // Generating a list of error messages with the field and its associated message
        List<String> errorDetails = result.getFieldErrors().stream()
                .map(fieldError -> String.format("Field '%s': %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ExceptionResponse.GENERIC_ERROR.getCode());
        errorResponse.setMessage("Validation failed for one or more fields.");
        errorResponse.setDetails(errorDetails);
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setExceptionClassName(exception.getClass().getName());

        return errorResponse;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleGenericError(Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ExceptionResponse.GENERIC_ERROR.getCode());
        errorResponse.setMessage(ExceptionResponse.GENERIC_ERROR.getMessage());
        errorResponse.setDetails(Collections.singletonList(exception.getMessage()));
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setExceptionClassName(exception.getClass().getName());
        return errorResponse;
    }
}
