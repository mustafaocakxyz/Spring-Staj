package com.example.demo.adapter.impl;

import com.example.demo.adapter.ClientAdapter;
import com.example.demo.dto.Client1Response;
import com.example.demo.service.Client1Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Client1Adapter implements ClientAdapter {
    
    @Autowired
    private Client1Service client1Service;
    
    @Override
    public Map<String, Double> fetchRates() {
        // Call the original service
        Client1Response response = client1Service.fetchRates();
        
        // Convert the response to the common format (Map<String, Double>)
        if (response != null && response.getConversion_rates() != null) {
            return response.getConversion_rates();
        }
        
        return Map.of(); // Return empty map if no data
    }
} 