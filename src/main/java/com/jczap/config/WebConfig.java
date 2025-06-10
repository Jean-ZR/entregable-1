package com.jczap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Permitir acceso a todas las rutas
                        .allowedOrigins("*") // Permitir todos los orígenes (puedes personalizarlo)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos permitidos
                        .allowedHeaders("*") // Permitir todas las cabeceras
                        .exposedHeaders("Authorization") // Exponer cabeceras específicas si las necesitas
                        .allowCredentials(false) // Cambia a true si manejas cookies
                        .maxAge(3600); // Tiempo de cache del CORS preflight en segundos
            }
        };
    }
}
