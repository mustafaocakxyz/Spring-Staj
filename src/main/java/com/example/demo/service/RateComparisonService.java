package com.example.demo.service;

import com.example.demo.dto.RateComparisonResult;
import com.example.demo.entity.Client1Rate;
import com.example.demo.entity.Client2Rate;
import com.example.demo.repository.Client1RateRepository;
import com.example.demo.repository.Client2RateRepository;
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

    public List<RateComparisonResult> compareRates() {
        List<Client1Rate> client1Rates = client1RateRepository.findAll();
        List<Client2Rate> client2Rates = client2RateRepository.findAll();

        Map<String, Double> client1Map = client1Rates.stream()
                .collect(Collectors.toMap(Client1Rate::getCurrencyCode, Client1Rate::getRate, (a, b) -> b));
        Map<String, Double> client2Map = client2Rates.stream()
                .collect(Collectors.toMap(Client2Rate::getCurrencyCode, Client2Rate::getRate, (a, b) -> b));

        Set<String> commonCurrencies = new HashSet<>(client1Map.keySet());
        commonCurrencies.retainAll(client2Map.keySet());

        List<RateComparisonResult> results = new ArrayList<>();
        for (String currency : commonCurrencies) {
            Double rate1 = client1Map.get(currency);
            Double rate2 = client2Map.get(currency);
            Double lowest = null;
            String source = null;
            if (rate1 != null && rate2 != null) {
                if (rate1 <= rate2) {
                    lowest = rate1;
                    source = "Client1";
                } else {
                    lowest = rate2;
                    source = "Client2";
                }
            }
            results.add(new RateComparisonResult(currency, rate1, rate2, lowest, source));
        }
        return results;
    }
} 