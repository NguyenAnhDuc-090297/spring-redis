package com.ducnguyen.sbredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SbRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbRedisApplication.class, args);
    }

}
