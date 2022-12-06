package com.abn.amro.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties("")
@Data
public class ApplicationProperties {

    private String hostName;
    private Map<String,String> securitiesAPI;
    private Map<String,String> consumer;
    private Map<String,String> casper;

}

