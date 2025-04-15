package com.nhnacademy.daily.model.member;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberRequest {
    private String id;
    private String name;
    private Integer age;
    private String clazz;

    public MemberRequest() {}
}

