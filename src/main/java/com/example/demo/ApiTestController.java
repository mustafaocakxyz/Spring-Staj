package com.example.demo;

import com.example.demo.dto.Client1Response;
import com.example.demo.dto.Client2Response;
import com.example.demo.service.Client1Service;
import com.example.demo.service.Client2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class ApiTestController {
    @Autowired
    private Client1Service client1Service;
    @Autowired
    private Client2Service client2Service;

    @GetMapping("/client1")
    public Client1Response testClient1() {
        return client1Service.fetchRates();
    }

    @PostMapping("/client2")
    public Map<String, Double> testClient2() {
        return client2Service.fetchRates();
    }
} 