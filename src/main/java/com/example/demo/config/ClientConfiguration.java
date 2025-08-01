package com.example.demo.config;

import com.example.demo.repository.Client1RateRepository;
import com.example.demo.repository.Client2RateRepository;
import com.example.demo.repository.Client3RateRepository;
import com.example.demo.service.Client1Service;
import com.example.demo.service.Client2Service;
import com.example.demo.service.Client3Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ClientConfiguration {
    
    @Value("${client1.api.url}")
    private String client1Url;
    
    @Value("${client2.api.url}")
    private String client2Url;
    
    @Value("${client3.api.url}")
    private String client3Url;
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    @Bean
    public Client1Service client1Service(RestTemplate restTemplate, Client1RateRepository client1RateRepository) {
        return new Client1Service(client1Url, restTemplate, client1RateRepository);
    }
    
    @Bean
    public Client2Service client2Service(RestTemplate restTemplate, Client2RateRepository client2RateRepository) {
        return new Client2Service(client2Url, restTemplate, client2RateRepository);
    }
    
    @Bean
    public Client3Service client3Service(RestTemplate restTemplate, Client3RateRepository client3RateRepository) {
        return new Client3Service(client3Url, restTemplate, client3RateRepository);
    }
} 