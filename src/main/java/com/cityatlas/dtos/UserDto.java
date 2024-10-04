package com.cityatlas.dtos;

import com.cityatlas.models.Role;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserDto {

    private Long id;
    private String username;

    private Set<Role> authorities = new HashSet<>();

    public UserDto(long id, String username, Set<Role> authorities) {
        this.id = id;
        this.username = username;
        this.authorities = authorities;
    }
}
