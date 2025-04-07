package com.nhnacademy.day2assignment.controller;

import com.nhnacademy.day2assignment.domain.Student;
import com.nhnacademy.day2assignment.domain.StudentRequest;
import com.nhnacademy.day2assignment.repository.StudentRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/student/login")
public class StudentLoginController {
    private final StudentRepository repository;

    public StudentLoginController(StudentRepository repository) {
        this.repository = repository;
    }


    @GetMapping
    public String login(Model model,
                        @CookieValue(name = "SESSION", defaultValue = "") String sessionId,
                        HttpServletRequest request){
        log.info("sessionId={}",sessionId);
        if(Objects.isNull(sessionId) || sessionId.isEmpty()){
            return "loginForm";
        }

        HttpSession session = request.getSession();
        Student student = (Student) session.getAttribute("student");
        log.info("stduent={}",student.getId());
        model.addAttribute("student", student.maskingPassword());
        return "studentView";
    }


    @PostMapping
    public ModelAndView doLogin(@ModelAttribute StudentRequest studentRequest,
                                HttpServletRequest request,
                                HttpServletResponse response){
        if(!repository.doLogin(studentRequest.getId(),studentRequest.getPassword())){
            ModelAndView mav = new ModelAndView("redirect:/student/login");
            return mav;
        }
        HttpSession session = request.getSession();
        Student student = repository.getStudent(studentRequest.getId());
        session.setAttribute("student", student);

        Cookie cookie = new Cookie("SESSION", session.getId());
        response.addCookie(cookie);
        ModelAndView mav = new ModelAndView(String.format("redirect:/student/%s",studentRequest.getId()));
        mav.addObject("student",student);
        return mav;
    }
}
