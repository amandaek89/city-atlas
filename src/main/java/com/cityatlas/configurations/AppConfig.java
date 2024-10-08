package com.cityatlas.configurations;

import com.cityatlas.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Configuration
public class AppConfig {


    private final UserRepo userRepo;
    @Autowired
    public AppConfig(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Skapar en PasswordEncoder för att kryptera lösenord
    }

    @Bean
    UserDetailsService userDetailsService() {
        try {
            return username -> (UserDetails) userRepo.findByUsername(username);
        }
        catch (Exception e) {
            throw new UsernameNotFoundException("User not found");
        }

    }



}
