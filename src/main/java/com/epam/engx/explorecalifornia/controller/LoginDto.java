package com.epam.engx.explorecalifornia.controller;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginDto {
    @NotNull
    private String username;
    @NotNull
    private String password;
    private String firstName;
    private String lastName;

    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
