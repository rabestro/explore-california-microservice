package com.epam.engx.explorecalifornia;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(
    title = "Explore California API",
    description = "API Definitions of Explore California microservice",
    version = "3.0.1"
))
@SpringBootApplication
public class ExploreCaliforniaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExploreCaliforniaApplication.class, args);
    }
}
