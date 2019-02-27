// Copyright (c) 2019 Travelex Ltd

package com.common.models.utils;

import com.common.models.exceptions.ApiValidationError;
import lombok.experimental.UtilityClass;

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
}
