package com.cityatlas.models;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
