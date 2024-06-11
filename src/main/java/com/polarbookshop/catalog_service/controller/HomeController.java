package com.polarbookshop.catalog_service.controller;

import com.polarbookshop.catalog_service.config.PolarConfigurationProperties;
import com.polarbookshop.catalog_service.domain.UserData;
import com.polarbookshop.catalog_service.repository.UserDataRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
public class HomeController {

    private final PolarConfigurationProperties polarConfigurationProperties;
    private final UserDataRepository userDataRepository;

    public HomeController(PolarConfigurationProperties polarConfigurationProperties, UserDataRepository userDataRepository) {
        this.polarConfigurationProperties = polarConfigurationProperties;
        this.userDataRepository = userDataRepository;
    }

    @GetMapping("/")
    public ResponseEntity<String> getGreeting (){
        return ResponseEntity.ok(polarConfigurationProperties.getGreeting());
    }

    @GetMapping("/userdata")
    public ResponseEntity<Iterable<UserData>> getUserData(){
        return ResponseEntity.ok(userDataRepository.findAll());
    }

    @PostMapping("/userdata")
    public ResponseEntity<UserData> postUserData(
            @Valid @RequestPart UserData userData,
            @RequestParam("userProfile") MultipartFile file
    ) throws IOException {
        var fileBytes = file.isEmpty() ? null : file.getBytes();
        var newUserData = UserData.of(userData.username(), userData.emailId(), fileBytes);
        return new ResponseEntity<>(userDataRepository.save(newUserData), HttpStatus.CREATED);
    }
}
