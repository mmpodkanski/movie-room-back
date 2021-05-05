package io.github.mmpodkanski.user.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class RegisterRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private final String username;

    @NotBlank
    @Size(max = 30)
    @Email
    private final String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private final String password;

    RegisterRequest(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}