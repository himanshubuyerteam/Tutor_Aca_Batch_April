package com.myimdb.searchbook.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean(destroyMethod = "shutdown")
    public ExecutorService orderExecutorService() {
        return Executors.newFixedThreadPool(3);
    }
}
