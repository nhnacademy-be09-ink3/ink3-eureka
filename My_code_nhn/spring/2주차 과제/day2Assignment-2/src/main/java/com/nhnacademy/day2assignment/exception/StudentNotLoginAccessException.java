package com.nhnacademy.day2assignment.exception;

public class StudentNotLoginAccessException extends RuntimeException {

    public StudentNotLoginAccessException() {
        super("로그인이 필요합니다.");
    }

    public StudentNotLoginAccessException(String message) {
        super(message);
    }
}