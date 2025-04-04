package com.example.waterwork.common.aop;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import com.example.waterwork.commad.MyCommands;
import com.example.waterwork.service.login.service.LoginService;
import org.aspectj.lang.annotation.DeclareMixin;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.AopTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        "spring.shell.interactive.enabled=false",
        "file.type=csv",
        "file.price-path=src/main/resources/data/csv/Tariff.csv",
        "file.account-path=src/main/resources/data/csv/account.csv"
})
public class searchAopTest {

    @Autowired
    private LoginService loginService;

    @Autowired
    private MyCommands myCommands;

    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    public void setUp() {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        listAppender = new ListAppender<>();
        listAppender.setContext(loggerContext);
        listAppender.start();
        loggerContext.getLogger("com.example.waterwork.common.aop.searchAop").addAppender(listAppender);
    }

    @Test
    @Order(1)
    @DisplayName("로그인 없는 city() 테스트")
    public void testNotLogin() {
        assertThrows(Exception.class, () -> myCommands.city());
    }

    @Test
    @Order(2)
    @DisplayName("로그인 및 city() 테스트")
    public void testLogin() {
        loginService.login(1L, "1");
        String cities = myCommands.city();
        loginService.logout();
        assertTrue(cities.contains("동두천시"));
    }

    @Test
    @Order(3)
    @DisplayName("로그아웃 테스트")
    public void testLogout() {
        loginService.login(1L, "1");
        loginService.logout();
        assertThrows(Exception.class, () -> myCommands.city());
    }

    @Test
    @Order(4)
    @DisplayName("logging 테스트")
    public void testLoggingAspect() {
        loginService.login(1L, "1");

        myCommands.sector("동두천시");
        loginService.logout();
        List<ILoggingEvent> logsList = listAppender.list;
        assertThat(logsList).isNotEmpty();

        assertTrue(logsList.getFirst().getFormattedMessage().contains("선도형"));
        assertTrue(logsList.getFirst().getFormattedMessage().contains("동두천시"));
    }

    @Test
    @Order(5)
    public void isAop() {

        assertTrue(AopUtils.isAopProxy(myCommands));
    }

    @Test
    @Order(6)
    public void testNonAop() {
        MyCommands nonAopCommands = AopTestUtils.getTargetObject(myCommands);
        assertFalse(AopUtils.isAopProxy(nonAopCommands));

        String cities = nonAopCommands.city();
        assertTrue(cities.contains("동두천시"));
    }
}