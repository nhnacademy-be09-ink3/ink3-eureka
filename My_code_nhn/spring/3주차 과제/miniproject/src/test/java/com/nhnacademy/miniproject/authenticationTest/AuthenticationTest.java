package com.nhnacademy.miniproject.authenticationTest;

import com.nhnacademy.miniproject.config.WebConfig;
import com.nhnacademy.miniproject.service.LoginFailureCounterService;
import com.nhnacademy.miniproject.service.MemberService;
import com.nhnacademy.miniproject.service.MessageSendService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import({WebConfig.class, TestSecurityConfig.class})
@AutoConfigureMockMvc
public class AuthenticationTest {

    @Autowired
    private MockMvc mockMvc;

    // SecurityConfig가 의존하는 빈들을 Mock 처리
    @MockitoBean
    private MemberService memberService;

    @MockitoBean
    private MessageSendService messageSendService;

    @MockitoBean
    private LoginFailureCounterService loginFailureCounterService;

    @MockitoBean
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    @DisplayName("admin 페이지 접근 - member 유저")
    @WithMockUser(username = "memberUser", roles = {"MEMBER"})
    void member2admin() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("google 페이지 접근 - member 유저")
    @WithMockUser(username = "memberUser", roles = {"MEMBER"})
    void member2google() throws Exception {
        mockMvc.perform(get("/google"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("member 페이지 접근 - member 유저")
    @WithMockUser(username = "memberUser", roles = {"MEMBER"})
    void member2member() throws Exception {
        mockMvc.perform(get("/member"))
                .andExpect(status().isOk());
    }




    @Test
    @DisplayName("member 페이지 접근 - admin 유저")
    @WithMockUser(username = "memberUser", roles = {"ADMIN"})
    void admin2member() throws Exception {
        mockMvc.perform(get("/member"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("google 페이지 접근 - admin 유저")
    @WithMockUser(username = "memberUser", roles = {"ADMIN"})
    void admin2google() throws Exception {
        mockMvc.perform(get("/google"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("admin 페이지 접근 - admin 유저")
    @WithMockUser(username = "memberUser", roles = {"ADMIN"})
    void admin2admin() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk());
    }




    @Test
    @DisplayName("admin 페이지 접근 - google 유저")
    @WithMockUser(username = "memberUser", roles = {"GOOGLE"})
    void google2member() throws Exception {
        mockMvc.perform(get("/member"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("google 페이지 접근 - google 유저")
    @WithMockUser(username = "memberUser", roles = {"GOOGLE"})
    void google2admin() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("member 페이지 접근 - google 유저")
    @WithMockUser(username = "memberUser", roles = {"GOOGLE"})
    void google2google() throws Exception {
        mockMvc.perform(get("/google"))
                .andExpect(status().isOk());
    }
}
