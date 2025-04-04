package com.example.waterwork.common.dataParser;

import com.example.waterwork.common.properties.FileProperties;
import com.example.waterwork.service.login.domain.Account;
import com.example.waterwork.service.search.domain.Price;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
public class CsvDataParser implements DataParser {

    private final FileProperties fileProperties;
    private File csvTariff;
    private File csvAccount;
    BufferedReader br = null;
    String line = "";


    @PostConstruct
    public void init() throws FileNotFoundException {
        this.csvTariff = new File(fileProperties.getPricePath());
        this.csvAccount = new File(fileProperties.getAccountPath());

        if(!this.csvTariff.exists()){
            throw new FileNotFoundException("수도 데이터를 찾지 못했습니다.");
        }
        if(!this.csvAccount.exists()){
            throw new FileNotFoundException("회원 데이터를 찾지 못했습니다.");
        }
    }

    @Override
    public List<String> parsetCityList() {
        Set<String> cityListSet = new HashSet<>();

        try{
            br = new BufferedReader(new FileReader(csvTariff));
            line=br.readLine();
            while((line=br.readLine())!=null){
                String[] tokens = line.split(",");
                cityListSet.add(tokens[1].trim());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<String> cityList = new ArrayList<>(cityListSet);
        return cityList;
    }

    @Override
    public List<String> parsetSectorList(String city) {
        Set<String> sectorListSet = new HashSet<>();

        try{
            br = new BufferedReader(new FileReader(csvTariff));
            line=br.readLine();
            while((line=br.readLine())!=null){
                String[] tokens = line.split(",");

                if(city.equals(tokens[1].trim())){
                    sectorListSet.add(tokens[2].trim());
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<String> sectorList = new ArrayList<>(sectorListSet);
        return sectorList;
    }

    @Override
    public List<Price> parsetPriceList(String city, String sector) {
        List<Price> prices = new ArrayList<>();

        try{
            br = new BufferedReader(new FileReader(csvTariff));
            line=br.readLine();
            while((line=br.readLine())!=null){
                String[] tokens = line.split(",");

                if(city.equals(tokens[1].trim()) && sector.equals(tokens[2].trim())){
                    prices.add(new Price(
                            Long.parseLong(tokens[0].trim()),
                            city,
                            sector,
                            Integer.parseInt(tokens[4].trim()),
                            Integer.parseInt(tokens[5].trim()),
                            Integer.parseInt(tokens[6].trim())
                    ));
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return prices;
    }

    @Override
    public List<Account> parserAccountList() {
        List<Account> accountList = new ArrayList<>();

        try{
            br = new BufferedReader(new FileReader(csvAccount));
            line=br.readLine();
            while((line=br.readLine())!=null){
                String[] tokens = line.split(",");

                accountList.add(new Account(Long.parseLong(tokens[0].trim()), tokens[1].trim(), tokens[2].trim()));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        return accountList;
    }
}
