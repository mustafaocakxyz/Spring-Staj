package com.example.demo.service;

import com.example.demo.adapter.ClientAdapter;
import com.example.demo.adapter.Client1Adapter;
import com.example.demo.adapter.Client2Adapter;
import com.example.demo.adapter.Client3Adapter;
import com.example.demo.dto.RateComparisonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RateComparisonService {
    
    @Autowired
    private Client1Adapter client1Adapter;
    
    @Autowired
    private Client2Adapter client2Adapter;
    
    @Autowired
    private Client3Adapter client3Adapter;

    public List<RateComparisonResult> compareRates() {
        // Use adapters to get rates in common format
        Map<String, Double> client1Rates = client1Adapter.fetchRates();
        Map<String, Double> client2Rates = client2Adapter.fetchRates();
        Map<String, Double> client3Rates = client3Adapter.fetchRates();

        // Get all currencies that exist in all three clients
        Set<String> allCurrencies = new HashSet<>();
        allCurrencies.addAll(client1Rates.keySet());
        allCurrencies.addAll(client2Rates.keySet());
        allCurrencies.addAll(client3Rates.keySet());

        List<RateComparisonResult> results = new ArrayList<>();
        for (String currency : allCurrencies) {
            Double rate1 = client1Rates.get(currency);
            Double rate2 = client2Rates.get(currency);
            Double rate3 = client3Rates.get(currency);
            
            // Only include currencies that have data from all three clients
            if (rate1 != null && rate2 != null && rate3 != null) {
                Double lowest = null;
                String source = null;
                
                // Find the lowest rate among all three
                if (rate1 <= rate2 && rate1 <= rate3) {
                    lowest = rate1;
                    source = "Client1";
                } else if (rate2 <= rate1 && rate2 <= rate3) {
                    lowest = rate2;
                    source = "Client2";
                } else {
                    lowest = rate3;
                    source = "Client3";
                }
                
                results.add(new RateComparisonResult(currency, rate1, rate2, rate3, lowest, source));
            }
        }
        return results;
    }
} 