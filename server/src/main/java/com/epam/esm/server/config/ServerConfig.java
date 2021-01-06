package com.epam.esm.server.config;

import com.epam.esm.service.сonfig.ServiceConfig;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@Import(ServiceConfig.class)
@ComponentScan("com.epam.esm")
@PropertySource("classpath:application.properties")
@EnableWebMvc
public class ServerConfig {
}
