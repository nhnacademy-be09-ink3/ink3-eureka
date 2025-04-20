package com.nhnacademy.miniproject.common.filter;

import com.nhnacademy.miniproject.domain.member.model.AcademyUser;
import com.nhnacademy.miniproject.domain.member.model.Member;
import com.nhnacademy.miniproject.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;

public class LoginSessionCheckFilter extends OncePerRequestFilter {

    private RedisTemplate<String, Object> redisTemplate;
    private MemberService memberService;

    public LoginSessionCheckFilter(RedisTemplate<String, Object> redisTemplate, MemberService memberService) {
        this.redisTemplate = redisTemplate;
        this.memberService = memberService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 세션 값 확인하기

        String sessionId = null;
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie : cookies){
            if (cookie.getName().equals("SESSIONID")) {
                sessionId = cookie.getValue();
            }
        }

        if(sessionId!=null){
            Object o = redisTemplate.opsForValue().get(sessionId);
            if(!Objects.isNull(o)){
                String id = (String) o;
                Member member = memberService.getMemberById(id);
                AcademyUser academyUser = new AcademyUser(member);


                Authentication auth = new PreAuthenticatedAuthenticationToken(academyUser, null, academyUser.getAuthorities());
                auth.setAuthenticated(true);
                // 다음 필터에서는 인증이 된 것으로 인식해서 authentication 필터를 거치지 않음.
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        filterChain.doFilter(request,response);
    }
}
