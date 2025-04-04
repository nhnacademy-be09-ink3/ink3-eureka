package com.example.waterwork.common.dataParser;

import com.example.waterwork.service.login.domain.Account;
import com.example.waterwork.service.login.service.LoginService;
import com.example.waterwork.service.search.domain.Price;
import com.example.waterwork.service.search.outputFommer.EngOutputFormatter;
import com.example.waterwork.service.search.outputFommer.KorOutputFormatter;
import com.example.waterwork.service.search.service.SearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        "spring.shell.interactive.enabled=false",
        "file.type=json",
        "file.price-path=src/main/resources/data/json/Tariff.json",
        "file.account-path=src/main/resources/data/json/account.json"
})
class JsonDataParserTest {
    @Autowired
    DataParser dataParser;
    @Autowired
    private LoginService loginService;
    @Autowired
    private SearchService searchService;


    @Test
    void beanTest() {
        assertInstanceOf(JsonDataParser.class, dataParser);
    }

    @Test
    void loginTest() throws Exception {
        loginService.login(1L, "1");
        Account account = loginService.getCurrentAccount();
        loginService.logout();
        assertEquals("선도형", account.getName());
    }

    @Test
    void logoutTest() throws Exception {
        loginService.login(1L, "1");
        loginService.logout();
        assertThrows(IllegalArgumentException.class,()->loginService.getCurrentAccount());
    }

    @Test
    void cityTest() throws Exception {
        List<String> cities = searchService.getCityList();
        assertEquals(cities.size(), 21);
        org.assertj.core.api.Assertions.assertThat(cities).contains("동두천시");
    }

    @Test
    void sectorsTest() throws Exception {
        List<String> sectors = searchService.getSectorList("동두천시");
        assertEquals(sectors.size(), 5);
        assertTrue(sectors.contains("가정용"));
    }

    @Test
    void priceTest() throws Exception {
        List<Price> priceList = searchService.getPriceList("동두천시", "가정용");
        assertEquals("동두천시", priceList.get(0).getCity());
        assertEquals("가정용",  priceList.get(0).getSector());
        assertEquals(690,  priceList.get(0).getUnitPrice());
    }

    @Test
    void billTotalTest() throws Exception {
        Price billTotal = searchService.getBillTotal("동두천시", "가정용", 10);
        org.assertj.core.api.Assertions.assertThat(billTotal.getUnitPrice()).isEqualTo(690);
    }

    @Test
    void outPutFormatterTest() throws Exception {
        List<Price> priceList = searchService.getPriceList("동두천시", "가정용");

        KorOutputFormatter koreanOutputFormatter = new KorOutputFormatter();
        EngOutputFormatter englishOutputFormatter = new EngOutputFormatter();

        String korean = koreanOutputFormatter.format(priceList.get(0), 10);
        String english = englishOutputFormatter.format(priceList.get(0), 10);

        assertTrue(korean.contains("690"));
        assertTrue(english.contains("690"));
        assertNotEquals(korean, english);
    }
}