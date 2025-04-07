package com.nhnacademy.day2assignment.controller;

import com.nhnacademy.day2assignment.domain.Student;
import com.nhnacademy.day2assignment.domain.StudentRegisterRequest;
import com.nhnacademy.day2assignment.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/student/register")
public class StudentRegisterController {
    private StudentRepository repository;
    public StudentRegisterController(StudentRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public String registerFormStudent(){
        return "studentRegister";
    }

    @PostMapping
    public String registerStudent(@ModelAttribute StudentRegisterRequest registerRequest){
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
}
