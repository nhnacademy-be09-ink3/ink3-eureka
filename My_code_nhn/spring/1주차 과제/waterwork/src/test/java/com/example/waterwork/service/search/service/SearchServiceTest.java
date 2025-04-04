package com.example.waterwork.service.search.service;

import com.example.waterwork.common.dataParser.DataParser;
import com.example.waterwork.service.search.domain.Price;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@SpringBootTest(properties = {
        "spring.shell.interactive.enabled=false",
        "file.type=csv",
        "file.price-path=src/main/resources/data/csv/Tariff.csv",
        "file.account-path=src/main/resources/data/csv/account.csv"
})
class SearchServiceTest {
    @Spy
    private DataParser dataParser;
    @InjectMocks
    private SearchService searchService;

    private List<String> cityList = new ArrayList<>();
    private List<String> sectorList = new ArrayList<>();
    private List<Price> priceList = new ArrayList<>();
    private String city = "동두천사";
    private String sector = "가정용";
    private Price price = new Price(
            1,
            city,
            sector,
            1,
            10,
            690
    );


    @BeforeEach
    void setUp(){
        cityList.add(city);
        sectorList.add(sector);
        priceList.add(price);
    }


    @Test
    @Order(1)
    @DisplayName("City 리스트 테스트")
    void getCityList() {
        Mockito.when(dataParser.parsetCityList()).thenReturn(cityList);

        List<String> cityTestList = searchService.getCityList();
        assertThat(cityTestList).isEqualTo(this.cityList);
        Mockito.verify(dataParser, times(1)).parsetCityList();
    }

    @Test
    @Order(2)
    @DisplayName("Sector 리스트 테스트")
    void getSectorList() {
        Mockito.when(dataParser.parsetSectorList(city)).thenReturn(sectorList);

        List<String> sectorTestList = searchService.getSectorList(city);
        assertThat(sectorTestList).isEqualTo(this.sectorList);
        Mockito.verify(dataParser, times(1)).parsetSectorList(city);
    }

    @Test
    @Order(3)
    @DisplayName("Price 리스트 테스트")
    void getPriceList() {
        Mockito.when(dataParser.parsetPriceList(city,sector)).thenReturn(priceList);

        List<Price> priceTestList = searchService.getPriceList(city,sector);
        assertThat(priceTestList).isEqualTo(this.priceList);
        Mockito.verify(dataParser, times(1)).parsetPriceList(city,sector);
    }

    @Test
    @Order(4)
    @DisplayName("biil-total 테스트")
    void getBillTotal() {
        Mockito.when(dataParser.parsetPriceList(city,sector)).thenReturn(priceList);

        Price billTest = searchService.getBillTotal(city, sector, 1);
        assertThat(billTest).isEqualTo(price);
        Mockito.verify(dataParser, times(1)).parsetPriceList(city,sector);
    }
}
