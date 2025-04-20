package com.nhnacademy.miniproject.common.handler;

import com.nhnacademy.miniproject.domain.member.dto.CreateMemberRequest;
import com.nhnacademy.miniproject.domain.member.model.AcademyUser;
import com.nhnacademy.miniproject.domain.member.model.Role;
import com.nhnacademy.miniproject.service.LoginFailureCounterService;
import com.nhnacademy.miniproject.service.MemberService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.UUID;

@Slf4j
@Component
public class CustomOauthAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final RedisTemplate<String, Object> sessionRedisTemplate;
    private final LoginFailureCounterService loginFailureCounterService;
    private final MemberService memberService;

    public CustomOauthAuthenticationSuccessHandler(RedisTemplate<String, Object> sessionRedisTemplate, LoginFailureCounterService loginFailureCounterService, MemberService memberService) {
        this.sessionRedisTemplate = sessionRedisTemplate;
        this.loginFailureCounterService = loginFailureCounterService;
        this.memberService = memberService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String sessionId = UUID.randomUUID().toString();
        Cookie sessionCookie = new Cookie("SESSIONID", sessionId);
        sessionCookie.setHttpOnly(true);
        sessionCookie.setMaxAge(60 * 60);
        sessionCookie.setPath("/");


        // 멤버 정보 저장
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String name = oAuth2User.getAttribute("name");
        String id = oAuth2User.getAttribute("sub"); // 구글의 고유 ID (unique한 ID 역할 가능)
        memberService.createMember(new CreateMemberRequest(id, name,null, null, Role.GOOGLE));


        // 세션 추가
        response.addCookie(sessionCookie);
        sessionRedisTemplate.opsForValue().set(sessionId, id);
        loginFailureCounterService.reset(id);

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
