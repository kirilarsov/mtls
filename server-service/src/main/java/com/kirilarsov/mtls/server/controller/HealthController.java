package com.kirilarsov.mtls.server.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @GetMapping("/health")
    public ResponseEntity getHealth() {
        System.out.println("Health called");
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
