package com.example.waterwork.service.login.service;

import com.example.waterwork.common.dataParser.DataParser;
import com.example.waterwork.service.login.domain.Account;
import com.example.waterwork.service.login.exception.AccountAlreadyExistException;
import com.example.waterwork.service.login.exception.AccountNotFoundException;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest(properties = {
        "spring.shell.interactive.enabled=false",
        "file.type=csv",
        "file.price-path=src/main/resources/data/csv/Tariff.csv",
        "file.account-path=src/main/resources/data/csv/account.csv"
})class LoginServiceTest {
    @Mock
    private DataParser dataParser;

    @InjectMocks
    private LoginService loginService;

    private Account testAccount;

    @BeforeEach
    void setUp(){
        testAccount = new Account(
                1L,
                "1",
                "테스트유저"
        );
    }

    @Test
    @Order(1)
    @DisplayName("로그인 테스트")
    void loginTest() {
        when(dataParser.parserAccountList()).thenReturn(List.of(testAccount));

        Account result = loginService.login(1L, "1");

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getName()).isEqualTo("테스트유저");
        verify(dataParser, times(1)).parserAccountList();
    }

    @Test
    @Order(2)
    @DisplayName("이미 로그인 되어 있을 때")
    void alreadyLogin() {
        when(dataParser.parserAccountList()).thenReturn(List.of(testAccount));

        Account result = loginService.login(1L, "1");

        assertThat(result).isNotNull();

        assertThrows(AccountAlreadyExistException.class, ()-> loginService.login(1,"1"));
        verify(dataParser, times(1)).parserAccountList();
    }

    @Test
    @Order(3)
    @DisplayName("parser 결과가 있을 경우")
    void notParserLogin() {
        when(dataParser.parserAccountList()).thenReturn(null);

        assertThrows(AccountNotFoundException.class, ()-> loginService.login(1,"1"));
        verify(dataParser, times(1)).parserAccountList();
    }



    @Test
    @Order(4)
    @DisplayName("로그아웃 테스트")
    void logoutTest() {
        when(dataParser.parserAccountList()).thenReturn(List.of(testAccount));

        loginService.login(1L, "1");

        loginService.logout();
        Assertions.assertThrows(IllegalArgumentException.class, ()->{loginService.getCurrentAccount();});
        verify(dataParser, times(1)).parserAccountList();
    }

    @Test
    @Order(5)
    @DisplayName("로그인 되어 있지 않을 때 로그아웃 테스트")
    void notLoginLogout() {
        when(dataParser.parserAccountList()).thenReturn(List.of(testAccount));

        loginService.login(1L, "1");

        loginService.logout();
        Assertions.assertThrows(IllegalArgumentException.class, ()->{loginService.logout();});
        verify(dataParser, times(1)).parserAccountList();
    }

    @Test
    void getCurrentAccount() {
        when(dataParser.parserAccountList()).thenReturn(List.of(testAccount));

        Account result = loginService.login(1L, "1");
        Account currentAccount = loginService.getCurrentAccount();
        assertThat(currentAccount.getId()).isEqualTo(result.getId());
        verify(dataParser, times(1)).parserAccountList();
    }
}