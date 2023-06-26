package com.devmountain.noteApp.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.devmountain.noteApp.repositories")
public class JpaConfiguration {
    // Additional configuration if needed
}
