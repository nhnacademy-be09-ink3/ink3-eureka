package com.example.waterwork.bean.service.login;

public class LogoutBean {

    public String logout(){
        LoginInfo.currentUser.remove(LoginInfo.currentId);
        return "good bye";
    }
}
