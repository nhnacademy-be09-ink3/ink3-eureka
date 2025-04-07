package com.nhnacademy.day2assignment.controller;

import com.nhnacademy.day2assignment.domain.Student;
import com.nhnacademy.day2assignment.exception.StudentNotLoginAccessException;
import com.nhnacademy.day2assignment.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@Controller
public class StudentController {
    private StudentRepository repository;
    public StudentController(StudentRepository repository){
        this.repository = repository;
    }

    @ModelAttribute
    public Student getStudent(@PathVariable("studentId") String studentId, @CookieValue(value = "SESSION", required = false) String sessionId){
        if(Objects.isNull(sessionId) || sessionId.isEmpty()){
            throw new StudentNotLoginAccessException();
        }

        Student student = repository.getStudent(studentId);
        return student.maskingPassword();
    }



    @GetMapping("/student/{studentId}")
    public String getStudentView(){
        log.info("sssssssss");
        return "studentView";
    }

    @GetMapping(value = "/student/{studentId}", params = "hideScore=yes")
    public String getStudentViewWithHideScore(ModelMap modelMap, @RequestParam( value = "hideScore", required = false) String hideScore){
        log.info("ssss");
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
    public String updatePostStudent(
            @RequestParam String id,
            @RequestParam String password,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam int grade,
            @RequestParam String evaluation
    ){
        Student updateStudent = new Student(id, password,name,email,grade,evaluation);

        repository.updateStudent(updateStudent);
        log.info("id={}",id);
        return String.format("redirect:/student/%s",id);
    }
}
