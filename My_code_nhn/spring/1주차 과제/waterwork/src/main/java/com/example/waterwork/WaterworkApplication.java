package com.example.waterwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example.waterwork")
public class WaterworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(WaterworkApplication.class, args);
	}

}
