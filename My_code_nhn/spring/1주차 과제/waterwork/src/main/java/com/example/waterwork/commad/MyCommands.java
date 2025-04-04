package com.example.waterwork.commad;

import com.example.waterwork.service.login.domain.Account;
import com.example.waterwork.service.login.service.LoginService;
import com.example.waterwork.service.search.domain.Price;
import com.example.waterwork.service.search.outputFommer.OutPutFormatter;
import com.example.waterwork.service.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@ShellComponent
@RequiredArgsConstructor
public class MyCommands {
    private final LoginService loginService;
    private final SearchService searchService;
    private final OutPutFormatter outPutFormatter;
    private Account currentAccount = null;


    // Login logic
    @ShellMethod
    public String login(long id, String password){
        if(Objects.isNull(id) || Objects.isNull(password) || password.isEmpty()){
            throw new IllegalArgumentException("로그인 실패!");
        }

        Account account = loginService.login(id, password);
        this.currentAccount = account;
        return account.toString();
    }

    @ShellMethod
    public String logout(){
        loginService.logout();
        return "good bye";
    }

    @ShellMethod
    public String currentUser(){
        List<Account> accounts = loginService.getCurrentAccountList();

        String result = "";
        for(Account account : accounts){
            result += account.toString();
        }
        return result;
    }




    // search logic
    @ShellMethod
    public String city(){
        List<String> cities = searchService.getCityList();

        String list = String.join(", ", cities);
        return String.format("[%s]",list);
    }

    @ShellMethod
    public String sector(String sector){
        List<String> sectors = searchService.getSectorList(sector);

        String list = String.join(", ",sectors);
        return String.format("[%s]",list);
    }

    @ShellMethod
    public String price(String city, String sector){
        List<Price> prices = searchService.getPriceList(city, sector);

        return makePriceListToString(prices);
    }

    @ShellMethod
    public String billTotal(String city, String sector, int usage) {
        Price price = searchService.getBillTotal(city, sector, usage);
        return outPutFormatter.format(price, usage);
    }



    private static String makePriceListToString(List<Price> prices) {
        List<String> tempList = new ArrayList<>();
        for(int i = 0; i< prices.size(); i++){
            Price price = prices.get(i);
            tempList.add(price.toString());
        }

        String list = String.join(",  ",tempList);
        return list;
    }
}
