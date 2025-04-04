package com.example.waterwork.service.search.outputFommer;

import com.example.waterwork.service.search.domain.Price;

public interface OutPutFormatter {
    String format(Price price, int usage);
}
