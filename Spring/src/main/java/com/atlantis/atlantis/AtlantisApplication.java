package com.atlantis.atlantis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class AtlantisApplication {

    public static void main(String[] args) {
        SpringApplication.run(AtlantisApplication.class, args);
    }
}