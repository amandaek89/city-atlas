package com.cityatlas.controllers;

import com.cityatlas.models.AuthRequest;
import com.cityatlas.models.AuthResponse;
import com.cityatlas.services.AuthService;
import com.cityatlas.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @Autowired
    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    // Endpoint för att registrera en ny användare
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest authRequest) {
        String response = authService.register(authRequest);
        if (response.equals("User already exists")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        return ResponseEntity.ok(response);
    }

    // Endpoint för att logga in och få JWT-token
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        String response = authService.authenticate(authRequest);
        if (response.equals("User not found")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        if (response.equals( "Invalid login credentials")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        return ResponseEntity.ok(new AuthResponse(response));
    }

}