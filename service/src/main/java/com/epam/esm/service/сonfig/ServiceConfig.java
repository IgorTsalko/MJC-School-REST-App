package com.epam.esm.service.сonfig;

import com.epam.esm.repository.config.RepositoryConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(RepositoryConfig.class)
public class ServiceConfig {
}
