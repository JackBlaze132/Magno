package com.unibague.magno.infrastructure.exceptionhandler;

import com.unibague.magno.domain.exception.AcademicPeriodNotFoundException;
import org.springframework.http.HttpStatus;
import com.unibague.magno.domain.model.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

import static com.unibague.magno.infrastructure.exceptionhandler.ExceptionResponse.ACADEMIC_PERIOD_NOT_FOUND;

@RestControllerAdvice
public class ControllerAdvisor {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(AcademicPeriodNotFoundException.class)
    public ErrorResponse handleAcademicPeriodNotFoundException() {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(ACADEMIC_PERIOD_NOT_FOUND.getCode());
        errorResponse.setMessage(ACADEMIC_PERIOD_NOT_FOUND.getMessage());
        errorResponse.setTimestamp(LocalDateTime.now());
        return errorResponse;
    }

}
