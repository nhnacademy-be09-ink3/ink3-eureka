package com.nhnacademy.daily.model.project;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ProjectType {
    PUBLIC, PRIVATE;

    @JsonCreator
    public static ProjectType fromString(String str){
        for(ProjectType value : ProjectType.values()){
            if(value.name().equals(str)){
                return value;
            }
        }
        return PUBLIC;
    }

    @JsonValue
    public String toJson(){
        return this.name().toLowerCase();
    }

}
