package com.cityatlas.controllers;

import com.cityatlas.dtos.UserDto;
import com.cityatlas.models.AuthRequest;
import com.cityatlas.models.AuthResponse;
import com.cityatlas.models.User;
import com.cityatlas.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // Endpoint för att registrera en ny användare
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto user) {
        authService.register(user);
        return ResponseEntity.ok("User registered successfully");
    }

    // Endpoint för att logga in och få JWT-token
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        String token = authService.authenticate(authRequest);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}