package com.tdtu.webproject;

import lombok.RequiredArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableCaching
@RequiredArgsConstructor
@Configuration
@ComponentScan(basePackages = {
        "com.tdtu.webproject",
        "com.tdtu.mbGenerator.generate.mybatis.mapper",
        "com.tdtu.webproject.mybatis.mapper"
})
@MapperScan({
        "com.tdtu.mbGenerator.generate.mybatis.mapper",
        "com.tdtu.webproject.mybatis.mapper"
})
public class WebProjectApplication implements ApplicationRunner {


    public static void main(String[] args) {
        SpringApplication.run(WebProjectApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) {

    }
}
