package com.nhnacademy.daily.model.project;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class Project {
    private String code;
    @JsonSerialize(using = LocalDateYYYYMMDDSerializer.class)
    @JsonDeserialize(using = LocalDateYYYYMMDDDeserializer.class)
    private LocalDate localDate;
    @JsonSerialize(using = ToStringSerializer.class)
    private ProjectType type;

    public Project() {}
}
