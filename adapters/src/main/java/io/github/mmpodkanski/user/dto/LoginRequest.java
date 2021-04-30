package io.github.mmpodkanski.user.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class LoginRequest {
    @NotBlank
    private final String username;

    @NotBlank
    private final String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}