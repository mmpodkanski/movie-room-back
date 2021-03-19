package io.github.mmpodkanski.auth;

import io.github.mmpodkanski.exception.ApiBadRequestException;
import io.github.mmpodkanski.user.ERole;
import io.github.mmpodkanski.user.User;
import io.github.mmpodkanski.user.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;


    AuthService(
            final UserRepository userRepository,
            final AuthenticationManager authenticationManager,
            final PasswordEncoder encoder,
            final JwtUtils jwtUtils
    ) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    public void signup(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new ApiBadRequestException("Username is already taken!");
        }
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ApiBadRequestException("Email is already in use!");
        }

        var user = new User(registerRequest.getUsername(),
                registerRequest.getEmail(),
                encoder.encode(registerRequest.getPassword()));

        user.setRole(ERole.ROLE_USER);
        userRepository.save(user);
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
}
