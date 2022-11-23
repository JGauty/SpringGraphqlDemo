package com.example.springgraphqldemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringGraphqlDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringGraphqlDemoApplication.class, args);
    }

}
