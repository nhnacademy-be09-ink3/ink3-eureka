package com.nhnacademy.miniproject.service;

import com.nhnacademy.miniproject.domain.member.dto.CreateMemberRequest;
import com.nhnacademy.miniproject.domain.member.dto.MemberLoginRequest;
import com.nhnacademy.miniproject.domain.member.exception.MemberAlreadyExistException;
import com.nhnacademy.miniproject.domain.member.exception.MemberLoginFailedException;
import com.nhnacademy.miniproject.domain.member.exception.MemberNotFoundException;
import com.nhnacademy.miniproject.domain.member.model.Member;
import com.nhnacademy.miniproject.domain.member.model.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
public class MemberService {
    private String HASH_NAME = "Member:";

    private final RedisTemplate<String, Object> redisTemplate;
    private final PasswordEncoder passwordEncoder;

    public MemberService(RedisTemplate<String, Object> redisTemplate, PasswordEncoder passwordEncoder) {
        this.redisTemplate = redisTemplate;
        this.passwordEncoder = passwordEncoder;
    }


    public void createMember(CreateMemberRequest createMemberRequest){
        if(existMember(createMemberRequest.getId())){
            throw new MemberAlreadyExistException();
        }

        if(!createMemberRequest.getRole().equals(Role.GOOGLE)){
            String encodePassword = passwordEncoder.encode(createMemberRequest.getPassword());
            createMemberRequest.setPassword(encodePassword);
            log.info("encodePassword={}",encodePassword);
        }

        Member newMember = createMemberRequest.createMember();
        redisTemplate.opsForHash().put(HASH_NAME+createMemberRequest.getId(),"member", newMember);
    }



    public List<Member> getMemberList(){
        List<Member> memberList = new ArrayList<>();
        Set<String> keys = redisTemplate.keys("Member:*");
        if(Objects.isNull(keys)){
            return null;
        }

        for(String key : keys){
            Object o = redisTemplate.opsForHash().get(key, "member");
            if(o instanceof Member){
                memberList.add((Member) o);
            }
        }

        return memberList;
    }



    public Member getMemberById(String memberId) {
        Object o = redisTemplate.opsForHash().get(HASH_NAME + memberId, "member");
        if (o == null) {
            throw new MemberNotFoundException();
        }

        return (Member) o;
    }



/*    public boolean memberLogin(MemberLoginRequest memberLoginRequest){
        if(Objects.isNull(memberLoginRequest)){
            throw new IllegalArgumentException();
        }

        Member member = getMemberById(memberLoginRequest.getId());
        if (!passwordEncoder.matches(member.getPassword(), memberLoginRequest.getPassword())) {
            throw new MemberLoginFailedException();
        }
        return true;
    }*/



    public boolean existMember(String memberId){
        if(redisTemplate.opsForHash().hasKey(HASH_NAME + memberId,"member")){
            return true;
        }
        return false;
    }
}
