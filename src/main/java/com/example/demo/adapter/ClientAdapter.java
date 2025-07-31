package com.example.demo.adapter;

import java.util.Map;

public interface ClientAdapter {
    /**
     * Fetches currency rates from the client API
     * @return Map of currency codes to rates
     */
    Map<String, Double> fetchRates();
} 