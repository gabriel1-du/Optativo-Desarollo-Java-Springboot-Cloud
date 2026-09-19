package com.example.api_compras.RestClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration 
public class RestClientConfig {
    @Bean
    public RestClient usuariosRestClient() {
        return RestClient.builder()
                .baseUrl("http://localhost:8080/api/usuariosApi") // Endpoint base de usuarios
                .build();
    }
}
