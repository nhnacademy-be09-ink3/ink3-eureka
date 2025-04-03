package com.example.waterwork.runner;

import com.example.waterwork.bean.service.login.LoginBean;
import com.example.waterwork.bean.service.login.LoginInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class AppStartupRunner implements ApplicationRunner {


    @Override
    public void run(ApplicationArguments args) throws Exception {
        Map<String, String> idPassword = LoginInfo.idPassword;
        Map<String, String> idUserName = LoginInfo.idUserName;
        idPassword.put("1","1");
        idUserName.put("1", "yongmin");
    }
}
