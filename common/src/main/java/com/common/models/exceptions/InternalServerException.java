// Copyright (c) 2019 Travelex Ltd

package com.common.models.exceptions;

public class InternalServerException extends RuntimeException {

    public InternalServerException(String message) {
        super(message);
    }
}
