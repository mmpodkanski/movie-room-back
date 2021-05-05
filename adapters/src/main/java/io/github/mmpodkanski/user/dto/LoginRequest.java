package io.github.mmpodkanski.user.dto;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private final String username;

    @NotBlank
    private final String password;

    LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}