package com.cityatlas.services;

import com.cityatlas.dtos.ChangePasswordDto;
import com.cityatlas.dtos.UserDto;
import com.cityatlas.models.User;
import com.cityatlas.repositories.UserRepo;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<UserDto> getAllUsers() {
        List<User> users = userRepo.findAll();
        return users.stream()
                .map(user -> new UserDto(user.getId(), user.getUsername(), user.getAuthorities()))
                .collect(Collectors.toList());
    }

    public Optional<UserDto> getUserByUsername(String username) {
        return Optional.ofNullable(userRepo.findByUsername(username))
                .map(user -> new UserDto(user.getId(), user.getUsername(), user.getAuthorities()));
    }

    public String updatePassword (String username, ChangePasswordDto changePasswordDto){
        User user = userRepo.findByUsername(username);
        if (user != null) {
            user.setPassword(changePasswordDto.getNewPassword());
            userRepo.save(user);
            return "Password updated";
        }
        else {
            throw new RuntimeException("User not found");
        }
    }

    public UserDto setRoles(UserDto user) {
        UserDto userToUpdate = getUserByUsername(user.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        userToUpdate.setAuthorities(user.getAuthorities());
        return userToUpdate;
    }

    public void deleteUser(String username) {
        User user = userRepo.findByUsername(username);
        if (user != null) {
            userRepo.delete(user);
        }
        else {
            throw new RuntimeException("User not found");
        }
    }

    public UserDto setRoles(String username, UserDto user){
        UserDto userToSetRoles = getUserByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        userToSetRoles.setAuthorities(user.getAuthorities());
        return userToSetRoles;
    }




}
