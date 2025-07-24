package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RateComparisonResult {
    private String currencyCode;
    private Double client1Rate;
    private Double client2Rate;
    private Double lowestRate;
    private String source;
} 