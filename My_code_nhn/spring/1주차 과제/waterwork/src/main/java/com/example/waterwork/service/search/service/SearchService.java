package com.example.waterwork.service.search.service;

import com.example.waterwork.common.dataParser.DataParser;
import com.example.waterwork.service.search.domain.Price;
import com.example.waterwork.service.search.exception.SearchFailException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class SearchService {
    private final DataParser dataParser;

    public List<String> getCityList() {
        List<String> cityList = dataParser.parsetCityList();
        if(Objects.isNull(cityList) || cityList.isEmpty()){
            throw new SearchFailException("city 데이터를 찾지 못했습니다.");
        }

        return cityList;
    }

    public List<String> getSectorList(String city) {
        List<String> sectorList = dataParser.parsetSectorList(city);
        if(Objects.isNull(sectorList) || sectorList.isEmpty()){
            throw new SearchFailException("적절한 sector를 찾지 못했습니다.");
        }

        return sectorList;
    }

    public List<Price> getPriceList(String city, String sector) {
        List<Price> priceList = dataParser.parsetPriceList(city, sector);
        if(Objects.isNull(priceList) || priceList.isEmpty()){
            throw new SearchFailException("적절한 price를 찾지 못했습니다.");
        }

        return priceList;
    }

    public Price getBillTotal(String city, String sector, int usage) {
        List<Price> prices = dataParser.parsetPriceList(city, sector);
        for(Price price : prices){
            if(price.getStart() <= usage && usage<=price.getEnd()){
                return price;
            }
        }
        throw new SearchFailException("적절한 bill을 찾지 못했습니다!");
    }
}
