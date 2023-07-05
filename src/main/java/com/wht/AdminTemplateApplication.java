package com.wht;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@MapperScan("com.wht.mapper")
public class AdminTemplateApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminTemplateApplication.class, args);
    }

}
