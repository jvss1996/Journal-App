package com.shekhawat.journalApp.controller;

import com.shekhawat.journalApp.entity.User;
import com.shekhawat.journalApp.repository.UserRepository;
import com.shekhawat.journalApp.service.GoogleAuthService;
import com.shekhawat.journalApp.service.UserDetailsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/auth/google")
@Tag(name = "Google Auth APIs")
public class GoogleAuthController {

    @Autowired
    private GoogleAuthService googleAuthService;

    @GetMapping("/callback")
    @Operation(summary = "exchange code for token")
    public ResponseEntity<?> handleGoogleCallback(@RequestParam String code) {
        return googleAuthService.exchange(code);
    }
}
