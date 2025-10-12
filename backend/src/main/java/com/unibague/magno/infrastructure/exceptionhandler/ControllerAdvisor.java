package com.unibague.magno.infrastructure.exceptionhandler;

import com.unibague.magno.domain.exception.academicperiod.AcademicPeriodNotFoundException;
import com.unibague.magno.domain.exception.academicprogram.AcademicProgramNotFoundException;
import com.unibague.magno.domain.exception.dependency.DependencyNotFoundException;
import com.unibague.magno.domain.exception.enums.EnumBadRequestException;
import com.unibague.magno.domain.exception.excel.UploadExcelException;
import com.unibague.magno.domain.exception.externaluser.ExternalUserProfileNotFoundException;
import com.unibague.magno.domain.exception.externaluser.UserIsNotExternalException;
import com.unibague.magno.domain.exception.functionaryprofile.FunctionaryProfileAlreadyExistsException;
import com.unibague.magno.domain.exception.functionaryprofile.FunctionaryProfileNotFoundException;
import com.unibague.magno.domain.exception.integra.IntegraUserNotFoundException;
import com.unibague.magno.domain.exception.integra.NullIntegraResponseException;
import com.unibague.magno.domain.exception.investigationgroup.InvestigationGroupNotFoundException;
import com.unibague.magno.domain.exception.investigationgroupprofile.InvestigationGroupProfileNotFoundException;
import com.unibague.magno.domain.exception.researchseedbed.ResearchSeedbedNotFoundException;
import com.unibague.magno.domain.exception.researchseedbedprofile.ResearchSeedbedProfileNotFoundException;
import com.unibague.magno.domain.exception.researchseedbedprofile.SameCoordinatorAndTutorException;
import com.unibague.magno.domain.exception.researchseedbedstudentprofile.ResearchSeedbedStudentProfileNotFoundException;
import com.unibague.magno.domain.exception.role.RoleNotFoundException;
import com.unibague.magno.domain.exception.security.InvalidEmailException;
import com.unibague.magno.domain.exception.security.NullEmailException;
import com.unibague.magno.domain.exception.security.UnsupportedPrincipalException;
import com.unibague.magno.domain.exception.studentprofile.StudentProfileAlreadyExistsException;
import com.unibague.magno.domain.exception.studentprofile.StudentProfileNotFoundException;
import com.unibague.magno.domain.exception.user.UserAlreadyExistsException;
import com.unibague.magno.domain.exception.user.UserNotFoundException;
import org.springframework.http.HttpStatus;
import com.unibague.magno.domain.model.ErrorResponse;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authorization.AuthorizationDeniedException;
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
import static com.unibague.magno.infrastructure.exceptionhandler.ExceptionResponse.INVALID_SEEDBED_ROLE;

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

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ErrorResponse handleUserNotFoundException(UserAlreadyExistsException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ExceptionResponse.USER_ALREADY_EXISTS.getCode());
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

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(FunctionaryProfileAlreadyExistsException.class)
    public ErrorResponse handleUserNotFoundException(FunctionaryProfileAlreadyExistsException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ExceptionResponse.FUNCTIONARY_PROFILE_ALREADY_EXISTS.getCode());
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

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(SameCoordinatorAndTutorException.class)
    public ErrorResponse handleSameCoordinatorAndTutorException(SameCoordinatorAndTutorException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ExceptionResponse.RESEARCH_SEEDBED_PROFILE_SAME_COORDINATOR_AND_TUTOR.getCode());
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

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(StudentProfileAlreadyExistsException.class)
    public ErrorResponse handleUserNotFoundException(StudentProfileAlreadyExistsException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ExceptionResponse.STUDENT_PROFILE_ALREADY_EXISTS.getCode());
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

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(UserIsNotExternalException.class)
    public ErrorResponse handleUserNotFoundException(UserIsNotExternalException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ExceptionResponse.USER_IS_NOT_EXTERNAL.getCode());
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        String errorMessage = exception.getMessage();

        if (errorMessage != null && errorMessage.contains("Cannot deserialize value of type `com.unibague.magno.domain.model.enums.SeedbedRole`")) {
            errorResponse.setCode(INVALID_SEEDBED_ROLE.getCode());
            errorResponse.setMessage(INVALID_SEEDBED_ROLE.getMessage());
        } else {
            errorResponse.setCode(ExceptionResponse.GENERIC_ERROR.getCode());
            errorResponse.setMessage("Request body is not readable.");
        }
        errorResponse.setDetails(Collections.singletonList(errorMessage));
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setExceptionClassName(exception.getClass().getName());
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullEmailException.class)
    public ErrorResponse handleNullEmailException(NullEmailException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ExceptionResponse.NULL_EMAIL.getCode());
        errorResponse.setMessage(ExceptionResponse.NULL_EMAIL.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setExceptionClassName(exception.getClass().getName());
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidEmailException.class)
    public ErrorResponse handleInvalidEmailException(InvalidEmailException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ExceptionResponse.INVALID_EMAIL.getCode());
        errorResponse.setMessage(ExceptionResponse.INVALID_EMAIL.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setExceptionClassName(exception.getClass().getName());
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(NullIntegraResponseException.class)
    public ErrorResponse handleNullIntegraResponseException(NullIntegraResponseException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ExceptionResponse.NULL_INTEGRA_RESPONSE.getCode());
        errorResponse.setMessage(exception.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setExceptionClassName(exception.getClass().getName());
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnsupportedPrincipalException.class)
    public ErrorResponse handleUnsupportedPrincipalException(UnsupportedPrincipalException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ExceptionResponse.UNSUPPORTED_PRINCIPAL.getCode());
        errorResponse.setMessage(ExceptionResponse.UNSUPPORTED_PRINCIPAL.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setExceptionClassName(exception.getClass().getName());
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ErrorResponse handleForbiddenExceptions(AuthorizationDeniedException exception) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ExceptionResponse.FORBIDDEN_REQUEST.getCode());
        errorResponse.setMessage(ExceptionResponse.FORBIDDEN_REQUEST.getMessage());
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
