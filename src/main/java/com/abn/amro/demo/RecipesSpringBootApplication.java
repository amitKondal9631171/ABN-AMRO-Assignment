package com.abn.amro.demo;


import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ModelMapper.class)
public class RecipesSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(RecipesSpringBootApplication.class, args);
    }

}
