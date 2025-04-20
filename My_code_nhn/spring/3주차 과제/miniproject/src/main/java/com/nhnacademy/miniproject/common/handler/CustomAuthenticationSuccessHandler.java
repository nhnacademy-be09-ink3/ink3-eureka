package com.nhnacademy.miniproject.common.handler;

import com.nhnacademy.miniproject.domain.member.model.AcademyUser;
import com.nhnacademy.miniproject.service.LoginFailureCounterService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final RedisTemplate<String, Object> sessionRedisTemplate;
    private final LoginFailureCounterService loginFailureCounterService;

    public CustomAuthenticationSuccessHandler(RedisTemplate<String, Object> sessionRedisTemplate, LoginFailureCounterService loginFailureCounterService) {
        this.sessionRedisTemplate = sessionRedisTemplate;
        this.loginFailureCounterService = loginFailureCounterService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 왜? 세션값을 랜덤값으로 사용하나?
        String sessionId = UUID.randomUUID().toString();
        Cookie sessionCookie = new Cookie("SESSIONID", sessionId);
        sessionCookie.setHttpOnly(true);
        sessionCookie.setMaxAge(60 * 60);
        sessionCookie.setPath("/");

        response.addCookie(sessionCookie);
        AcademyUser academyUser = (AcademyUser)authentication.getPrincipal();
        sessionRedisTemplate.opsForValue().set(sessionId, academyUser.getUsername() );

        String id = request.getParameter("id");
        loginFailureCounterService.reset(id);

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
