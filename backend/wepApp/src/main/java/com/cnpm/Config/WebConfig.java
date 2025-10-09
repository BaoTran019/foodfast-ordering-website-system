package com.cnpm.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // cho phép tất cả endpoint
                .allowedOrigins("http://localhost:5173") // FE chạy React
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // cho phép các HTTP method
                .allowedHeaders("*") // cho phép tất cả header
                .allowCredentials(true);
    }
}
