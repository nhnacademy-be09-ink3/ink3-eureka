package com.example.waterwork.config;

import com.example.waterwork.bean.service.login.CurrentUserBean;
import com.example.waterwork.bean.service.login.LoginBean;
import com.example.waterwork.bean.service.login.LogoutBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoginConfig {

    @Bean
    LoginBean loginBean(){
        return new LoginBean();
    }

    @Bean
    LogoutBean logoutBean(){
        return new LogoutBean();
    }

    @Bean
    CurrentUserBean currentUserBean(){
        return new CurrentUserBean();
    }
}
