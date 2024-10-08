package com.cityatlas.services;

import com.cityatlas.dtos.UserDto;
import com.cityatlas.models.AuthRequest;
import com.cityatlas.models.Role;
import com.cityatlas.models.User;
import com.cityatlas.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Serviceklass för autentisering och användarregistrering.
 * Den hanterar skapande av nya användare och autentisering av befintliga användare.
 */
@Service
public class AuthService {

    private final UserRepo userRepo;                  // Repository för att hantera användare i databasen
    private final JwtService jwtService;              // Tjänst för att hantera JWT-token
    private final PasswordEncoder passwordEncoder;    // Verktyg för att kryptera och kontrollera lösenord

    /**
     * Konstruktor för att injicera nödvändiga beroenden.
     *
     * @param userRepo        Användarrepository för att hantera databasanrop.
     * @param jwtService      JWT-tjänst för att skapa och verifiera token.
     * @param passwordEncoder PasswordEncoder för att hantera lösenordskryptering.
     */
    @Autowired
    public AuthService(UserRepo userRepo, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registrerar en ny användare baserat på en AuthRequest.
     *
     * @param authRequest Innehåller användarnamn och lösenord för den nya användaren.
     * @return En sträng som anger att användaren har registrerats, eller ett felmeddelande.
     */
    public String register(AuthRequest authRequest) {

        // Kontrollera om användarnamnet redan finns i databasen
        if (userRepo.findByUsername(authRequest.getUsername()) != null) {
            return new RuntimeException("User already exists").getMessage();
        }

        // Skapa en ny användare
        User newUser = new User();
        newUser.setUsername(authRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(authRequest.getPassword()));  // Kryptera lösenordet

        // Tilldela användaren standardrollen 'ROLE_USER'
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_USER);
        newUser.setCreatedAt(new Date());   // Ange skapelsedatum
        newUser.setUpdatedAt(new Date());   // Ange senaste uppdateringsdatum
        newUser.setAuthorities(roles);      // Sätt användarens roller

        userRepo.save(newUser);  // Spara den nya användaren i databasen

        return "User registered successfully";
    }

    /**
     * Autentiserar en användare baserat på en AuthRequest.
     *
     * @param authRequest Innehåller användarnamn och lösenord för autentisering.
     * @return En JWT-token om autentiseringen lyckas, annars ett felmeddelande.
     */
    public String authenticate(AuthRequest authRequest) {
        // Hämta användaren från databasen baserat på användarnamnet
        User user = userRepo.findByUsername(authRequest.getUsername());

        // Kontrollera om användaren finns
        if (user == null) {
            return "User not found";
        }

        // Verifiera lösenordet
        if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            return jwtService.generateToken(user);  // Generera JWT-token om lösenordet är korrekt
        }

        return new RuntimeException("Invalid login credentials").getMessage();  // Felmeddelande vid fel lösenord
    }

}
