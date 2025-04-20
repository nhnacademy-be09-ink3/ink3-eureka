package com.nhnacademy.miniproject.common.handler;

import com.nhnacademy.miniproject.service.LoginFailureCounterService;
import com.nhnacademy.miniproject.service.MessageSendService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Component
public class CustomOauthAuthenticationFailureHandler implements AuthenticationFailureHandler {
    private final LoginFailureCounterService loginFailureCounterService;
    private final RedisTemplate<String, Object> sessionRedisTemplate;
    private final MessageSendService messageSendService;
    private String HASH_NAME = "BLOCK:";


    public CustomOauthAuthenticationFailureHandler(LoginFailureCounterService loginFailureCounterService, RedisTemplate<String, Object> sessionRedisTemplate, MessageSendService messageSendService) {
        this.loginFailureCounterService = loginFailureCounterService;
        this.sessionRedisTemplate = sessionRedisTemplate;
        this.messageSendService = messageSendService;
    }


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String id = request.getParameter("id");
        int cnt = loginFailureCounterService.increase(id);
        log.info("id={}, login fail count={}, now={}", id, cnt, LocalDateTime.now());

        if (loginFailureCounterService.check5count(id)) {
            log.info("5번 이상 로그인 실패");
            // 해당 ID 접근 차단하기
            messageSendService.send(id);
            response.sendRedirect("/block");
        }

        response.sendRedirect("/login?error=true");
    }
}
