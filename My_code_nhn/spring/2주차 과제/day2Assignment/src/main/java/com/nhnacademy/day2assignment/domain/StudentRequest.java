package com.nhnacademy.day2assignment.domain;

import lombok.Getter;
import lombok.Value;

@Value
public class StudentRequest {
    String id;
    String password;
}
