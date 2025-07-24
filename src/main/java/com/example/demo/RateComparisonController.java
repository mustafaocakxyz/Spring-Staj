package com.example.demo;

import com.example.demo.dto.RateComparisonResult;
import com.example.demo.service.RateComparisonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RateComparisonController {
    @Autowired
    private RateComparisonService rateComparisonService;

    @GetMapping("/compare")
    public String compareRates(Model model) {
        List<RateComparisonResult> results = rateComparisonService.compareRates();
        model.addAttribute("results", results);
        return "compare";
    }
} 