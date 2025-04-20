package com.nhnacademy.miniproject.domain.member.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

public class AcademyUser implements UserDetails{
    private String id;
    private String name;
    private String password;
    private Integer age;
    private Role role;

    public AcademyUser(Member member) {
        this.id = member.getId();
        this.password = member.getPassword();
        this.name = member.getName();
        this.age = member.getAge();
        this.role = member.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = "ROLE_" + this.role;
        return Arrays.asList(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.id;
    }
}
