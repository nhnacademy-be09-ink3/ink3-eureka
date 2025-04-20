package com.nhnacademy.miniproject.service;

import com.nhnacademy.miniproject.domain.member.dto.CreateMemberRequest;
import com.nhnacademy.miniproject.domain.member.exception.MemberAlreadyExistException;
import com.nhnacademy.miniproject.domain.member.exception.MemberNotFoundException;
import com.nhnacademy.miniproject.domain.member.model.Member;
import com.nhnacademy.miniproject.domain.member.model.Role;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {
    private String HASH_NAME = "Member:";

    @Mock
    private RedisTemplate<String, Object> redisTemplate;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private HashOperations<String, Object, Object> hashOperations;

    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    void setUp() {
        when(redisTemplate.opsForHash()).thenReturn(hashOperations);
    }

    @Test
    @DisplayName("멤버 생성 성공")
    void createMember() {
        // given
        CreateMemberRequest requestMember = new CreateMemberRequest("1", "nhn", "1", 20, Role.MEMBER);
        when(hashOperations.hasKey(anyString(), anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // when
        memberService.createMember(requestMember);

        // then
        verify(hashOperations).put(eq(HASH_NAME + requestMember.getId()), eq("member"), any(Member.class));
    }

    @Test
    @DisplayName("이미 동일한 id의 멤버가 존재할 때 멤버 생성 실패")
    void failCreateMember() {
        // given
        CreateMemberRequest requestMember = new CreateMemberRequest("1", "nhn", "1", 20, Role.MEMBER);
        when(hashOperations.hasKey(anyString(), anyString())).thenReturn(true);

        // when, then
        Assertions.assertThrows(MemberAlreadyExistException.class,()-> memberService.createMember(requestMember));
    }


    @Test
    @DisplayName("멤버 리스트 조회 성공")
    void getMemberList() {
        // given
        Set<String> keys = new HashSet<>();
        keys.add("Member:1");
        Member member1 = new Member("1", "nhn1", "1", 21, Role.MEMBER);
        when(redisTemplate.keys(anyString())).thenReturn(keys);
        when(hashOperations.get("Member:1", "member")).thenReturn(member1);


        // when
        List<Member> memberList = memberService.getMemberList();

        // then
        Assertions.assertTrue(memberList.size()==1);
        Assertions.assertTrue(memberList.get(0).getId().equals(member1.getId()));
    }

    @Test
    @DisplayName("멤버 조회 성공")
    void getMemberById() {
        // given
        Member member1 = new Member("1", "nhn1", "1", 21, Role.MEMBER);
        when(hashOperations.get(anyString(), anyString())).thenReturn(member1);

        // when
        Member member = memberService.getMemberById(member1.getId());

        // then
        Assertions.assertTrue(member1.getId().equals(member.getId()));
    }

    @Test
    @DisplayName("멤버 조회 실패")
    void failGetMemberById() {
        // given
        Member member1 = new Member("1", "nhn1", "1", 21, Role.MEMBER);
        when(hashOperations.get(anyString(), anyString())).thenReturn(null);

        // when, then
        Assertions.assertThrows(MemberNotFoundException.class, () -> memberService.getMemberById(member1.getId()));
    }



    @Test
    @DisplayName("멤버 존재 여부")
    void existMember() {
        // given
        CreateMemberRequest requestMember = new CreateMemberRequest("1", "nhn", "1", 20, Role.MEMBER);
        when(hashOperations.hasKey(anyString(), anyString())).thenReturn(true);

        // when
        boolean checkMemberExist = memberService.existMember(requestMember.getId());

        // then
        Assertions.assertTrue(checkMemberExist);
    }
}