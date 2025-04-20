package com.nhnacademy.miniproject.controller;

import com.nhnacademy.miniproject.domain.member.dto.CreateMemberRequest;
import com.nhnacademy.miniproject.domain.member.exception.MemberNotFoundException;
import com.nhnacademy.miniproject.domain.member.model.Member;
import com.nhnacademy.miniproject.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    @GetMapping("/members")
    public List<Member> getMembers(Pageable pageable){
        log.info("page={}",pageable.getPageNumber());
        log.info("size={}",pageable.getPageSize());
        List<Member> memberList = memberService.getMemberList();
        return memberList;
    }


    @GetMapping("/members/{memberId}")
    public Member getMember(@PathVariable String memberId){
        if(Objects.isNull(memberId) || memberId.isEmpty()){
            throw new IllegalArgumentException();
        }

        if(!memberService.existMember(memberId)){
            throw new MemberNotFoundException();
        }

        Member member = memberService.getMemberById(memberId);
        return member;
    }


    @PostMapping("/members")
    public ResponseEntity createMember(@RequestBody CreateMemberRequest createMemberRequest){
        if(Objects.isNull(createMemberRequest)){
            throw new IllegalArgumentException();
        }

        memberService.createMember(createMemberRequest);
        return ResponseEntity.ok().build();
    }
}
