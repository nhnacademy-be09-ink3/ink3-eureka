package com.nhnacademy.miniproject.service;

import com.nhnacademy.miniproject.domain.member.model.AcademyUser;
import com.nhnacademy.miniproject.domain.member.model.Member;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberService memberService;

    public CustomUserDetailsService(MemberService memberService) {
        this.memberService = memberService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberService.getMemberById(username);
        if (Objects.isNull(member)){
            throw new UsernameNotFoundException("user not found");
        }
        return new AcademyUser(member);
    }
}
