package com.cityatlas.dtos;

import lombok.Data;

@Data
public class ChangePasswordDto {
    private String username;
    private String currentPassword;
    private String newPassword;
}
