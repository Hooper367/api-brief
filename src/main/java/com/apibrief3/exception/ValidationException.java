package com.apibrief3.exception;

import org.springframework.validation.FieldError;

import java.util.List;

public class ValidationException extends RuntimeException{

    public List<FieldError> errors;

    public ValidationException(List<FieldError> validationErrors) {
        this.errors = validationErrors;
    }
}
