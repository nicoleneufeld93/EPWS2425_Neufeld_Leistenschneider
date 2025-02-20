package com.webdev.dateikomprimierung.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Alle Endpoints zulassen
        .allowedOrigins("http://127.0.0.1:5500", "http://localhost:5500") // Deine Frontend-URLs
        .allowedMethods("GET", "POST", "PUT", "DELETE"); // Zulässige HTTP-Methoden
    }
}
