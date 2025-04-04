package com.example.waterwork.common.dataParser;

import com.example.waterwork.service.login.domain.Account;
import com.example.waterwork.service.search.domain.Price;

import java.util.List;

public interface DataParser {

    List<String> parsetCityList();

    List<String> parsetSectorList(String city);

    List<Price> parsetPriceList(String city, String sector);

    List<Account> parserAccountList();
}
