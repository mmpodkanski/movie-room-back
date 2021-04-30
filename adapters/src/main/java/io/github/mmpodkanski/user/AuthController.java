package io.github.mmpodkanski.user;

import io.github.mmpodkanski.user.dto.JwtResponse;
import io.github.mmpodkanski.user.dto.LoginRequest;
import io.github.mmpodkanski.user.dto.RegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600)
class AuthController {
    private final AuthService service;

    AuthController(final AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse response = service.login(loginRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register")
    ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {
        service.signup(signUpRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
