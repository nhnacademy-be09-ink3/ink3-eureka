package com.nhnacademy.daily.model.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Member {
    private String id;
    private String name;

    public Member() {}

    @JsonSerialize(using = ToStringSerializer.class)
    private Integer age;
    @JsonProperty("class")
    private String clazz;

    @JsonSerialize(using = ToStringSerializer.class)
    private Locale locale;
}
