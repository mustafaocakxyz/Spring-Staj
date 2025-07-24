package com.example.demo.dto;

import lombok.Data;
import java.util.Map;

@Data
public class Client1Response {
    private String result;
    private String faq;
    private String terms_of_use;
    private String start_date;
    private String end_date;
    private String base_code;
    private Map<String, Double> conversion_rates;
} 