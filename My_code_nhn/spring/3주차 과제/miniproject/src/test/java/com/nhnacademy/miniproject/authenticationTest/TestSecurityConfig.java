package com.nhnacademy.miniproject.authenticationTest;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@TestConfiguration
public class TestSecurityConfig {

    @Bean
    public SecurityFilterChain testSecurityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())  // csrf 비활성화 (새로운 방식)
            .logout(logout -> logout.disable())
            .authorizeHttpRequests(authz -> authz
                    .requestMatchers("/admin").hasRole("ADMIN")
                    .requestMatchers("/member").hasRole("MEMBER")
                    .requestMatchers("/google").hasRole("GOOGLE")
                    .anyRequest().authenticated()
            );

        // 필터 적용 생략
        return http.build();
    }
}