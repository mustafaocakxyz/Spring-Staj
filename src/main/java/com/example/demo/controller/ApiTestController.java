package com.example.demo.controller;

import com.example.demo.service.Client1Service;
import com.example.demo.service.Client2Service;
import com.example.demo.service.Client3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @Autowired
    private Client3Service client3Service;

    @GetMapping("/client1")
    public ResponseEntity<?> testClient1() {
        try {
            var result = client1Service.fetchRates();
            return ResponseEntity.ok(Map.of(
                "status", "success",
                "data", result
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                "status", "error",
                "message", e.getMessage(),
                "exception", e.getClass().getSimpleName()
            ));
        }
    }

    @GetMapping("/client2")
    public ResponseEntity<?> testClient2() {
        try {
            var result = client2Service.fetchRates();
            return ResponseEntity.ok(Map.of(
                "status", "success",
                "data", result
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                "status", "error",
                "message", e.getMessage(),
                "exception", e.getClass().getSimpleName()
            ));
        }
    }

    @GetMapping("/client3")
    public ResponseEntity<?> testClient3() {
        try {
            var result = client3Service.fetchRates();
            return ResponseEntity.ok(Map.of(
                "status", "success",
                "data", result
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                "status", "error",
                "message", e.getMessage(),
                "exception", e.getClass().getSimpleName()
            ));
        }
    }

    @GetMapping("/database")
    public ResponseEntity<?> testDatabase() {
        try {
            // Test database connectivity by trying to fetch some data
            var client1Rates = client1Service.fetchRates();
            return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Database connection working",
                "sampleData", client1Rates
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                "status", "error",
                "message", "Database connection failed",
                "exception", e.getMessage()
            ));
        }
    }
} 