package com.example.waterwork.commad;

import com.example.waterwork.bean.service.login.CurrentUserBean;
import com.example.waterwork.bean.service.login.LoginBean;
import com.example.waterwork.bean.service.login.LogoutBean;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.pl.PESEL;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class MyCommands {
    private final LoginBean loginBean;
    private final LogoutBean logoutBean;
    private final CurrentUserBean currentUserBean;

    @ShellMethod
    public String greet(String name) {
        return "Hello, " + name;
    }

    @ShellMethod
    public String farewell(String name) {
        return "Bye, " + name;
    }

    @ShellMethod
    public String login(String id, String password){
        return loginBean.loginCheck(id,password);
    }

    @ShellMethod
    public String logout(){
        return logoutBean.logout();
    }

    @ShellMethod
    public String currentUser(){
        return currentUserBean.currentUser();
    }
}
