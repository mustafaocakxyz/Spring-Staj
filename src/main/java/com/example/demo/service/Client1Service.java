package com.example.demo.service;

import com.example.demo.dto.Client1Response;
import com.example.demo.entity.Client1Rate;
import com.example.demo.repository.Client1RateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
public class Client1Service {
    
    private final String CLIENT1_URL;
    private final RestTemplate restTemplate;
    private final Client1RateRepository client1RateRepository;

    public Client1Service(String client1Url, RestTemplate restTemplate, Client1RateRepository client1RateRepository) {
        this.CLIENT1_URL = client1Url;
        this.restTemplate = restTemplate;
        this.client1RateRepository = client1RateRepository;
    }

    public Client1Response fetchRates() {
        LocalDateTime requestTime = LocalDateTime.now();
        log.info("[Client1] Sending GET request | URL: {} | Method: GET | Timestamp: {}", CLIENT1_URL, requestTime);
        try {
            ResponseEntity<Client1Response> response = restTemplate.getForEntity(CLIENT1_URL, Client1Response.class);
            LocalDateTime responseTime = LocalDateTime.now();
            log.info("[Client1] Received response | Status: {} | Body: {} | Timestamp: {}", response.getStatusCode(), response.getBody(), responseTime);
            Client1Response client1Response = response.getBody();
            if (client1Response != null && client1Response.getConversion_rates() != null) {
                for (Map.Entry<String, Double> entry : client1Response.getConversion_rates().entrySet()) {
                    Client1Rate rate = new Client1Rate();
                    rate.setBaseCode(client1Response.getBase_code());
                    rate.setCurrencyCode(entry.getKey());
                    rate.setRate(entry.getValue());
                    rate.setStartDate(client1Response.getStart_date());
                    rate.setEndDate(client1Response.getEnd_date());
                    client1RateRepository.save(rate);
                }
            }
            return client1Response;
        } catch (Exception e) {
            LocalDateTime errorTime = LocalDateTime.now();
            log.error("[Client1] Error during API call | URL: {} | Method: GET | Timestamp: {} | Exception: {}", CLIENT1_URL, errorTime, e.getMessage(), e);
            throw e;
        }
    }
} 