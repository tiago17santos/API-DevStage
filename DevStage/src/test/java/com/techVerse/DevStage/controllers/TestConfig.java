package com.techVerse.DevStage.controllers;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Configuration
public class TestConfig {

    @Bean
    public TestRestTemplate restTemplate() {
        return new TestRestTemplate();
    }
}