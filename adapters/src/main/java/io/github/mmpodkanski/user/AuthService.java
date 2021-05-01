package io.github.mmpodkanski.user;

import io.github.mmpodkanski.exception.ApiBadRequestException;
import io.github.mmpodkanski.user.dto.JwtResponse;
import io.github.mmpodkanski.user.dto.LoginRequest;
import io.github.mmpodkanski.user.dto.RegisterRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
class AuthService {
    private final UserQueryRepository queryRepository;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;


    AuthService(
            final UserQueryRepository queryRepository,
            final UserRepository userRepository,
            final AuthenticationManager authenticationManager,
            final PasswordEncoder encoder,
            final JwtUtils jwtUtils
    ) {
        this.queryRepository = queryRepository;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    public void signup(RegisterRequest registerRequest) {
        if (queryRepository.existsByUsername(registerRequest.getUsername())) {
            throw new ApiBadRequestException("Username is already taken!");
        }
        if (queryRepository.existsByEmail(registerRequest.getEmail())) {
            throw new ApiBadRequestException("Email is already in use!");
        }

        var user = new User(0,
                registerRequest.getUsername(),
                registerRequest.getEmail(),
                encoder.encode(registerRequest.getPassword()),
                ERole.ROLE_USER,
                false,
                null
        );

        userRepository.save(user);
    }

    public JwtResponse login(LoginRequest loginRequest) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwtAccessToken = jwtUtils.generateJwtToken(auth);

        var user = (User) auth.getPrincipal();
        String role = auth.getAuthorities().toString();


        return new JwtResponse(
                user.getSnapshot().getId(),
                jwtAccessToken,
                user.getSnapshot().getUsername(),
                user.getSnapshot().getEmail(),
//                user.getSnapshot().getRole().toString());
                role);
    }
}
