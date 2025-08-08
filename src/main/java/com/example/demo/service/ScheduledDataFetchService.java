package com.example.demo.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScheduledDataFetchService {
    
    private final Client1Service client1Service;
    private final Client2Service client2Service;
    private final Client3Service client3Service;
    
    @Scheduled(cron = "0 */10 * * * *")
    public void fetchDataFromAllClients() {
        log.info("Starting scheduled data fetch from all clients...");
        
        try {
            // Fetch data from Client 1
            log.info("Fetching data from Client 1...");
            client1Service.fetchRates();
            log.info("Successfully fetched and saved data from Client 1");
            
        } catch (Exception e) {
            log.error("Error fetching data from Client 1: {}", e.getMessage(), e);
        }
        
        try {
            // Fetch data from Client 2
            log.info("Fetching data from Client 2...");
            client2Service.fetchRates();
            log.info("Successfully fetched and saved data from Client 2");
            
        } catch (Exception e) {
            log.error("Error fetching data from Client 2: {}", e.getMessage(), e);
        }
        
        try {
            // Fetch data from Client 3
            log.info("Fetching data from Client 3...");
            client3Service.fetchRates();
            log.info("Successfully fetched and saved data from Client 3");
            
        } catch (Exception e) {
            log.error("Error fetching data from Client 3: {}", e.getMessage(), e);
        }
        
        log.info("Scheduled data fetch completed for all clients");
    }
} 