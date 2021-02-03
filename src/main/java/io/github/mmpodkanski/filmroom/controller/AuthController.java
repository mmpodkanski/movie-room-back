package io.github.mmpodkanski.filmroom.controller;

import com.fasterxml.jackson.databind.node.TextNode;
import io.github.mmpodkanski.filmroom.models.request.LoginRequest;
import io.github.mmpodkanski.filmroom.models.request.RegisterRequest;
import io.github.mmpodkanski.filmroom.models.response.JwtResponse;
import io.github.mmpodkanski.filmroom.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:8081")
public class AuthController {
    private final AuthService service;

    AuthController(final AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse response = service.login(loginRequest);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {
        service.signup(signUpRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/upgrade/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<?> upgradeUser(
            @PathVariable int id,
            @RequestBody TextNode key
    ) {
        service.addAdminRole(id, key.asText());
        return ResponseEntity.ok().build();
    }
}
