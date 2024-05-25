package com.polarbookshop.catalog_service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<String> getGreeting (){
        return ResponseEntity.ok("Welcome to the book catalog!");
    }
}
