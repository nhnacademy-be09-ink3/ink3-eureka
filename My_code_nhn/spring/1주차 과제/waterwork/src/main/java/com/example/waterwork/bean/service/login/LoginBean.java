package com.example.waterwork.bean.service.login;

import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;


public class LoginBean {

    Map<String, String> idPassword = LoginInfo.idPassword;
    Map<String, String> idUserName = LoginInfo.idUserName;

    public String loginCheck(String id, String password){
        if(idPassword.containsKey(id)){
            if(idPassword.get(id).equals(password)){
                if(idUserName.containsKey(id)){
                    LoginInfo.currentUser.add(id);
                    LoginInfo.currentId = id;
                    String userName = idUserName.get(id);
                    return String.format("Account(id=?, password=?, name=?)",id,password, userName);
                }
            }
        }


        throw new IllegalArgumentException("login fail!");
    }
}
