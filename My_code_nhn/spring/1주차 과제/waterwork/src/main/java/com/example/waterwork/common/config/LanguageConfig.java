package com.example.waterwork.common.config;

import com.example.waterwork.common.dataParser.CsvDataParser;
import com.example.waterwork.common.dataParser.DataParser;
import com.example.waterwork.common.dataParser.JsonDataParser;
import com.example.waterwork.service.search.outputFommer.EngOutputFormatter;
import com.example.waterwork.service.search.outputFommer.KorOutputFormatter;
import com.example.waterwork.service.search.outputFommer.OutPutFormatter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class LanguageConfig {

    @Bean
    @ConditionalOnProperty(name = "spring.profiles.active", havingValue = "kor")
    OutPutFormatter korOutPutFormatter(){
        return new KorOutputFormatter();
    }

    @Bean
    @Primary
    @ConditionalOnProperty(name = "spring.profiles.active", havingValue = "eng", matchIfMissing = true )
    OutPutFormatter engOutputFormatter(){
        return new EngOutputFormatter();
    }

}
