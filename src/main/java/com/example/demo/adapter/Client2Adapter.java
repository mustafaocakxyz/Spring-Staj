package com.example.demo.adapter;

import com.example.demo.service.Client2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Client2Adapter implements ClientAdapter {
    
    @Autowired
    private Client2Service client2Service;
    
    @Override
    public Map<String, Double> fetchRates() {
        // Client2Service already returns Map<String, Double>, so we can return it directly
        Map<String, Double> rates = client2Service.fetchRates();
        
        // Return the rates or empty map if null
        return rates != null ? rates : Map.of();
    }
} 