package com.example.waterwork.service.login.service;

import com.example.waterwork.common.dataParser.CsvDataParser;
import com.example.waterwork.common.dataParser.DataParser;
import com.example.waterwork.service.login.domain.Account;
import com.example.waterwork.service.login.exception.AccountAlreadyExistException;
import com.example.waterwork.service.login.exception.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final DataParser dataParser;
    List<Account> currentAccountList = new ArrayList<>();
    private Account currentAccount;


    public Account login(long id, String password){
        if(Objects.nonNull(currentAccount)){
            throw new AccountAlreadyExistException("이미 로그인 되어 있습니다.");
        }

        List<Account> accountList = dataParser.parserAccountList();
        if(Objects.isNull(accountList)){
            throw new AccountNotFoundException();
        }

        for(Account account : accountList){
            if(account.getId()==id && account.getPassword().equals(password)){
                currentAccount = account;
                currentAccountList.add(account);

                return account;
            }
        }
        throw new AccountNotFoundException();
    }

    public void logout(){
        if(Objects.isNull(currentAccount)){
            throw new IllegalArgumentException("로그인 되어 있지 않습니다.");
        }

        currentAccountList.remove(currentAccount);
        currentAccount = null;
    }


    public List<Account> getCurrentAccountList(){
        return currentAccountList;
    }
    public Account getCurrentAccount(){
        if(Objects.isNull(currentAccount)){
            throw new IllegalArgumentException("로그인 되어 있지 않습니다.");
        }

        return this.currentAccount;
    }
}
