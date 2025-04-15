package com.nhnacademy.daily.service;

import com.nhnacademy.daily.exception.memberException.MemberAlreadyExistException;
import com.nhnacademy.daily.model.member.Locale;
import com.nhnacademy.daily.model.member.Member;
import com.nhnacademy.daily.model.member.MemberRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MemberService {

    private final RedisTemplate<String, Object> redisTemplate;
    private String HASH_NAME = "Member:";

    public MemberService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;

        Member member1 = new Member("1", "백종원",  50, "A", Locale.KO);
        Member member2 = new Member("2", "백종원",  50, "A", Locale.KO);
        Member member3 = new Member("3", "백종원",  50, "A", Locale.KO);
        redisTemplate.opsForHash().put(HASH_NAME+member1.getId(), "member", member1);
        redisTemplate.opsForHash().put(HASH_NAME+member2.getId(), "member", member2);
        redisTemplate.opsForHash().put(HASH_NAME+member3.getId(), "member", member3);
    }

    public void registerMember(MemberRequest memberRequest){
        if(existMember(memberRequest.getId())){
            throw new MemberAlreadyExistException();
        }
        Member member = new Member(memberRequest.getId(), memberRequest.getName(), memberRequest.getAge(), memberRequest.getClazz(), Locale.KO);
        redisTemplate.opsForHash().put(HASH_NAME+member.getId(), "member", member);
    }

    public Member getMember(String id){
        Object o = redisTemplate.opsForHash().get(HASH_NAME+id, "member");
        if(o==null){
            throw new RuntimeException();
        }
        return (Member) o;
    }

    public List<Member> getMemberList(){
        List<Member> returnList = new ArrayList<>();
        Set<String> keys = redisTemplate.keys("Member:*");
        if(Objects.isNull(keys)){
            return null;
        }

        for(String key : keys){
            Object o = redisTemplate.opsForHash().get(key, "member");
            if(o instanceof Member){
                returnList.add((Member) o);
            }
        }

        return returnList;
    }

    public boolean existMember(String memberId){
        if (redisTemplate.opsForHash().hasKey(HASH_NAME + memberId, "member")) {
            return true;
        }
        return false;
    }
}
