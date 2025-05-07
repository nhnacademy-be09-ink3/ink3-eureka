package com.nhnacademy.ink3eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class Ink3EurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(Ink3EurekaApplication.class, args);
    }

}
