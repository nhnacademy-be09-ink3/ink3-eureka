package com.nhnacademy.daily.model.member;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Locale {
    KO, EN, JP;

    @JsonCreator
    public static Locale fromString(String str){
        for(Locale value : Locale.values()){
            if(value.name().equals(str)){
                return value;
            }
        }
        return Locale.KO;
    }

    @JsonValue
    public String toJson(){
        return this.name().toLowerCase();
    }
}
