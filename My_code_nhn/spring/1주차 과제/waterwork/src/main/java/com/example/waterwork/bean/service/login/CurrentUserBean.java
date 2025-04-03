package com.example.waterwork.bean.service.login;

import java.util.List;
import java.util.Map;

public class CurrentUserBean {

    public String currentUser(){
        List<String> currentUserIdList = LoginInfo.currentUser;
        Map<String, String> idPasswordList = LoginInfo.idPassword;
        Map<String, String> idUserNameList = LoginInfo.idUserName;
        String result = null;
        for(String id : currentUserIdList){
            String password = idPasswordList.get(id);
            String name = idUserNameList.get(id);
            result += String.format("Account(id=?, password=?, name=?)\n", id,password,name);
        }
        return result;
    }

}
