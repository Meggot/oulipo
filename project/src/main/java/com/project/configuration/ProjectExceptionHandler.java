// Copyright (c) 2019 Travelex Ltd

package com.project.configuration;

import com.common.models.exceptions.ApiError;
import com.common.models.exceptions.ApiValidationError;
import com.common.models.exceptions.EntityValidationException;
import com.common.models.utils.ValidationUtil;
import org.springframework.boot.context.properties.bind.validation.BindValidationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

@ControllerAdvice
public class ProjectExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity handleNoSuchElementException(NoSuchElementException ex) {
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleEntityValidationException(EntityValidationException ex) {
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        apiError.setSubErrors(ex.getApiSubErrors());
        return buildResponseEntity(apiError);
    }

    @ExceptionHandler(BindValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    protected ResponseEntity<Object> handleBindException(BindValidationException ex) {
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setMessage(ex.getMessage());
        return buildResponseEntity(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiError apiError = new ApiError();
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        Set<ApiValidationError> violations = ex.getBindingResult().getAllErrors().stream().map(objectError -> {
            FieldError fieldError = (FieldError) objectError;
            return ValidationUtil.mapFieldErrorToApiValidationError(fieldError);
        }).collect(Collectors.toSet());
        apiError.setSubErrors(violations);
        return buildResponseEntity(apiError);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity(apiError, apiError.getStatus());
    }
}
