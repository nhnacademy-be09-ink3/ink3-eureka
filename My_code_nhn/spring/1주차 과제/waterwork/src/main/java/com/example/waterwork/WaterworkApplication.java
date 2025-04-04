package com.example.waterwork;

import com.example.waterwork.common.properties.FileProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = "com.example.waterwork")
@EnableConfigurationProperties(FileProperties.class)
public class WaterworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaterworkApplication.class, args);
	}

}
