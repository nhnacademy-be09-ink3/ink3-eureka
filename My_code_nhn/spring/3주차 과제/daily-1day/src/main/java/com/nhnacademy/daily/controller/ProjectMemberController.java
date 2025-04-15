package com.nhnacademy.daily.controller;

import com.nhnacademy.daily.model.member.Member;
import com.nhnacademy.daily.service.MemberProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectMemberController {

    private final MemberProjectService service;

    public ProjectMemberController(MemberProjectService service) {
        this.service = service;
    }


    /**
     * project의 member 리스트 조회
     * @param code
     * @return : code에 맞는 project의 속한 member 리스트
     */
    @GetMapping("/projects/{code}/members")
    public List<Member> getProjectMemberList(@PathVariable String code) {
        List<Member> memberList = service.getMember2Project(code);
        return memberList;
    }


    /**
     * project에 멤버 등록
     * @param code
     * @param id
     */
    @PostMapping("/projects/{code}/members")
    public void addMember2Project(@PathVariable String code, @RequestParam String id){
        service.addMember2Project(code, id);
    }
}
