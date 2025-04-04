package com.example.waterwork.common.config;

import com.example.waterwork.service.search.outputFommer.EngOutputFormatter;
import com.example.waterwork.service.search.outputFommer.OutPutFormatter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@SpringBootTest(properties = {
        "spring.shell.interactive.enabled=false",
        "file.type=csv",
        "file.price-path=src/main/resources/data/csv/Tariff.csv",
        "file.account-path=src/main/resources/data/csv/account.csv"
})
@ActiveProfiles("eng")
public class EngProfileTest {

    @Autowired
    private OutPutFormatter outPutFormatter;

    @Test
    void beanTest() {
        assertInstanceOf(EngOutputFormatter.class, outPutFormatter);
    }
}
