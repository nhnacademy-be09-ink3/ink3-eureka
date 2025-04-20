package com.nhnacademy.miniproject.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.miniproject.domain.member.dto.CreateMemberRequest;
import com.nhnacademy.miniproject.domain.member.exception.MemberNotFoundException;
import com.nhnacademy.miniproject.domain.member.model.Member;
import com.nhnacademy.miniproject.domain.member.model.Role;
import com.nhnacademy.miniproject.service.MemberService;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
@AutoConfigureMockMvc(addFilters = false)
class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MemberService memberService;


    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("멤버 리스트 조회")
    void getMembers() throws Exception {
        // given
        Member member1 = new Member("1", "nhn1", "1", 20, Role.MEMBER);
        Member member2 = new Member("2", "nhn2", "2", 20, Role.ADMIN);
        List<Member> memberList = new ArrayList<>();
        memberList.add(member1);
        memberList.add(member2);

        Pageable pageable = (Pageable) PageRequest.of(1, 5);
        when(memberService.getMemberList()).thenReturn(memberList);

        // when, then
        mockMvc.perform(get("/members")
                        .param("page", "0")
                        .param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }


    @Test
    @DisplayName("멤버 단일 조회 성공")
    void getMember() throws Exception {
        // given
        Member member = new Member("1", "nhn", "1", 20, Role.MEMBER);

        when(memberService.existMember("1")).thenReturn(true);
        when(memberService.getMemberById("1")).thenReturn(member);


        // when, then
        mockMvc.perform(get("/members/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.name").value("nhn"));
    }


    @Test
    @DisplayName("멤버 단일 조회 실패 - 존재하지 않는 멤버")
    void failGetMember() throws Exception {
        when(memberService.existMember("notExistId")).thenReturn(false);

        Assertions.assertThrows(ServletException.class, () -> mockMvc.perform(get("/members/notExistId")));
    }



    @Test
    @DisplayName("멤버 생성")
    void createMember() throws Exception {
        // given
        CreateMemberRequest newMember = new CreateMemberRequest("1", "nhn", "1", 20, Role.MEMBER);

        // when, then
        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newMember)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("멤버 생성 실패 - 멤버값 null 일 때")
    void failCreateMember() throws Exception {
        mockMvc.perform(post("/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(""))
                .andExpect(status().isBadRequest());
    }
}