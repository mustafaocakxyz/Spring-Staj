package com.example.demo.service;

import com.example.demo.entity.Client2Rate;
import com.example.demo.repository.Client2RateRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class Client2Service {
    private static final String CLIENT2_URL = "https://6f0028f3-b77d-451e-8471-7ed5480d2e3d.mock.pstmn.io/client2";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Client2RateRepository client2RateRepository;

    public Map<String, Double> fetchRates() {
        LocalDateTime requestTime = LocalDateTime.now();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        String requestBody = "{}";
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        log.info("[Client2] Sending GET request (internal) | URL: {} | Method: GET | Headers: {} | Body: {} | Timestamp: {}", CLIENT2_URL, headers, requestBody, requestTime);
        try {
            ResponseEntity<Map> response = restTemplate.exchange(CLIENT2_URL, org.springframework.http.HttpMethod.GET, request, Map.class);
            LocalDateTime responseTime = LocalDateTime.now();
            log.info("[Client2] Received response | Status: {} | Body: {} | Timestamp: {}", response.getStatusCode(), response.getBody(), responseTime);
            Map<String, Double> rates = response.getBody();
            if (rates != null) {
                for (Map.Entry<String, Double> entry : rates.entrySet()) {
                    Client2Rate rate = new Client2Rate();
                    rate.setCurrencyCode(entry.getKey());
                    rate.setRate(entry.getValue());
                    client2RateRepository.save(rate);
                }
            }
            return rates;
        } catch (Exception e) {
            LocalDateTime errorTime = LocalDateTime.now();
            log.error("[Client2] Error during API call | URL: {} | Method: GET | Timestamp: {} | Exception: {}", CLIENT2_URL, errorTime, e.getMessage(), e);
            throw e;
        }
    }
} 