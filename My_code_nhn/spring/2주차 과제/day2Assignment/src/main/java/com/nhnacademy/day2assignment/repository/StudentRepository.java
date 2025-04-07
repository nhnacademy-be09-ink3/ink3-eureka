package com.nhnacademy.day2assignment.repository;

import com.nhnacademy.day2assignment.domain.Student;

import java.util.HashMap;
import java.util.Map;

public interface StudentRepository {

    Student getStudent(String id);
    void registerStudent(Student student);
    void updateStudent(Student updateStudent);

    boolean doLogin(String id, String password);
}
