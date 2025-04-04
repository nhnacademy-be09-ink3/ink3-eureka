package com.example.waterwork.service.login.domain;

import com.example.waterwork.service.search.outputFommer.OutPutFormatter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;


@Getter
@Setter
public class Account {
    @JsonProperty("아이디")
    long id;
    @JsonProperty("비밀번호")
    String password;
    @JsonProperty("이름")
    String name;

    public Account(long id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
    }

    @Override
    public String toString(){
        return String.format("Account(id=%s, password=%s, name=%s)", this.id, this.password, this.name);
    }

}
