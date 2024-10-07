package com.cityatlas.controllers;

import com.cityatlas.dtos.ChangePasswordDto;
import com.cityatlas.dtos.UserDto;
import com.cityatlas.services.JwtService;
import com.cityatlas.services.UserService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
    @PutMapping
    public ResponseEntity<String> updatePassword(@RequestBody ChangePasswordDto changePasswordDto, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        String password = userDetails.getPassword();
        if (changePasswordDto.getNewPassword().equals(password)) {
            throw new RuntimeException("New password cannot be the same as the old password");
        }
        if (changePasswordDto.getCurrentPassword().equals(password)){
            throw new RuntimeException("Current password is incorrect");
        }
        userService.updatePassword(username, changePasswordDto);
        return ResponseEntity.ok("Password updated");
    }
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok("User deleted");
    }
    @PutMapping("/roles")
    public ResponseEntity<UserDto> setRoles(@RequestBody UserDto userDto, @AuthenticationPrincipal UserDetails userDetails) {

        String loggedInUsername = userDetails.getUsername();

        if (userDto.getUsername().equals(loggedInUsername)) {
            throw new RuntimeException("You cannot change your own role");
        }

        return ResponseEntity.ok(userService.setRoles(userDto));
    }


}
