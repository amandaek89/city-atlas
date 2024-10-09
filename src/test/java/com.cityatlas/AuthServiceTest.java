package com.cityatlas;

import com.cityatlas.models.AuthRequest;
import com.cityatlas.models.User;
import com.cityatlas.repositories.UserRepo;
import com.cityatlas.services.AuthService;
import com.cityatlas.services.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void register_newUser_registersSuccessfully() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("newuser");
        authRequest.setPassword("password");

        // Mocking the user repository to return null for a new username
        when(userRepo.findByUsername("newuser")).thenReturn(null);
        when(passwordEncoder.encode("password")).thenReturn("encodedpassword");

        // Registering the new user
        String result = authService.register(authRequest);

        assertEquals("User registered successfully", result);
        // Verifying that the user is saved to the repository
        verify(userRepo).save(any(User.class));
    }

    @Test
    void register_existingUser_throwsException() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("existinguser");
        authRequest.setPassword("password");

        // Mocking the user repository to return an existing user
        when(userRepo.findByUsername("existinguser")).thenReturn(new User());

        // Attempting to register an existing user
        String result = authService.register(authRequest);

        assertEquals("User already exists", result);
        // Verifying that save is never called when user already exists
        verify(userRepo, never()).save(any(User.class));
    }

    @Test
    void authenticate_validCredentials_returnsToken() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("validuser");
        authRequest.setPassword("password");

        User user = new User();
        user.setUsername("validuser");
        user.setPassword("encodedpassword");

        // Mocking repository and password check for valid user
        when(userRepo.findByUsername("validuser")).thenReturn(user);
        when(passwordEncoder.matches("password", "encodedpassword")).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("token");

        // Authenticating the valid user
        String result = authService.authenticate(authRequest);

        assertEquals("token", result);
        // Verifying that the token is generated
        verify(jwtService).generateToken(user);
    }

    @Test
    void authenticate_invalidUsername_returnsUserNotFound() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("invaliduser");
        authRequest.setPassword("password");

        // Mocking repository to return null for invalid user
        when(userRepo.findByUsername("invaliduser")).thenReturn(null);

        // Authenticating the invalid user
        String result = authService.authenticate(authRequest);

        assertEquals("User not found", result);
        // Verifying that password checking is not called
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    void authenticate_invalidPassword_throwsException() {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUsername("validuser");
        authRequest.setPassword("wrongpassword");

        User user = new User();
        user.setUsername("validuser");
        user.setPassword("encodedpassword");

        // Mocking repository and password check for valid user
        when(userRepo.findByUsername("validuser")).thenReturn(user);
        when(passwordEncoder.matches("wrongpassword", "encodedpassword")).thenReturn(false);

        // Authenticating the valid user with wrong password
        String result = authService.authenticate(authRequest);

        assertEquals("Invalid login credentials", result);
    }
}
