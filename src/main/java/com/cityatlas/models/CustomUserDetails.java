package com.cityatlas.models;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean isAccountNonExpired;
    private boolean isAccountNonLocked;
    private boolean isCredentialsNonExpired;
    private boolean isEnabled;

    public  CustomUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities = user.getRoles(); // Antag att roller implementerar GrantedAuthority
        this.isAccountNonExpired = true; // Kan anpassas
        this.isAccountNonLocked = true; // Kan anpassas
        this.isCredentialsNonExpired = true; // Kan anpassas
        this.isEnabled = true; // Kan anpassas
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isAccountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isAccountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isCredentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
