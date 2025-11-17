package com.unibague.magno.infrastructure.exceptionhandler;

import com.unibague.magno.domain.api.IErrorLogServicePort;
import com.unibague.magno.domain.exception.academicperiod.EndDateBeforeStartDateException;
import com.unibague.magno.domain.exception.researchseedbedstudentprofile.*;
import com.unibague.magno.domain.exception.user.FunctionaryNotAllowedToGenerateCertificateException;
import com.unibague.magno.domain.exception.user.NoDataAvailableToGenerateCertificateException;
import com.unibague.magno.domain.model.ErrorLog;
import com.unibague.magno.infrastructure.util.ErrorLogContextService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
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
import com.unibague.magno.domain.exception.investigationgroupprofile.InvestigationGroupProfileDuplicatedInSameAcademicPeriodException;
import com.unibague.magno.domain.exception.investigationgroupprofile.InvestigationGroupProfileNotFoundException;
import com.unibague.magno.domain.exception.researchseedbed.ResearchSeedbedNotFoundException;
import com.unibague.magno.domain.exception.researchseedbedprofile.ResearchSeedbedProfileAlreadyExistsInInvestigationGroup;
import com.unibague.magno.domain.exception.researchseedbedprofile.ResearchSeedbedProfileNotFoundException;
import com.unibague.magno.domain.exception.researchseedbedprofile.SameCoordinatorAndTutorException;
import com.unibague.magno.domain.exception.role.RoleNotFoundException;
import com.unibague.magno.domain.exception.security.InvalidEmailException;
import com.unibague.magno.domain.exception.security.NullEmailException;
import com.unibague.magno.domain.exception.security.UnsupportedPrincipalException;
import com.unibague.magno.domain.exception.studentprofile.StudentProfileAlreadyExistsException;
import com.unibague.magno.domain.exception.studentprofile.StudentProfileNotFoundException;
import com.unibague.magno.domain.exception.user.UserAlreadyExistsException;
import com.unibague.magno.domain.exception.user.UserNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
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
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.unibague.magno.infrastructure.exceptionhandler.ExceptionResponse.ACADEMIC_PERIOD_NOT_FOUND;
import static com.unibague.magno.infrastructure.exceptionhandler.ExceptionResponse.INVALID_SEEDBED_ROLE;

@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvisor {

    private final IErrorLogServicePort errorLogServicePort;
    private final ErrorLogContextService errorLogContextService;

    private void logError(Exception exception, String errorCode, String errorMessage, HttpServletRequest request) {
        try {
            ErrorLog errorLog = errorLogContextService.createErrorLog(exception, errorCode, errorMessage, request);
            errorLogServicePort.save(errorLog);
        } catch (Exception e) {
            // If error logging fails, we don't want to interrupt the original error handling
            // This prevents infinite loops or masking the original error
        }
    }

    private ErrorResponse buildErrorResponse(Exception exception, String code, String message, List<String> details) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(code);
        errorResponse.setMessage(message);
        errorResponse.setDetails(details);
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setExceptionClassName(exception.getClass().getName());
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AcademicPeriodNotFoundException.class)
    public ErrorResponse handleAcademicPeriodNotFoundException(AcademicPeriodNotFoundException exception, HttpServletRequest request) {

        String code = ACADEMIC_PERIOD_NOT_FOUND.getCode();
        String message = ACADEMIC_PERIOD_NOT_FOUND.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EndDateBeforeStartDateException.class)
    public ErrorResponse handleEndDateBeforeStartDateException(EndDateBeforeStartDateException exception, HttpServletRequest request) {

        String code = ExceptionResponse.END_DATE_BEFORE_START_DATE.getCode();
        String message = ExceptionResponse.END_DATE_BEFORE_START_DATE.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));

        logError(exception, code, message, request);

        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RoleNotFoundException.class)
    public ErrorResponse handleRoleNotFoundException(RoleNotFoundException exception, HttpServletRequest request) {

        String code = ExceptionResponse.ROLE_NOT_FOUND.getCode();
        String message = ExceptionResponse.ROLE_NOT_FOUND.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DependencyNotFoundException.class)
    public ErrorResponse handleDependencyNotFoundException(DependencyNotFoundException exception, HttpServletRequest request) {

        String code = ExceptionResponse.DEPENDENCY_NOT_FOUND.getCode();
        String message = ExceptionResponse.DEPENDENCY_NOT_FOUND.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AcademicProgramNotFoundException.class)
    public ErrorResponse handleDependencyNotFoundException(AcademicProgramNotFoundException exception, HttpServletRequest request) {

        String code = ExceptionResponse.ACADEMIC_PROGRAM_NOT_FOUND.getCode();
        String message = ExceptionResponse.ACADEMIC_PROGRAM_NOT_FOUND.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponse handleUserNotFoundException(UserNotFoundException exception, HttpServletRequest request) {

        String code = ExceptionResponse.USER_NOT_FOUND.getCode();
        String message = ExceptionResponse.USER_NOT_FOUND.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ErrorResponse handleUserNotFoundException(UserAlreadyExistsException exception, HttpServletRequest request) {

        String code = ExceptionResponse.USER_ALREADY_EXISTS.getCode();
        String message = ExceptionResponse.USER_ALREADY_EXISTS.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(FunctionaryNotAllowedToGenerateCertificateException.class)
    public ErrorResponse handleFunctionaryNotAllowedException(FunctionaryNotAllowedToGenerateCertificateException exception, HttpServletRequest request) {

        String code = ExceptionResponse.FUNCTIONARY_OR_EXTERNAL_USER_NOT_ALLOWED_TO_GENERATE_CERTIFICATE.getCode();
        String message = ExceptionResponse.FUNCTIONARY_OR_EXTERNAL_USER_NOT_ALLOWED_TO_GENERATE_CERTIFICATE.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));

        logError(exception, code, message, request);

        return errorResponse;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(NoDataAvailableToGenerateCertificateException.class)
    public ErrorResponse handleNoDataAvailableException(NoDataAvailableToGenerateCertificateException exception, HttpServletRequest request) {

        String code = ExceptionResponse.NO_DATA_AVAILABLE_TO_GENERATE_SEEDBED_CERTIFICATE.getCode();
        String message = ExceptionResponse.NO_DATA_AVAILABLE_TO_GENERATE_SEEDBED_CERTIFICATE.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));

        logError(exception, code, message, request);

        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(InvestigationGroupNotFoundException.class)
    public ErrorResponse handleUserNotFoundException(InvestigationGroupNotFoundException exception, HttpServletRequest request) {

        String code = ExceptionResponse.INVESTIGATION_GROUP_NOT_FOUND.getCode();
        String message = ExceptionResponse.INVESTIGATION_GROUP_NOT_FOUND.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(FunctionaryProfileNotFoundException.class)
    public ErrorResponse handleUserNotFoundException(FunctionaryProfileNotFoundException exception, HttpServletRequest request) {

        String code = ExceptionResponse.FUNCTIONARY_PROFILE_NOT_FOUND.getCode();
        String message = ExceptionResponse.FUNCTIONARY_PROFILE_NOT_FOUND.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(FunctionaryProfileAlreadyExistsException.class)
    public ErrorResponse handleUserNotFoundException(FunctionaryProfileAlreadyExistsException exception, HttpServletRequest request) {

        String code = ExceptionResponse.FUNCTIONARY_PROFILE_ALREADY_EXISTS.getCode();
        String message = ExceptionResponse.FUNCTIONARY_PROFILE_ALREADY_EXISTS.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ErrorResponse handleUserNotFoundException(HttpClientErrorException.NotFound exception, HttpServletRequest request) {

        String code = ExceptionResponse.INTEGRA_API_ERROR.getCode();
        String message = ExceptionResponse.INTEGRA_API_ERROR.getMessage();
        List<String> details = Collections
                .singletonList("Possible reasons: Incorrect path in environment variables or VPN access required.");

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message, details);
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ResourceAccessException.class)
    public ErrorResponse handleUserNotFoundException(ResourceAccessException exception, HttpServletRequest request) {

        String code = ExceptionResponse.INTEGRA_VPN_ACCESS_ERROR.getCode();
        String message = ExceptionResponse.INTEGRA_VPN_ACCESS_ERROR.getMessage();
        List<String> details = Collections
                .singletonList("Possible reasons: Incorrect path in environment variables or VPN access required.");

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message, details);

        logError(exception, code, message, request);

        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IntegraUserNotFoundException.class)
    public ErrorResponse handleUserNotFoundException(IntegraUserNotFoundException exception, HttpServletRequest request) {

        String code = ExceptionResponse.INTEGRA_API_ERROR.getCode();
        String message = ExceptionResponse.INTEGRA_API_ERROR.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(InvestigationGroupProfileNotFoundException.class)
    public ErrorResponse handleUserNotFoundException(InvestigationGroupProfileNotFoundException exception, HttpServletRequest request) {

        String code = ExceptionResponse.INVESTIGATION_GROUP_PROFILE_NOT_FOUND.getCode();
        String message = ExceptionResponse.INVESTIGATION_GROUP_PROFILE_NOT_FOUND.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(InvestigationGroupProfileDuplicatedInSameAcademicPeriodException.class)
    public ErrorResponse handleUserNotFoundException(InvestigationGroupProfileDuplicatedInSameAcademicPeriodException exception, HttpServletRequest request) {

        String code = ExceptionResponse.INVESTIGATION_GROUP_PROFILE_DUPLICATED_IN_SAME_ACADEMIC_PERIOD.getCode();
        String message = ExceptionResponse.INVESTIGATION_GROUP_PROFILE_DUPLICATED_IN_SAME_ACADEMIC_PERIOD.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResearchSeedbedNotFoundException.class)
    public ErrorResponse handleUserNotFoundException(ResearchSeedbedNotFoundException exception, HttpServletRequest request) {

        String code = ExceptionResponse.RESEARCH_SEEDBED_NOT_FOUND.getCode();
        String message = ExceptionResponse.RESEARCH_SEEDBED_NOT_FOUND.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResearchSeedbedProfileNotFoundException.class)
    public ErrorResponse handleUserNotFoundException(ResearchSeedbedProfileNotFoundException exception, HttpServletRequest request) {

        String code = ExceptionResponse.RESEARCH_SEEDBED_PROFILE_NOT_FOUND.getCode();
        String message = ExceptionResponse.RESEARCH_SEEDBED_PROFILE_NOT_FOUND.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(SameCoordinatorAndTutorException.class)
    public ErrorResponse handleSameCoordinatorAndTutorException(SameCoordinatorAndTutorException exception, HttpServletRequest request) {

        String code = ExceptionResponse.RESEARCH_SEEDBED_PROFILE_SAME_COORDINATOR_AND_TUTOR.getCode();
        String message = ExceptionResponse.RESEARCH_SEEDBED_PROFILE_SAME_COORDINATOR_AND_TUTOR.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ResearchSeedbedProfileAlreadyExistsInInvestigationGroup.class)
    public ErrorResponse handleRSPAlreadyExistsInIg(ResearchSeedbedProfileAlreadyExistsInInvestigationGroup exception, HttpServletRequest request) {

        String code = ExceptionResponse.RESEARCH_SEEDBED_PROFILE_ALREADY_EXISTS_IN_INVESTIGATION_GROUP.getCode();
        String message = ExceptionResponse.RESEARCH_SEEDBED_PROFILE_ALREADY_EXISTS_IN_INVESTIGATION_GROUP.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(StudentProfileNotFoundException.class)
    public ErrorResponse handleUserNotFoundException(StudentProfileNotFoundException exception, HttpServletRequest request) {

        String code = ExceptionResponse.STUDENT_PROFILE_NOT_FOUND.getCode();
        String message = ExceptionResponse.STUDENT_PROFILE_NOT_FOUND.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(StudentProfileAlreadyExistsException.class)
    public ErrorResponse handleUserNotFoundException(StudentProfileAlreadyExistsException exception, HttpServletRequest request) {

        String code = ExceptionResponse.STUDENT_PROFILE_ALREADY_EXISTS.getCode();
        String message = ExceptionResponse.STUDENT_PROFILE_ALREADY_EXISTS.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResearchSeedbedStudentProfileNotFoundException.class)
    public ErrorResponse handleUserNotFoundException(ResearchSeedbedStudentProfileNotFoundException exception, HttpServletRequest request) {

        String code = ExceptionResponse.RESEARCH_SEEDBED_STUDENT_PROFILE_NOT_FOUND.getCode();
        String message = ExceptionResponse.RESEARCH_SEEDBED_STUDENT_PROFILE_NOT_FOUND.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(StudentProfileAlreadyExistsInSeedbedException.class)
    public ErrorResponse handleStudentProfileAlreadyExistsInSeedbedException(StudentProfileAlreadyExistsInSeedbedException exception, HttpServletRequest request) {

        String code = ExceptionResponse.RESEARCH_SEEDBED_STUDENT_PROFILE_ALREADY_EXISTS.getCode();
        String message = ExceptionResponse.RESEARCH_SEEDBED_STUDENT_PROFILE_ALREADY_EXISTS.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ALeaderAlreadyExistsInSeedbedException.class)
    public ErrorResponse handleLeaderAlreadyExistsInSeedbedException(ALeaderAlreadyExistsInSeedbedException exception, HttpServletRequest request) {

        String code = ExceptionResponse.RESEARCH_SEEDBED_STUDENT_PROFILE_LEADER_ALREADY_EXISTS.getCode();
        String message = ExceptionResponse.RESEARCH_SEEDBED_STUDENT_PROFILE_LEADER_ALREADY_EXISTS.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ExternalUserProfileNotFoundException.class)
    public ErrorResponse handleUserNotFoundException(ExternalUserProfileNotFoundException exception, HttpServletRequest request) {

        String code = ExceptionResponse.EXTERNAL_USER_PROFILE_NOT_FOUND.getCode();
        String message = ExceptionResponse.EXTERNAL_USER_PROFILE_NOT_FOUND.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EnumBadRequestException.class)
    public ErrorResponse handleUserNotFoundException(EnumBadRequestException exception, HttpServletRequest request) {

        String code = ExceptionResponse.ENUM_BAD_REQUEST.getCode();
        String message = ExceptionResponse.ENUM_BAD_REQUEST.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UploadExcelException.class)
    public ErrorResponse handleUserNotFoundException(UploadExcelException exception, HttpServletRequest request) {

        String code = ExceptionResponse.UPLOAD_EXCEL_ERROR.getCode();
        String message = ExceptionResponse.UPLOAD_EXCEL_ERROR.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(UserIsNotExternalException.class)
    public ErrorResponse handleUserNotFoundException(UserIsNotExternalException exception, HttpServletRequest request) {

        String code = ExceptionResponse.USER_IS_NOT_EXTERNAL.getCode();
        String message = ExceptionResponse.USER_IS_NOT_EXTERNAL.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, HttpServletRequest request) {

        BindingResult result = exception.getBindingResult();
        String code = ExceptionResponse.GENERIC_ERROR.getCode();
        String message = "Validation failed for one or more fields.";
        
        // Generating a list of error messages with the field and its associated message
        List<String> errorDetails = result.getFieldErrors().stream()
                .map(fieldError -> String.format("Field '%s': %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(code);
        errorResponse.setMessage(message);
        errorResponse.setDetails(errorDetails);
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setExceptionClassName(exception.getClass().getName());
        
        logError(exception, code, message, request);

        return errorResponse;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException exception, HttpServletRequest request) {

        String errorMessage = exception.getMessage();
        String code;
        String message;

        if (errorMessage != null && errorMessage.contains("Cannot deserialize value of type `com.unibague.magno.domain.model.enums.SeedbedRole`")) {
            code = INVALID_SEEDBED_ROLE.getCode();
            message = INVALID_SEEDBED_ROLE.getMessage();
        } else {
            code = ExceptionResponse.GENERIC_ERROR.getCode();
            message = "Request body is not readable.";
        }
        
        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullEmailException.class)
    public ErrorResponse handleNullEmailException(NullEmailException exception, HttpServletRequest request) {

        String code = ExceptionResponse.NULL_EMAIL.getCode();
        String message = ExceptionResponse.NULL_EMAIL.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidEmailException.class)
    public ErrorResponse handleInvalidEmailException(InvalidEmailException exception, HttpServletRequest request) {

        String code = ExceptionResponse.INVALID_EMAIL.getCode();
        String message = ExceptionResponse.INVALID_EMAIL.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    @ExceptionHandler(NullIntegraResponseException.class)
    public ErrorResponse handleNullIntegraResponseException(NullIntegraResponseException exception, HttpServletRequest request) {

        String code = ExceptionResponse.NULL_INTEGRA_RESPONSE.getCode();
        String message = ExceptionResponse.NULL_INTEGRA_RESPONSE.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnsupportedPrincipalException.class)
    public ErrorResponse handleUnsupportedPrincipalException(UnsupportedPrincipalException exception, HttpServletRequest request) {

        String code = ExceptionResponse.UNSUPPORTED_PRINCIPAL.getCode();
        String message = ExceptionResponse.UNSUPPORTED_PRINCIPAL.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ErrorResponse handleForbiddenExceptions(AuthorizationDeniedException exception, HttpServletRequest request) {

        String code = ExceptionResponse.FORBIDDEN_REQUEST.getCode();
        String message = ExceptionResponse.FORBIDDEN_REQUEST.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorResponse handleSQLException(DataIntegrityViolationException exception, HttpServletRequest request) {

        String code = ExceptionResponse.SQL_EXCEPTION.getCode();
        String message = ExceptionResponse.SQL_EXCEPTION.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse handleGenericError(Exception exception, HttpServletRequest request) {

        String code = ExceptionResponse.GENERIC_ERROR.getCode();
        String message = ExceptionResponse.GENERIC_ERROR.getMessage();

        ErrorResponse errorResponse = buildErrorResponse(exception, code, message,
                Collections.singletonList(exception.getMessage()));
        
        logError(exception, code, message, request);
        
        return errorResponse;
    }
}
