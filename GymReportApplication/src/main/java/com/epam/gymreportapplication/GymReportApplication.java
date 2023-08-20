package com.epam.gymreportapplication;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class GymReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(GymReportApplication.class, args);
    }

    @Bean
    public ObjectMapper getObjectMapper(){return new ObjectMapper();}
    

}
