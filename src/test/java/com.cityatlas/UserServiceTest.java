package com.cityatlas;

import com.cityatlas.dtos.UserDto;
import com.cityatlas.models.Role;
import com.cityatlas.models.User;
import com.cityatlas.repositories.UserRepo;
import com.cityatlas.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
class UserServiceTest {

    @Mock
    private UserRepo userRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User("testUser", "oldPassword");
        user.setId(1L);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        Set<Role> roles = new HashSet<>();
        roles.add(Role.ROLE_USER); // Assuming there's an enum Role with USER defined
        user.setAuthorities(roles);
    }

    @Test
    void testGetAllUsers() {
        when(userRepo.findAll()).thenReturn(List.of(user));

        List<UserDto> users = userService.getAllUsers();

        assertEquals(1, users.size());
        assertEquals("testUser", users.get(0).getUsername());
        verify(userRepo, times(1)).findAll();
    }

    @Test
    void testGetUserByUsername_UserFound() {
        when(userRepo.findByUsername("testUser")).thenReturn(user);

        Optional<UserDto> userDto = userService.getUserByUsername("testUser");

        assertTrue(userDto.isPresent());
        assertEquals("testUser", userDto.get().getUsername());
        verify(userRepo, times(1)).findByUsername("testUser");
    }

    @Test
    void testGetUserByUsername_UserNotFound() {
        when(userRepo.findByUsername("nonExistentUser")).thenReturn(null);

        Optional<UserDto> userDto = userService.getUserByUsername("nonExistentUser");

        assertFalse(userDto.isPresent());
        verify(userRepo, times(1)).findByUsername("nonExistentUser");
    }

    @Test
    void testUpdatePassword_UserFound() {
        String newEncryptedPassword = "newPassword";
        when(userRepo.findByUsername("testUser")).thenReturn(user);
        when(userRepo.save(any(User.class))).thenReturn(user);

        String result = userService.updatePassword("testUser", newEncryptedPassword);

        assertEquals("Password updated", result);
        assertEquals(newEncryptedPassword, user.getPassword());
        verify(userRepo, times(1)).findByUsername("testUser");
        verify(userRepo, times(1)).save(user);
    }

    @Test
    void testUpdatePassword_UserNotFound() {
        String result = userService.updatePassword("nonExistentUser", "newPassword");

        assertEquals("User not found", result);
        verify(userRepo, times(1)).findByUsername("nonExistentUser");
        verify(userRepo, never()).save(any(User.class));
    }

    @Test
    void testSetRoles_UserFound() {
        UserDto userDto = new UserDto(user.getId(), user.getUsername(), Set.of(Role.ROLE_ADMIN));
        when(userRepo.findByUsername("testUser")).thenReturn(user);
        when(userRepo.save(any(User.class))).thenReturn(user);

        Optional<UserDto> updatedUserDto = userService.setRoles(userDto);

        assertTrue(updatedUserDto.isPresent());
        assertEquals(Role.ROLE_ADMIN, updatedUserDto.get().getAuthorities().iterator().next());
        verify(userRepo, times(1)).findByUsername("testUser");
        verify(userRepo, times(1)).save(user);
    }

    @Test
    void testSetRoles_UserNotFound() {
        UserDto userDto = new UserDto(1L, "nonExistentUser", Set.of(Role.ROLE_ADMIN));
        when(userRepo.findByUsername("nonExistentUser")).thenReturn(null);

        Optional<UserDto> updatedUserDto = userService.setRoles(userDto);

        assertFalse(updatedUserDto.isPresent());
        verify(userRepo, times(1)).findByUsername("nonExistentUser");
        verify(userRepo, never()).save(any(User.class));
    }

    @Test
    void testDeleteUser_UserFound() {
        when(userRepo.findByUsername("testUser")).thenReturn(user);

        String result = userService.deleteUser("testUser");

        assertEquals("User deleted", result);
        verify(userRepo, times(1)).findByUsername("testUser");
        verify(userRepo, times(1)).delete(user);
    }

    @Test
    void testDeleteUser_UserNotFound() {
        when(userRepo.findByUsername("nonExistentUser")).thenReturn(null);

        String result = userService.deleteUser("nonExistentUser");

        assertEquals("User not found", result);
        verify(userRepo, times(1)).findByUsername("nonExistentUser");
        verify(userRepo, never()).delete(any(User.class));
    }

    @Test
    void testGetPassword_UserFound() {
        when(userRepo.findByUsername("testUser")).thenReturn(user);

        String password = userService.getPassword("testUser");

        assertEquals("oldPassword", password);
        verify(userRepo, times(1)).findByUsername("testUser");
    }

    @Test
    void testGetPassword_UserNotFound() {
        when(userRepo.findByUsername("nonExistentUser")).thenReturn(null);

        String password = userService.getPassword("nonExistentUser");

        assertEquals("User not found", password);
        verify(userRepo, times(1)).findByUsername("nonExistentUser");
    }
}
