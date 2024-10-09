package com.cityatlas.controllers;

import com.cityatlas.models.AuthRequest;
import com.cityatlas.models.AuthResponse;
import com.cityatlas.services.AuthService;
import com.cityatlas.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication", description = "API för hantering av autentisering och registrering")
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    /**
     * Konstruktor för att injicera AuthService och UserService.
     *
     * @param authService - Tjänsten som hanterar autentisering och registrering.
     * @param userService - Tjänsten som hanterar användarrelaterade operationer.
     */
    @Autowired
    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    /**
     * Endpoint för att registrera en ny användare.
     *
     * @param authRequest - Begäran som innehåller användarnamn och lösenord för att registrera en användare.
     * @return ResponseEntity med ett meddelande som anger om registreringen lyckades eller om användaren redan finns (status 409 vid konflikt).
     */
    @Operation(summary = "Registrera ny användare", description = "Registrerar en ny användare i systemet med användarnamn och lösenord.")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthRequest authRequest) {
        String response = authService.register(authRequest);
        if (response.equals("User already exists")) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint för att logga in en användare och få en JWT-token vid lyckad autentisering.
     *
     * @param authRequest - Begäran som innehåller användarnamn och lösenord.
     * @return ResponseEntity med en JWT-token vid lyckad inloggning, annars en felmeddelande med status 401 (Unauthorized) om inloggningen misslyckas.
     */
    @Operation(summary = "Logga in användare", description = "Autentiserar användaren och returnerar en JWT-token vid lyckad inloggning.")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        String response = authService.authenticate(authRequest);

        // Om användaren inte hittas, returnera status 401 (Unauthorized)
        if (response.equals("User not found")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // Om inloggningsuppgifterna är felaktiga, returnera status 401 (Unauthorized)
        if (response.equals("Invalid login credentials")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // Returnera JWT-token vid lyckad inloggning
        return ResponseEntity.ok(new AuthResponse(response));
    }
}
