package com.nhnacademy.daily.model.project;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProjectRequest {
    private String code;
    @JsonSerialize(using = ToStringSerializer.class)
    private ProjectType type;

    public ProjectRequest() {}
}
