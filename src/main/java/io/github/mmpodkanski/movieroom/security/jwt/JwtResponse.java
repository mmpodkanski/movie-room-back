package io.github.mmpodkanski.movieroom.security.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
    private int id;
    private String token;
    private String type = "Bearer";
    private String username;
    private String email;
    private String role;

    public JwtResponse(
            int id,
            String accessToken,
            String username,
            String email,
            String role
    ) {
        this.id = id;
        this.token = accessToken;
        this.username = username;
        this.email = email;
        this.role = role;
    }

}
