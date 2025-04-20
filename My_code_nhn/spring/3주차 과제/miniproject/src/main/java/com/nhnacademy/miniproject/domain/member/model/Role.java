package com.nhnacademy.miniproject.domain.member.model;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Role {
    ADMIN, MEMBER, GOOGLE;

    @JsonCreator
    public static Role setRole(String input){
        for (Role role : Role.values()) {
            if(role.name().equals(input.toUpperCase())){
                return role;
            }
        }
        return MEMBER;
    }
}
