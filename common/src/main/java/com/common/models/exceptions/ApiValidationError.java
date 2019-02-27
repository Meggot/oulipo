// Copyright (c) 2019 Travelex Ltd

package com.common.models.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiValidationError {
    private String field;
    private Object rejectedValue;
    private String message;
}
