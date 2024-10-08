package com.cityatlas.services;

import com.cityatlas.dtos.ChangePasswordDto;
import com.cityatlas.dtos.UserDto;
import com.cityatlas.models.User;
import com.cityatlas.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Serviceklass som hanterar användarrelaterade operationer, som att hämta, uppdatera och radera användare.
 */
@Service
public class UserService {

    @Autowired
    private final UserRepo userRepo;  // Repository för att interagera med användardatabasen

    /**
     * Konstruktor som injicerar UserRepo.
     *
     * @param userRepo UserRepo för att utföra CRUD-operationer på användare.
     */
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * Hämtar alla användare från databasen och konverterar dem till UserDto-objekt.
     *
     * @return En lista av UserDto som representerar alla användare.
     */
    public List<UserDto> getAllUsers() {
        List<User> users = userRepo.findAll();  // Hämta alla användare
        // Konvertera varje User till UserDto och samla dem i en lista
        return users.stream()
                .map(user -> new UserDto(user.getId(), user.getUsername(), user.getAuthorities()))
                .collect(Collectors.toList());
    }

    /**
     * Hämtar en användare baserat på användarnamnet och returnerar den som UserDto.
     *
     * @param username Användarnamnet för användaren som ska hämtas.
     * @return En Optional som innehåller UserDto om användaren hittas, annars tomt.
     */
    public Optional<UserDto> getUserByUsername(String username) {
        // Försök hämta användaren från databasen och konvertera den till UserDto
        return Optional.ofNullable(userRepo.findByUsername(username))
                .map(user -> new UserDto(user.getId(), user.getUsername(), user.getAuthorities()));
    }

    /**
     * Uppdaterar lösenordet för en användare om användaren hittas.
     *
     * @param changePasswordDto DTO som innehåller användarnamn och nytt lösenord.
     * @return En sträng som indikerar om lösenordet har uppdaterats eller om användaren inte hittades.
     */
    public String updatePassword(ChangePasswordDto changePasswordDto) {
        // Hämta användaren baserat på användarnamn
        User user = userRepo.findByUsername(changePasswordDto.getUsername());
        if (user != null) {
            // Uppdatera lösenord och sätt det uppdaterade datumet
            user.setPassword(changePasswordDto.getNewPassword());
            user.setUpdatedAt(new Date());
            userRepo.save(user);  // Spara uppdaterad användare i databasen
            return "Password updated";  // Returnera framgångsmeddelande
        } else {
            return new RuntimeException("User not found").getMessage();  // Returnera felmeddelande om användaren inte hittades
        }
    }

    /**
     * Uppdaterar rollerna för en användare om användaren hittas.
     *
     * @param userDto DTO som innehåller användarens nya roller.
     * @return En Optional som innehåller den uppdaterade UserDto, eller tomt om användaren inte hittas.
     */
    public Optional<UserDto> setRoles(UserDto userDto) {
        // Hämta användaren som ska uppdateras
        User userToUpdate = userRepo.findByUsername(userDto.getUsername());
        if (userToUpdate == null) {
            return Optional.empty();  // Returnera tom Optional om användaren inte hittas
        }

        // Uppdatera användarens roller och det uppdaterade datumet
        userToUpdate.setAuthorities(userDto.getAuthorities());
        userToUpdate.setUpdatedAt(new Date());
        User updatedUser = userRepo.save(userToUpdate);  // Spara den uppdaterade användaren

        // Returnera den uppdaterade användaren som UserDto
        return Optional.of(new UserDto(updatedUser.getId(), updatedUser.getUsername(), updatedUser.getAuthorities()));
    }

    /**
     * Raderar en användare från databasen baserat på användarnamn.
     *
     * @param username Användarnamnet för den användare som ska raderas.
     * @return En sträng som indikerar om användaren har raderats eller om användaren inte hittades.
     */
    public String deleteUser(String username) {
        // Hämta användaren som ska raderas
        User user = userRepo.findByUsername(username);
        if (user != null) {
            userRepo.delete(user);  // Ta bort användaren från databasen
        } else {
            return new RuntimeException("User not found").getMessage();  // Returnera felmeddelande om användaren inte hittades
        }
        return "User deleted";  // Returnera framgångsmeddelande
    }

    /**
     * Hämtar lösenordet för en användare baserat på användarnamn.
     *
     * @param username Användarnamnet för användaren vars lösenord ska hämtas.
     * @return En sträng som innehåller användarens lösenord eller ett felmeddelande om användaren inte hittades.
     */
    public String getPassword(String username) {
        // Hämta användaren baserat på användarnamn
        User user = userRepo.findByUsername(username);
        if (user != null) {
            return user.getPassword();  // Returnera användarens lösenord
        } else {
            return new RuntimeException("User not found").getMessage();  // Returnera felmeddelande om användaren inte hittades
        }
    }
}
