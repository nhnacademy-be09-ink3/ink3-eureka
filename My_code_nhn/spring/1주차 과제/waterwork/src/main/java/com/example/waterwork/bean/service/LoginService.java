package com.example.waterwork.bean.service;

import com.example.waterwork.bean.service.login.LoginInfo;

import java.util.List;
import java.util.Map;

public class LoginService {
    List<String> currentUserIdList = LoginInfo.currentUser;
    Map<String, String> idPasswordList = LoginInfo.idPassword;
    Map<String, String> idUserNameList = LoginInfo.idUserName;



    public String loginCheck(String id, String password){
        if(idPasswordList.containsKey(id)){
            if(idPasswordList.get(id).equals(password)){
                if(idUserNameList.containsKey(id)){
                    LoginInfo.currentId = id;
                    LoginInfo.currentUser.add(id);
                    String userName = idUserNameList.get(id);
                    return String.format("Account(id=?, password=?, name=?)",id,password, userName);
                }
            }
        }


        throw new IllegalArgumentException("login fail!");
    }

    public String currentUser(){
        String result = "";
        for(String id : currentUserIdList){
            String password = idPasswordList.get(id);
            String name = idUserNameList.get(id);
            result += String.format("Account(id=?, password=?, name=?)\n", id,password,name);
        }
        return result;
    }


    public String logout(){
        LoginInfo.currentUser.remove(LoginInfo.currentId);
        return "good bye";
    }
}
