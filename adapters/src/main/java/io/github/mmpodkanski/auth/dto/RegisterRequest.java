package io.github.mmpodkanski.auth.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
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