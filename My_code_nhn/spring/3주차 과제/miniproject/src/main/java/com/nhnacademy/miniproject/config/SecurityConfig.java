package com.nhnacademy.miniproject.config;

import com.nhnacademy.miniproject.common.filter.LoginFailureAccessBlockFilter;
import com.nhnacademy.miniproject.common.filter.LoginSessionCheckFilter;
import com.nhnacademy.miniproject.common.handler.CustomAuthenticationFailureHandler;
import com.nhnacademy.miniproject.common.handler.CustomAuthenticationSuccessHandler;
import com.nhnacademy.miniproject.common.handler.CustomOauthAuthenticationFailureHandler;
import com.nhnacademy.miniproject.common.handler.CustomOauthAuthenticationSuccessHandler;
import com.nhnacademy.miniproject.service.LoginFailureCounterService;
import com.nhnacademy.miniproject.service.MemberService;
import com.nhnacademy.miniproject.service.MessageSendService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Profile("!test")
public class SecurityConfig {

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    private final CustomOauthAuthenticationFailureHandler customOauthAuthenticationFailureHandler;
    private final CustomOauthAuthenticationSuccessHandler customOauthAuthenticationSuccessHandler;
    private final MemberService memberService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final MessageSendService messageSendService;
    private final LoginFailureCounterService loginFailureCounterService;

    public SecurityConfig(CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler, CustomAuthenticationFailureHandler customAuthenticationFailureHandler, CustomOauthAuthenticationFailureHandler customOauthAuthenticationFailureHandler, CustomOauthAuthenticationSuccessHandler customOauthAuthenticationSuccessHandler, MemberService memberService, RedisTemplate<String, Object> redisTemplate, MessageSendService messageSendService, LoginFailureCounterService loginFailureCounterService) {
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
        this.customAuthenticationFailureHandler = customAuthenticationFailureHandler;
        this.customOauthAuthenticationFailureHandler = customOauthAuthenticationFailureHandler;
        this.customOauthAuthenticationSuccessHandler = customOauthAuthenticationSuccessHandler;
        this.memberService = memberService;
        this.redisTemplate = redisTemplate;
        this.messageSendService = messageSendService;
        this.loginFailureCounterService = loginFailureCounterService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.logout(AbstractHttpConfigurer::disable);


        // 필터 적용
        http.addFilterBefore(new LoginSessionCheckFilter(redisTemplate, memberService), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new LoginFailureAccessBlockFilter(redisTemplate,
                                                                memberService,
                                                                messageSendService,
                                                                loginFailureCounterService),
                                                                UsernamePasswordAuthenticationFilter.class);


        // 인증
        http.formLogin(formLogin ->
                formLogin.loginPage("/login")
                        .usernameParameter("id")
                        .passwordParameter("pwd")
                        .loginProcessingUrl("/login/process")
                        .successHandler(customAuthenticationSuccessHandler)
                        .failureHandler(customAuthenticationFailureHandler)
        );


        http.oauth2Login(oauth2 ->
                oauth2.loginPage("/login")
                        .defaultSuccessUrl("/")
                        .failureHandler(customOauthAuthenticationFailureHandler)
                        .successHandler(customOauthAuthenticationSuccessHandler));



        // 인가
        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests.requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/member").hasRole("MEMBER")
                        .requestMatchers("/google").hasRole("GOOGLE")
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/members/**").permitAll()
                        .requestMatchers("/members").permitAll()
                        .requestMatchers("/block").permitAll()
                        .anyRequest().authenticated()
        );



        http.exceptionHandling(exceptionHandling ->
                exceptionHandling.accessDeniedPage("/403"));


        // login
        return http.build();
    }
}
