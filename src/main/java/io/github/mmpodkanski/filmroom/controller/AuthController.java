package io.github.mmpodkanski.filmroom.controller;

import io.github.mmpodkanski.filmroom.models.request.LoginRequest;
import io.github.mmpodkanski.filmroom.models.request.RegisterRequest;
import io.github.mmpodkanski.filmroom.models.response.JwtResponse;
import io.github.mmpodkanski.filmroom.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
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
}
