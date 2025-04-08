package com.nhnacademy.day2assignment.exception;

import org.springframework.validation.BindingResult;

import java.util.stream.Collectors;

public class ValidationFailedException extends RuntimeException {
    private final BindingResult bindingResult;
    public ValidationFailedException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }
    public BindingResult getBindingResult() {
        return bindingResult;
    }
}