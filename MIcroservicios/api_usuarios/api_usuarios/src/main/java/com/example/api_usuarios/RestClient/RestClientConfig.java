package com.example.api_usuarios.RestClient;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    public RestClient comprasRestClient() {
        return RestClient.builder()
                .baseUrl("http://localhost:8081/api/carritosApi") // URL base del controlador de compras
                .build();
    }
}
