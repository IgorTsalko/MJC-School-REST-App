package com.epam.esm.server.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.epam.esm")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
