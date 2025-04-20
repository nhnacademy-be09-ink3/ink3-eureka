package com.nhnacademy.miniproject.controller;

import com.nhnacademy.miniproject.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
@AutoConfigureMockMvc(addFilters = true )
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private MemberService memberService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("기본 페이지 접근 성공")
    void home() throws Exception {
        mockMvc.perform(get("/")
                        .with(user("testUser").roles("MEMBER")))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }
}