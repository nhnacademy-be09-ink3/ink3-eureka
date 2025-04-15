package com.nhnacademy.daily.service;

import com.nhnacademy.daily.exception.memberException.MemberNotFoundException;
import com.nhnacademy.daily.exception.projectException.ProjectNotFoundException;
import com.nhnacademy.daily.model.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class MemberProjectService {
    private final MemberService memberService;
    private final ProjectService projectService;

    private final RedisTemplate<String, Object> redisTemplate;
    private String HASH_NAME = "MemberProject:";

    public MemberProjectService(MemberService memberService, ProjectService projectService, RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;

        this.memberService = memberService;
        this.projectService = projectService;
    }

    public void addMember2Project(String projectId, String memberId){
        if(!memberService.existMember(memberId)){
            throw new MemberNotFoundException();
        }
        if(!projectService.existProject(projectId)){
            throw new ProjectNotFoundException();
        }

        Member member = memberService.getMember(memberId);
        String key = HASH_NAME + projectId;

        List<Member> memberList = (List<Member>) redisTemplate.opsForValue().get(key);
        if (memberList == null) {
            memberList = new ArrayList<>();
        }

        if (!memberList.contains(member)) {
            memberList.add(member);
            redisTemplate.opsForValue().set(key, memberList);
        }
    }

    public List<Member> getMember2Project(String projectId){
        if(!projectService.existProject(projectId)){
            throw new ProjectNotFoundException();
        }

        String key = HASH_NAME + projectId;
        return (List<Member>) redisTemplate.opsForValue().get(key);
    }
}
