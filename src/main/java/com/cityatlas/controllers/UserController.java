package com.cityatlas.controllers;

import com.cityatlas.dtos.ChangePasswordDto;
import com.cityatlas.dtos.UserDto;
import com.cityatlas.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/admin/user")
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/{username}")
    public ResponseEntity<Optional<UserDto>> getUser(@PathVariable String username) {
        if (Objects.isNull(userService.getUserByUsername(username))) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        if (userService.getAllUsers().isEmpty()) {
            return ResponseEntity.status(404).build();
        }
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @PutMapping
    public ResponseEntity<String> updatePassword(@RequestBody ChangePasswordDto changePasswordDto) {
        if (!userService.getPassword(changePasswordDto.getUsername()).equals(changePasswordDto.getCurrentPassword())) {
            return ResponseEntity.status(400).body("Current password is incorrect");
        }
        if (changePasswordDto.getCurrentPassword().equals(changePasswordDto.getNewPassword())) {
            return ResponseEntity.status(400).body("New password cannot be the same as the current password");
        }
        userService.updatePassword(changePasswordDto);
        return ResponseEntity.ok("Password updated");
    }
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        String response = userService.deleteUser(username);
        if (userService.deleteUser(username).equals(response)) {
            return ResponseEntity.status(404).body(response);
        }
        return ResponseEntity.ok("User deleted");
    }
    @PutMapping("/roles")
    public ResponseEntity<UserDto> setRoles(@RequestBody UserDto userDto, @AuthenticationPrincipal UserDetails userDetails) {

        String loggedInUsername = userDetails.getUsername();

        if (userDto.getUsername().equals(loggedInUsername)) {
            return ResponseEntity.status(400).build();
        }

        return ResponseEntity.ok(userService.setRoles(userDto).get());
    }


}
