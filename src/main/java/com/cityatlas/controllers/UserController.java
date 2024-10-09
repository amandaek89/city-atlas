package com.cityatlas.controllers;

import com.cityatlas.dtos.ChangePasswordDto;
import com.cityatlas.dtos.UserDto;
import com.cityatlas.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Tag(name = "User Management", description = "API för hantering av användarrelaterade operationer som lösenordsändring och rollhantering.") // Swagger Tag
@RestController
@RequestMapping("/admin/user")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Operation(summary = "Hämta användare", description = "Hämtar en specifik användare baserat på användarnamn.")
    @GetMapping("/{username}")
    public ResponseEntity<Optional<UserDto>> getUser(@PathVariable String username) {
        if (Objects.isNull(userService.getUserByUsername(username))) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @Operation(summary = "Hämta alla användare", description = "Returnerar en lista med alla användare.")
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        if (userService.getAllUsers().isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @Operation(summary = "Uppdatera lösenord", description = "Uppdaterar ett lösenord för en specifik användare. Kräver nuvarande lösenord för att ändra till ett nytt.")
    @PutMapping
    public ResponseEntity<String> updatePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        String encryptedPassword = userService.getPassword(changePasswordDto.getUsername());

        if(userService.getUserByUsername(changePasswordDto.getUsername()).isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        if (!passwordEncoder.matches(changePasswordDto.getCurrentPassword(), encryptedPassword)) {
            return ResponseEntity.status(400).body("Current password is incorrect");
        }

        if (passwordEncoder.matches(changePasswordDto.getNewPassword(), encryptedPassword)) {
            return ResponseEntity.status(400).body("New password cannot be the same as the current password");
        }

        String newEncryptedPassword = passwordEncoder.encode(changePasswordDto.getNewPassword());
        userService.updatePassword(changePasswordDto.getUsername(), newEncryptedPassword);

        return ResponseEntity.ok("Password updated");
    }

    @Operation(summary = "Radera användare", description = "Raderar en specifik användare baserat på användarnamn.")
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        String response = userService.deleteUser(username);
        if (userService.deleteUser(username).equals(response)) {
            return ResponseEntity.status(404).body(response);
        }
        return ResponseEntity.ok("User deleted");
    }

    @Operation(summary = "Sätt roller", description = "Sätter roller för en användare. Användaren kan inte ändra sina egna roller.")
    @PutMapping("/roles")
    public ResponseEntity<UserDto> setRoles(@RequestBody UserDto userDto, @AuthenticationPrincipal UserDetails userDetails) {
        String loggedInUsername = userDetails.getUsername();

        if (userDto.getUsername().equals(loggedInUsername)) {
            return ResponseEntity.status(400).build();
        }

        return ResponseEntity.ok(userService.setRoles(userDto).get());
    }
}
