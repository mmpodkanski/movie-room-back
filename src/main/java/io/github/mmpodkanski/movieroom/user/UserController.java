package io.github.mmpodkanski.movieroom.user;

import io.github.mmpodkanski.movieroom.security.LoginRequest;
import io.github.mmpodkanski.movieroom.security.RegisterRequest;
import io.github.mmpodkanski.movieroom.security.jwt.JwtResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600)
class UserController {
    private final UserService service;

    UserController(final UserService service) {
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

    @PostMapping("/upgrade/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    ResponseEntity<?> upgradeUser(
            @PathVariable int id
    ) {
        service.setAdminRole(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
