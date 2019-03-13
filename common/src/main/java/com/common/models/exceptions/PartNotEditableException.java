package com.common.models.exceptions;

public class PartNotEditableException extends RuntimeException {
    public PartNotEditableException(String message ){
        super(message);
    }
}
