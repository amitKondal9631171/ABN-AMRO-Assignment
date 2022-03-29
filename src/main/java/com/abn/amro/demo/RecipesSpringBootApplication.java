package com.abn.amro.demo;


import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.abn.amro.demo")
public class RecipesSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecipesSpringBootApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
