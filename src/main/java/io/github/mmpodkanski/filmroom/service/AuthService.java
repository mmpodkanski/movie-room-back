package io.github.mmpodkanski.filmroom.service;

import io.github.mmpodkanski.filmroom.models.ERole;
import io.github.mmpodkanski.filmroom.models.User;
import io.github.mmpodkanski.filmroom.models.request.LoginRequest;
import io.github.mmpodkanski.filmroom.models.request.RegisterRequest;
import io.github.mmpodkanski.filmroom.models.response.JwtResponse;
import io.github.mmpodkanski.filmroom.repository.UserRepository;
import io.github.mmpodkanski.filmroom.security.jwt.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    AuthService(
            final AuthenticationManager authenticationManager,
            final UserRepository userRepository,
            final PasswordEncoder encoder,
            final JwtUtils jwtUtils
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;

    }

    public JwtResponse login(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwtAccessToken = jwtUtils.generateJwtToken(authentication);

        User userDetails = (User) authentication.getPrincipal();
        String role = userDetails.getAuthorities().toString();

        return new JwtResponse(
                userDetails.getId(),
                jwtAccessToken,
                userDetails.getUsername(),
                userDetails.getEmail(),
                role);
    }

    // TODO: write exception handlers
    public void signup(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new IllegalStateException("Username is already taken!");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new IllegalStateException("Email is already in use!");
        }

        User user = new User(registerRequest.getUsername(),
                registerRequest.getEmail(),
                encoder.encode(registerRequest.getPassword()));

        user.setRole(ERole.ROLE_USER);
        userRepository.save(user);
    }

    public void addAdminRole(int userId, String key) {
        User userDetails = userRepository
                .findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with that id not found!"));

        if (!key.equals("ADMIN")) {
            throw new IllegalStateException("Incorrect key!");
        }

        userDetails.setRole(ERole.ROLE_ADMIN);
        userRepository.save(userDetails);
    }
}
