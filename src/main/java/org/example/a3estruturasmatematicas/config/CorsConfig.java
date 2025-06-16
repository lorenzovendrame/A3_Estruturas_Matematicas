package org.example.a3estruturasmatematicas.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // aplica para todas as rotas/endpoints
                .allowedOriginPatterns("*")
                .allowedMethods("*") // métodos HTTP permitidos
                .allowedHeaders("*") // permite todos os headers
                .allowCredentials(true) // permite envio de cookies, se necessário
                .maxAge(3600); // tempo em segundos que a resposta pode ser cacheada pelo navegador
    }
}
