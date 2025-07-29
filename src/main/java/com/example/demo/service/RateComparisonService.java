package com.example.demo.service;

import com.example.demo.dto.RateComparisonResult;
import com.example.demo.entity.Client1Rate;
import com.example.demo.entity.Client2Rate;
import com.example.demo.entity.Client3Rate;
import com.example.demo.repository.Client1RateRepository;
import com.example.demo.repository.Client2RateRepository;
import com.example.demo.repository.Client3RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RateComparisonService {
    @Autowired
    private Client1RateRepository client1RateRepository;
    @Autowired
    private Client2RateRepository client2RateRepository;
    @Autowired
    private Client3RateRepository client3RateRepository;

    public List<RateComparisonResult> compareRates() {
        List<Client1Rate> client1Rates = client1RateRepository.findAll();
        List<Client2Rate> client2Rates = client2RateRepository.findAll();
        List<Client3Rate> client3Rates = client3RateRepository.findAll();

        Map<String, Double> client1Map = client1Rates.stream()
                .collect(Collectors.toMap(Client1Rate::getCurrencyCode, Client1Rate::getRate, (a, b) -> b));
        Map<String, Double> client2Map = client2Rates.stream()
                .collect(Collectors.toMap(Client2Rate::getCurrencyCode, Client2Rate::getRate, (a, b) -> b));
        Map<String, Double> client3Map = client3Rates.stream()
                .collect(Collectors.toMap(Client3Rate::getCurrencyCode, Client3Rate::getRate, (a, b) -> b));

        Set<String> allCurrencies = new HashSet<>();
        allCurrencies.addAll(client1Map.keySet());
        allCurrencies.addAll(client2Map.keySet());
        allCurrencies.addAll(client3Map.keySet());

        List<RateComparisonResult> results = new ArrayList<>();
        for (String currency : allCurrencies) {
            Double rate1 = client1Map.get(currency);
            Double rate2 = client2Map.get(currency);
            Double rate3 = client3Map.get(currency);
            
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