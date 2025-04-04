package com.example.waterwork.common.dataParser;

import com.example.waterwork.common.properties.FileProperties;
import com.example.waterwork.service.login.domain.Account;
import com.example.waterwork.service.search.domain.Price;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
public class JsonDataParser implements DataParser {
    private final FileProperties fileProperties;

    private File JsonTariff;
    private File JsonAccount;

    @PostConstruct
    public void init() throws FileNotFoundException {
        this.JsonTariff = new File(fileProperties.getPricePath());
        this.JsonAccount = new File(fileProperties.getAccountPath());

        if(!this.JsonTariff.exists()){
            throw new FileNotFoundException("수도 데이터를 찾지 못했습니다.");
        }
        if(!this.JsonAccount.exists()){
            throw new FileNotFoundException("회원 데이터를 찾지 못했습니다.");
        }
    }


    @Override
    public List<String> parsetCityList() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Map<String, Object>> dataList = objectMapper.readValue(this.JsonTariff, new TypeReference<>() {});
            Set<String> citySet = new HashSet<>();


            for(Map<String, Object> data : dataList){
                citySet.add((String) data.get("지자체명"));
            }

            List<String> cityList = new ArrayList<>(citySet);
            return cityList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<String> parsetSectorList(String city) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Map<String, Object>> dataList = objectMapper.readValue(this.JsonTariff, new TypeReference<>() {});
            Set<String> sectorSet = new HashSet<>();


            for(Map<String, Object> data : dataList){
                if(city.equals((String) data.get("지자체명"))){
                    sectorSet.add((String) data.get("업종"));

                }
            }

            List<String> sectorList = new ArrayList<>(sectorSet);
            return sectorList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Price> parsetPriceList(String city, String sector) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Map<String, Object>> dataList = objectMapper.readValue(this.JsonTariff, new TypeReference<>() {});
            List<Price> priceList = new ArrayList<>();


            for(Map<String, Object> data : dataList){
                if(city.equals((String) data.get("지자체명")) && sector.equals((String) data.get("업종"))){
                    priceList.add(new Price(
                            ((Number) data.get("순번")).longValue(),
                            city,
                            sector,
                            (Integer) data.get("구간시작(세제곱미터)"),
                            (Integer) data.get("구간끝(세제곱미터)"),
                            (Integer) data.get("구간금액(원)")
                    ));
                }
            }

            return priceList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Account> parserAccountList() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Map<String, Object>> dataList = objectMapper.readValue(this.JsonAccount, new TypeReference<>() {});
            List<Account> accountList = new ArrayList<>();


            for(Map<String, Object> data : dataList){
                accountList.add(new Account(
                        ((Number) data.get("아이디")).longValue(),
                        data.get("비밀번호").toString(),
                        (String) data.get("이름")
                ));
            }

            return accountList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
