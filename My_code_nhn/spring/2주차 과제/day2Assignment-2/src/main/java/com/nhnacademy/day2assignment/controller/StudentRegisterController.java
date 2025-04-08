package com.nhnacademy.day2assignment.controller;

import com.nhnacademy.day2assignment.domain.Student;
import com.nhnacademy.day2assignment.domain.StudentRegisterRequest;
import com.nhnacademy.day2assignment.exception.ValidationFailedException;
import com.nhnacademy.day2assignment.repository.StudentRepository;
import com.nhnacademy.day2assignment.validation.StudentRegisterRequestValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/student/register")
public class StudentRegisterController {
    private final StudentRepository repository;
    private final StudentRegisterRequestValidation validation;

    public StudentRegisterController(StudentRepository repository, StudentRegisterRequestValidation validation){
        this.repository = repository;
        this.validation = validation;
    }

    @GetMapping
    public String registerFormStudent(){
        return "studentRegister";
    }

    @PostMapping
    public String registerStudent(@Validated @ModelAttribute StudentRegisterRequest registerRequest,
                                  BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationFailedException(bindingResult);
        }
        repository.registerStudent(new Student(
                registerRequest.getId(),
                registerRequest.getPassword(),
                registerRequest.getName(),
                registerRequest.getEmail(),
                registerRequest.getGrade(),
                registerRequest.getEvaluation()
        ));

        return "redirect:/student/login";
    }

    @InitBinder("studentRegisterRequest")
    protected void initBinder(WebDataBinder binder){
        binder.addValidators(validation);
    }
}
