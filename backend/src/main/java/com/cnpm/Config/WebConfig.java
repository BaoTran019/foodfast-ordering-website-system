package com.cnpm.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // cho phép tất cả endpoint
                .allowedOrigins(
                    "http://localhost:5173",
                    "http://localhost:5174",
                    "http://localhost:8081",
                    "http://192.168.0.104:8081",
                    "http://192.168.0.103:8081",
                    "http://192.168.0.102:8081",
                    "http://192.168.0.101:8081"
                ) // FE chạy React
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // cho phép các HTTP method
                .allowedHeaders("*") // cho phép tất cả header
                .allowCredentials(true);
    }
}
