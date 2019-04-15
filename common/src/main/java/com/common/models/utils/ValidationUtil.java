// Copyright (c) 2019 Travelex Ltd

package com.common.models.utils;

import com.common.models.exceptions.ApiValidationError;
import lombok.experimental.UtilityClass;
import org.springframework.validation.FieldError;

import javax.validation.ConstraintViolation;

@UtilityClass
public class ValidationUtil {

    public ApiValidationError mapConstraintViolationToApiValidationError(ConstraintViolation constraintViolation) {
        return ApiValidationError.builder()
                                 .message(constraintViolation.getMessage())
                                 .rejectedValue(constraintViolation.getInvalidValue())
                                 .field(constraintViolation.getPropertyPath().toString())
                                 .build();
    }

    public ApiValidationError mapFieldErrorToApiValidationError(FieldError field) {
        ApiValidationError apiValidationError = new ApiValidationError();
        apiValidationError.setRejectedValue(field.getRejectedValue());
        apiValidationError.setField(field.getField());
        apiValidationError.setMessage(field.getDefaultMessage());
        return apiValidationError;
    }
}
