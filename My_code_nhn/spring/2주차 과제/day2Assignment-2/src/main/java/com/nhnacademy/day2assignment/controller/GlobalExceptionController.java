package com.nhnacademy.day2assignment.controller;

import com.nhnacademy.day2assignment.exception.StudentNotLoginAccessException;
import com.nhnacademy.day2assignment.exception.ValidationFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(ValidationFailedException.class)
    public String validationFailedException(ValidationFailedException ex, Model model){
        BindingResult bindingResult = ex.getBindingResult();
        String collect = bindingResult.getAllErrors()
                .stream()
                .map(error -> new StringBuilder().append("ObjectName=").append(error.getObjectName())
                        .append(",Message=").append(error.getDefaultMessage())
                        .append(",code=").append(error.getCode()))
                .collect(Collectors.joining(" | "));


        model.addAttribute("errorMessage", collect);
        model.addAttribute("exception", ex);
        return "error";
    }


    @ExceptionHandler(Exception.class)
    public String exception(Exception ex, Model model){
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("exception", ex);
        return "error";
    }
}
