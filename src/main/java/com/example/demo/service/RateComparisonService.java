package com.example.demo.service;

import com.example.demo.adapter.ClientAdapter;
import com.example.demo.dto.RateComparisonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

@Service
@Slf4j
public class RateComparisonService {
    
    @Autowired
    private List<ClientAdapter> clientAdapters;

    public List<RateComparisonResult> compareRates() {
        StopWatch totalWatch = new StopWatch("rate-compare");
        totalWatch.start("fetch-all-clients");

        // Fetch from all adapters in parallel while preserving order by index
        Map<Integer, Map<String, Double>> indexToRates = new ConcurrentHashMap<>();
        IntStream.range(0, clientAdapters.size()).parallel().forEach(idx -> {
            ClientAdapter adapter = clientAdapters.get(idx);
            StopWatch clientWatch = new StopWatch("client-" + idx);
            clientWatch.start("fetchRates");
            Map<String, Double> rates = adapter.fetchRates();
            clientWatch.stop();
            indexToRates.put(idx, rates);
            log.info("[Perf] clientIndex={} duration_ms={} fetched_keys={}", idx, clientWatch.getTotalTimeMillis(), rates != null ? rates.size() : 0);
        });

        // Restore ordered list by index
        List<Map<String, Double>> allRates = new ArrayList<>();
        for (int i = 0; i < clientAdapters.size(); i++) {
            allRates.add(indexToRates.getOrDefault(i, Map.of()));
        }

        totalWatch.stop();
        long fetchAllMs = totalWatch.getLastTaskTimeMillis();

        totalWatch.start("compare-logic");
        // For now, we'll use the first three adapters (Client1, Client2, Client3)
        if (allRates.size() >= 3) {
            Map<String, Double> client1Rates = allRates.get(0);
            Map<String, Double> client2Rates = allRates.get(1);
            Map<String, Double> client3Rates = allRates.get(2);

            Set<String> allCurrencies = new HashSet<>();
            allCurrencies.addAll(client1Rates.keySet());
            allCurrencies.addAll(client2Rates.keySet());
            allCurrencies.addAll(client3Rates.keySet());

            List<RateComparisonResult> results = new ArrayList<>();
            for (String currency : allCurrencies) {
                Double rate1 = client1Rates.get(currency);
                Double rate2 = client2Rates.get(currency);
                Double rate3 = client3Rates.get(currency);
                if (rate1 != null && rate2 != null && rate3 != null) {
                    Double lowest;
                    String source;
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
            totalWatch.stop();
            long compareMs = totalWatch.getLastTaskTimeMillis();
            log.info("[Perf] fetch_all_ms={} compare_ms={} total_ms={}", fetchAllMs, compareMs, fetchAllMs + compareMs);
            return results;
        }
        totalWatch.stop();
        long compareMs = totalWatch.getLastTaskTimeMillis();
        log.info("[Perf] fetch_all_ms={} compare_ms={} total_ms={} (not enough adapters)", fetchAllMs, compareMs, fetchAllMs + compareMs);
        return new ArrayList<>();
    }
} 