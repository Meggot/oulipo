// Copyright (c) 2019 Travelex Ltd

package com.common.models.exceptions;

import lombok.Data;

import java.util.Set;

@Data
public class EntityValidationException extends RuntimeException {

    Set<ApiValidationError> apiSubErrors;

    public EntityValidationException(Set<ApiValidationError> apiSubErrors, String errorMessage) {
        super(errorMessage);
        this.apiSubErrors = apiSubErrors;
    }

}
