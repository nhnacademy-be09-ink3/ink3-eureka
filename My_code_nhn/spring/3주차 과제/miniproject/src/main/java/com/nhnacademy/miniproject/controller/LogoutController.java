package com.nhnacademy.miniproject.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.util.Objects;

@Slf4j
@Controller
public class LogoutController {
    private final RedisTemplate<String, Object> redisTemplate;

    public LogoutController(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @GetMapping(value = "/logout")
    public void logout(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        log.info("로그이웃 visit");

        // 쿠키에 있는 세션값을 찾음
        String sessionId = null;
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if(cookie.getName().equals("SESSIONID")){
                sessionId = cookie.getValue();
            }
        }
        if(Objects.isNull(sessionId)){
            throw new RuntimeException("사용자 세션값을 찾지 못했습니다.");
        }

        log.info("sessionId={}",sessionId);


        Cookie newCookie = new Cookie("SESSIONID", null);
        newCookie.setPath("/");
        newCookie.setMaxAge(0);
        response.addCookie(newCookie);


        // redis에 저장되어 있는 세션값을 제거하기
        redisTemplate.delete(sessionId);
        // SecurityContextHolder.getContext()에서 제거
        SecurityContextHolder.clearContext();
        // 다시 /화면으로 로그인 화면 띄우기
        response.sendRedirect("/login");
    }
}
