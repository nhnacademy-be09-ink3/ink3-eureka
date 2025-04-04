package com.example.waterwork.common.aop;

import com.example.waterwork.service.login.domain.Account;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Aspect
@Slf4j
@Configuration
public class loginAop {

    @Pointcut("execution(* com.example.waterwork.service.login.service.LoginService.login(..))")
    public void loginCut(){}

    @Pointcut("execution(* com.example.waterwork.service.login.service.LoginService.logout(..))")
    public void logoutCut(){}



    @Around("loginCut()")
    public Account login(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("{}({})",joinPoint.getSignature().getName(), joinPoint.getArgs());
        return (Account) joinPoint.proceed();
    }
    @Around("logoutCut()")
    public void logout(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("{}({})",joinPoint.getSignature().getName(), joinPoint.getArgs());
        joinPoint.proceed();
    }


}
