package com.example.waterwork.common.config;

import com.example.waterwork.common.dataParser.CsvDataParser;
import com.example.waterwork.common.dataParser.DataParser;
import com.example.waterwork.common.dataParser.JsonDataParser;
import com.example.waterwork.common.properties.FileProperties;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@RequiredArgsConstructor
public class FileConfig {
    private final FileProperties fileProperties;

    @Bean
    @ConditionalOnProperty(name = "file.type", havingValue = "json")
    DataParser jsonParser(){
        return new JsonDataParser(fileProperties);
    }

    @Bean
    @ConditionalOnProperty(name = "file.type", havingValue = "csv")
    DataParser csvParser(){
        return new CsvDataParser(fileProperties);
    }

}
