package io.github.mmpodkanski.user.dto;

public class JwtResponse {
    private final int id;
    private final String token;
    private final String type = "Bearer";
    private final String username;
    private final String email;
    private final String role;

    public JwtResponse(
            final int id,
            final String accessToken,
            final String username,
            final String email,
            final String role
    ) {
        this.id = id;
        this.token = accessToken;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getType() {
        return type;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
