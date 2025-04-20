package com.nhnacademy.miniproject.domain.member.dto;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.nhnacademy.miniproject.domain.member.model.Member;
import com.nhnacademy.miniproject.domain.member.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateMemberRequest {
    private String id;
    private String name;
    private String password;
    private Integer age;
    @JsonSerialize(using = ToStringSerializer.class)
    private Role role;

    public Member createMember(){
        Member newMember = new Member(this.id,
                                    this.name,
                                    this.password,
                                    this.age,
                                    this.role);
        return newMember;
    }
}
