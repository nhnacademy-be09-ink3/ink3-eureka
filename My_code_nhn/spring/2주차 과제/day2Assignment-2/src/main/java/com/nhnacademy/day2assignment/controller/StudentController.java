package com.nhnacademy.day2assignment.controller;

import com.nhnacademy.day2assignment.domain.Student;
import com.nhnacademy.day2assignment.domain.StudentRegisterRequest;
import com.nhnacademy.day2assignment.exception.StudentNotLoginAccessException;
import com.nhnacademy.day2assignment.exception.ValidationFailedException;
import com.nhnacademy.day2assignment.repository.StudentRepository;
import com.nhnacademy.day2assignment.validation.StudentRegisterRequestValidation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@Controller
public class StudentController {
    private final StudentRepository repository;
    private final StudentRegisterRequestValidation validation;

    public StudentController(StudentRepository repository, StudentRegisterRequestValidation validation){
        this.repository = repository;
        this.validation = validation;
    }


    @ModelAttribute("student")
    public Student getStudent(@PathVariable("studentId") String studentId, @CookieValue(value = "SESSION", required = false) String sessionId){
        if(Objects.isNull(sessionId) || sessionId.isEmpty()){
            throw new StudentNotLoginAccessException();
        }

        Student student = repository.getStudent(studentId);
        return student.maskingPassword();
    }

    @GetMapping("/student/{studentId}")
    public String getStudentView(){
        return "studentView";
    }

    @GetMapping(value = "/student/{studentId}", params = "hideScore=yes")
    public String getStudentViewWithHideScore(ModelMap modelMap, @RequestParam( value = "hideScore", required = false) String hideScore){
        if(Objects.nonNull(hideScore)){
            modelMap.put("hideScore","Yes");
            return "studentView";
        }
        return "studentView";
    }

    @GetMapping("/student/{studentId}/update")
    public String updateGetStudent(){
        return "studentUpdate";
    }

    @PostMapping("/student/{studentId}/update")
    public String updatePostStudent(@Validated @ModelAttribute StudentRegisterRequest registerRequest,
                                    BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new ValidationFailedException(bindingResult);
        }
        Student updateStudent = new Student(
                    registerRequest.getId(),
                    registerRequest.getPassword(),
                    registerRequest.getName(),
                    registerRequest.getEmail(),
                    registerRequest.getGrade(),
                    registerRequest.getEvaluation()
                );

        repository.updateStudent(updateStudent);
        return String.format("redirect:/student/%s",registerRequest.getId());
    }


    @InitBinder("studentRegisterRequest")
    protected void initBinder(WebDataBinder binder){
        binder.addValidators(validation);
    }

    @ExceptionHandler(StudentNotLoginAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void studentNotLoginAccessException(){
    }
}
