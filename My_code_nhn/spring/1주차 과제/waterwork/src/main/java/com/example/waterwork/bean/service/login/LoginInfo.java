package com.example.waterwork.bean.service.login;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

//@Repository
//@Getter
public class LoginInfo {
    public static Map<String, String> idPassword = new HashMap<>();
    public static Map<String, String> idUserName = new HashMap<>();
    public static List<String> currentUser = new ArrayList<>();
    public static String currentId;

//    public LoginInfo(){
//        this.idPassword = new HashMap<>();
//        this.idUserName = new HashMap<>();
//    }
}
