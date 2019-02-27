// Copyright (c) 2019 Travelex Ltd

package com.common.models.responses;

import com.common.models.exceptions.ApiValidationError;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@NoArgsConstructor
@Data
public class EntityModificationResponse<T> {

    @JsonFormat
    public Set<ApiValidationError> apiSubErrors;

    private boolean error = false;

    private T entity;
}
