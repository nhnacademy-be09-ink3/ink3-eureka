package com.nhnacademy.day2assignment.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Student {
    private final String id;
    private String password;
    private String name;
    private String email;
    private int grade;
    private String evaluation;

    public Student(String id, String password, String name, String email, int grade, String evaluation) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.email = email;
        this.grade = grade;
        this.evaluation = evaluation;
    }

    private static final String MASK = "*****";
    public Student maskingPassword(){
        Student newStudent = new Student(this.id, MASK, this.name, this.email, this.grade, this.evaluation);
        return newStudent;
    }
}
