package com.example.waterwork.common.aop;

import com.example.waterwork.service.login.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;


@Slf4j
@Aspect
@Configuration
@RequiredArgsConstructor
public class searchAop {
    private final LoginService loginService;

    @Pointcut("execution(* com.example.waterwork.commad.MyCommands.city())")
    public void cityCut(){}

    @Pointcut("execution(* com.example.waterwork.commad.MyCommands.sector(..))")
    public void sectorCut(){}

    @Pointcut("execution(* com.example.waterwork.commad.MyCommands.price(..))")
    public void priceCut(){}

    @Pointcut("execution(* com.example.waterwork.commad.MyCommands.billTotal(..))")
    public void billTotalCut(){}


    @Around("cityCut() || sectorCut() || priceCut() || billTotalCut()")
    public String cities(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("------ {} class {}.{}({})  ----->",loginService.getCurrentAccount().getName(),joinPoint.getTarget().getClass().getName(),joinPoint.getSignature().getName(), joinPoint.getArgs());
        String proceed = (String) joinPoint.proceed();
        log.info("<----- {} class {}.{}({})  -----",loginService.getCurrentAccount().getName(),joinPoint.getTarget().getClass().getName(),joinPoint.getSignature().getName(), proceed);
        return proceed;
    }
}
