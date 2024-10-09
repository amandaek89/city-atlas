package com.cityatlas.controllers;

import com.cityatlas.dtos.ChangePasswordDto;
import com.cityatlas.dtos.UserDto;
import com.cityatlas.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/admin/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Konstruktor för att injicera UserService.
     *
     * @param userService - Tjänsten som hanterar användaroperationer.
     */
    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Hämtar en specifik användare baserat på användarnamnet.
     *
     * @param username - Användarnamnet för användaren som ska hämtas.
     * @return ResponseEntity med UserDto-objektet om användaren hittas,
     *         annars status 404 (Not Found).
     */
    @GetMapping("/{username}")
    public ResponseEntity<Optional<UserDto>> getUser(@PathVariable String username) {
        if (Objects.isNull(userService.getUserByUsername(username))) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    /**
     * Hämtar alla användare.
     *
     * @return ResponseEntity med en lista över alla användare. Om listan är tom
     *         returneras status 404 (Not Found).
     */
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        if (userService.getAllUsers().isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * Uppdaterar ett lösenord för en specifik användare.
     *
     * @param changePasswordDto - DTO som innehåller användarnamn, nuvarande och nytt lösenord.
     * @return ResponseEntity som innehåller ett framgångsmeddelande om uppdatering lyckas,
     *         annars felkoder som beskriver problemet (400 om lösenorden är felaktiga).
     */
    @PutMapping
    public ResponseEntity<String> updatePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        // Hämta det krypterade lösenordet från databasen
        String encryptedPassword = userService.getPassword(changePasswordDto.getUsername());

        if(userService.getUserByUsername(changePasswordDto.getUsername()).isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        // Kontrollera att nuvarande lösenord matchar det krypterade lösenordet
        if (!passwordEncoder.matches(changePasswordDto.getCurrentPassword(), encryptedPassword)) {
            return ResponseEntity.status(400).body("Current password is incorrect");
        }

        // Kontrollera att nytt lösenord inte är samma som det gamla
        if (passwordEncoder.matches(changePasswordDto.getNewPassword(), encryptedPassword)) {
            return ResponseEntity.status(400).body("New password cannot be the same as the current password");
        }

        // Kryptera det nya lösenordet innan uppdatering
        String newEncryptedPassword = passwordEncoder.encode(changePasswordDto.getNewPassword());

        // Uppdatera lösenordet
        userService.updatePassword(changePasswordDto.getUsername(), newEncryptedPassword);

        return ResponseEntity.ok("Password updated");
    }


    /**
     * Raderar en specifik användare baserat på användarnamn.
     *
     * @param username - Användarnamnet för användaren som ska raderas.
     * @return ResponseEntity med ett meddelande om att användaren raderades, eller status 404 om användaren inte hittades.
     */
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        String response = userService.deleteUser(username);
        if (userService.deleteUser(username).equals(response)) {
            return ResponseEntity.status(404).body(response);
        }
        return ResponseEntity.ok("User deleted");
    }

    /**
     * Sätter roller för en användare. Förhindrar att en användare kan ändra sina egna roller.
     *
     * @param userDto - DTO som innehåller användarens roller som ska uppdateras.
     * @param userDetails - Information om den inloggade användaren (hämtas via JWT).
     * @return ResponseEntity med det uppdaterade UserDto-objektet eller status 400 om den inloggade användaren försöker ändra sina egna roller.
     */
    @PutMapping("/roles")
    public ResponseEntity<UserDto> setRoles(@RequestBody UserDto userDto, @AuthenticationPrincipal UserDetails userDetails) {

        // Hämta användarnamnet för den inloggade användaren
        String loggedInUsername = userDetails.getUsername();

        // Kontrollera om användaren försöker ändra sina egna roller
        if (userDto.getUsername().equals(loggedInUsername)) {
            return ResponseEntity.status(400).build();
        }

        // Uppdatera rollerna för den angivna användaren
        return ResponseEntity.ok(userService.setRoles(userDto).get());
    }

}
