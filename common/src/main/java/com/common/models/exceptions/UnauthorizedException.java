// Copyright (c) 2019 Travelex Ltd

package com.common.models.exceptions;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }

}
