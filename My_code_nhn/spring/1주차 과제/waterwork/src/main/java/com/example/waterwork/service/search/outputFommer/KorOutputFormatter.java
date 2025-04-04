package com.example.waterwork.service.search.outputFommer;

import com.example.waterwork.service.search.domain.Price;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

public class KorOutputFormatter implements OutPutFormatter{
    @Override
    public String format(Price price, int usage) {
        return String.format("지자체명: %s, 업종=%s, 구간금액(원): %s, 총금액(원): %s",
                price.getCity(), price.getSector(), price.getUnitPrice() ,price.getUnitPrice()*usage);
    }
}
