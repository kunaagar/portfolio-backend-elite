package com.portfolio.elite.customerservice.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1")
@RestController
public class CustomerConfigServerApi {

    private final String message;

    public CustomerConfigServerApi(@Value("${customer.service.message:INVALID_CONFIG}") String message) {
        this.message = message;
    }

    @GetMapping("/config-checks")
    public ResponseEntity<String> getConfig() {
        return  ResponseEntity.ok(message);
    }
}
