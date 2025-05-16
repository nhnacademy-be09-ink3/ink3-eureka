package com.nhnacademy.ink3eureka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity(debug = false)
@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                        .requestMatchers(
                                "/",
                                "/static/**",         // 루트
                                "/eureka/**",         // 대시보드 HTML
                                "/css/**",            // CSS 리소스
                                "/js/**",             // JS 리소스
                                "/images/**",         // 이미지 리소스
                                "/webjars/**",        // 웹자르 포함 리소스
                                "/actuator/**"        // (선택) actuator 상태 확인
                        ).permitAll()
                        .anyRequest().permitAll()
        );

        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
