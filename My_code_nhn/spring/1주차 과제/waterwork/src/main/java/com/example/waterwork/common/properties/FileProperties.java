package com.example.waterwork.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Setter
@Getter
@ConfigurationProperties("file")
public class FileProperties {
    private String type;
    private String pricePath;
    private String accountPath;
}
