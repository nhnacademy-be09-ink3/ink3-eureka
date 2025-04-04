package com.example.waterwork.service.search.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class Price {
    @JsonProperty("순번")
    long id;
    @JsonProperty("지자체명")
    String city;
    @JsonProperty("업종")
    String sector;
    @JsonProperty("구간시작(세제곱미터)")
    int start;
    @JsonProperty("구간끝(세제곱미터)")
    int end;
    @JsonProperty("구간금액(원)")
    int unitPrice;

    public Price(long id, String city, String sector, int start, int end, int unitPrice) {
        this.id = id;
        this.city = city;
        this.sector = sector;
        this.start = start;
        this.end = end;
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString(){
        return String.format("Price(id=%d, city=%s, sector=%s, unitPrice=%d)",
                this.id, this.city, this.sector, this.unitPrice);
    }
}
