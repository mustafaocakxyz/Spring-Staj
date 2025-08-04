package com.example.demo.service;

import com.example.demo.dto.Client3Response;
import com.example.demo.entity.Client3Rate;
import com.example.demo.repository.Client3RateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@Slf4j
public class Client3Service {
    
    @Value("${client3.api.url}")
    private String CLIENT3_URL;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private Client3RateRepository client3RateRepository;

    public Client3Response fetchRates() {
        LocalDateTime requestTime = LocalDateTime.now();
        log.info("[Client3] Sending GET request | URL: {} | Method: GET | Timestamp: {}", CLIENT3_URL, requestTime);
        try {
            ResponseEntity<Client3Response> response = restTemplate.getForEntity(CLIENT3_URL, Client3Response.class);
            LocalDateTime responseTime = LocalDateTime.now();
            log.info("[Client3] Received response | Status: {} | Body: {} | Timestamp: {}", response.getStatusCode(), response.getBody(), responseTime);
            Client3Response client3Response = response.getBody();
            if (client3Response != null && client3Response.getConversion_rates() != null) {
                for (Map.Entry<String, Double> entry : client3Response.getConversion_rates().entrySet()) {
                    Client3Rate rate = new Client3Rate();
                    rate.setBaseCode(client3Response.getBase_code());
                    rate.setCurrencyCode(entry.getKey());
                    rate.setRate(entry.getValue());
                    client3RateRepository.save(rate);
                }
            }
            return client3Response;
        } catch (Exception e) {
            LocalDateTime errorTime = LocalDateTime.now();
            log.error("[Client3] Error during API call | URL: {} | Method: GET | Timestamp: {} | Exception: {}", CLIENT3_URL, errorTime, e.getMessage(), e);
            throw e;
        }
    }
} 