package com.nhnacademy.daily.controller;

import com.nhnacademy.daily.exception.memberException.MemberNotFoundException;
import com.nhnacademy.daily.model.member.Member;
import com.nhnacademy.daily.model.member.MemberRequest;
import com.nhnacademy.daily.service.MemberService;
import com.nhnacademy.daily.service.MessageService;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Objects;

@RestController
public class MemberController {

    private final MemberService service;
    private final MessageService messageService;
    public MemberController(MemberService service, MessageService messageService) {
        this.service = service;
        this.messageService = messageService;
    }

    /**
     * 페이지 처리 된 member 리스트 조회
     * @param pageable
     * @return : 전체 member 리스트
     */
    @GetMapping("/members")
    public List<Member> getMembers(Pageable pageable){
        System.out.println(pageable.getPageNumber());
        System.out.println(pageable.getPageSize());
        int pageSize = pageable.getPageSize();
        int pageNumber = pageable.getPageNumber();

        List<Member> memberList = service.getMemberList();

        return memberList;
    }


    /**
     * member 등록
     * @param member
     */
    @PostMapping("/members")
    public void registerMember(@RequestBody MemberRequest member){
        service.registerMember(member);
        messageService.send();
    }


    /**
     * member 조회
     * @param id
     * @return : 조회한 멤버 객체
     */
    @GetMapping("/members/{id}")
    public Member getMember(@PathVariable String id){

        Member member = service.getMember(id);
        if(Objects.isNull(member)){
            throw new MemberNotFoundException();
        }
        return member;
    }
}

