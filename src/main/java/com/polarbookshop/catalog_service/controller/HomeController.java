package com.polarbookshop.catalog_service.controller;

import com.polarbookshop.catalog_service.config.PolarConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    private final PolarConfigurationProperties polarConfigurationProperties;

    public HomeController(PolarConfigurationProperties polarConfigurationProperties) {
        this.polarConfigurationProperties = polarConfigurationProperties;
    }

    @GetMapping("/")
    public ResponseEntity<String> getGreeting (){
        return ResponseEntity.ok(polarConfigurationProperties.getGreeting());
    }
}
