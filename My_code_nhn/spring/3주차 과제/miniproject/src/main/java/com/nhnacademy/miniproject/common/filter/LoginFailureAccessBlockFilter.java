package com.nhnacademy.miniproject.common.filter;

import com.nhnacademy.miniproject.domain.member.model.Member;
import com.nhnacademy.miniproject.service.LoginFailureCounterService;
import com.nhnacademy.miniproject.service.MemberService;
import com.nhnacademy.miniproject.service.MessageSendService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class LoginFailureAccessBlockFilter extends OncePerRequestFilter {

    private RedisTemplate redisTemplate;
    private MemberService memberService;
    private MessageSendService messageSendService;
    private LoginFailureCounterService loginFailureCounterService;

    public LoginFailureAccessBlockFilter(RedisTemplate redisTemplate, MemberService memberService, MessageSendService messageSendService, LoginFailureCounterService loginFailureCounterService) {
        this.redisTemplate = redisTemplate;
        this.memberService = memberService;
        this.messageSendService = messageSendService;
        this.loginFailureCounterService = loginFailureCounterService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 차단된 정보가 있는지 확인하고 요청 id와 차단 정보들 중 동일한 값이 있는지 확인하기
        if (!(request.getRequestURI().contains("/login/process") && "POST".equalsIgnoreCase(request.getMethod()))) {
            filterChain.doFilter(request, response);
            return;
        }

        String id = request.getParameter("id");
        Member member = memberService.getMemberById(id);

        if(loginFailureCounterService.check5count(id)){
            // 차단된 id라는 뜻! 차단해줘야함
            messageSendService.send(id);
            response.sendRedirect("/block");
            return ;
        }

        filterChain.doFilter(request, response);
    }
}
