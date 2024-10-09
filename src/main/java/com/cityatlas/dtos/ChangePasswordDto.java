package com.cityatlas.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChangePasswordDto {
    private String username;
    private String currentPassword;
    private String newPassword;
}
