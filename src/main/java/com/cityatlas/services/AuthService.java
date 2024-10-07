package com.cityatlas.services;

import com.cityatlas.dtos.UserDto;
import com.cityatlas.models.AuthRequest;
import com.cityatlas.models.User;
import com.cityatlas.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {

    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public AuthService(UserRepo userRepo, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(AuthRequest authRequest) {

        if (userRepo.findByUsername(authRequest.getUsername()) != null) {
            throw new RuntimeException("User already exists");
        }

        User newUser = new User();

        newUser.setUsername(authRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        newUser.setCreatedAt(new Date());
        newUser.setUpdatedAt(new Date());
        userRepo.save(newUser);
    }

    public String authenticate(AuthRequest authRequest) {
        User user = userRepo.findByUsername(authRequest.getUsername());

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
            return jwtService.generateToken(user);
        }

        throw new RuntimeException("Invalid credentials");
    }

}
