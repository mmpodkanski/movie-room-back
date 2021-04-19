package io.github.mmpodkanski.auth.dto;

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
