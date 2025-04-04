package com.example.waterwork.common.aop;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.example.waterwork.service.login.service.LoginService;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        "spring.shell.interactive.enabled=false",
        "file.type=csv",
        "file.price-path=src/main/resources/data/csv/Tariff.csv",
        "file.account-path=src/main/resources/data/csv/account.csv"
})
class loginAopTest {

    @Autowired
    private LoginService loginService;
    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    public void setUp(){
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        listAppender = new ListAppender<>();
        listAppender.setContext(loggerContext);
        listAppender.start();
        loggerContext.getLogger("com.example.waterwork.common.aop.loginAop").addAppender(listAppender);
    }

    @Test
    public void testLoggingAspect() {
        loginService.login(1L, "1");
        loginService.logout();

        List<ILoggingEvent> logsList = listAppender.list;
        assertThat(logsList).isNotEmpty();

        assertTrue(logsList.get(0).getFormattedMessage().contains("login"));
        assertTrue(logsList.get(0).getFormattedMessage().contains("1"));

        assertTrue(logsList.get(1).getFormattedMessage().contains("logout"));
    }

    @Test
    public void isAop() {
        assertTrue(AopUtils.isAopProxy(loginService));
    }
}