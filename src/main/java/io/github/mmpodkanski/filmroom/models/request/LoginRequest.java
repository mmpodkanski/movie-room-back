package io.github.mmpodkanski.filmroom.models.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class LoginRequest {
    @NotBlank
    private final String username;

    @NotBlank
    private final String password;

}