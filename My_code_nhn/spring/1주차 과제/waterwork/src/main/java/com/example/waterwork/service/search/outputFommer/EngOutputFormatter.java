package com.example.waterwork.service.search.outputFommer;

import com.example.waterwork.service.search.domain.Price;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

public class EngOutputFormatter implements OutPutFormatter{
    @Override
    public String format(Price price, int usage) {
        return String.format("city: %s, sector=%s, unit price(won): %s, bill total(won): %s",
                price.getCity(), price.getSector(), price.getUnitPrice() ,price.getUnitPrice()*usage);
    }
}
